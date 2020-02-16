package by.larchanka.tiptopcleaning.service.impl;

import by.larchanka.tiptopcleaning.dao.CatalogItemDao;
import by.larchanka.tiptopcleaning.entity.CatalogItem;
import by.larchanka.tiptopcleaning.service.CatalogItemService;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CatalogItemServiceImplTest {
    private static final long CATALOG_ITEM_ID = 1;

    @Mock
    private CatalogItemDao catalogItemDao;
    private CatalogItemService subject;
    private List<CatalogItem> catalogItemList;
    private CatalogItem catalogItem;

    @BeforeMethod
    public void setUpMocks() {
        MockitoAnnotations.initMocks(this);
        subject = new CatalogItemServiceImpl(catalogItemDao);

        catalogItem = new CatalogItem();
        catalogItem.setId(1);
        CatalogItem catalogItem2 = new CatalogItem();
        catalogItem2.setId(2);

        catalogItemList = new ArrayList<>();
        catalogItemList.add(catalogItem);
        catalogItemList.add(catalogItem2);
    }

    @Test
    public void getCatalogItemListValidResultTest() throws Exception {
        when(catalogItemDao.getCatalogItemList()).thenReturn(catalogItemList);
        //when
        List<CatalogItem> actual = subject.getCatalogItemList();
        //then
        verify(catalogItemDao, times(1)).getCatalogItemList();
        Assert.assertEquals(actual, catalogItemList);
    }

    @Test
    public void getCatalogItemListByIdsValidResultTest() throws Exception {
        List<Long> ids = new ArrayList<>();
        ids.add((long)1);
        ids.add((long)2);

        when(catalogItemDao.getCatalogItemListByIds(ids)).thenReturn(catalogItemList);
        //when
        List<CatalogItem> actual = subject.getCatalogItemListByIds(ids);
        //then
        verify(catalogItemDao, times(1)).getCatalogItemListByIds(ids);
        Assert.assertEquals(actual, catalogItemList);
    }

    @Test
    public void deleteCatalogItemByIdValidResultTest() throws Exception {
        when(catalogItemDao.deleteCatalogItemById(CATALOG_ITEM_ID)).thenReturn(true);
        //when
        boolean actual = subject.deleteCatalogItemById(CATALOG_ITEM_ID);
        //then
        verify(catalogItemDao, times(1)).deleteCatalogItemById(CATALOG_ITEM_ID);
        Assert.assertTrue(actual);
    }

    @Test
    public void deleteCatalogItemByIdInvalidResultTest() throws Exception {
        when(catalogItemDao.deleteCatalogItemById(CATALOG_ITEM_ID)).thenReturn(false);
        //when
        boolean actual = subject.deleteCatalogItemById(CATALOG_ITEM_ID);
        //then
        verify(catalogItemDao, times(1)).deleteCatalogItemById(CATALOG_ITEM_ID);
        Assert.assertFalse(actual);
    }

    @Test
    public void createCatalogItemValidResultTest() throws Exception {
        when(catalogItemDao.createCatalogItem(catalogItem)).thenReturn(Optional.of(catalogItem));
        //when
        Optional<CatalogItem> actual = subject.createCatalogItem(catalogItem);
        //then
        verify(catalogItemDao, times(1)).createCatalogItem(catalogItem);
        Assert.assertEquals(actual, Optional.of(catalogItem));
    }

    @Test
    public void createCatalogItemInvalidResultTest() throws Exception {
        when(catalogItemDao.createCatalogItem(catalogItem)).thenReturn(Optional.empty());
        //when
        Optional<CatalogItem> actual = subject.createCatalogItem(catalogItem);
        //then
        verify(catalogItemDao, times(1)).createCatalogItem(catalogItem);
        Assert.assertEquals(actual, Optional.empty());
    }

    @Test
    public void updateCatalogItemValidResultTest() throws Exception {
        when(catalogItemDao.updateCatalogItem(catalogItem)).thenReturn(Optional.of(catalogItem));
        //when
        Optional<CatalogItem> actual = subject.updateCatalogItem(catalogItem);
        //then
        verify(catalogItemDao, times(1)).updateCatalogItem(catalogItem);
        Assert.assertEquals(actual, Optional.of(catalogItem));
    }

    @Test
    public void updateCatalogItemInvalidResultTest() throws Exception {
        when(catalogItemDao.updateCatalogItem(catalogItem)).thenReturn(Optional.empty());
        //when
        Optional<CatalogItem> actual = subject.updateCatalogItem(catalogItem);
        //then
        verify(catalogItemDao, times(1)).updateCatalogItem(catalogItem);
        Assert.assertEquals(actual, Optional.empty());
    }
}
