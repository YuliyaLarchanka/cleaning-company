package by.larchanka.tiptopcleaning.service;

import by.larchanka.tiptopcleaning.entity.CatalogItem;

import java.util.List;
import java.util.Optional;

public interface CatalogItemService {
    List<CatalogItem> getCatalogItemList() throws ServiceException;

    List<CatalogItem> getCatalogItemListByIds(List<Long> ids) throws ServiceException;

    boolean deleteCatalogItemById(long id) throws ServiceException;

    Optional<CatalogItem> createCatalogItem(CatalogItem catalogItem) throws ServiceException;

    Optional<CatalogItem> updateCatalogItem(CatalogItem catalogItem) throws ServiceException;
}
