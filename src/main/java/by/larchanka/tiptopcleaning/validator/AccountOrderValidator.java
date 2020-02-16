package by.larchanka.tiptopcleaning.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;

import static by.larchanka.tiptopcleaning.service.MessageConstant.SET_DATE_TIME_ERROR;

public class AccountOrderValidator {
    private static final Logger logger = LogManager.getLogger();

    private static final AccountOrderValidator INSTANCE = new AccountOrderValidator();

    private AccountOrderValidator() {
    }

    public static AccountOrderValidator getInstance() {
        return INSTANCE;
    }

    public boolean validateDate(Timestamp userDate){
        Timestamp current = new Timestamp(System.currentTimeMillis());

        if (userDate.before(current)){
            logger.debug(SET_DATE_TIME_ERROR);
            return false;
        }
        return true;
    }
}
