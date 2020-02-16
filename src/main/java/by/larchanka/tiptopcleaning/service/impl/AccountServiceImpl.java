package by.larchanka.tiptopcleaning.service.impl;

import by.larchanka.tiptopcleaning.dao.AccountDao;
import by.larchanka.tiptopcleaning.dao.DaoException;
import by.larchanka.tiptopcleaning.entity.User;
import by.larchanka.tiptopcleaning.entity.UserType;
import by.larchanka.tiptopcleaning.service.AccountService;
import by.larchanka.tiptopcleaning.service.ServiceException;
import by.larchanka.tiptopcleaning.validator.ServiceValidator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class AccountServiceImpl implements AccountService {
    private AccountDao dao;
    private ServiceValidator serviceValidator;

    public AccountServiceImpl(AccountDao dao, ServiceValidator serviceValidator) {
        this.dao = dao;
        this.serviceValidator = serviceValidator;
    }

    @Override
    public Optional<User> addUser(User user, String confirmationPassword) throws ServiceException {
        boolean isValid = serviceValidator.validateUser(user, confirmationPassword);
        if (!isValid) {
            return Optional.empty();
        }

        try {
            if (dao.isEmailAlreadyRegistered(user.getEmail())) {
                return Optional.empty();
            }

            return dao.addUser(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<User> authenticateUser(String email, String password) throws ServiceException {
        try {
            boolean isValid = serviceValidator.validateEmailPassword(email, password);
            if (!isValid) {
                return Optional.empty();
            }

            return dao.authenticateUser(email, password);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<User> findUserById(long id) throws ServiceException {
        try {
            return dao.findUserById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> findUsers() throws ServiceException {
        try {
            return dao.findUsers();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean changeUserType(long id, UserType type) throws ServiceException{
        try {
            return dao.changeUserType(id, type);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean addMoney(BigDecimal money, long id) throws ServiceException{
        if(!serviceValidator.validateMoney(money)){
            return false;
        }

        try {
            return dao.addMoney(money, id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<User> updateUser(User updatedUser, String confirmationPassword) throws ServiceException {
        Optional<User> optionalOldUser = findUserById(updatedUser.getId());

        if (optionalOldUser.isEmpty()) {
            return Optional.empty();
        }

        boolean isValidationUserSuccess = serviceValidator.validateUser(updatedUser, confirmationPassword);
        if (!isValidationUserSuccess) {
            return Optional.empty();
        }

        try {
            User oldUser = optionalOldUser.get();

            if (!updatedUser.getEmail().equalsIgnoreCase(oldUser.getEmail())
                    && dao.isEmailAlreadyRegistered(updatedUser.getEmail())) {
                return Optional.empty();
            }
            return dao.updateUser(updatedUser);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
