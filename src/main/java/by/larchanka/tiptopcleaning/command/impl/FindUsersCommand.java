package by.larchanka.tiptopcleaning.command.impl;

import by.larchanka.tiptopcleaning.command.Command;
import by.larchanka.tiptopcleaning.command.CommandResponse;
import by.larchanka.tiptopcleaning.entity.User;
import by.larchanka.tiptopcleaning.service.AccountService;
import by.larchanka.tiptopcleaning.service.ServiceException;
import by.larchanka.tiptopcleaning.service.ServiceStorage;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static by.larchanka.tiptopcleaning.command.PageParameterConstant.USER_LIST;
import static by.larchanka.tiptopcleaning.controller.PagePathConstant.PATH_ERROR;
import static by.larchanka.tiptopcleaning.controller.PagePathConstant.PATH_REAL_USERS;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_DEFAULT_ERROR;

public class FindUsersCommand implements Command {
    @Override
    public CommandResponse execute(HttpServletRequest request) {
        ServiceStorage creator = ServiceStorage.getInstance();
        AccountService accountService = creator.getAccountService();
        CommandResponse commandResponse = new CommandResponse();

        try {
            List<User> userList = accountService.findUsers();
            request.setAttribute(USER_LIST, userList);
            commandResponse.setTargetURL(PATH_REAL_USERS);
        } catch (ServiceException e) {
            commandResponse.setErrorStatus(true);
            commandResponse.setMessage(KEY_DEFAULT_ERROR);
            commandResponse.setTargetURL(PATH_ERROR);
        }

        return commandResponse;
    }
}
