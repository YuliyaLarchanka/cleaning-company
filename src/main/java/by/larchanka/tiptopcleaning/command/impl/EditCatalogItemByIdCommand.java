package by.larchanka.tiptopcleaning.command.impl;

import by.larchanka.tiptopcleaning.command.Command;
import by.larchanka.tiptopcleaning.command.CommandResponse;
import by.larchanka.tiptopcleaning.entity.CatalogItem;
import by.larchanka.tiptopcleaning.service.CatalogItemService;
import by.larchanka.tiptopcleaning.service.ServiceException;
import by.larchanka.tiptopcleaning.service.ServiceStorage;
import by.larchanka.tiptopcleaning.util.UploadFileHelper;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Optional;

import static by.larchanka.tiptopcleaning.command.PageParameterConstant.CATALOG_ITEM_ID;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.CATALOG_ITEM_MULTIPLE_SUPPORTED;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.CATALOG_ITEM_NAME;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.CATALOG_ITEM_PRICE;
import static by.larchanka.tiptopcleaning.controller.PagePathConstant.PATH_CATALOG_ITEMS;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_DEFAULT_ERROR;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_UPDATE_CATALOG_ITEM_ERROR;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_UPDATE_CATALOG_ITEM_SUCCESS;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_UPLOAD_CATALOG_ITEM_IMAGE_ERROR;

public class EditCatalogItemByIdCommand implements Command {
    @Override
    public CommandResponse execute(HttpServletRequest request) {
        CommandResponse commandResponse = new CommandResponse();

        long id = Long.parseLong(request.getParameter(CATALOG_ITEM_ID));
        String name = request.getParameter(CATALOG_ITEM_NAME);
        boolean multipleSupported = Boolean.parseBoolean(request.getParameter(CATALOG_ITEM_MULTIPLE_SUPPORTED));
        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(request.getParameter(CATALOG_ITEM_PRICE)));

        UploadFileHelper uploadFileHelper = new UploadFileHelper();
        Optional<String> imageNameOptional = uploadFileHelper.uploadFile(request);

        String imageName;
        if (imageNameOptional.isPresent()) {
            imageName = imageNameOptional.get();
        } else {
            commandResponse.setErrorStatus(true);
            commandResponse.setMessage(KEY_UPLOAD_CATALOG_ITEM_IMAGE_ERROR);
            commandResponse.setTargetURL(PATH_CATALOG_ITEMS);
            return commandResponse;
        }

        CatalogItem updatedCatalogItem = new CatalogItem();
        updatedCatalogItem.setName(name);
        updatedCatalogItem.setPrice(price);
        updatedCatalogItem.setMultipleSupported(multipleSupported);
        updatedCatalogItem.setImageName(imageName);
        updatedCatalogItem.setId(id);

        ServiceStorage creator = ServiceStorage.getInstance();
        CatalogItemService catalogItemService = creator.getCatalogItemService();


        try {
            Optional<CatalogItem> optionalCatalogItem = catalogItemService.updateCatalogItem(updatedCatalogItem);

            if(optionalCatalogItem.isPresent()){
                commandResponse.setMessage(KEY_UPDATE_CATALOG_ITEM_SUCCESS);
            }else{
                commandResponse.setErrorStatus(true);
                commandResponse.setMessage(KEY_UPDATE_CATALOG_ITEM_ERROR);
            }
            commandResponse.setTargetURL(PATH_CATALOG_ITEMS);
        } catch (ServiceException e){
            commandResponse.setErrorStatus(true);
            commandResponse.setMessage(KEY_DEFAULT_ERROR);
            commandResponse.setTargetURL(PATH_CATALOG_ITEMS);
        }

        return commandResponse;
    }
}