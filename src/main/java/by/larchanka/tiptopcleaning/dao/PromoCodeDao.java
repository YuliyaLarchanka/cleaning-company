package by.larchanka.tiptopcleaning.dao;

import by.larchanka.tiptopcleaning.entity.PromoCode;

import java.util.List;
import java.util.Optional;

public interface PromoCodeDao {
    /**
     *Finds promo code by name in database
     *
     * @param name a {@code String} object to find promo code with
     * @return {@code Optional<PromoCode>} object containing promo code
     */
    Optional<PromoCode> findPromoCodeByName(String name) throws DaoException;

    /**
     *Finds all promo codes in database
     *
     * @return {@code List<PromoCode>} object containing promo codes
     */
    List<PromoCode> findAllPromoCodes() throws DaoException;

    /**
     *Deletes promo code from database by id
     *
     * @param id a {@code long} value to find promo code with
     * @return {@code boolean} value. True if deletion was successful and false otherwise.
     */
    boolean deletePromoCodeById(long id) throws DaoException;

    /**
     *Adds promo code into database
     *
     * @param promoCode a {@code PromoCode} object to set
     * @return {@code Optional<PromoCode>} object containing promo code
     */
    Optional<PromoCode> addPromoCode(PromoCode promoCode) throws DaoException;

    /**
     *Verifies is promo code already exist in database
     *
     * @param value a {@code String} object to find promo code with
     * @return {@code boolean} value. True if exist and false if not.
     */
    boolean isPromoCodeAlreadyExist(String value) throws DaoException;
}
