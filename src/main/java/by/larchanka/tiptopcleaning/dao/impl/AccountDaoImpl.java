package by.larchanka.tiptopcleaning.dao.impl;

import by.larchanka.tiptopcleaning.connection.ConnectionPool;
import by.larchanka.tiptopcleaning.connection.ConnectionPoolException;
import by.larchanka.tiptopcleaning.dao.AccountDao;
import by.larchanka.tiptopcleaning.dao.DaoException;
import by.larchanka.tiptopcleaning.entity.User;
import by.larchanka.tiptopcleaning.entity.UserType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.larchanka.tiptopcleaning.dao.SqlParameterConstant.SQL_ACCOUNT;
import static by.larchanka.tiptopcleaning.dao.SqlParameterConstant.SQL_ACCOUNT_APARTMENT;
import static by.larchanka.tiptopcleaning.dao.SqlParameterConstant.SQL_ACCOUNT_EMAIL;
import static by.larchanka.tiptopcleaning.dao.SqlParameterConstant.SQL_ACCOUNT_ENTRANCE;
import static by.larchanka.tiptopcleaning.dao.SqlParameterConstant.SQL_ACCOUNT_FIRST_NAME;
import static by.larchanka.tiptopcleaning.dao.SqlParameterConstant.SQL_ACCOUNT_FLOOR;
import static by.larchanka.tiptopcleaning.dao.SqlParameterConstant.SQL_ACCOUNT_HOUSE;
import static by.larchanka.tiptopcleaning.dao.SqlParameterConstant.SQL_ACCOUNT_ID;
import static by.larchanka.tiptopcleaning.dao.SqlParameterConstant.SQL_ACCOUNT_INTERCOM_CODE;
import static by.larchanka.tiptopcleaning.dao.SqlParameterConstant.SQL_ACCOUNT_LAST_NAME;
import static by.larchanka.tiptopcleaning.dao.SqlParameterConstant.SQL_ACCOUNT_MONEY;
import static by.larchanka.tiptopcleaning.dao.SqlParameterConstant.SQL_ACCOUNT_PASSWORD;
import static by.larchanka.tiptopcleaning.dao.SqlParameterConstant.SQL_ACCOUNT_PHONE_NUMBER;
import static by.larchanka.tiptopcleaning.dao.SqlParameterConstant.SQL_ACCOUNT_STREET;
import static by.larchanka.tiptopcleaning.dao.SqlParameterConstant.SQL_ACCOUNT_TYPE;

public class AccountDaoImpl implements AccountDao {
    private static final Logger logger = LogManager.getLogger();
    private final static String INSERT_USER_COMMAND = "INSERT INTO account"
            + " (account_type, first_name, last_name, email, account_password, money)" +
            " VALUES (?, ?, ?, ?, ?, ?)";

    private final static String UPDATE_USER_COMMAND = "UPDATE account SET first_name = ?," +
            " last_name = ?, email = ?, account_password = ?, phone_number = ?," +
            " street = ?, house = ?, apartment = ?, entrance = ?, floor = ?, intercom_code = ?" +
            " WHERE account_id = ?";

    private final static String CHANGE_USER_TYPE_COMMAND = "UPDATE account SET account_type = ?" +
            " WHERE account_id = ?";

    private final static String ADD_MONEY_COMMAND = "UPDATE account SET money = money + ?" +
            " WHERE account_id = ?";


    private static final String GET_USER_COMMAND =
            "SELECT " +
                    SQL_ACCOUNT_ID + ", " +
                    SQL_ACCOUNT_TYPE + ", " +
                    SQL_ACCOUNT_FIRST_NAME + ", " +
                    SQL_ACCOUNT_LAST_NAME + ", " +
                    SQL_ACCOUNT_EMAIL + ", " +
                    SQL_ACCOUNT_PASSWORD + ", " +
                    SQL_ACCOUNT_MONEY +
                    " FROM account " +
                    "WHERE " +
                    SQL_ACCOUNT_EMAIL + " = ? and " + SQL_ACCOUNT_PASSWORD + " = ?";

    private static final String GET_USER_BY_ID_COMMAND = "SELECT * FROM account WHERE account_id = ?";

    private static final String FIND_USER_BY_EMAIL_SQL = "SELECT * FROM account WHERE email = ?";

    private static final String GET_ACCOUNT_LIST =
            "SELECT " +
                    SQL_ACCOUNT_ID + ", " +
                    SQL_ACCOUNT_TYPE + ", " +
                    SQL_ACCOUNT_FIRST_NAME + ", " +
                    SQL_ACCOUNT_LAST_NAME + ", " +
                    SQL_ACCOUNT_EMAIL +
                    " FROM " + SQL_ACCOUNT;

