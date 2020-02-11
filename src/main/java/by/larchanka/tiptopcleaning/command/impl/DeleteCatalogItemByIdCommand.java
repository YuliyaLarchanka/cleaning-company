package by.larchanka.tiptopcleaning.command.impl;

import by.larchanka.tiptopcleaning.command.Command;
import by.larchanka.tiptopcleaning.command.CommandResponse;
import by.larchanka.tiptopcleaning.service.CatalogItemService;
import by.larchanka.tiptopcleaning.service.ServiceException;
import by.larchanka.tiptopcleaning.service.ServiceStorage;

import javax.servlet.http.HttpServletRequest;

import static by.larchanka.tiptopcleaning.command.PageParameterConstant.CATALOG_ITEM_ID;
import static by.larchanka.tiptopcleaning.controller.PagePathConstant.PATH_CATALOG_ITEMS;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_DEFAULT_ERROR;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_DELETE_CATALOG_ITEM_ERROR;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_DELETE_CATALOG_ITEM_SUCCESS;

public class DeleteCatalogItemByIdCommand implements Command {
    @Override
    public CommandResponse execute(HttpServletRequest request) {
        ServiceStorage creator = ServiceStorage.getInstance();
        CatalogItemService  catalogItemService = creator.getCatalogItemService();

        long id = Long.parseLong(request.getParameter(CATALOG_ITEM_ID));

        CommandResponse commandResponse = new CommandResponse();
        
        try {
            boolean isDeleted = catalogItemService.deleteCatalogItemById(id);

            if (isDeleted) {
                commandResponse.setMessage(KEY_DELETE_CATALOG_ITEM_SUCCESS);
            } else {
                commandResponse.setErrorStatus(true);
                commandResponse.setMessage(KEY_DELETE_CATALOG_ITEM_ERROR);
            }
            commandResponse.setTargetURL(PATH_CATALOG_ITEMS);
        } catch (ServiceException e) {
            commandResponse.setErrorStatus(true);
            commandResponse.setMessage(KEY_DEFAULT_ERROR);
            commandResponse.setTargetURL(PATH_CATALOG_ITEMS);
        }

        return commandResponse;
    }
}
