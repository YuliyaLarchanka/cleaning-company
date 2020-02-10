package by.larchanka.tiptopcleaning.service.impl;

import by.larchanka.tiptopcleaning.dao.AccountDao;
import by.larchanka.tiptopcleaning.dao.DaoCreator;
import by.larchanka.tiptopcleaning.dao.DaoException;
import by.larchanka.tiptopcleaning.entity.User;
import by.larchanka.tiptopcleaning.entity.UserType;
import by.larchanka.tiptopcleaning.service.AccountService;
import by.larchanka.tiptopcleaning.service.ServiceException;
import by.larchanka.tiptopcleaning.service.ServiceStorage;
import by.larchanka.tiptopcleaning.validation.ServiceValidator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class AccountServiceImpl implements AccountService {
    @Override
    public Optional<User> addUser(User user, String confirmationPassword) throws ServiceException {
        ServiceValidator serviceValidator = ServiceValidator.getInstance();
        boolean isValid = serviceValidator.validateUser(user, confirmationPassword);
        if (!isValid) {
            return Optional.empty();
        }

        DaoCreator creator = DaoCreator.getInstance();
        AccountDao dao = creator.getAccountDAO();

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
        ServiceValidator serviceValidator = ServiceValidator.getInstance();
        DaoCreator creator = DaoCreator.getInstance();
        AccountDao dao = creator.getAccountDAO();

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
        DaoCreator creator = DaoCreator.getInstance();
        AccountDao dao = creator.getAccountDAO();

        try {
            return dao.findUserById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> findUsers() throws ServiceException {
        DaoCreator creator = DaoCreator.getInstance();
        AccountDao dao = creator.getAccountDAO();

        try {
            return dao.findUsers();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean changeUserType(long id, UserType type) throws ServiceException{
        DaoCreator creator = DaoCreator.getInstance();
        AccountDao dao = creator.getAccountDAO();

        try {
            return dao.changeUserType(id, type);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean addMoney(BigDecimal money, long id) throws ServiceException{
        ServiceValidator serviceValidator = ServiceValidator.getInstance();

        DaoCreator creator = DaoCreator.getInstance();
        AccountDao dao = creator.getAccountDAO();


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
        ServiceStorage factory = ServiceStorage.getInstance();
        AccountService accountService = factory.getAccountService();
        Optional<User> optionalOldUser = accountService.findUserById(updatedUser.getId());

        if (optionalOldUser.isEmpty()) {
            return Optional.empty();
        }

        ServiceValidator serviceValidator = ServiceValidator.getInstance();
        boolean isValidationUserSuccess = serviceValidator.validateUser(updatedUser, confirmationPassword);
        if (!isValidationUserSuccess) {
            return Optional.empty();
        }

        try {
            DaoCreator creator = DaoCreator.getInstance();
            AccountDao dao = creator.getAccountDAO();

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
