package com.tklimczak.mortagecalc.services.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tklimczak.mortagecalc.domain.models.Installment;
import com.tklimczak.mortagecalc.domain.models.Mortage;
import com.tklimczak.mortagecalc.domain.models.MortageResult;
import com.tklimczak.mortagecalc.services.InstallmentsCalculatorService;
import com.tklimczak.mortagecalc.services.utils.FinancialUtils;

@Service
public class InstallmentsCalculatorServiceLocal implements InstallmentsCalculatorService {
    @Override
    public MortageResult calculateInstallments(Mortage mortage) throws IllegalArgumentException {
        mortage.validate();
        List<Installment> installments = new ArrayList<Installment>(mortage.getMonths());
        final RoundingMode RMD = RoundingMode.HALF_DOWN;
        final RoundingMode RMU = RoundingMode.HALF_UP;
        final BigDecimal interest = mortage.getInterest();
        final BigDecimal amount = mortage.getAmount();
        final BigDecimal factorInterest = FinancialUtils.calculateInterestFactor(interest);
        final BigDecimal annuity = FinancialUtils.calculateAnnuity(factorInterest, mortage.getMonths(), amount, BigDecimal.ZERO, false)
                .setScale(2, RMU)
                .negate();

        BigDecimal amountLeft = amount;
        short month = 1;

        while(amountLeft.signum() > 0) {
            BigDecimal interestInstallmentPart = amountLeft.multiply(factorInterest).setScale(2, RMD);
            BigDecimal wholeInstallment = annuity.compareTo(amountLeft.add(interestInstallmentPart)) > 0 ? amountLeft.add(interestInstallmentPart) : annuity.setScale(2, RMD);
            BigDecimal capitalInstallmentPart = wholeInstallment.subtract(interestInstallmentPart).setScale(2, RMU);
            if(month == mortage.getMonths()) {
                capitalInstallmentPart = amountLeft;
            }
            Installment installment = new Installment(month, capitalInstallmentPart, interestInstallmentPart, amountLeft);
            installments.add(installment);
            month++;
            amountLeft = amountLeft.subtract(capitalInstallmentPart);
        }

        return new MortageResult(installments, FinancialUtils.calculateInterests(installments), mortage);
    }
}
