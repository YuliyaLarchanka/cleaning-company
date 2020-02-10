package by.larchanka.tiptopcleaning.command.impl;

import by.larchanka.tiptopcleaning.command.Command;
import by.larchanka.tiptopcleaning.command.CommandResponse;
import by.larchanka.tiptopcleaning.entity.PromoCode;
import by.larchanka.tiptopcleaning.service.PromoCodeService;
import by.larchanka.tiptopcleaning.service.ServiceException;
import by.larchanka.tiptopcleaning.service.ServiceStorage;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static by.larchanka.tiptopcleaning.command.PageParameterConstant.PROMO_CODE_DISCOUNT;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.PROMO_CODE_VALUE;
import static by.larchanka.tiptopcleaning.controller.PagePathConstant.PATH_ALL_PROMO_CODES;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_ADD_PROMO_CODE_ERROR;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_ADD_PROMO_CODE_SUCCESS;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_DEFAULT_ERROR;

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