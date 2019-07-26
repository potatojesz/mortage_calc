package com.tklimczak.mortagecalc;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.tklimczak.mortagecalc.domain.enums.MortageType;
import com.tklimczak.mortagecalc.domain.enums.OverpaymentPeriod;
import com.tklimczak.mortagecalc.domain.enums.OverpaymentType;
import com.tklimczak.mortagecalc.domain.models.Installment;
import com.tklimczak.mortagecalc.domain.models.Mortage;
import com.tklimczak.mortagecalc.domain.models.Overpayment;
import com.tklimczak.mortagecalc.domain.models.OverpaymentResult;
import com.tklimczak.mortagecalc.services.OverpaymentCalculatorService;
import com.tklimczak.mortagecalc.services.impl.OverpaymentCalculatorServiceLocal;

public class OverpaymentCalculatorServiceTest {
    public transient OverpaymentCalculatorService service;
    private Mortage mortage;
    private Overpayment overpayment;

    @Before
    public void prepareData() {
        service = new OverpaymentCalculatorServiceLocal();
        mortage = new Mortage(MortageType.CONSTANT_INSTALLMENT, new BigDecimal("3.79"), new BigDecimal("250000"), (short)360);
        overpayment = new Overpayment(OverpaymentType.SHORTER_PERIOD, OverpaymentPeriod.MONTHLY, new BigDecimal("1000"));
    }

    @Test(expected = NullPointerException.class)
    public void calculateNullExceptionTest() throws IllegalArgumentException {
        service.calculate(null, null);
    }

    @Test
    public void calculateMonthlyOverpaymentShorterPeriodTest() throws IllegalArgumentException {
        OverpaymentResult result = service.calculate(overpayment, mortage);
        Assert.assertNotNull(result);
        Assert.assertTrue(result.getNewMonths() == 144);
        Assert.assertNotNull(result.getInstallments());
        Assert.assertTrue(result.getInstallments().size() > 0);
        Assert.assertTrue(result.getInterest().setScale(0, RoundingMode.HALF_DOWN).compareTo(new BigDecimal("61530")) == 0);

        final Installment installment = result.getInstallments().get(1);
        Assert.assertTrue(installment.getWhole().setScale(0,RoundingMode.HALF_DOWN).compareTo(new BigDecimal("2163")) == 0);
        Assert.assertTrue(installment.getOverpayPart().compareTo(new BigDecimal("1000")) == 0);
    }

    @Test
    public void calculateYearlyOverpaymentShorterPeriodTest() throws IllegalArgumentException {
        overpayment.setPeriod(OverpaymentPeriod.YEARLY);
        overpayment.setOverpayAmount(new BigDecimal("12000"));

        OverpaymentResult result = service.calculate(overpayment, mortage);
        Assert.assertNotNull(result);
        Assert.assertTrue(result.getNewMonths() == 147);
        Assert.assertNotNull(result.getInstallments());
        Assert.assertTrue(result.getInstallments().size() > 0);
        Assert.assertTrue(result.getInterest().setScale(0, RoundingMode.HALF_DOWN).compareTo(new BigDecimal("64688")) == 0);

        final Installment installment = result.getInstallments().get(11);
        Assert.assertTrue(installment.getWhole().setScale(0,RoundingMode.HALF_DOWN).compareTo(new BigDecimal("13163")) == 0);
        Assert.assertTrue(installment.getOverpayPart().compareTo(new BigDecimal("12000")) == 0);
    }

    @Test
    public void calculateMonthlyOverpaymentLesserInstallmentsTest() throws IllegalArgumentException {
        overpayment.setType(OverpaymentType.LESSER_INSTALLMENTS);
        OverpaymentResult result = service.calculate(overpayment, mortage);
        Assert.assertNotNull(result);
        Assert.assertTrue(result.getNewMonths() == 199);
        Assert.assertNotNull(result.getInstallments());
        Assert.assertTrue(result.getInstallments().size() > 0);
        Assert.assertTrue(result.getInterest().setScale(0, RoundingMode.HALF_DOWN).compareTo(new BigDecimal("75309")) == 0);

        final Installment installment = result.getInstallments().get(1);
        Assert.assertTrue(installment.getWhole().setScale(0,RoundingMode.HALF_DOWN).compareTo(new BigDecimal("2159")) == 0);
        Assert.assertTrue(installment.getOverpayPart().compareTo(new BigDecimal("1000")) == 0);
    }

    @Test
    public void calculateYearlyOverpaymentLesserInstallmentsTest() throws IllegalArgumentException {
        overpayment.setType(OverpaymentType.LESSER_INSTALLMENTS);
        overpayment.setPeriod(OverpaymentPeriod.YEARLY);
        overpayment.setOverpayAmount(new BigDecimal("15000"));

        OverpaymentResult result = service.calculate(overpayment, mortage);
        Assert.assertNotNull(result);
        Assert.assertTrue(result.getNewMonths() == 168);
        Assert.assertNotNull(result.getInstallments());
        Assert.assertTrue(result.getInstallments().size() > 0);
        System.out.println(result.getInterest());
        Assert.assertTrue(result.getInterest().setScale(0, RoundingMode.HALF_DOWN).compareTo(new BigDecimal("67026")) == 0);

        final Installment installment = result.getInstallments().get(11);
        Assert.assertTrue(installment.getWhole().setScale(0,RoundingMode.HALF_DOWN).compareTo(new BigDecimal("16163")) == 0);
        Assert.assertTrue(installment.getOverpayPart().compareTo(new BigDecimal("15000")) == 0);
    }
}
