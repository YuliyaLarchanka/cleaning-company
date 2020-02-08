package by.larchanka.tiptopcleaning.dao.impl;

import by.larchanka.tiptopcleaning.connection.ConnectionPool;
import by.larchanka.tiptopcleaning.connection.ConnectionPoolException;
import by.larchanka.tiptopcleaning.dao.DaoException;
import by.larchanka.tiptopcleaning.dao.PromoCodeDao;
import by.larchanka.tiptopcleaning.entity.PromoCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.larchanka.tiptopcleaning.dao.SqlParameterConstant.SQL_PROMO_CODE;
import static by.larchanka.tiptopcleaning.dao.SqlParameterConstant.SQL_PROMO_CODE_DISCOUNT;
import static by.larchanka.tiptopcleaning.dao.SqlParameterConstant.SQL_PROMO_CODE_ID;
import static by.larchanka.tiptopcleaning.dao.SqlParameterConstant.SQL_PROMO_CODE_VALUE;

public class PromoCodeDaoImpl implements PromoCodeDao {
    private static final Logger logger = LogManager.getLogger();

    private static final String GET_PROMO_CODE_BY_NAME_COMMAND =
            "SELECT " +
                    SQL_PROMO_CODE_ID + ", " +
                    SQL_PROMO_CODE_DISCOUNT +
                    " FROM promo_code" +
                    " WHERE " +
                    SQL_PROMO_CODE_VALUE + " = ?";

    private static final String GET_PROMO_CODE_LIST =
            "SELECT " +
                    SQL_PROMO_CODE_ID + ", " +
                    SQL_PROMO_CODE_VALUE + ", " +
                    SQL_PROMO_CODE_DISCOUNT +
                    " FROM promo_code";

    private static final String DELETE_PROMO_CODE_BY_ID_COMMAND =
            "DELETE FROM " +
                    SQL_PROMO_CODE + " WHERE " +
                    SQL_PROMO_CODE_ID + " = ?";

    private static final String ADD_PROMO_CODE =
            "INSERT INTO " + SQL_PROMO_CODE + " (promo_code_value, discount_percentage) VALUES (?, ?)";

    private static final String FIND_PROMO_CODE_BY_VALUE = "SELECT * FROM promo_code WHERE promo_code_value = ?";


    @Override
    public Optional<PromoCode> findPromoCodeByName(String name) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_PROMO_CODE_BY_NAME_COMMAND)) {

            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                long id = resultSet.getLong(SQL_PROMO_CODE_ID);
                byte discount = resultSet.getByte(SQL_PROMO_CODE_DISCOUNT);

                PromoCode promoCode = new PromoCode();
                promoCode.setId(id);
                promoCode.setValue(name);
                promoCode.setDiscountPercentage(discount);
                return Optional.of(promoCode);
            } else {
                return Optional.empty();
            }
        } catch (ConnectionPoolException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<PromoCode> findAllPromoCodes() throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_PROMO_CODE_LIST)) {

            ResultSet resultSet = statement.executeQuery();
            List<PromoCode> promoCodeList = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong(SQL_PROMO_CODE_ID);
                String value = resultSet.getString(SQL_PROMO_CODE_VALUE);
                byte discount = Byte.parseByte(resultSet.getString(SQL_PROMO_CODE_DISCOUNT));

                PromoCode promoCode = new PromoCode();
                promoCode.setId(id);
                promoCode.setValue(value);
                promoCode.setDiscountPercentage(discount);
                promoCodeList.add(promoCode);
            }
            return promoCodeList;
        } catch (ConnectionPoolException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean deletePromoCodeById(long id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PROMO_CODE_BY_ID_COMMAND)) {

            statement.setLong(1, id);
            int resultInt = statement.executeUpdate();
            return resultInt > 0;
        } catch (ConnectionPoolException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }


    public Optional<PromoCode> addPromoCode(PromoCode promoCode) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_PROMO_CODE, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, promoCode.getValue());
            statement.setByte(2, promoCode.getDiscountPercentage());
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                long id = resultSet.getInt(1);
                promoCode.setId(id);
                return Optional.of(promoCode);
            } else {
                return Optional.empty();
            }
        } catch (ConnectionPoolException | SQLException e) {
            logger.error(e);
            throw new DaoException();
        }
    }

    @Override
    public boolean isPromoCodeAlreadyExist(String value) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_PROMO_CODE_BY_VALUE)) {

            statement.setString(1, value);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (ConnectionPoolException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

}
