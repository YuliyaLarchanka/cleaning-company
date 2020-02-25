package by.larchanka.tiptopcleaning.command.impl;

import by.larchanka.tiptopcleaning.command.Command;
import by.larchanka.tiptopcleaning.command.CommandResponse;
import by.larchanka.tiptopcleaning.entity.AccountOrder;
import by.larchanka.tiptopcleaning.entity.OrderStatus;
import by.larchanka.tiptopcleaning.service.AccountOrderService;
import by.larchanka.tiptopcleaning.service.ServiceException;
import by.larchanka.tiptopcleaning.service.ServiceStorage;

import javax.servlet.http.HttpServletRequest;

import static by.larchanka.tiptopcleaning.command.PageParameterConstant.ACCOUNT_ORDER_ID;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.ACCOUNT_ORDER_STATUS;
import static by.larchanka.tiptopcleaning.controller.PagePathConstant.PATH_ALL_ORDERS;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_CHANGE_ACCOUNT_ORDER_STATUS_ERROR;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_CHANGE_ACCOUNT_ORDER_STATUS_SUCCESS;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_DEFAULT_ERROR;

public class ChangeAccountOrderStatusCommand implements Command {
    @Override
    public CommandResponse execute(HttpServletRequest request) {
        long id = Long.parseLong(request.getParameter(ACCOUNT_ORDER_ID));
        OrderStatus status = OrderStatus.valueOf(request.getParameter(ACCOUNT_ORDER_STATUS));

        AccountOrder updatedAccountOrder = new AccountOrder();
        updatedAccountOrder.setId(id);
        updatedAccountOrder.setOrderStatus(status);

        ServiceStorage creator = ServiceStorage.getInstance();
        AccountOrderService accountOrderService = creator.getAccountOrderService();
        CommandResponse commandResponse = new CommandResponse();

        try {
            boolean isStatusChanged = accountOrderService.changeAccountOrderStatus(id, status);

            if (isStatusChanged) {
                commandResponse.setMessage(KEY_CHANGE_ACCOUNT_ORDER_STATUS_SUCCESS);
            } else {
                commandResponse.setErrorStatus(true);
                commandResponse.setMessage(KEY_CHANGE_ACCOUNT_ORDER_STATUS_ERROR);
            }
            commandResponse.setTargetURL(PATH_ALL_ORDERS);
        } catch (ServiceException e) {
            commandResponse.setErrorStatus(true);
            commandResponse.setMessage(KEY_DEFAULT_ERROR);
            commandResponse.setTargetURL(PATH_ALL_ORDERS);
        }

        return commandResponse;
    }
}
