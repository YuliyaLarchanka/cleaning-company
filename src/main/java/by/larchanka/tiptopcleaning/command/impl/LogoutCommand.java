package by.larchanka.tiptopcleaning.command.impl;

import by.larchanka.tiptopcleaning.command.Command;
import by.larchanka.tiptopcleaning.command.CommandResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.larchanka.tiptopcleaning.command.PageParameterConstant.ACCOUNT_TYPE;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.USER_ID;
import static by.larchanka.tiptopcleaning.controller.PagePathConstant.PATH_AUTHENTICATION;
import static by.larchanka.tiptopcleaning.controller.PagePathConstant.PATH_ERROR;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_DEFAULT_ERROR;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_SIGN_OUT_SUCCESS;

public class LogoutCommand implements Command {
    @Override
    public CommandResponse execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        CommandResponse commandResponse = new CommandResponse();

        if (session != null) {
            session.removeAttribute(USER_ID);
            session.removeAttribute(ACCOUNT_TYPE);
            commandResponse.setTargetURL(PATH_AUTHENTICATION);
            commandResponse.setMessage(KEY_SIGN_OUT_SUCCESS);
        } else {
            commandResponse.setErrorStatus(true);
            commandResponse.setTargetURL(PATH_ERROR);
            commandResponse.setTargetURL(KEY_DEFAULT_ERROR);
        }

        return commandResponse;
    }
}