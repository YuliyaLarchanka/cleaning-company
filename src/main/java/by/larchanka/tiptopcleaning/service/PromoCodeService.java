package by.larchanka.tiptopcleaning.service;

import by.larchanka.tiptopcleaning.entity.PromoCode;

import java.util.List;
import java.util.Optional;

public interface PromoCodeService {
    /**
     *Calls dao method
     *
     * @param name a {@code String} value to pass into dao method
     * @return {@code Optional<PromoCode>} object containing promo code
     */
    Optional<PromoCode> findPromoCodeByName(String name) throws ServiceException;

    /**
     *Calls dao method
     *
     * @return {@code List<PromoCode>} object containing promo codes
     */
    List<PromoCode> findAllPromoCodes() throws ServiceException;

    /**
     *Calls dao method
     *
     * @param id a {@code long} value to pass into dao method
     * @return {@code boolean} value. True if deletion was successful and false otherwise.
     */
    boolean deletePromoCodeById(long id) throws ServiceException;

    /**
     *Calls dao method
     *
     * @param promoCode a {@code PromoCode} value to pass into dao method
     * @return {@code Optional<PromoCode>} object containing promo code
     */
    Optional<PromoCode> addPromoCode(PromoCode promoCode) throws ServiceException;
}
