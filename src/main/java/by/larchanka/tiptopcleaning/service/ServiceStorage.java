package by.larchanka.tiptopcleaning.service;

import by.larchanka.tiptopcleaning.service.impl.AccountOrderServiceImpl;
import by.larchanka.tiptopcleaning.service.impl.CatalogItemServiceImpl;
import by.larchanka.tiptopcleaning.service.impl.AccountServiceImpl;
import by.larchanka.tiptopcleaning.service.impl.PromoCodeServiceImpl;

public class ServiceStorage {
    private static final ServiceStorage INSTANCE = new ServiceStorage();

    private AccountService accountService = new AccountServiceImpl();
    private CatalogItemService catalogItemService = new CatalogItemServiceImpl();
    private AccountOrderService accountOrderService = new AccountOrderServiceImpl();
    private PromoCodeService promocodeService = new PromoCodeServiceImpl();

    private ServiceStorage() {
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
