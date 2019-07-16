package com.tklimczak.mortagecalc;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.tklimczak.mortagecalc.domain.enums.MortageType;
import com.tklimczak.mortagecalc.domain.models.Installment;
import com.tklimczak.mortagecalc.domain.models.Mortage;
import com.tklimczak.mortagecalc.domain.models.MortageResult;
import com.tklimczak.mortagecalc.services.InstallmentsCalculatorService;
import com.tklimczak.mortagecalc.services.impl.InstallmentsCalculatorServiceLocal;
import com.tklimczak.mortagecalc.services.utils.FinancialUtils;

public class InstallmentsCalculatorServiceTest {
    public transient InstallmentsCalculatorService installmentsService;
    private Mortage mortage;

    @Before
    public void prepareData() {
        installmentsService = new InstallmentsCalculatorServiceLocal();
        mortage = new Mortage(MortageType.CONSTANT_INSTALLMENT, new BigDecimal("3.79"), new BigDecimal("250000"), (short)360);
    }

    @Test
    public void mortageCalculationTest() throws IllegalArgumentException {
        Assert.assertNotNull(mortage);
        MortageResult result = installmentsService.calculateInstallments(mortage);
        List<Installment> calculatedInstallment = result.getInstallments();
        Assert.assertNotNull(calculatedInstallment);
        Assert.assertTrue(calculatedInstallment.size() > 0);
        Installment first = calculatedInstallment.get(0);
        Installment second = calculatedInstallment.get(1);
        Installment last = calculatedInstallment.get(mortage.getMonths() - 1);

        Assert.assertTrue(first.getAmountLeft().compareTo(mortage.getAmount()) == 0);
        Assert.assertTrue(first.getMonth() == (short)1);

        Assert.assertTrue(second.getAmountLeft().compareTo(mortage.getAmount()) < 0);
        Assert.assertTrue(second.getMonth() == (short)2);

        Assert.assertTrue(last.getAmountLeft().subtract(last.getCapitalPart()).signum() <= 0);
        Assert.assertTrue(last.getMonth() <= mortage.getMonths());

        BigDecimal wholeInterests = result.getInterestsSum();
        Assert.assertNotNull(wholeInterests);
        Assert.assertTrue(wholeInterests.setScale(0, RoundingMode.HALF_DOWN).compareTo(new BigDecimal("168849")) == 0);

        BigDecimal wholeCapital = FinancialUtils.calculateCapitalPaid(calculatedInstallment);
        Assert.assertNotNull(wholeCapital);
        Assert.assertTrue(wholeCapital.setScale(0, RoundingMode.HALF_DOWN).compareTo(new BigDecimal("250000")) == 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void mortageValidationTest() throws IllegalArgumentException {
        mortage.setAmount(BigDecimal.ZERO);
        installmentsService.calculateInstallments(mortage);
    }

    @Test(expected = NullPointerException.class)
    public void mortageNullValidationTest() throws IllegalArgumentException {
        mortage.setAmount(null);
        installmentsService.calculateInstallments(mortage);
    }
}