package by.larchanka.tiptopcleaning.command.impl;

import by.larchanka.tiptopcleaning.command.Command;
import by.larchanka.tiptopcleaning.command.CommandResponse;
import by.larchanka.tiptopcleaning.entity.User;
import by.larchanka.tiptopcleaning.service.AccountService;
import by.larchanka.tiptopcleaning.service.ServiceException;
import by.larchanka.tiptopcleaning.service.ServiceStorage;
import by.larchanka.tiptopcleaning.util.CryptorSHA256;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static by.larchanka.tiptopcleaning.command.PageParameterConstant.ACCOUNT_TYPE;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.EMAIL;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.PASSWORD;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.USER_ID;
import static by.larchanka.tiptopcleaning.controller.PagePathConstant.PATH_AUTHENTICATION;
import static by.larchanka.tiptopcleaning.controller.PagePathConstant.PATH_CATALOG;
import static by.larchanka.tiptopcleaning.controller.PagePathConstant.PATH_HOME;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_AUTHENTICATION_SUCCESS;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_DEFAULT_ERROR;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_USER_MISMATCH;

public class AuthenticationCommand implements Command {
    @Override
    public CommandResponse execute(HttpServletRequest request) {
        String email = request.getParameter(EMAIL).toLowerCase();
        String password = CryptorSHA256.cryptWithSHA256(request.getParameter(PASSWORD));

        ServiceStorage creator = ServiceStorage.getInstance();
        AccountService accountService = creator.getAccountService();

        CommandResponse commandResponse = new CommandResponse();
        try {
            Optional<User> userOptional = accountService.authenticateUser(email, password);

            if (userOptional.isPresent()) {
                commandResponse.setMessage(KEY_AUTHENTICATION_SUCCESS);
                HttpSession session = request.getSession(true);
                User authenticatedUser = userOptional.get();
                session.setAttribute(USER_ID, authenticatedUser.getId());
                session.setAttribute(ACCOUNT_TYPE, authenticatedUser.getType());
                commandResponse.setTargetURL(PATH_HOME);
            } else {
                commandResponse.setErrorStatus(true);
                commandResponse.setMessage(KEY_USER_MISMATCH);
                commandResponse.setTargetURL(PATH_AUTHENTICATION);
            }
        } catch (ServiceException e) {
            commandResponse.setErrorStatus(true);
            commandResponse.setMessage(KEY_DEFAULT_ERROR);
            commandResponse.setTargetURL(PATH_AUTHENTICATION);
        }
        return commandResponse;
    }
}
