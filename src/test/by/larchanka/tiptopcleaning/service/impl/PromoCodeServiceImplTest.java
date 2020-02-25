package by.larchanka.tiptopcleaning.service.impl;

import by.larchanka.tiptopcleaning.dao.PromoCodeDao;
import by.larchanka.tiptopcleaning.entity.PromoCode;
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

public class PromoCodeServiceImplTest {
    private static final String VALID_PROMO_CODE_NAME = "SPRING2020";
    private static final String INVALID_PROMO_CODE_NAME = "HELLO";
    private static final int PROMO_CODE_ID = 1;
    private PromoCode promoCode = new PromoCode(VALID_PROMO_CODE_NAME);
    private PromoCode promoCode2 = new PromoCode(INVALID_PROMO_CODE_NAME);

    @Mock
    private PromoCodeDao promoCodeDao;
    private PromoCodeServiceImpl subject;

    @BeforeMethod
    public void setUpMocks() {
        MockitoAnnotations.initMocks(this);
        subject = new PromoCodeServiceImpl(promoCodeDao);
    }

    @Test
    public void findPromoCodeByNameValidResultTest() throws Exception {
        when(promoCodeDao.findPromoCodeByName(VALID_PROMO_CODE_NAME)).thenReturn(Optional.of(promoCode));
        //when
        Optional<PromoCode> actual = subject.findPromoCodeByName(VALID_PROMO_CODE_NAME);
        //then
        verify(promoCodeDao, times(1)).findPromoCodeByName(VALID_PROMO_CODE_NAME);
        Assert.assertEquals(actual, Optional.of(promoCode));
    }

    @Test
    public void findPromoCodeByNameInvalidResultTest() throws Exception {
        when(promoCodeDao.findPromoCodeByName(INVALID_PROMO_CODE_NAME)).thenReturn(Optional.empty());
        //when
        Optional<PromoCode> actual = subject.findPromoCodeByName(INVALID_PROMO_CODE_NAME);
        //then
        verify(promoCodeDao, times(1)).findPromoCodeByName(INVALID_PROMO_CODE_NAME);
        Assert.assertEquals(actual, Optional.empty());
    }

    @Test
    public void findAllPromoCodesValidResultTest() throws Exception {
        List<PromoCode> promoCodeList = new ArrayList<>();
        promoCodeList.add(promoCode);
        promoCodeList.add(promoCode2);

        when(promoCodeDao.findAllPromoCodes()).thenReturn(promoCodeList);
        //when
        List<PromoCode> actual = subject.findAllPromoCodes();
        //then
        verify(promoCodeDao, times(1)).findAllPromoCodes();
        Assert.assertEquals(actual, promoCodeList);
    }

    @Test
    public void deletePromoCodeByIdValidResultTest() throws Exception {
        when(promoCodeDao.deletePromoCodeById(PROMO_CODE_ID)).thenReturn(true);
        //when
        boolean actual = subject.deletePromoCodeById(PROMO_CODE_ID);
        //then
        verify(promoCodeDao, times(1)).deletePromoCodeById(PROMO_CODE_ID);
        Assert.assertTrue(actual);
    }

    @Test
    public void deletePromoCodeByInvalidResultTest() throws Exception {
        when(promoCodeDao.deletePromoCodeById(PROMO_CODE_ID)).thenReturn(false);
        //when
        boolean actual = subject.deletePromoCodeById(PROMO_CODE_ID);
        //then
        verify(promoCodeDao, times(1)).deletePromoCodeById(PROMO_CODE_ID);
        Assert.assertFalse(actual);
    }

    @Test
    public void addPromoCodeValidResultTest() throws Exception {
        when(promoCodeDao.addPromoCode(promoCode)).thenReturn(Optional.of(promoCode));
        //when
        Optional<PromoCode> actual = subject.addPromoCode(promoCode);
        //then
        verify(promoCodeDao, times(1)).addPromoCode(promoCode);
        Assert.assertEquals(actual, Optional.of(promoCode));
    }

    @Test
    public void addPromoCodeInvalidResultTest() throws Exception {
        when(promoCodeDao.addPromoCode(promoCode)).thenReturn(Optional.empty());
        //when
        Optional<PromoCode> actual = subject.addPromoCode(promoCode);
        //then
        verify(promoCodeDao, times(1)).addPromoCode(promoCode);
        Assert.assertEquals(actual, Optional.empty());
    }
}
