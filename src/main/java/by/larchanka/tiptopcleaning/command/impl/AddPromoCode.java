package by.larchanka.tiptopcleaning.command.impl;

import by.larchanka.tiptopcleaning.command.Command;
import by.larchanka.tiptopcleaning.command.CommandResponse;
import by.larchanka.tiptopcleaning.entity.PromoCode;
import by.larchanka.tiptopcleaning.entity.User;
import by.larchanka.tiptopcleaning.service.AccountService;
import by.larchanka.tiptopcleaning.service.PromoCodeService;
import by.larchanka.tiptopcleaning.service.ServiceException;
import by.larchanka.tiptopcleaning.service.ServiceStorage;
import by.larchanka.tiptopcleaning.util.CryptorSHA256;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static by.larchanka.tiptopcleaning.command.PageParameterConstant.APARTMENT_NUMBER;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.CONFIRM_PASSWORD;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.EMAIL;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.ENTRANCE_NUMBER;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.FIRST_NAME;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.FLOOR_NUMBER;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.HOUSE_NUMBER;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.INTERCOM_CODE;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.LAST_NAME;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.PASSWORD;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.PHONE_NUMBER;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.PROMO_CODE_DISCOUNT;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.PROMO_CODE_VALUE;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.STREET;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.USER_ID;
import static by.larchanka.tiptopcleaning.controller.PagePathConstant.PATH_ALL_PROMO_CODES;
import static by.larchanka.tiptopcleaning.controller.PagePathConstant.PATH_PROFILE;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_ADD_PROMO_CODE_ERROR;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_ADD_PROMO_CODE_SUCCESS;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_DEFAULT_ERROR;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_UPDATE_PROFILE_ERROR;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_UPDATE_PROFILE_SUCCESS;

public class AddPromoCode implements Command {
    @Override
    public CommandResponse execute(HttpServletRequest request) {
        String value = request.getParameter(PROMO_CODE_VALUE);
        byte discount = Byte.parseByte(request.getParameter(PROMO_CODE_DISCOUNT));


        PromoCode promoCode = new PromoCode();
        promoCode.setValue(value);
        promoCode.setDiscountPercentage(discount);

        ServiceStorage creator = ServiceStorage.getInstance();
        PromoCodeService promoCodeService = creator.getPromoCodeService();


        CommandResponse commandResponse = new CommandResponse();
        try {
            Optional<PromoCode> optionalPromoCode = promoCodeService.addPromoCode(promoCode);

            if (optionalPromoCode.isPresent()) {
                commandResponse.setMessage(KEY_ADD_PROMO_CODE_SUCCESS);
            } else {
                commandResponse.setErrorStatus(true);
                commandResponse.setMessage(KEY_ADD_PROMO_CODE_ERROR);
            }
            commandResponse.setTargetURL(PATH_ALL_PROMO_CODES);

        } catch (ServiceException e) {
            commandResponse.setErrorStatus(true);
            commandResponse.setMessage(KEY_DEFAULT_ERROR);
            commandResponse.setTargetURL(PATH_ALL_PROMO_CODES);
        }

        return commandResponse;
    }
}