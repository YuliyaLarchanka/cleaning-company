package by.larchanka.tiptopcleaning.dao;

import by.larchanka.tiptopcleaning.entity.PromoCode;

import java.util.List;
import java.util.Optional;

public interface PromoCodeDao {
    Optional<PromoCode> findPromoCodeByName(String name) throws DaoException;

    List<PromoCode> findAllPromoCodes() throws DaoException;

    boolean deletePromoCodeById(long id) throws DaoException;

    Optional<PromoCode> addPromoCode(PromoCode promoCode) throws DaoException;

    boolean isPromoCodeAlreadyExist(String value) throws DaoException;
}
