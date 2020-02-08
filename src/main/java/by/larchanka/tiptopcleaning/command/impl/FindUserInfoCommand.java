package by.larchanka.tiptopcleaning.command.impl;

import by.larchanka.tiptopcleaning.command.Command;
import by.larchanka.tiptopcleaning.command.CommandResponse;
import by.larchanka.tiptopcleaning.entity.User;
import by.larchanka.tiptopcleaning.service.AccountService;
import by.larchanka.tiptopcleaning.service.ServiceException;
import by.larchanka.tiptopcleaning.service.ServiceStorage;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static by.larchanka.tiptopcleaning.command.PageParameterConstant.USER;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.USER_ID;
import static by.larchanka.tiptopcleaning.controller.PagePathConstant.PATH_HOME;
import static by.larchanka.tiptopcleaning.controller.PagePathConstant.PATH_REAL_PROFILE;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_DEFAULT_ERROR;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_USER_NOT_FOUND;

public class FindUserInfoCommand implements Command {
    @Override
    public CommandResponse execute(HttpServletRequest request) {
        ServiceStorage factory = ServiceStorage.getInstance();
        AccountService accountService = factory.getAccountService();

        long userId = Long.parseLong(request.getSession().getAttribute(USER_ID).toString());
        CommandResponse commandResponse = new CommandResponse();
        try {
            Optional<User> userOptional = accountService.findUserById(userId);

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                request.setAttribute(USER, user);
                commandResponse.setTargetURL(PATH_REAL_PROFILE);
            }else {
                commandResponse.setErrorStatus(true);
                commandResponse.setMessage(KEY_USER_NOT_FOUND);
                commandResponse.setTargetURL(PATH_HOME);
            }
        }catch (ServiceException e){
            commandResponse.setErrorStatus(true);
            commandResponse.setMessage(KEY_DEFAULT_ERROR);
            commandResponse.setTargetURL(PATH_HOME);
        }

        return commandResponse;
    }
}
