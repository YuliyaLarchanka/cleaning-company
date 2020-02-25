package by.larchanka.tiptopcleaning.dao;

import by.larchanka.tiptopcleaning.entity.CatalogItem;

import java.util.List;
import java.util.Optional;

public interface CatalogItemDao {
    /**
     *Gets all catalog items in database
     *
     * @return {@code List<CatalogItem>} object containing catalog items
     */
    List<CatalogItem> getCatalogItemList() throws DaoException;

    /**
     *Gets catalog items from database by id
     *
     * @param ids a {@code List<Long>} object to find catalog items with
     * @return {@code List<CatalogItem>} object containing catalog items
     */
    List<CatalogItem> getCatalogItemListByIds(List<Long> ids) throws DaoException;

    /**
     *Deletes catalog item from database by id
     *
     * @param id a {@code long} value to find catalog item with
     * @return {@code boolean} value. True if deletion was successful and false otherwise.
     */
    boolean deleteCatalogItemById(long id) throws DaoException;

    /**
     *Sets new catalog item in database
     *
     * @param catalogItem a {@code CatalogItem} object to set
     * @return {@code Optional<CatalogItem>} object containing catalog item
     */
    Optional<CatalogItem> createCatalogItem(CatalogItem catalogItem) throws DaoException;

    /**
     *Updates catalog item in database
     *
     * @param catalogItem a {@code CatalogItem} object to set
     * @return {@code Optional<CatalogItem>} object containing catalog item
     */
    Optional<CatalogItem> updateCatalogItem(CatalogItem catalogItem) throws DaoException;
}
