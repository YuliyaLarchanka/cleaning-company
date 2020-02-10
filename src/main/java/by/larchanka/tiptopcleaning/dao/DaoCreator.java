package by.larchanka.tiptopcleaning.dao;

import by.larchanka.tiptopcleaning.dao.impl.AccountDaoImpl;
import by.larchanka.tiptopcleaning.dao.impl.AccountOrderDaoImpl;
import by.larchanka.tiptopcleaning.dao.impl.CatalogItemDaoImpl;
import by.larchanka.tiptopcleaning.dao.impl.PromoCodeDaoImpl;

public class DaoCreator {
    private static final DaoCreator INSTANCE = new DaoCreator();

    private AccountDao accountDAO = new AccountDaoImpl();
    private CatalogItemDao catalogItemDao = new CatalogItemDaoImpl();
    private AccountOrderDao accountOrderDao = new AccountOrderDaoImpl();
    private PromoCodeDao promoCodeDao = new PromoCodeDaoImpl();

    private DaoCreator(){}

    public static DaoCreator getInstance(){
        return INSTANCE;
    }

    public AccountDao getAccountDAO() {
        return accountDAO;
    }

    public CatalogItemDao getCatalogItemDao() {
        return catalogItemDao;
    }

    public AccountOrderDao getAccountOrderDao() {
        return accountOrderDao;
    }

    public PromoCodeDao getPromoCodeDao() {
        return promoCodeDao;
    }
}
