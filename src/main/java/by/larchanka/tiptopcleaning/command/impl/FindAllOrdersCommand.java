package by.larchanka.tiptopcleaning.command.impl;

import by.larchanka.tiptopcleaning.command.Command;
import by.larchanka.tiptopcleaning.command.CommandResponse;
import by.larchanka.tiptopcleaning.entity.AccountOrder;
import by.larchanka.tiptopcleaning.service.AccountOrderService;
import by.larchanka.tiptopcleaning.service.ServiceException;
import by.larchanka.tiptopcleaning.service.ServiceStorage;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static by.larchanka.tiptopcleaning.command.PageParameterConstant.ORDER_LIST;
import static by.larchanka.tiptopcleaning.controller.PagePathConstant.PATH_ERROR;
import static by.larchanka.tiptopcleaning.controller.PagePathConstant.PATH_REAL_ALL_ORDERS;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_DEFAULT_ERROR;

public class FindAllOrdersCommand implements Command {
    @Override
    public CommandResponse execute(HttpServletRequest request) {
        ServiceStorage factory = ServiceStorage.getInstance();
        AccountOrderService accountOrderService = factory.getAccountOrderService();
        CommandResponse commandResponse = new CommandResponse();

        long page = 1;
        long recordsPerPage = 6;
        if (request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page"));

        try {
            List<AccountOrder> accountOrderList = accountOrderService.findAllAccountOrders();

            long recordsCount = accountOrderList.size();
            long pagesCount = (long) Math.ceil(recordsCount * 1.0 / recordsPerPage);
            accountOrderList = accountOrderList
                    .stream()
                    .skip((page - 1) * recordsPerPage)
                    .limit(recordsPerPage)
                    .collect(Collectors.toList());
            request.setAttribute("pagesCount", pagesCount);
            request.setAttribute("currentPage", page);
            request.setAttribute(ORDER_LIST, accountOrderList);
            commandResponse.setTargetURL(PATH_REAL_ALL_ORDERS);
        } catch (ServiceException e) {
            commandResponse.setErrorStatus(true);
            commandResponse.setMessage(KEY_DEFAULT_ERROR);
            commandResponse.setTargetURL(PATH_ERROR);
        }

        return commandResponse;
    }
}
