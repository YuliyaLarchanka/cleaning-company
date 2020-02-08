package by.larchanka.tiptopcleaning.service.impl;

import by.larchanka.tiptopcleaning.dao.CatalogItemDao;
import by.larchanka.tiptopcleaning.dao.DaoCreator;
import by.larchanka.tiptopcleaning.dao.DaoException;
import by.larchanka.tiptopcleaning.dao.PromoCodeDao;
import by.larchanka.tiptopcleaning.entity.PromoCode;
import by.larchanka.tiptopcleaning.service.PromoCodeService;
import by.larchanka.tiptopcleaning.service.ServiceException;
import by.larchanka.tiptopcleaning.validation.AccountOrderValidator;

import java.util.List;
import java.util.Optional;

public class PromoCodeServiceImpl implements PromoCodeService {

    private DaoCreator creator = DaoCreator.getInstance();

    @Override
    public Optional<PromoCode> findPromoCodeByName(String name) throws ServiceException {
        PromoCodeDao dao = creator.getPromoCodeDao();

        try {
            return dao.findPromoCodeByName(name);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<PromoCode> findAllPromoCodes() throws ServiceException{
        PromoCodeDao dao = creator.getPromoCodeDao();

        List<PromoCode> promoCodeList;
        try {
            promoCodeList = dao.findAllPromoCodes();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return promoCodeList;
    }

    @Override
    public boolean deletePromoCodeById(long id) throws ServiceException{
        PromoCodeDao dao = creator.getPromoCodeDao();

        try {
            return dao.deletePromoCodeById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<PromoCode> addPromoCode(PromoCode promoCode) throws ServiceException{
        PromoCodeDao dao = creator.getPromoCodeDao();

        try {
            if (dao.isPromoCodeAlreadyExist(promoCode.getValue())) {
                return Optional.empty();
            }
            return dao.addPromoCode(promoCode);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

}
