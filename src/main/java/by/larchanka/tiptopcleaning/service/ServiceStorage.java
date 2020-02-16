package by.larchanka.tiptopcleaning.service;

import by.larchanka.tiptopcleaning.dao.DaoCreator;
import by.larchanka.tiptopcleaning.service.impl.AccountOrderServiceImpl;
import by.larchanka.tiptopcleaning.service.impl.AccountServiceImpl;
import by.larchanka.tiptopcleaning.service.impl.CatalogItemServiceImpl;
import by.larchanka.tiptopcleaning.service.impl.PromoCodeServiceImpl;
import by.larchanka.tiptopcleaning.validator.AccountOrderValidator;
import by.larchanka.tiptopcleaning.validator.ServiceValidator;

public class ServiceStorage {
    private static final ServiceStorage INSTANCE = new ServiceStorage();

    private AccountService accountService;
    private AccountOrderService accountOrderService;
    private CatalogItemService catalogItemService;
    private PromoCodeService promocodeService;

    private ServiceStorage() {
        DaoCreator creator = DaoCreator.getInstance();
        catalogItemService = new CatalogItemServiceImpl(creator.getCatalogItemDao());
        accountService = new AccountServiceImpl(creator.getAccountDAO(), ServiceValidator.getInstance());
        promocodeService = new PromoCodeServiceImpl(creator.getPromoCodeDao());
        accountOrderService = new AccountOrderServiceImpl(creator.getAccountOrderDao(), AccountOrderValidator.getInstance(), catalogItemService, promocodeService, accountService);
    }

    public static ServiceStorage getInstance() {
        return INSTANCE;
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public CatalogItemService getCatalogItemService() {
        return catalogItemService;
    }

    public AccountOrderService getAccountOrderService() {
        return accountOrderService;
    }

    public PromoCodeService getPromoCodeService() {
        return promocodeService;
    }
}
