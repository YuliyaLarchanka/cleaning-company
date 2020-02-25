package by.larchanka.tiptopcleaning.dao;

import by.larchanka.tiptopcleaning.entity.AccountOrder;
import by.larchanka.tiptopcleaning.entity.OrderItem;
import by.larchanka.tiptopcleaning.entity.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface AccountOrderDao {
    /**
     *Sets new account order in database
     *
     * @param accountOrder a {@code accountOrder} object to set
     * @param orderItemList a {@code  List<OrderItem>} object to set into account order
     * @return {@code Optional<AccountOrder>} object containing account order
     */
    Optional<AccountOrder> createAccountOrder(AccountOrder accountOrder, List<OrderItem> orderItemList) throws DaoException;

    /**
     *Finds account orders in database by user id
     *
     * @param userId a {@code long} value to find account orders with
     * @return {@code List<AccountOrder>} object containing account orders
     */
    List<AccountOrder> findAccountOrdersByUserId(long userId) throws DaoException;

    /**
     *Finds all account orders in database
     *
     * @return {@code List<AccountOrder>} object containing account orders
     */
    List<AccountOrder> findAllAccountOrders() throws DaoException;

    /**
     *Changes account order status in database by id
     *
     * @param id a {@code long} value to find account order with
     * @param status a {@code OrderStatus} object to set
     * @return {@code boolean} value. True if changing was successful and false otherwise.
     */
    boolean changeAccountOrderStatus(long id, OrderStatus status) throws DaoException;
}
