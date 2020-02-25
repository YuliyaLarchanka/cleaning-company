package by.larchanka.tiptopcleaning.command.impl;

import by.larchanka.tiptopcleaning.command.Command;
import by.larchanka.tiptopcleaning.command.CommandHelper;
import by.larchanka.tiptopcleaning.command.CommandResponse;
import by.larchanka.tiptopcleaning.entity.User;
import by.larchanka.tiptopcleaning.service.AccountService;
import by.larchanka.tiptopcleaning.service.ServiceException;
import by.larchanka.tiptopcleaning.service.ServiceStorage;
import by.larchanka.tiptopcleaning.util.CryptorSHA256;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static by.larchanka.tiptopcleaning.command.PageParameterConstant.EMAIL;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.PASSWORD;
import static by.larchanka.tiptopcleaning.controller.PagePathConstant.PATH_AUTHENTICATION;
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
                CommandHelper.processUserAuthenticationSuccess(request, commandResponse, userOptional.get(), KEY_AUTHENTICATION_SUCCESS);
            } else {
                CommandHelper.processUserAuthenticationFailed(commandResponse, KEY_USER_MISMATCH, PATH_AUTHENTICATION);
            }
        } catch (ServiceException e) {
            commandResponse.setErrorStatus(true);
            commandResponse.setMessage(KEY_DEFAULT_ERROR);
            commandResponse.setTargetURL(PATH_AUTHENTICATION);
        }

        return commandResponse;
    }
}
