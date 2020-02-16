package by.larchanka.tiptopcleaning.validator;

import by.larchanka.tiptopcleaning.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

import static by.larchanka.tiptopcleaning.service.MessageConstant.AUTHENTICATION_ERROR;
import static by.larchanka.tiptopcleaning.service.MessageConstant.CONFIRMATION_ERROR;
import static by.larchanka.tiptopcleaning.service.MessageConstant.NEGATIVE_MONEY_AMOUNT_ERROR;
import static by.larchanka.tiptopcleaning.service.MessageConstant.VALIDATION_ERROR;

public class ServiceValidator {
    private static final Logger logger = LogManager.getLogger();

    private static final ServiceValidator INSTANCE = new ServiceValidator();

    public static ServiceValidator getInstance() {
        return INSTANCE;
    }

    public boolean validateUser(User user, String confirmationPassword) {
        if (user.getEmail().isEmpty() || user.getPassword().isEmpty() || user.getFirstName().isEmpty()
                || user.getLastName().isEmpty()) {
            logger.debug(VALIDATION_ERROR);
            return false;
        }
        if(!user.getPassword().equals(confirmationPassword)){
            logger.debug(CONFIRMATION_ERROR);
            return false;
        }
        return true;
    }

    public boolean validateEmailPassword(String email, String password) {
        if (email.isEmpty()||password.isEmpty()){
            logger.debug(AUTHENTICATION_ERROR);
            return false;
        }
        return true;
    }

    public boolean validateMoney(BigDecimal money) {
        if (money.compareTo(BigDecimal.ZERO) < 0){
            logger.debug(NEGATIVE_MONEY_AMOUNT_ERROR);
            return false;
        }
        return true;
    }
}
