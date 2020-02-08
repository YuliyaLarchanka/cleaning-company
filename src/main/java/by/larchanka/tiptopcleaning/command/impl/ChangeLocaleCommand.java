package by.larchanka.tiptopcleaning.command.impl;

import by.larchanka.tiptopcleaning.command.Command;
import by.larchanka.tiptopcleaning.command.CommandResponse;

import javax.servlet.http.HttpServletRequest;

import static by.larchanka.tiptopcleaning.util.CommonConstant.LOCALE;
import static by.larchanka.tiptopcleaning.util.CommonConstant.PATH;

public class ChangeLocaleCommand implements Command {
    @Override
    public CommandResponse execute(HttpServletRequest request) {
        CommandResponse commandResponse = new CommandResponse();

        request.getSession(true).setAttribute(LOCALE, request.getParameter(LOCALE));
        commandResponse.setTargetURL(request.getParameter(PATH));

        return commandResponse;
    }
}
