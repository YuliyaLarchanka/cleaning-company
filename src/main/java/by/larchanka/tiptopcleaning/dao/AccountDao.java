package by.larchanka.tiptopcleaning.dao;

import by.larchanka.tiptopcleaning.entity.User;
import by.larchanka.tiptopcleaning.entity.UserType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountDao {

    Optional<User> addUser(User user) throws DaoException;

    Optional<User> updateUser(User user) throws DaoException;

    Optional<User> authenticateUser(String email, String password) throws DaoException;

    Optional<User> findUserById(long userId) throws DaoException;

    List<User> findUsers() throws DaoException;

    boolean changeUserType(long id, UserType userType) throws DaoException;

    boolean addMoney(BigDecimal money, long id) throws DaoException;

    boolean isEmailAlreadyRegistered(String email) throws DaoException;
}











