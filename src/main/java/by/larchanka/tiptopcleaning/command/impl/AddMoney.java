package by.larchanka.tiptopcleaning.command.impl;

import by.larchanka.tiptopcleaning.command.Command;
import by.larchanka.tiptopcleaning.command.CommandResponse;
import by.larchanka.tiptopcleaning.service.AccountService;
import by.larchanka.tiptopcleaning.service.ServiceException;
import by.larchanka.tiptopcleaning.service.ServiceStorage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

import static by.larchanka.tiptopcleaning.command.PageParameterConstant.MONEY;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.USER_ID;
import static by.larchanka.tiptopcleaning.controller.PagePathConstant.PATH_MONEY;
import static by.larchanka.tiptopcleaning.controller.PagePathConstant.PATH_PROFILE;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_ADD_MONEY_ERROR;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_ADD_MONEY_SUCCESS;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_DEFAULT_ERROR;

public class AddMoney implements Command {
    @Override
    public CommandResponse execute(HttpServletRequest request) {
        BigDecimal money = BigDecimal.valueOf(Double.parseDouble(request.getParameter(MONEY)));
        HttpSession session = request.getSession(true);
        long userId = Long.parseLong(session.getAttribute(USER_ID).toString());

        ServiceStorage creator = ServiceStorage.getInstance();
        AccountService accountService = creator.getAccountService();

        CommandResponse commandResponse = new CommandResponse();
        try {
            boolean isMoneyAdded = accountService.addMoney(money, userId);

            if (isMoneyAdded) {
                commandResponse.setMessage(KEY_ADD_MONEY_SUCCESS);
                commandResponse.setTargetURL(PATH_PROFILE);
            } else {
                commandResponse.setErrorStatus(true);
                commandResponse.setMessage(KEY_ADD_MONEY_ERROR);
                commandResponse.setTargetURL(PATH_MONEY);
            }

        } catch (ServiceException e) {
            commandResponse.setErrorStatus(true);
            commandResponse.setMessage(KEY_DEFAULT_ERROR);
            commandResponse.setTargetURL(PATH_MONEY);
        }

        return commandResponse;
    }
}