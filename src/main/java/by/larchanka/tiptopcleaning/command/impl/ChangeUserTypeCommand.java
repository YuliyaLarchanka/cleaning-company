package by.larchanka.tiptopcleaning.command.impl;

import by.larchanka.tiptopcleaning.command.Command;
import by.larchanka.tiptopcleaning.command.CommandResponse;
import by.larchanka.tiptopcleaning.entity.User;
import by.larchanka.tiptopcleaning.entity.UserType;
import by.larchanka.tiptopcleaning.service.AccountService;
import by.larchanka.tiptopcleaning.service.ServiceException;
import by.larchanka.tiptopcleaning.service.ServiceStorage;

import javax.servlet.http.HttpServletRequest;

import static by.larchanka.tiptopcleaning.command.PageParameterConstant.ACCOUNT_ID;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.ACCOUNT_TYPE;
import static by.larchanka.tiptopcleaning.controller.PagePathConstant.PATH_USERS;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_CHANGE_USER_TYPE_ERROR;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_CHANGE_USER_TYPE_SUCCESS;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_DEFAULT_ERROR;

public class ChangeUserTypeCommand implements Command {
    @Override
    public CommandResponse execute(HttpServletRequest request) {
        long id = Long.parseLong(request.getParameter(ACCOUNT_ID));
        UserType type = UserType.valueOf(request.getParameter(ACCOUNT_TYPE));

        User updatedUser = new User();
        updatedUser.setId(id);
        updatedUser.setType(type);

        ServiceStorage creator = ServiceStorage.getInstance();
        AccountService accountService = creator.getAccountService();
        CommandResponse commandResponse = new CommandResponse();

        try {
            boolean isTypeChanged = accountService.changeUserType(id, type);

            if (isTypeChanged) {
                commandResponse.setMessage(KEY_CHANGE_USER_TYPE_SUCCESS);
            } else {
                commandResponse.setErrorStatus(true);
                commandResponse.setMessage(KEY_CHANGE_USER_TYPE_ERROR);
            }
            commandResponse.setTargetURL(PATH_USERS);
        } catch (ServiceException e) {
            commandResponse.setErrorStatus(true);
            commandResponse.setMessage(KEY_DEFAULT_ERROR);
            commandResponse.setTargetURL(PATH_USERS);
        }

        return commandResponse;
    }
}
