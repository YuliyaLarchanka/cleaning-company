package by.larchanka.tiptopcleaning.validator;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AccountOrderValidatorTest {
    private AccountOrderValidator subject;

    @BeforeMethod
    public void setUp() {
        subject = AccountOrderValidator.getInstance();
    }

    @Test
    public void validateDateValidResultTest() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = dateFormat.parse("2020/5/10");
        Timestamp timestamp = new Timestamp(date.getTime());
        //when
        boolean actual = subject.validateDate(timestamp);
        //then
        Assert.assertTrue(actual);
    }

    @Test
    public void validateDateInvalidResultTest() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = dateFormat.parse("2010/5/10");
        Timestamp timestamp = new Timestamp(date.getTime());
        //when
        boolean actual = subject.validateDate(timestamp);
        //then
        Assert.assertFalse(actual);
    }
}
