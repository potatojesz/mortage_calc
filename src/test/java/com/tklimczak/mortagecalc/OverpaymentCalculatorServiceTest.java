package com.tklimczak.mortagecalc;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.tklimczak.mortagecalc.domain.enums.MortageType;
import com.tklimczak.mortagecalc.domain.enums.OverpaymentPeriod;
import com.tklimczak.mortagecalc.domain.enums.OverpaymentType;
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
    public void calculateNullExceptionTest() {
        service.calculate(null, null);
    }

    @Test
    @Ignore
    public void calculateMonthlyOverpaymentShorterPeriodTest() {
        OverpaymentResult result = service.calculate(overpayment, mortage);

        Assert.assertNotNull(result);
    }
}
