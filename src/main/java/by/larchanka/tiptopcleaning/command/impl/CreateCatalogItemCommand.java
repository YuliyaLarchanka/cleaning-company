package by.larchanka.tiptopcleaning.command.impl;

import by.larchanka.tiptopcleaning.command.Command;
import by.larchanka.tiptopcleaning.command.CommandResponse;
import by.larchanka.tiptopcleaning.entity.CatalogItem;
import by.larchanka.tiptopcleaning.service.CatalogItemService;
import by.larchanka.tiptopcleaning.service.ServiceException;
import by.larchanka.tiptopcleaning.service.ServiceStorage;
import by.larchanka.tiptopcleaning.util.UploadFileHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Optional;

import static by.larchanka.tiptopcleaning.command.PageParameterConstant.CATALOG_ITEM_MULTIPLE_SUPPORTED;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.CATALOG_ITEM_NAME;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.CATALOG_ITEM_PRICE;
import static by.larchanka.tiptopcleaning.controller.PagePathConstant.PATH_CATALOG_ITEMS;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_ADD_CATALOG_ITEM_ERROR;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_ADD_CATALOG_ITEM_SUCCESS;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_DEFAULT_ERROR;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_UPLOAD_CATALOG_ITEM_IMAGE_ERROR;

public class CreateCatalogItemCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResponse execute(HttpServletRequest request) {
        CommandResponse commandResponse = new CommandResponse();

        String name = request.getParameter(CATALOG_ITEM_NAME);
        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(request.getParameter(CATALOG_ITEM_PRICE)));
        boolean multipleSupported = request.getParameter(CATALOG_ITEM_MULTIPLE_SUPPORTED) != null;

        UploadFileHelper uploadFileHelper = new UploadFileHelper();
        Optional<String> imageNameOptional = uploadFileHelper.uploadFile(request);

        String imageName = null;
        if (imageNameOptional.isPresent()) {
            imageName = imageNameOptional.get();
        } else {
            commandResponse.setErrorStatus(true);
            commandResponse.setMessage(KEY_UPLOAD_CATALOG_ITEM_IMAGE_ERROR);
            commandResponse.setTargetURL(PATH_CATALOG_ITEMS);
            return commandResponse;
        }

        CatalogItem catalogItem = new CatalogItem();
        catalogItem.setName(name);
        catalogItem.setPrice(price);
        catalogItem.setMultipleSupported(multipleSupported);
        catalogItem.setImageName(imageName);

        ServiceStorage creator = ServiceStorage.getInstance();
        CatalogItemService catalogItemService = creator.getCatalogItemService();

        try {
            Optional<CatalogItem> optionalCatalogItem = catalogItemService.createCatalogItem(catalogItem);

            if (optionalCatalogItem.isPresent()) {
                commandResponse.setMessage(KEY_ADD_CATALOG_ITEM_SUCCESS);
            } else {
                commandResponse.setErrorStatus(true);
                commandResponse.setMessage(KEY_ADD_CATALOG_ITEM_ERROR);
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
