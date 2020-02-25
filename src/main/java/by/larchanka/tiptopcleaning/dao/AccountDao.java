package by.larchanka.tiptopcleaning.dao;

import by.larchanka.tiptopcleaning.entity.User;
import by.larchanka.tiptopcleaning.entity.UserType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountDao {

    /**
     *Adds user to database
     *
     * @param user a {@code User} object to insert
     * @return {@code Optional<User>} object containing user
     */
    Optional<User> addUser(User user) throws DaoException;

    /**
     *Updates user information in database by passing updated user object
     *
     * @param user a {@code User} object to update
     * @return {@code Optional<User>} object containing user
     */
    Optional<User> updateUser(User user) throws DaoException;

    /**
     *Finds user by exact match of email and password in database
     *
     * @param email a {@code String} object to find user with
     * @param password a {@code String} object to find user with
     * @return {@code Optional<User>} object containing user
     */
    Optional<User> authenticateUser(String email, String password) throws DaoException;

    /**
     *Finds user by id in database
     *
     * @param userId a {@code long} value to find user with
     * @return {@code Optional<User>} object containing user
     */
    Optional<User> findUserById(long userId) throws DaoException;

    /**
     *Finds all users that the database contains
     *
     * @return {@code List<User>} object containing users
     */
    List<User> findUsers() throws DaoException;

    /**
     *Changes user type by exact match of id in database
     *
     * @param id a {@code long} value to find user with
     * @param userType a {@code UserType} object to set
     * @return {@code boolean} value. True if changing was successful and false otherwise.
     */
    boolean changeUserType(long id, UserType userType) throws DaoException;

    /**
     *Adds money to user in database
     *
     * @param money a {@code BigDecimal} object to set
     * @param id a {@code long} value to find user with
     * @return {@code boolean} value. True if addition was successful and false otherwise.
     */
    boolean addMoney(BigDecimal money, long id) throws DaoException;

    /**
     *Verifies is email in database already exist
     *
     * @param email a {@code String} object to find user with
     * @return {@code boolean} value. True if exist and false if not.
     */
    boolean isEmailAlreadyRegistered(String email) throws DaoException;
}











