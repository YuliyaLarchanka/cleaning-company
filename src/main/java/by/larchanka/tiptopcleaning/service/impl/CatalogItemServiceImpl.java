package by.larchanka.tiptopcleaning.service.impl;

import by.larchanka.tiptopcleaning.dao.CatalogItemDao;
import by.larchanka.tiptopcleaning.dao.DaoCreator;
import by.larchanka.tiptopcleaning.dao.DaoException;
import by.larchanka.tiptopcleaning.entity.CatalogItem;
import by.larchanka.tiptopcleaning.service.CatalogItemService;
import by.larchanka.tiptopcleaning.service.ServiceException;

import java.util.List;
import java.util.Optional;

public class CatalogItemServiceImpl implements CatalogItemService {

    private DaoCreator creator = DaoCreator.getInstance();
    private CatalogItemDao dao = creator.getCatalogItemDao();

    @Override
    public List<CatalogItem> getCatalogItemList() throws ServiceException {
        List<CatalogItem> catalogItemList;
        try {
            catalogItemList = dao.getCatalogItemList();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return catalogItemList;
    }

    @Override
    public List<CatalogItem> getCatalogItemListByIds(List<Long> ids) throws ServiceException {
        List<CatalogItem> catalogItemList;
        try {
            catalogItemList = dao.getCatalogItemListByIds(ids);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return catalogItemList;
    }

    @Override
    public boolean deleteCatalogItemById(long id) throws ServiceException{
        CatalogItemDao dao = creator.getCatalogItemDao();

        try {
            return dao.deleteCatalogItemById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<CatalogItem> createCatalogItem(CatalogItem catalogItem) throws ServiceException{
        CatalogItemDao dao = creator.getCatalogItemDao();

        try {
            return dao.createCatalogItem(catalogItem);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<CatalogItem> updateCatalogItem(CatalogItem catalogItem) throws ServiceException{
        CatalogItemDao dao = creator.getCatalogItemDao();

        try {
            return dao.updateCatalogItem(catalogItem);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
