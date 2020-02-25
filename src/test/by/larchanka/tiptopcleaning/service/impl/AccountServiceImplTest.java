package by.larchanka.tiptopcleaning.service.impl;

import by.larchanka.tiptopcleaning.dao.impl.AccountDaoImpl;
import by.larchanka.tiptopcleaning.entity.User;
import by.larchanka.tiptopcleaning.entity.UserType;
import by.larchanka.tiptopcleaning.validator.ServiceValidator;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AccountServiceImplTest {
    private static final String CONFIRMATION_PASSWORD = "123";
    private static final String email = "lar4enko.yulya@gmail.com";
    private static final String password = "123";
    private User user = new User();

    @Mock
    private AccountDaoImpl accountDao;
    @Mock
    private ServiceValidator serviceValidator;
    private AccountServiceImpl subject;

    @BeforeMethod
    public void setUpMocks() {
        MockitoAnnotations.initMocks(this);
        subject = new AccountServiceImpl(accountDao, serviceValidator);
        user.setId(1);
        user.setType(UserType.CLIENT);
        user.setEmail(email);
        user.setPassword(password);
        user.setMoney(new BigDecimal(10));
    }

    @Test
    public void addUserValidResultTest() throws Exception {
        when(serviceValidator.validateUser(user, CONFIRMATION_PASSWORD)).thenReturn(true);
        when(accountDao.isEmailAlreadyRegistered(user.getEmail())).thenReturn(false);
        when(accountDao.addUser(user)).thenReturn(Optional.of(user));
        //when
        Optional<User> actual = subject.addUser(user, CONFIRMATION_PASSWORD);
        //then
        verify(serviceValidator, times(1)).validateUser(user, CONFIRMATION_PASSWORD);
        verify(accountDao, times(1)).isEmailAlreadyRegistered(user.getEmail());
        verify(accountDao, times(1)).addUser(user);
        Assert.assertEquals(actual, Optional.of(user));
    }

    @Test
    public void addUserInvalidResultTest1() throws Exception {
        when(serviceValidator.validateUser(user, CONFIRMATION_PASSWORD)).thenReturn(false);
        //when
        Optional<User> actual = subject.addUser(user, CONFIRMATION_PASSWORD);
        //then
        verify(serviceValidator, times(1)).validateUser(user, CONFIRMATION_PASSWORD);
        verify(accountDao, never()).isEmailAlreadyRegistered(user.getEmail());
        verify(accountDao, never()).addUser(user);
        Assert.assertEquals(actual, Optional.empty());
    }

    @Test
    public void addUserInvalidResultTest2() throws Exception {
        when(serviceValidator.validateUser(user, CONFIRMATION_PASSWORD)).thenReturn(true);
        when(accountDao.isEmailAlreadyRegistered(user.getEmail())).thenReturn(true);
        //when
        Optional<User> actual = subject.addUser(user, CONFIRMATION_PASSWORD);
        //then
        verify(serviceValidator, times(1)).validateUser(user, CONFIRMATION_PASSWORD);
        verify(accountDao, times(1)).isEmailAlreadyRegistered(user.getEmail());
        verify(accountDao, never()).addUser(user);
        Assert.assertEquals(actual, Optional.empty());
    }

    @Test
    public void addUserInvalidResultTest3() throws Exception {
        when(serviceValidator.validateUser(user, CONFIRMATION_PASSWORD)).thenReturn(true);
        when(accountDao.isEmailAlreadyRegistered(user.getEmail())).thenReturn(false);
        when(accountDao.addUser(user)).thenReturn(Optional.empty());
        //when
        Optional<User> actual = subject.addUser(user, CONFIRMATION_PASSWORD);
        //then
        verify(serviceValidator, times(1)).validateUser(user, CONFIRMATION_PASSWORD);
        verify(accountDao, times(1)).isEmailAlreadyRegistered(user.getEmail());
        verify(accountDao, times(1)).addUser(user);
        Assert.assertEquals(actual, Optional.empty());
    }

    @Test
    public void authenticateUserValidResultTest() throws Exception {
        when(serviceValidator.validateEmailPassword(email, password)).thenReturn(true);
        when(accountDao.authenticateUser(email, password)).thenReturn(Optional.of(user));
        //when
        Optional<User> actual = subject.authenticateUser(email, password);
        //then
        verify(serviceValidator, times(1)).validateEmailPassword(email, password);
        verify(accountDao, times(1)).authenticateUser(email, password);
        Assert.assertEquals(actual, Optional.of(user));
    }

    @Test
    public void authenticateUserInvalidResultTest1() throws Exception {
        when(serviceValidator.validateEmailPassword(email, password)).thenReturn(false);
        //when
        Optional<User> actual = subject.authenticateUser(email, password);
        //then
        verify(serviceValidator, times(1)).validateEmailPassword(email, password);
        verify(accountDao, never()).authenticateUser(email, password);
        Assert.assertEquals(actual, Optional.empty());
    }

    @Test
    public void authenticateUserInvalidResultTest2() throws Exception {
        when(serviceValidator.validateEmailPassword(email, password)).thenReturn(true);
        when(accountDao.authenticateUser(email, password)).thenReturn(Optional.empty());
        //when
        Optional<User> actual = subject.authenticateUser(email, password);
        //then
        verify(serviceValidator, times(1)).validateEmailPassword(email, password);
        verify(accountDao, times(1)).authenticateUser(email, password);
        Assert.assertEquals(actual, Optional.empty());
    }

    @Test
    public void findUserByIdValidResultTest() throws Exception {
        when(accountDao.findUserById(user.getId())).thenReturn(Optional.of(user));
        //when
        Optional<User> actual = subject.findUserById(user.getId());
        //then
        verify(accountDao, times(1)).findUserById(user.getId());
        Assert.assertEquals(actual, Optional.of(user));
    }

    @Test
    public void findUserByIdInvalidResultTest() throws Exception {
        when(accountDao.findUserById(user.getId())).thenReturn(Optional.empty());
        //when
        Optional<User> actual = subject.findUserById(user.getId());
        //then
        verify(accountDao, times(1)).findUserById(user.getId());
        Assert.assertEquals(actual, Optional.empty());
    }

    @Test
    public void findUsersValidResultTest() throws Exception {
        List<User> userList = new ArrayList<>();
        userList.add(user);

        when(accountDao.findUsers()).thenReturn(userList);
        //when
        List<User> actual = subject.findUsers();
        //then
        verify(accountDao, times(1)).findUsers();
        Assert.assertEquals(actual, userList);
    }

    @Test
    public void changeUserTypeValidResultTest() throws Exception {
        when(accountDao.changeUserType(user.getId(), user.getType())).thenReturn(true);
        //when
        boolean actual = subject.changeUserType(user.getId(), user.getType());
        //then
        verify(accountDao, times(1)).changeUserType(user.getId(), user.getType());
        Assert.assertTrue(actual);
    }

    @Test
    public void changeUserTypeInvalidResultTest() throws Exception {
        when(accountDao.changeUserType(user.getId(), user.getType())).thenReturn(false);
        //when
        boolean actual = subject.changeUserType(user.getId(), user.getType());
        //then
        verify(accountDao, times(1)).changeUserType(user.getId(), user.getType());
        Assert.assertFalse(actual);
    }

    @Test
    public void addMoneyValidResultTest() throws Exception {
        when(serviceValidator.validateMoney(user.getMoney())).thenReturn(true);
        when(accountDao.addMoney(user.getMoney(), user.getId())).thenReturn(true);
        //when
        boolean actual = subject.addMoney(user.getMoney(), user.getId());
        //then
        verify(serviceValidator, times(1)).validateMoney(user.getMoney());
        verify(accountDao, times(1)).addMoney(user.getMoney(), user.getId());
        Assert.assertTrue(actual);
    }

    @Test
    public void addMoneyInvalidResultTest1() throws Exception {
        when(serviceValidator.validateMoney(user.getMoney())).thenReturn(false);
        //when
        boolean actual = subject.addMoney(user.getMoney(), user.getId());
        //then
        verify(serviceValidator, times(1)).validateMoney(user.getMoney());
        verify(accountDao, never()).addMoney(user.getMoney(), user.getId());
        Assert.assertFalse(actual);
    }

    @Test
    public void addMoneyValidResultTest2() throws Exception {
        when(serviceValidator.validateMoney(user.getMoney())).thenReturn(true);
        when(accountDao.addMoney(user.getMoney(), user.getId())).thenReturn(false);
        //when
        boolean actual = subject.addMoney(user.getMoney(), user.getId());
        //then
        verify(serviceValidator, times(1)).validateMoney(user.getMoney());
        verify(accountDao, times(1)).addMoney(user.getMoney(), user.getId());
        Assert.assertFalse(actual);
    }

    @Test
    public void updateUserValidResultTest() throws Exception {
        AccountServiceImpl accountService = new AccountServiceImpl(accountDao, serviceValidator);
        accountService = Mockito.spy(accountService);

        doReturn(Optional.of(user)).when(accountService).findUserById(user.getId());
        when(serviceValidator.validateUser(user, CONFIRMATION_PASSWORD)).thenReturn(true);
        when(accountDao.updateUser(user)).thenReturn(Optional.of(user));
        //when
        Optional<User> actual = accountService.updateUser(user, CONFIRMATION_PASSWORD);
        //then
        verify(accountService, times(1)).findUserById(user.getId());
        verify(serviceValidator, times(1)).validateUser(user, CONFIRMATION_PASSWORD);
        verify(accountDao, times(1)).updateUser(user);
        Assert.assertEquals(actual, Optional.of(user));
    }

    @Test
    public void updateUserInvalidResultTest1() throws Exception {
        AccountServiceImpl accountService = new AccountServiceImpl(accountDao, serviceValidator);
        accountService = Mockito.spy(accountService);

        doReturn(Optional.empty()).when(accountService).findUserById(user.getId());
        //when
        Optional<User> actual = accountService.updateUser(user, CONFIRMATION_PASSWORD);
        //then
        verify(accountService, times(1)).findUserById(user.getId());
        verify(serviceValidator, never()).validateUser(user, CONFIRMATION_PASSWORD);
        verify(accountDao, never()).updateUser(user);
        Assert.assertEquals(actual, Optional.empty());
    }

    @Test
    public void updateUserInvalidResultTest2() throws Exception {
        AccountServiceImpl accountService = new AccountServiceImpl(accountDao, serviceValidator);
        accountService = Mockito.spy(accountService);

        doReturn(Optional.of(user)).when(accountService).findUserById(user.getId());
        when(serviceValidator.validateUser(user, CONFIRMATION_PASSWORD)).thenReturn(false);
        //when
        Optional<User> actual = accountService.updateUser(user, CONFIRMATION_PASSWORD);
        //then
        verify(accountService, times(1)).findUserById(user.getId());
        verify(serviceValidator, times(1)).validateUser(user, CONFIRMATION_PASSWORD);
        verify(accountDao, never()).updateUser(user);
        Assert.assertEquals(actual, Optional.empty());
    }

    @Test
    public void updateUserInvalidResultTest3() throws Exception {
        AccountServiceImpl accountService = new AccountServiceImpl(accountDao, serviceValidator);
        accountService = Mockito.spy(accountService);

        doReturn(Optional.of(user)).when(accountService).findUserById(user.getId());
        when(serviceValidator.validateUser(user, CONFIRMATION_PASSWORD)).thenReturn(true);
        when(accountDao.updateUser(user)).thenReturn(Optional.empty());
        //when
        Optional<User> actual = accountService.updateUser(user, CONFIRMATION_PASSWORD);
        //then
        verify(accountService, times(1)).findUserById(user.getId());
        verify(serviceValidator, times(1)).validateUser(user, CONFIRMATION_PASSWORD);
        verify(accountDao, times(1)).updateUser(user);
        Assert.assertEquals(actual, Optional.empty());
    }
}
