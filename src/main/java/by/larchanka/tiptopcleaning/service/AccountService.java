package by.larchanka.tiptopcleaning.service;

import by.larchanka.tiptopcleaning.entity.User;
import by.larchanka.tiptopcleaning.entity.UserType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountService {

     Optional<User> addUser(User user, String confirmPassword) throws ServiceException;

     Optional<User> authenticateUser(String email, String password) throws ServiceException;

     Optional<User> findUserById(long userId) throws ServiceException;

     List<User> findUsers() throws ServiceException;

     boolean changeUserType(long id, UserType type) throws ServiceException;

     boolean addMoney(BigDecimal money, long id) throws ServiceException;

     Optional<User> updateUser(User updateUser, String confirmationPassword) throws ServiceException;
}
