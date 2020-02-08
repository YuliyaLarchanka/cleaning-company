package by.larchanka.tiptopcleaning.dao.impl;

import by.larchanka.tiptopcleaning.connection.ConnectionPool;
import by.larchanka.tiptopcleaning.connection.ConnectionPoolException;
import by.larchanka.tiptopcleaning.dao.AccountOrderDao;
import by.larchanka.tiptopcleaning.dao.DaoException;
import by.larchanka.tiptopcleaning.entity.AccountOrder;
import by.larchanka.tiptopcleaning.entity.CatalogItem;
import by.larchanka.tiptopcleaning.entity.OrderItem;
import by.larchanka.tiptopcleaning.entity.OrderStatus;
import by.larchanka.tiptopcleaning.entity.PaymentMethod;
import by.larchanka.tiptopcleaning.entity.PromoCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.larchanka.tiptopcleaning.dao.SqlParameterConstant.SQL_ACCOUNT_ORDER;
import static by.larchanka.tiptopcleaning.dao.SqlParameterConstant.SQL_ACCOUNT_ORDER_DATE_TIME;
import static by.larchanka.tiptopcleaning.dao.SqlParameterConstant.SQL_ACCOUNT_ORDER_ID;
import static by.larchanka.tiptopcleaning.dao.SqlParameterConstant.SQL_ACCOUNT_ORDER_PAYMENT_METHOD;
import static by.larchanka.tiptopcleaning.dao.SqlParameterConstant.SQL_ACCOUNT_ORDER_STATUS;
import static by.larchanka.tiptopcleaning.dao.SqlParameterConstant.SQL_ACCOUNT_ORDER_TOTAL_COST;
import static by.larchanka.tiptopcleaning.dao.SqlParameterConstant.SQL_AO;
import static by.larchanka.tiptopcleaning.dao.SqlParameterConstant.SQL_CATALOG_ITEM;
import static by.larchanka.tiptopcleaning.dao.SqlParameterConstant.SQL_CATALOG_ITEM_ID;
import static by.larchanka.tiptopcleaning.dao.SqlParameterConstant.SQL_CATALOG_ITEM_NAME;
import static by.larchanka.tiptopcleaning.dao.SqlParameterConstant.SQL_CI;
import static by.larchanka.tiptopcleaning.dao.SqlParameterConstant.SQL_OI;
import static by.larchanka.tiptopcleaning.dao.SqlParameterConstant.SQL_ORDER_ITEM;
import static by.larchanka.tiptopcleaning.dao.SqlParameterConstant.SQL_ORDER_ITEM_AMOUNT;
import static by.larchanka.tiptopcleaning.dao.SqlParameterConstant.SQL_ACCOUNT_ID;

public class AccountOrderDaoImpl implements AccountOrderDao {
    private static final Logger logger = LogManager.getLogger();

    private final static String INSERT_ORDER_COMMAND = "INSERT INTO account_order"
            + " (account_id, promo_code_id, total_cost, payment_method, account_order_status, date_time)" +
            " VALUES (?, ?, ?, ?, ?, ?)";

    private final static String INSERT_ORDER_ITEM_COMMAND = "INSERT INTO order_item"
            + " (catalog_item_id, account_order_id, amount) VALUES (?, ?, ?)";

    private final static String SUBTRACT_USER_MONEY_COMMAND = "UPDATE account SET money = money - ?" +
            " WHERE account_id = ?";

    private final static String GET_ACCOUNT_ORDERS_BY_USER_ID_COMMAND =
            "SELECT " +
                    SQL_AO + "." + SQL_ACCOUNT_ORDER_ID + ", " +
                    SQL_AO + "." + SQL_ACCOUNT_ORDER_DATE_TIME + ", " +
                    SQL_AO + "." + SQL_ACCOUNT_ORDER_TOTAL_COST + ", " +
                    SQL_AO + "." + SQL_ACCOUNT_ORDER_PAYMENT_METHOD + ", " +
                    SQL_AO + "." + SQL_ACCOUNT_ORDER_STATUS + ", " +
                    SQL_OI + "." + SQL_ORDER_ITEM_AMOUNT + ", " +
                    SQL_CI + "." + SQL_CATALOG_ITEM_ID + ", " +
                    SQL_CI + "." + SQL_CATALOG_ITEM_NAME +
                    " FROM " + SQL_ACCOUNT_ORDER + " " + SQL_AO +
                    " JOIN " + SQL_ORDER_ITEM + " " +
                    SQL_OI + " ON " + SQL_OI + "." + SQL_ACCOUNT_ORDER_ID + " = " +
                    SQL_AO + "." + SQL_ACCOUNT_ORDER_ID +
                    " JOIN " + SQL_CATALOG_ITEM + " " +
                    SQL_CI + " ON " + SQL_OI + "." + SQL_CATALOG_ITEM_ID + " = " +
                    SQL_CI + "." + SQL_CATALOG_ITEM_ID +
                    " WHERE " + SQL_AO + "." + SQL_ACCOUNT_ID + " = ?";

