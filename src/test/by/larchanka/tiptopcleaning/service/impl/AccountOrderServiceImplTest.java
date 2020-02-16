package by.larchanka.tiptopcleaning.service.impl;

import by.larchanka.tiptopcleaning.dao.AccountOrderDao;
import by.larchanka.tiptopcleaning.entity.AccountOrder;
import by.larchanka.tiptopcleaning.entity.CatalogItem;
import by.larchanka.tiptopcleaning.entity.OrderItem;
import by.larchanka.tiptopcleaning.entity.PaymentMethod;
import by.larchanka.tiptopcleaning.entity.PromoCode;
import by.larchanka.tiptopcleaning.entity.User;
import by.larchanka.tiptopcleaning.service.AccountOrderService;
import by.larchanka.tiptopcleaning.service.AccountService;
import by.larchanka.tiptopcleaning.service.CatalogItemService;
import by.larchanka.tiptopcleaning.service.PromoCodeService;
import by.larchanka.tiptopcleaning.validator.AccountOrderValidator;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AccountOrderServiceImplTest {
    private AccountOrder accountOrder = new AccountOrder();
    private List<Long> ids;
    private List<CatalogItem> catalogItems;
    private List<OrderItem> orderItemList;

    @Mock
    private AccountOrderDao accountOrderDao;
    @Mock
    private AccountOrderValidator accountOrderValidator;
    @Mock
    private CatalogItemService catalogItemService;
    @Mock
    private PromoCodeService promoCodeService;
    @Mock
    private AccountService accountService;
    private AccountOrderService subject;

    @BeforeMethod
    public void setUpMocks() throws ParseException{
        MockitoAnnotations.initMocks(this);
        subject = new AccountOrderServiceImpl(accountOrderDao, accountOrderValidator, catalogItemService,
                promoCodeService, accountService);

        User user = new User();
        user.setId(1);
        user.setMoney(new BigDecimal(100));

        PromoCode promoCode = new PromoCode("SPRING");
        promoCode.setId(1);
        promoCode.setDiscountPercentage((byte) 10);

        accountOrder.setId(1);
        accountOrder.setUser(user);
        accountOrder.setPromoCode(promoCode);
        accountOrder.setTotalCost(new BigDecimal(30));
        accountOrder.setPaymentMethod(PaymentMethod.ACCOUNT_BALANCE);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = dateFormat.parse("2016/3/24");
        accountOrder.setDateTime(new Timestamp(date.getTime()));

        ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);

        catalogItems = new ArrayList<>();
        CatalogItem catalogItem1 = new CatalogItem();
        catalogItem1.setId(1);
        catalogItem1.setPrice(new BigDecimal(10));
        CatalogItem catalogItem2 = new CatalogItem();
        catalogItem2.setId(2);
        catalogItem2.setPrice(new BigDecimal(20));
        catalogItems.add(catalogItem1);
        catalogItems.add(catalogItem2);

        orderItemList = new ArrayList<>();
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setId(1);
        orderItem1.setCatalogItem(catalogItem1);
        orderItem1.setAmount((byte) 1);
        orderItem1.setAccountOrder(accountOrder);
        OrderItem orderItem2 = new OrderItem();
        orderItem2.setId(2);
        orderItem2.setCatalogItem(catalogItem2);
        orderItem2.setAccountOrder(accountOrder);
        orderItem1.setAmount((byte) 2);
        orderItemList.add(orderItem1);
        orderItemList.add(orderItem2);

        accountOrder.setOrderItemList(orderItemList);
    }

    @Test
    public void createAccountOrderValidResultTest() throws Exception {
        when(accountOrderValidator.validateDate(accountOrder.getDateTime())).thenReturn(true);
        when(catalogItemService.getCatalogItemListByIds(ids)).thenReturn(catalogItems);
        when(promoCodeService.findPromoCodeByName(accountOrder.getPromoCode().getValue())).thenReturn(Optional.of(accountOrder.getPromoCode()));
        when(accountService.findUserById(accountOrder.getUser().getId())).thenReturn(Optional.of(accountOrder.getUser()));
        when(accountOrderDao.createAccountOrder(accountOrder, orderItemList)).thenReturn(Optional.of(accountOrder));
        //when
        Optional<AccountOrder> actual = subject.createAccountOrder(accountOrder);
        //then
        verify(accountOrderValidator, times(1)).validateDate(accountOrder.getDateTime());
        verify(catalogItemService, times(1)).getCatalogItemListByIds(ids);
        verify(promoCodeService, times(1)).findPromoCodeByName(accountOrder.getPromoCode().getValue());
        verify(accountService, times(1)).findUserById(accountOrder.getUser().getId());
        verify(accountOrderDao, times(1)).createAccountOrder(accountOrder, orderItemList);
        Assert.assertEquals(actual, Optional.of(accountOrder));
    }

    @Test
    public void createAccountOrderInvalidResultTest1() throws Exception {
        when(accountOrderValidator.validateDate(accountOrder.getDateTime())).thenReturn(true);
        when(catalogItemService.getCatalogItemListByIds(ids)).thenReturn(catalogItems);
        when(promoCodeService.findPromoCodeByName(accountOrder.getPromoCode().getValue())).thenReturn(Optional.of(accountOrder.getPromoCode()));
        when(accountService.findUserById(accountOrder.getUser().getId())).thenReturn(Optional.of(accountOrder.getUser()));
        when(accountOrderDao.createAccountOrder(accountOrder, orderItemList)).thenReturn(Optional.empty());
        //when
        Optional<AccountOrder> actual = subject.createAccountOrder(accountOrder);
        //then
        verify(accountOrderValidator, times(1)).validateDate(accountOrder.getDateTime());
        verify(catalogItemService, times(1)).getCatalogItemListByIds(ids);
        verify(promoCodeService, times(1)).findPromoCodeByName(accountOrder.getPromoCode().getValue());
        verify(accountService, times(1)).findUserById(accountOrder.getUser().getId());
        verify(accountOrderDao, times(1)).createAccountOrder(accountOrder, orderItemList);
        Assert.assertEquals(actual, Optional.empty());
    }

    @Test
    public void createAccountOrderInvalidResultTest2() throws Exception {
        when(accountOrderValidator.validateDate(accountOrder.getDateTime())).thenReturn(true);
        when(catalogItemService.getCatalogItemListByIds(ids)).thenReturn(catalogItems);
        when(promoCodeService.findPromoCodeByName(accountOrder.getPromoCode().getValue())).thenReturn(Optional.of(accountOrder.getPromoCode()));
        when(accountService.findUserById(accountOrder.getUser().getId())).thenReturn(Optional.empty());
        //when
        Optional<AccountOrder> actual = subject.createAccountOrder(accountOrder);
        //then
        verify(accountOrderValidator, times(1)).validateDate(accountOrder.getDateTime());
        verify(catalogItemService, times(1)).getCatalogItemListByIds(ids);
        verify(promoCodeService, times(1)).findPromoCodeByName(accountOrder.getPromoCode().getValue());
        verify(accountService, times(1)).findUserById(accountOrder.getUser().getId());
        verify(accountOrderDao, never()).createAccountOrder(accountOrder, orderItemList);
        Assert.assertEquals(actual, Optional.empty());
    }

    @Test
    public void createAccountOrderInvalidResultTest3() throws Exception {
        when(accountOrderValidator.validateDate(accountOrder.getDateTime())).thenReturn(true);
        when(catalogItemService.getCatalogItemListByIds(ids)).thenReturn(catalogItems);
        when(promoCodeService.findPromoCodeByName(accountOrder.getPromoCode().getValue())).thenReturn(Optional.empty());
        //when
        Optional<AccountOrder> actual = subject.createAccountOrder(accountOrder);
        //then
        verify(accountOrderValidator, times(1)).validateDate(accountOrder.getDateTime());
        verify(catalogItemService, times(1)).getCatalogItemListByIds(ids);
        verify(promoCodeService, times(1)).findPromoCodeByName(accountOrder.getPromoCode().getValue());
        verify(accountService, never()).findUserById(accountOrder.getUser().getId());
        verify(accountOrderDao, never()).createAccountOrder(accountOrder, orderItemList);
        Assert.assertEquals(actual, Optional.empty());
    }

    @Test
    public void createAccountOrderInvalidResultTest4() throws Exception {
        catalogItems.remove(1);

        when(accountOrderValidator.validateDate(accountOrder.getDateTime())).thenReturn(true);
        when(catalogItemService.getCatalogItemListByIds(ids)).thenReturn(catalogItems);
        //when
        Optional<AccountOrder> actual = subject.createAccountOrder(accountOrder);
        //then
        verify(accountOrderValidator, times(1)).validateDate(accountOrder.getDateTime());
        verify(catalogItemService, times(1)).getCatalogItemListByIds(ids);
        verify(promoCodeService, never()).findPromoCodeByName(accountOrder.getPromoCode().getValue());
        verify(accountService, never()).findUserById(accountOrder.getUser().getId());
        verify(accountOrderDao, never()).createAccountOrder(accountOrder, orderItemList);
        Assert.assertEquals(actual, Optional.empty());
    }

    @Test
    public void createAccountOrderInvalidResultTest5() throws Exception {
        when(accountOrderValidator.validateDate(accountOrder.getDateTime())).thenReturn(false);
        //when
        Optional<AccountOrder> actual = subject.createAccountOrder(accountOrder);
        //then
        verify(accountOrderValidator, times(1)).validateDate(accountOrder.getDateTime());
        verify(catalogItemService, never()).getCatalogItemListByIds(ids);
        verify(promoCodeService, never()).findPromoCodeByName(accountOrder.getPromoCode().getValue());
        verify(accountService, never()).findUserById(accountOrder.getUser().getId());
        verify(accountOrderDao, never()).createAccountOrder(accountOrder, orderItemList);
        Assert.assertEquals(actual, Optional.empty());
    }
}
