package by.larchanka.tiptopcleaning.command.impl;

import by.larchanka.tiptopcleaning.command.Command;
import by.larchanka.tiptopcleaning.command.CommandHelper;
import by.larchanka.tiptopcleaning.command.CommandResponse;
import by.larchanka.tiptopcleaning.entity.User;
import by.larchanka.tiptopcleaning.entity.UserType;
import by.larchanka.tiptopcleaning.service.AccountService;
import by.larchanka.tiptopcleaning.service.ServiceException;
import by.larchanka.tiptopcleaning.service.ServiceStorage;
import by.larchanka.tiptopcleaning.util.CryptorSHA256;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Optional;

import static by.larchanka.tiptopcleaning.command.PageParameterConstant.CONFIRM_PASSWORD;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.EMAIL;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.FIRST_NAME;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.LAST_NAME;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.PASSWORD;
import static by.larchanka.tiptopcleaning.controller.PagePathConstant.PATH_REGISTRATION;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_DEFAULT_ERROR;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_REGISTRATION_ERROR;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_REGISTRATION_SUCCESS;

public class RegistrationCommand implements Command {
    @Override
    public CommandResponse execute(HttpServletRequest request) {
        UserType type = UserType.CLIENT;
        String firstName = request.getParameter(FIRST_NAME);
        String lastName = request.getParameter(LAST_NAME);
        String email = request.getParameter(EMAIL).toLowerCase();
        String password = CryptorSHA256.cryptWithSHA256(request.getParameter(PASSWORD));
        String confirmationPassword = CryptorSHA256.cryptWithSHA256(request.getParameter(CONFIRM_PASSWORD));

        User registerUser = new User(type, email, password, firstName, lastName);
        registerUser.setMoney(new BigDecimal(0));

        ServiceStorage creator = ServiceStorage.getInstance();
        AccountService accountService = creator.getAccountService();
        CommandResponse commandResponse = new CommandResponse();

        try {
            Optional<User> userOptional = accountService.addUser(registerUser, confirmationPassword);

            if (userOptional.isPresent()) {
                CommandHelper.processUserAuthenticationSuccess(request, commandResponse, userOptional.get(), KEY_REGISTRATION_SUCCESS);
            } else {
                CommandHelper.processUserAuthenticationFailed(commandResponse, KEY_REGISTRATION_ERROR, PATH_REGISTRATION);
            }
        } catch (ServiceException e) {
            commandResponse.setErrorStatus(true);
            commandResponse.setMessage(KEY_DEFAULT_ERROR);
            commandResponse.setTargetURL(PATH_REGISTRATION);
        }

        return commandResponse;
    }
}