    private final static String GET_ALL_ACCOUNT_ORDERS =
            "SELECT " +
                    SQL_AO + "." + SQL_ACCOUNT_ORDER_ID + ", " +
                    SQL_AO + "." + SQL_ACCOUNT_ORDER_DATE_TIME + ", " +
                    SQL_AO + "." + SQL_ACCOUNT_ORDER_TOTAL_COST + ", " +
                    SQL_AO + "." + SQL_ACCOUNT_ORDER_PAYMENT_METHOD + ", " +
                    SQL_AO + "." + SQL_ACCOUNT_ORDER_STATUS + ", " +
                    SQL_OI + "." + SQL_ORDER_ITEM_AMOUNT + ", " +
                    SQL_CI + "." + SQL_CATALOG_ITEM_ID + ", " +
                    SQL_CI + "." + SQL_CATALOG_ITEM_NAME +
                    " FROM " + SQL_ACCOUNT_ORDER + " " + SQL_AO +
                    " JOIN " + SQL_ORDER_ITEM + " " +
                    SQL_OI + " ON " + SQL_OI + "." + SQL_ACCOUNT_ORDER_ID + " = " +
                    SQL_AO + "." + SQL_ACCOUNT_ORDER_ID +
                    " JOIN " + SQL_CATALOG_ITEM + " " +
                    SQL_CI + " ON " + SQL_OI + "." + SQL_CATALOG_ITEM_ID + " = " +
                    SQL_CI + "." + SQL_CATALOG_ITEM_ID;

    private final static String CHANGE_ACCOUNT_ORDER_STATUS_COMMAND = "UPDATE account_order SET account_order_status = ?" +
            " WHERE account_order_id = ?";

