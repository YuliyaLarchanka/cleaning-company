package by.larchanka.tiptopcleaning.command.impl;

import by.larchanka.tiptopcleaning.command.Command;
import by.larchanka.tiptopcleaning.command.CommandResponse;
import by.larchanka.tiptopcleaning.entity.PromoCode;
import by.larchanka.tiptopcleaning.service.PromoCodeService;
import by.larchanka.tiptopcleaning.service.ServiceException;
import by.larchanka.tiptopcleaning.service.ServiceStorage;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static by.larchanka.tiptopcleaning.command.PageParameterConstant.PROMO_CODE_LIST;
import static by.larchanka.tiptopcleaning.controller.PagePathConstant.PATH_ERROR;
import static by.larchanka.tiptopcleaning.controller.PagePathConstant.PATH_REAL_ALL_PROMO_CODES;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_DEFAULT_ERROR;

public class FindAllPromoCodes implements Command {
    @Override
    public CommandResponse execute(HttpServletRequest request) {
        ServiceStorage factory = ServiceStorage.getInstance();
        PromoCodeService promoCodeService = factory.getPromoCodeService();
        CommandResponse commandResponse = new CommandResponse();

        try {
            List<PromoCode> promoCodeList = promoCodeService.findAllPromoCodes();
            request.setAttribute(PROMO_CODE_LIST, promoCodeList);
            commandResponse.setTargetURL(PATH_REAL_ALL_PROMO_CODES);
        } catch (ServiceException e) {
            commandResponse.setErrorStatus(true);
            commandResponse.setMessage(KEY_DEFAULT_ERROR);
            commandResponse.setTargetURL(PATH_ERROR);
        }

        return commandResponse;
    }
}
