package by.larchanka.tiptopcleaning.service;

import by.larchanka.tiptopcleaning.entity.PromoCode;

import java.util.List;
import java.util.Optional;

public interface PromoCodeService {
    Optional<PromoCode> findPromoCodeByName(String name) throws ServiceException;

    List<PromoCode> findAllPromoCodes() throws ServiceException;

    boolean deletePromoCodeById(long id) throws ServiceException;

    Optional<PromoCode> addPromoCode(PromoCode promoCode) throws ServiceException;
}
