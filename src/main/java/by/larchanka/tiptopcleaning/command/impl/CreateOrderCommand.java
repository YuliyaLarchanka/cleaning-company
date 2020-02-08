package by.larchanka.tiptopcleaning.command.impl;

import by.larchanka.tiptopcleaning.command.Command;
import by.larchanka.tiptopcleaning.command.CommandResponse;
import by.larchanka.tiptopcleaning.entity.AccountOrder;
import by.larchanka.tiptopcleaning.entity.OrderItem;
import by.larchanka.tiptopcleaning.entity.OrderStatus;
import by.larchanka.tiptopcleaning.entity.PaymentMethod;
import by.larchanka.tiptopcleaning.entity.PromoCode;
import by.larchanka.tiptopcleaning.entity.User;
import by.larchanka.tiptopcleaning.service.AccountOrderService;
import by.larchanka.tiptopcleaning.service.ServiceException;
import by.larchanka.tiptopcleaning.service.ServiceStorage;
import com.mysql.cj.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static by.larchanka.tiptopcleaning.command.PageParameterConstant.DATE_FORMAT;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.DATE_TIME_PICKER;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.MULTIPLE_ITEM;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.PAYMENT_METHOD;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.PROMO_CODE;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.SINGLE_ITEM_ID;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.SPLITTER;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.USER_ID;
import static by.larchanka.tiptopcleaning.controller.PagePathConstant.PATH_CATALOG;
import static by.larchanka.tiptopcleaning.controller.PagePathConstant.PATH_ORDERS;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_CREATING_ORDER_ERROR;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_CREATING_ORDER_SUCCESS;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_ERROR_DURING_ORDER_CREATING;

public class CreateOrderCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final byte SINGLE_ITEM_AMOUNT = 1;

    @Override
    public CommandResponse execute(HttpServletRequest request) {
        Set<String> keySet = request.getParameterMap().keySet();
        List<OrderItem> orderItemList = new ArrayList<>();

        for (String key : keySet) {
            if (key.startsWith(MULTIPLE_ITEM)) {
                String[] arr = key.split(SPLITTER);
                long id = Long.parseLong(arr[1]);
                byte amount = Byte.parseByte(request.getParameter(key));
                OrderItem orderItem = new OrderItem(id, amount);
                orderItemList.add(orderItem);
            }
        }

        String[] singleItemIds = request.getParameterValues(SINGLE_ITEM_ID);
        if (singleItemIds != null) {
            for (String stringId : singleItemIds) {
                long id = Long.parseLong(stringId);
                OrderItem orderItem = new OrderItem(id, SINGLE_ITEM_AMOUNT);
                orderItemList.add(orderItem);
            }
        }
        String dateTimeString = request.getParameter(DATE_TIME_PICKER);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Timestamp timestamp = null;
        try {
            Date date = sdf.parse(dateTimeString);
            timestamp = new Timestamp(date.getTime());
        } catch (ParseException e) {
            logger.error(e);
        }
        AccountOrder accountOrder = new AccountOrder();
        accountOrder.setDateTime(timestamp);
        
        String paymentMethod = request.getParameter(PAYMENT_METHOD);
        accountOrder.setPaymentMethod(PaymentMethod.valueOf(paymentMethod.toUpperCase()));

        String promoCodeValue = request.getParameter(PROMO_CODE);
        if (!StringUtils.isNullOrEmpty(promoCodeValue)) {
            accountOrder.setPromoCode(new PromoCode(promoCodeValue));
        }

        long userId = (long)request.getSession().getAttribute(USER_ID);
        User user = new User();
        user.setId(userId);
        accountOrder.setUser(user);
        accountOrder.setOrderStatus(OrderStatus.PROCESSING);
        accountOrder.setOrderItemList(orderItemList);

        ServiceStorage creator = ServiceStorage.getInstance();
        AccountOrderService accountOrderService = creator.getAccountOrderService();

        CommandResponse commandResponse = new CommandResponse();
        try {
            Optional<AccountOrder> orderOptional = accountOrderService.createAccountOrder(accountOrder);
            if (orderOptional.isPresent()) {
                commandResponse.setMessage(KEY_CREATING_ORDER_SUCCESS);
                commandResponse.setTargetURL(PATH_ORDERS);
            } else {
                commandResponse.setErrorStatus(true);
                commandResponse.setMessage(KEY_CREATING_ORDER_ERROR);
                commandResponse.setTargetURL(PATH_CATALOG);
            }
        } catch (ServiceException e) {
            commandResponse.setErrorStatus(true);
            commandResponse.setMessage(KEY_ERROR_DURING_ORDER_CREATING);
            commandResponse.setTargetURL(PATH_CATALOG);
        }

        return commandResponse;
    }
}
