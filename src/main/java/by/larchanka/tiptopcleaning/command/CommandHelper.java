package by.larchanka.tiptopcleaning.command;

import by.larchanka.tiptopcleaning.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.larchanka.tiptopcleaning.command.PageParameterConstant.ACCOUNT_TYPE;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.USER_ID;
import static by.larchanka.tiptopcleaning.controller.PagePathConstant.PATH_HOME;

public class CommandHelper {

    public static void processUserAuthenticationSuccess(HttpServletRequest request, CommandResponse commandResponse, User authenticatedUser, String message) {
        commandResponse.setMessage(message);
        HttpSession session = request.getSession(true);
        session.setAttribute(USER_ID, authenticatedUser.getId());
        session.setAttribute(ACCOUNT_TYPE, authenticatedUser.getType());
        commandResponse.setTargetURL(PATH_HOME);
    }

    public static void processUserAuthenticationFailed(CommandResponse commandResponse, String message, String path) {
        commandResponse.setErrorStatus(true);
        commandResponse.setMessage(message);
        commandResponse.setTargetURL(path);
    }
}