    @Override
    public Optional<User> addUser(User user) throws DaoException {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_USER_COMMAND, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, user.getType().ordinal());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPassword());
            statement.setBigDecimal(6, user.getMoney());
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                long id = resultSet.getInt(1);
                user.setId(id);
                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        } catch (ConnectionPoolException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<User> updateUser(User updatedUser) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_COMMAND)) {

            int index = 1;
            statement.setString(index++, updatedUser.getFirstName());
            statement.setString(index++, updatedUser.getLastName());
            statement.setString(index++, updatedUser.getEmail());
            statement.setString(index++, updatedUser.getPassword());
            statement.setString(index++, updatedUser.getPhoneNumber());
            statement.setString(index++, updatedUser.getStreet());
            statement.setString(index++, updatedUser.getHouse());
            statement.setString(index++, updatedUser.getApartment());
            statement.setString(index++, updatedUser.getEntrance());
            statement.setString(index++, updatedUser.getFloor());
            statement.setString(index++, updatedUser.getIntercomCode());
            statement.setLong(index, updatedUser.getId());
            statement.executeUpdate();

            return Optional.of(updatedUser);

        } catch (ConnectionPoolException | SQLException e) {
            logger.error(e);
            throw new DaoException();
        }
    }

    @Override
    public Optional<User> authenticateUser(String email, String password) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_USER_COMMAND)) {
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                long id = resultSet.getInt(SQL_ACCOUNT_ID);
                UserType userType = UserType.values()[(resultSet.getInt(SQL_ACCOUNT_TYPE))];
                String firstName = resultSet.getString(SQL_ACCOUNT_FIRST_NAME);
                String lastName = resultSet.getString(SQL_ACCOUNT_LAST_NAME);

                User user = new User(userType, email, password, firstName, lastName);
                user.setId(id);
                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        } catch (ConnectionPoolException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<User> findUserById(long userId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_USER_BY_ID_COMMAND)) {
            statement.setLong(1, userId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                UserType userType = UserType.values()[(resultSet.getInt(SQL_ACCOUNT_TYPE))];
                String email = resultSet.getString(SQL_ACCOUNT_EMAIL);
                String firstName = resultSet.getString(SQL_ACCOUNT_FIRST_NAME);
                String lastName = resultSet.getString(SQL_ACCOUNT_LAST_NAME);
                BigDecimal money = resultSet.getBigDecimal(SQL_ACCOUNT_MONEY);

                String phoneNumber = resultSet.getString(SQL_ACCOUNT_PHONE_NUMBER);
                String street = resultSet.getString(SQL_ACCOUNT_STREET);
                String house = resultSet.getString(SQL_ACCOUNT_HOUSE);
                String apartment = resultSet.getString(SQL_ACCOUNT_APARTMENT);
                String entrance = resultSet.getString(SQL_ACCOUNT_ENTRANCE);
                String floor = resultSet.getString(SQL_ACCOUNT_FLOOR);
                String intercomCode = resultSet.getString(SQL_ACCOUNT_INTERCOM_CODE);

                User user = new User();
                user.setType(userType);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                user.setPhoneNumber(phoneNumber);
                user.setMoney(money);
                user.setHouse(house);
                user.setStreet(street);
                user.setApartment(apartment);
                user.setEntrance(entrance);
                user.setFloor(floor);
                user.setIntercomCode(intercomCode);
                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        } catch (ConnectionPoolException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<User> findUsers() throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ACCOUNT_LIST)) {

            ResultSet resultSet = statement.executeQuery();
            return parseResultSet(resultSet);
        } catch (ConnectionPoolException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    public boolean changeUserType(long id, UserType userType) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CHANGE_USER_TYPE_COMMAND)) {
            statement.setInt(1, userType.ordinal());
            statement.setLong(2, id);
            int resultInt = statement.executeUpdate();
            return resultInt > 0;
        } catch (ConnectionPoolException | SQLException e) {
            logger.error(e);
            throw new DaoException();
        }
    }

    public boolean addMoney(BigDecimal money, long id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_MONEY_COMMAND)) {
            statement.setBigDecimal(1, money);
            statement.setLong(2, id);
            int resultInt = statement.executeUpdate();
            return resultInt > 0;
        } catch (ConnectionPoolException | SQLException e) {
            logger.error(e);
            throw new DaoException();
        }
    }


    private List<User> parseResultSet(ResultSet resultSet) throws SQLException {
        List<User> users = new ArrayList<>();

        while (resultSet.next()) {
            long id = resultSet.getLong(SQL_ACCOUNT_ID);
            UserType type = UserType.values()[(resultSet.getByte(SQL_ACCOUNT_TYPE))];
            String firstName = resultSet.getString(SQL_ACCOUNT_FIRST_NAME);
            String lastName = resultSet.getString(SQL_ACCOUNT_LAST_NAME);
            String email = resultSet.getString(SQL_ACCOUNT_EMAIL);

            User user = new User();
            user.setId(id);
            user.setType(type);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            users.add(user);
        }
        return users;
    }

    @Override
    public boolean isEmailAlreadyRegistered(String email) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_EMAIL_SQL)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();

        } catch (ConnectionPoolException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }
}