package by.larchanka.tiptopcleaning.service.impl;

import by.larchanka.tiptopcleaning.dao.AccountOrderDao;
import by.larchanka.tiptopcleaning.dao.DaoCreator;
import by.larchanka.tiptopcleaning.dao.DaoException;
import by.larchanka.tiptopcleaning.entity.AccountOrder;
import by.larchanka.tiptopcleaning.entity.CatalogItem;
import by.larchanka.tiptopcleaning.entity.OrderItem;
import by.larchanka.tiptopcleaning.entity.OrderStatus;
import by.larchanka.tiptopcleaning.entity.PaymentMethod;
import by.larchanka.tiptopcleaning.entity.PromoCode;
import by.larchanka.tiptopcleaning.entity.User;
import by.larchanka.tiptopcleaning.service.AccountOrderService;
import by.larchanka.tiptopcleaning.service.AccountService;
import by.larchanka.tiptopcleaning.service.CatalogItemService;
import by.larchanka.tiptopcleaning.service.PromoCodeService;
import by.larchanka.tiptopcleaning.service.ServiceException;
import by.larchanka.tiptopcleaning.service.ServiceStorage;
import by.larchanka.tiptopcleaning.validation.AccountOrderValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AccountOrderServiceImpl implements AccountOrderService {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Optional<AccountOrder> createAccountOrder(AccountOrder order) throws ServiceException {
        AccountOrderValidator accountOrderValidator = AccountOrderValidator.getInstance();
        boolean isValidDate = accountOrderValidator.validateDate(order.getDateTime());
        if(!isValidDate) {
            return Optional.empty();
        }

        List<OrderItem> orderItemList = order.getOrderItemList();
        List<Long> catalogItemIds = orderItemList
                .stream()
                .map(OrderItem::getCatalogItem)
                .map(CatalogItem::getId)
                .collect(Collectors.toList());

        ServiceStorage serviceStorage = ServiceStorage.getInstance();
        CatalogItemService catalogItemService = serviceStorage.getCatalogItemService();
        List<CatalogItem> catalogItemsToOrder = catalogItemService.getCatalogItemListByIds(catalogItemIds);
        if (catalogItemIds.size() != catalogItemsToOrder.size()) {
            return Optional.empty();//в случае, если мэнеджер добавил или удалил услугу
        }

        BigDecimal totalCost = new BigDecimal(0);
        for (OrderItem orderItem : orderItemList) {
            Optional<CatalogItem> itemOptional = catalogItemsToOrder
                    .stream()
                    .filter(item -> orderItem.getCatalogItem().getId() == item.getId())
                    .findAny();
            if (itemOptional.isEmpty()) {
                return Optional.empty();
            }
            CatalogItem item = itemOptional.get();
            orderItem.setCatalogItem(item);

            BigDecimal itemCost = item.getPrice().multiply(new BigDecimal(orderItem.getAmount()));
            totalCost = totalCost.add(itemCost);
        }

        PromoCode promoCode = order.getPromoCode();
        if (promoCode != null) {
            String promoCodeName = promoCode.getValue();
            PromoCodeService promocodeService = serviceStorage.getPromoCodeService();
            Optional<PromoCode> promoCodeOptional = promocodeService.findPromoCodeByName(promoCodeName);
            if (promoCodeOptional.isEmpty()) {
                return Optional.empty();
            }

            promoCode = promoCodeOptional.get();
            order.setPromoCode(promoCode);
            double discount = promoCode.getDiscountPercentage();
            BigDecimal discountMultiplier = BigDecimal.valueOf((100 - discount)/100.0);
            totalCost = totalCost.multiply(discountMultiplier);
        }

        order.setTotalCost(totalCost);

        if (PaymentMethod.ACCOUNT_BALANCE.equals(order.getPaymentMethod())) {
            long userId = order.getUser().getId();
            AccountService accountService = serviceStorage.getAccountService();
            Optional<User> optionalUser = accountService.findUserById(userId);

            if (optionalUser.isEmpty()) {
                return Optional.empty();
            }

            User user = optionalUser.get();
            if (totalCost.compareTo(user.getMoney()) > 0) {
                return Optional.empty();
            }
        }

        DaoCreator creator = DaoCreator.getInstance();
        AccountOrderDao accountOrderDao = creator.getAccountOrderDao();

        try {
            return accountOrderDao.createAccountOrder(order, orderItemList);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<AccountOrder> findAccountOrdersByUserId(long userId) throws ServiceException{
        DaoCreator creator = DaoCreator.getInstance();
        AccountOrderDao dao = creator.getAccountOrderDao();

        try {
            return dao.findAccountOrdersByUserId(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<AccountOrder> findAllAccountOrders() throws ServiceException{
        DaoCreator creator = DaoCreator.getInstance();
        AccountOrderDao dao = creator.getAccountOrderDao();

        try {
            return dao.findAllAccountOrders();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean changeAccountOrderStatus(long id, OrderStatus status) throws ServiceException{
        DaoCreator creator = DaoCreator.getInstance();
        AccountOrderDao dao = creator.getAccountOrderDao();

        try {
            return dao.changeAccountOrderStatus(id, status);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
