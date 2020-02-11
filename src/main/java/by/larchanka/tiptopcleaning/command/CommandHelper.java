package by.larchanka.tiptopcleaning.command;

import by.larchanka.tiptopcleaning.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.larchanka.tiptopcleaning.command.PageParameterConstant.ACCOUNT_TYPE;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.USER_ID;
import static by.larchanka.tiptopcleaning.controller.PagePathConstant.PATH_AUTHENTICATION;
import static by.larchanka.tiptopcleaning.controller.PagePathConstant.PATH_HOME;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_AUTHENTICATION_SUCCESS;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_USER_MISMATCH;

public class CommandHelper {

    public static void processUserAuthenticationSuccess(
            HttpServletRequest request, CommandResponse commandResponse, User authenticatedUser) {
        commandResponse.setMessage(KEY_AUTHENTICATION_SUCCESS);
        HttpSession session = request.getSession(true);
        session.setAttribute(USER_ID, authenticatedUser.getId());
        session.setAttribute(ACCOUNT_TYPE, authenticatedUser.getType());
        commandResponse.setTargetURL(PATH_HOME);
    }

    public static void processUserAuthenticationFailed(CommandResponse commandResponse) {
        commandResponse.setErrorStatus(true);
        commandResponse.setMessage(KEY_USER_MISMATCH);
        commandResponse.setTargetURL(PATH_AUTHENTICATION);
    }
}
