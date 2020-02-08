package by.larchanka.tiptopcleaning.command.impl;

import by.larchanka.tiptopcleaning.command.Command;
import by.larchanka.tiptopcleaning.command.CommandResponse;
import by.larchanka.tiptopcleaning.service.PromoCodeService;
import by.larchanka.tiptopcleaning.service.ServiceException;
import by.larchanka.tiptopcleaning.service.ServiceStorage;

import javax.servlet.http.HttpServletRequest;

import static by.larchanka.tiptopcleaning.command.PageParameterConstant.PROMO_CODE_ID;
import static by.larchanka.tiptopcleaning.controller.PagePathConstant.PATH_ALL_PROMO_CODES;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_DEFAULT_ERROR;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_DELETE_PROMO_CODE_ERROR;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_DELETE_PROMO_CODE_SUCCESS;

public class DeletePromoCodeByIdCommand implements Command {
    @Override
    public CommandResponse execute(HttpServletRequest request) {
        ServiceStorage creator = ServiceStorage.getInstance();
        PromoCodeService promoCodeService = creator.getPromoCodeService();

        long id = Long.parseLong(request.getParameter(PROMO_CODE_ID));

        CommandResponse commandResponse = new CommandResponse();


        try {
            boolean isDeleted = promoCodeService.deletePromoCodeById(id);

            if (isDeleted) {
                commandResponse.setMessage(KEY_DELETE_PROMO_CODE_SUCCESS);
            } else {
                commandResponse.setErrorStatus(true);
                commandResponse.setMessage(KEY_DELETE_PROMO_CODE_ERROR);
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
