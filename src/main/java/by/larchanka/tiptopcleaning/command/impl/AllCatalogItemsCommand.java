package by.larchanka.tiptopcleaning.command.impl;

import by.larchanka.tiptopcleaning.command.Command;
import by.larchanka.tiptopcleaning.command.CommandResponse;
import by.larchanka.tiptopcleaning.entity.CatalogItem;
import by.larchanka.tiptopcleaning.service.CatalogItemService;
import by.larchanka.tiptopcleaning.service.ServiceException;
import by.larchanka.tiptopcleaning.service.ServiceStorage;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static by.larchanka.tiptopcleaning.command.PageParameterConstant.CATALOG_ITEM_LIST;
import static by.larchanka.tiptopcleaning.controller.PagePathConstant.PATH_ERROR;
import static by.larchanka.tiptopcleaning.controller.PagePathConstant.PATH_REAL_CATALOG_ITEMS;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_DEFAULT_ERROR;

public class AllCatalogItemsCommand implements Command {
    @Override
    public CommandResponse execute(HttpServletRequest request) {
        ServiceStorage factory = ServiceStorage.getInstance();
        CatalogItemService catalogItemService = factory.getCatalogItemService();
        CommandResponse commandResponse = new CommandResponse();

        try {
            List<CatalogItem> catalogItemList = catalogItemService.getCatalogItemList();
            request.setAttribute(CATALOG_ITEM_LIST, catalogItemList);
            commandResponse.setTargetURL(PATH_REAL_CATALOG_ITEMS);
        } catch (ServiceException e) {
            commandResponse.setErrorStatus(true);
            commandResponse.setMessage(KEY_DEFAULT_ERROR);
            commandResponse.setTargetURL(PATH_ERROR);
        }

        return commandResponse;
    }
}
