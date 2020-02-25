package by.larchanka.tiptopcleaning.service;

import by.larchanka.tiptopcleaning.entity.User;
import by.larchanka.tiptopcleaning.entity.UserType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountService {
     /**
      *Validates user and calls dao method
      *
      * @param user a {@code User} object to validate and pass into dao method
      * @param confirmPassword a {@code String} object to pass into validator method
      * @return {@code Optional<User>} object containing user
      */
     Optional<User> addUser(User user, String confirmPassword) throws ServiceException;

     /**
      *Validates account order and calls dao method
      *
      * @param email a {@code String} object to authenticate user with
      * @param password a {@code String} object to authenticate user with
      * @return {@code Optional<User>} object containing user
      */
     Optional<User> authenticateUser(String email, String password) throws ServiceException;

     /**
      *Calls dao method
      *
      * @param userId a {@code long} value to pass into dao method
      * @return {@code Optional<User>} object containing user
      */
     Optional<User> findUserById(long userId) throws ServiceException;

     /**
      *Calls dao method
      *
      * @return {@code List<User>} object containing users
      */
     List<User> findUsers() throws ServiceException;

     /**
      *Calls dao method
      *
      * @param id a {@code long} value to pass into dao method
      * @param type a {@code UserType} object to pass into dao method
      * @return {@code boolean} value. True if changing was successful and false otherwise.
      */
     boolean changeUserType(long id, UserType type) throws ServiceException;

     /**
      *Validates money value and call dao method
      *
      * @param money a {@code BigDecimal} object to pass into dao method
      * @param id a {@code long} value to pass into dao method
      * @return {@code boolean} value. True if addition was successful and false otherwise.
      */
     boolean addMoney(BigDecimal money, long id) throws ServiceException;

     /**
      *Validates updated user and call dao method
      *
      * @param updateUser a {@code User} object to validate
      * @param confirmationPassword a {@code String} object to pass into validator method
      * @return {@code Optional<User>} object containing user
      */
     Optional<User> updateUser(User updateUser, String confirmationPassword) throws ServiceException;
}