    @Override
    public Optional<AccountOrder> createAccountOrder(AccountOrder accountOrder, List<OrderItem> orderItemList) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_ORDER_COMMAND, Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);

            statement.setLong(1, accountOrder.getUser().getId());
            PromoCode promoCode = accountOrder.getPromoCode();
            if (promoCode != null) {
                statement.setLong(2, promoCode.getId());
            } else {
                statement.setNull(2, Types.INTEGER);
            }
            statement.setBigDecimal(3, accountOrder.getTotalCost());
            statement.setInt(4, accountOrder.getPaymentMethod().ordinal());
            statement.setInt(5, accountOrder.getOrderStatus().ordinal());
            statement.setTimestamp(6, accountOrder.getDateTime());
            statement.executeUpdate();

            long orderId;
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                orderId = resultSet.getInt(1);
                accountOrder.setId(orderId);
            } else {
                throw new DaoException();
            }

            for (OrderItem orderItem : orderItemList) {
                try (PreparedStatement insertItemStatement = connection.prepareStatement(INSERT_ORDER_ITEM_COMMAND)) {
                    insertItemStatement.setLong(1, orderItem.getCatalogItem().getId());
                    insertItemStatement.setLong(2, orderId);
                    insertItemStatement.setByte(3, orderItem.getAmount());
                    insertItemStatement.executeUpdate();
                }
            }

            if (PaymentMethod.ACCOUNT_BALANCE.equals(accountOrder.getPaymentMethod())) {
                try (PreparedStatement reduceMoneyStatement = connection.prepareStatement(SUBTRACT_USER_MONEY_COMMAND)) {
                    reduceMoneyStatement.setBigDecimal(1, accountOrder.getTotalCost());
                    reduceMoneyStatement.setLong(2, accountOrder.getUser().getId());
                    reduceMoneyStatement.executeUpdate();
                }
            }

            connection.commit();
            connection.setAutoCommit(true);

            return Optional.of(accountOrder);
        } catch (ConnectionPoolException | SQLException | DaoException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<AccountOrder> findAccountOrdersByUserId(long userId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ACCOUNT_ORDERS_BY_USER_ID_COMMAND)) {
            statement.setLong(1, userId);

            Map<Long, AccountOrder> accountOrderMap = new HashMap<>();
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long accountOrderId = resultSet.getLong(SQL_ACCOUNT_ORDER_ID);
                AccountOrder accountOrder = accountOrderMap.get(accountOrderId);
                if (accountOrder == null) {
                    BigDecimal totalCost = resultSet.getBigDecimal(SQL_ACCOUNT_ORDER_TOTAL_COST);
                    PaymentMethod paymentMethod = PaymentMethod.values()[(resultSet.getByte(SQL_ACCOUNT_ORDER_PAYMENT_METHOD))];
                    OrderStatus orderOrderStatus = OrderStatus.values()[(resultSet.getByte(SQL_ACCOUNT_ORDER_STATUS))];
                    Timestamp dateTime = resultSet.getTimestamp(SQL_ACCOUNT_ORDER_DATE_TIME);

                    accountOrder = new AccountOrder();
                    accountOrder.setOrderItemList(new ArrayList<>());
                    accountOrder.setId(accountOrderId);
                    accountOrder.setTotalCost(totalCost);
                    accountOrder.setPaymentMethod(paymentMethod);
                    accountOrder.setOrderStatus(orderOrderStatus);
                    accountOrder.setDateTime(dateTime);

                    accountOrderMap.put(accountOrderId, accountOrder);
                }

                long catalogItemId = resultSet.getLong(SQL_CATALOG_ITEM_ID);
                String catalogItemName = resultSet.getString(SQL_CATALOG_ITEM_NAME);

                CatalogItem catalogItem = new CatalogItem();
                catalogItem.setId(catalogItemId);
                catalogItem.setName(catalogItemName);

                byte amount = resultSet.getByte(SQL_ORDER_ITEM_AMOUNT);

                OrderItem orderItem = new OrderItem();
                orderItem.setCatalogItem(catalogItem);
                orderItem.setAccountOrder(accountOrder);
                orderItem.setAmount(amount);

                accountOrder.getOrderItemList().add(orderItem);
            }
            return new ArrayList<>(accountOrderMap.values());
        } catch (ConnectionPoolException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<AccountOrder> findAllAccountOrders() throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_ACCOUNT_ORDERS)) {

            Map<Long, AccountOrder> accountOrderMap = new HashMap<>();
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long accountOrderId = resultSet.getLong(SQL_ACCOUNT_ORDER_ID);
                AccountOrder accountOrder = accountOrderMap.get(accountOrderId);
                if (accountOrder == null) {
                    BigDecimal totalCost = resultSet.getBigDecimal(SQL_ACCOUNT_ORDER_TOTAL_COST);
                    PaymentMethod paymentMethod = PaymentMethod.values()[(resultSet.getByte(SQL_ACCOUNT_ORDER_PAYMENT_METHOD))];
                    OrderStatus orderOrderStatus = OrderStatus.values()[(resultSet.getByte(SQL_ACCOUNT_ORDER_STATUS))];
                    Timestamp dateTime = resultSet.getTimestamp(SQL_ACCOUNT_ORDER_DATE_TIME);

                    accountOrder = new AccountOrder();
                    accountOrder.setOrderItemList(new ArrayList<>());
                    accountOrder.setId(accountOrderId);
                    accountOrder.setTotalCost(totalCost);
                    accountOrder.setPaymentMethod(paymentMethod);
                    accountOrder.setOrderStatus(orderOrderStatus);
                    accountOrder.setDateTime(dateTime);

                    accountOrderMap.put(accountOrderId, accountOrder);
                }

                long catalogItemId = resultSet.getLong(SQL_CATALOG_ITEM_ID);
                String catalogItemName = resultSet.getString(SQL_CATALOG_ITEM_NAME);

                CatalogItem catalogItem = new CatalogItem();
                catalogItem.setId(catalogItemId);
                catalogItem.setName(catalogItemName);

                byte amount = resultSet.getByte(SQL_ORDER_ITEM_AMOUNT);

                OrderItem orderItem = new OrderItem();
                orderItem.setCatalogItem(catalogItem);
                orderItem.setAccountOrder(accountOrder);
                orderItem.setAmount(amount);

                accountOrder.getOrderItemList().add(orderItem);
            }
            return new ArrayList<>(accountOrderMap.values());
        } catch (ConnectionPoolException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    public boolean changeAccountOrderStatus(long id, OrderStatus status) throws DaoException{
        try (Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(CHANGE_ACCOUNT_ORDER_STATUS_COMMAND)) {
            statement.setInt(1, status.ordinal());
            statement.setLong(2, id);
            int resultInt = statement.executeUpdate();
            return resultInt > 0;
        } catch (ConnectionPoolException | SQLException e) {
            logger.error(e);
            throw new DaoException();
        }
    }
}