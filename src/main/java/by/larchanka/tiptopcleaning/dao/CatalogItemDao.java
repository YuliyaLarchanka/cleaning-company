package by.larchanka.tiptopcleaning.dao;

import by.larchanka.tiptopcleaning.entity.CatalogItem;

import java.util.List;
import java.util.Optional;

public interface CatalogItemDao {
    List<CatalogItem> getCatalogItemList() throws DaoException;

    List<CatalogItem> getCatalogItemListByIds(List<Long> ids) throws DaoException;

    boolean deleteCatalogItemById(long id) throws DaoException;

    Optional<CatalogItem> createCatalogItem(CatalogItem catalogItem) throws DaoException;

    Optional<CatalogItem> updateCatalogItem(CatalogItem catalogItem) throws DaoException;
}
