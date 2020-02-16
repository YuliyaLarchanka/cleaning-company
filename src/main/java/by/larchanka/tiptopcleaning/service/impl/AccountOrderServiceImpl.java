package by.larchanka.tiptopcleaning.service.impl;

import by.larchanka.tiptopcleaning.dao.AccountOrderDao;
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
import by.larchanka.tiptopcleaning.validator.AccountOrderValidator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AccountOrderServiceImpl implements AccountOrderService {
    private AccountOrderDao dao;
    private AccountOrderValidator accountOrderValidator;
    private CatalogItemService catalogItemService;
    private PromoCodeService promoCodeService;
    private AccountService accountService;

    public AccountOrderServiceImpl(AccountOrderDao dao, AccountOrderValidator accountOrderValidator,
                                   CatalogItemService catalogItemService, PromoCodeService promoCodeService, AccountService accountService) {
        this.dao = dao;
        this.accountOrderValidator = accountOrderValidator;
        this.catalogItemService = catalogItemService;
        this.promoCodeService = promoCodeService;
        this.accountService = accountService;
    }

    @Override
    public Optional<AccountOrder> createAccountOrder(AccountOrder order) throws ServiceException {
        boolean isValidDate = accountOrderValidator.validateDate(order.getDateTime());
        if (!isValidDate) {
            return Optional.empty();
        }

        List<OrderItem> orderItemList = order.getOrderItemList();
        List<Long> catalogItemIds = orderItemList
                .stream()
                .map(OrderItem::getCatalogItem)
                .map(CatalogItem::getId)
                .collect(Collectors.toList());

        List<CatalogItem> catalogItemsToOrder = catalogItemService.getCatalogItemListByIds(catalogItemIds);
        if (catalogItemIds.size() != catalogItemsToOrder.size()) {
            return Optional.empty();
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
            Optional<PromoCode> promoCodeOptional = promoCodeService.findPromoCodeByName(promoCodeName);
            if (promoCodeOptional.isEmpty()) {
                return Optional.empty();
            }

            promoCode = promoCodeOptional.get();
            order.setPromoCode(promoCode);
            double discount = promoCode.getDiscountPercentage();
            BigDecimal discountMultiplier = BigDecimal.valueOf((100 - discount) / 100.0);
            totalCost = totalCost.multiply(discountMultiplier);
        }

        order.setTotalCost(totalCost);

        if (PaymentMethod.ACCOUNT_BALANCE.equals(order.getPaymentMethod())) {
            long userId = order.getUser().getId();
            Optional<User> optionalUser = accountService.findUserById(userId);

            if (optionalUser.isEmpty()) {
                return Optional.empty();
            }

            User user = optionalUser.get();
            if (totalCost.compareTo(user.getMoney()) > 0) {
                return Optional.empty();
            }
        }

        try {
            return dao.createAccountOrder(order, orderItemList);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<AccountOrder> findAccountOrdersByUserId(long userId) throws ServiceException {
        try {
            return dao.findAccountOrdersByUserId(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<AccountOrder> findAllAccountOrders() throws ServiceException {
        try {
            return dao.findAllAccountOrders();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean changeAccountOrderStatus(long id, OrderStatus status) throws ServiceException {
        try {
            return dao.changeAccountOrderStatus(id, status);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
