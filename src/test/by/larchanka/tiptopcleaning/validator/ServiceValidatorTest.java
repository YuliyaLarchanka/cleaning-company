package by.larchanka.tiptopcleaning.validator;

import by.larchanka.tiptopcleaning.entity.User;
import by.larchanka.tiptopcleaning.entity.UserType;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class ServiceValidatorTest {
    private static final String VALID_CONFIRMATION_PASSWORD = "123";
    private static final String INVALID_CONFIRMATION_PASSWORD = "456";
    private ServiceValidator subject;
    private User user;

    @BeforeMethod
    public void setUp() {
        subject = ServiceValidator.getInstance();
        user = new User(UserType.CLIENT, "lar4enko.yulya@gmail", "123", "Julia", "Larchenko");
    }

    @Test
    public void validateUserValidResultTest() {
        //when
        boolean actual = subject.validateUser(user, VALID_CONFIRMATION_PASSWORD);
        //then
        Assert.assertTrue(actual);
    }

    @Test
    public void validateUserInvalidResultTest() {
        //when
        boolean actual = subject.validateUser(user, INVALID_CONFIRMATION_PASSWORD);
        //then
        Assert.assertFalse(actual);
    }

    @Test
    public void validateEmailPasswordValidResultTest() {
        //when
        boolean actual = subject.validateEmailPassword(user.getEmail(), user.getPassword());
        //then
        Assert.assertTrue(actual);
    }

    @Test
    public void validateEmailPasswordInvalidResultTest() {
        //when
        boolean actual = subject.validateEmailPassword(user.getEmail(), "");
        //then
        Assert.assertFalse(actual);
    }

    @Test
    public void validateMoneyValidResultTest() {
        //when
        boolean actual = subject.validateMoney(new BigDecimal(100));
        //then
        Assert.assertTrue(actual);
    }

    @Test
    public void validateMoneyInvalidResultTest() {
        //when
        boolean actual = subject.validateMoney(new BigDecimal(-5));
        //then
        Assert.assertFalse(actual);
    }
}
