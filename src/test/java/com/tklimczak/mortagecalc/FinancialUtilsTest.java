package com.tklimczak.mortagecalc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import com.tklimczak.mortagecalc.services.utils.FinancialUtils;

public class FinancialUtilsTest {
    @Test
    public void interestFactorTest() throws IllegalArgumentException {
        BigDecimal factor = FinancialUtils.calculateInterestFactor(new BigDecimal("4"));
        Assert.assertNotNull(factor);
        Assert.assertTrue(factor.compareTo(new BigDecimal("0.00333333")) == 0);
    }

    @Test(expected = NullPointerException.class)
    public void interestFactorExceptionNullTest() throws IllegalArgumentException {
    	FinancialUtils.calculateInterestFactor(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void interestFactorExceptionTest() throws IllegalArgumentException {
        FinancialUtils.calculateInterestFactor(BigDecimal.ONE.negate());
    }

    @Test
    public void pmtTest() throws IllegalArgumentException {
        BigDecimal pmt = FinancialUtils.calculateAnnuity(FinancialUtils.calculateInterestFactor(new BigDecimal("4")), (short)360, new BigDecimal("100000"), BigDecimal.ZERO, false)
                .negate();
        Assert.assertNotNull(pmt);
        Assert.assertTrue(pmt.setScale(0, RoundingMode.HALF_DOWN).compareTo(new BigDecimal("477")) == 0);
    }
   
    @Test(expected = NullPointerException.class)
    public void wholeInterestExceptionTest() {
        FinancialUtils.calculateInterests(null);
    }

    @Test
    public void wholeInterestEmptyTest() {
        Assert.assertTrue(FinancialUtils.calculateInterests(new ArrayList<>()).signum() == 0);
    }

    @Test(expected = NullPointerException.class)
    public void wholeCapitalExceptionTest() {
    	FinancialUtils.calculateCapitalPaid(null);
    }

    @Test
    public void wholeCapitalEmptyTest() {
        Assert.assertTrue(FinancialUtils.calculateCapitalPaid(new ArrayList<>()).signum() == 0);
    }
}
