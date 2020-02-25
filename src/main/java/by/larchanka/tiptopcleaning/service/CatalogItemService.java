package by.larchanka.tiptopcleaning.service;

import by.larchanka.tiptopcleaning.entity.CatalogItem;

import java.util.List;
import java.util.Optional;

public interface CatalogItemService {
    /**
     *Calls dao method
     *
     * @return {@code List<CatalogItem>} object containing catalog items
     */
    List<CatalogItem> getCatalogItemList() throws ServiceException;

    /**
     *Calls dao method
     *
     * @param ids a {@code List<Long>} object to pass into dao method
     * @return {@code List<CatalogItem>} object containing catalog items
     */
    List<CatalogItem> getCatalogItemListByIds(List<Long> ids) throws ServiceException;

    /**
     *Calls dao method
     *
     * @param id a {@code long} value to pass into dao method
     * @return {@code boolean} value. True if deletion was successful and false otherwise.
     */
    boolean deleteCatalogItemById(long id) throws ServiceException;

    /**
     *Calls dao method
     *
     * @param catalogItem a {@code CatalogItem} value to pass into dao method
     * @return {@code Optional<CatalogItem>} object containing catalog item
     */
    Optional<CatalogItem> createCatalogItem(CatalogItem catalogItem) throws ServiceException;

    /**
     *Calls dao method
     *
     * @param catalogItem a {@code CatalogItem} value to pass into dao method
     * @return {@code Optional<CatalogItem>} object containing catalog item
     */
    Optional<CatalogItem> updateCatalogItem(CatalogItem catalogItem) throws ServiceException;
}
