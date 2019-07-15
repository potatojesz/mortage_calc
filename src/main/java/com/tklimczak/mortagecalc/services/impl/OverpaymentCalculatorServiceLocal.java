package com.tklimczak.mortagecalc.services.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.tklimczak.mortagecalc.domain.enums.OverpaymentPeriod;
import com.tklimczak.mortagecalc.domain.enums.OverpaymentType;
import com.tklimczak.mortagecalc.domain.models.Installment;
import com.tklimczak.mortagecalc.domain.models.Mortage;
import com.tklimczak.mortagecalc.domain.models.Overpayment;
import com.tklimczak.mortagecalc.domain.models.OverpaymentResult;
import com.tklimczak.mortagecalc.services.OverpaymentCalculatorService;
import com.tklimczak.mortagecalc.services.utils.FinancialUtils;

@Service
public class OverpaymentCalculatorServiceLocal implements OverpaymentCalculatorService {
	public OverpaymentResult calculate(Overpayment overpayment, Mortage mortage) throws IllegalArgumentException {
	    final BigDecimal overpaymentAmount = overpayment.getOverpayAmount();
        Set<Short> overpaymentMonts = null;
        if(!overpayment.getPeriod().equals(OverpaymentPeriod.MONTHLY)) {
            overpaymentMonts = calculateOverpayMonths(overpayment.getPeriod(), mortage.getMonths());
        }

		if(OverpaymentType.LESSER_INSTALLMENTS.equals(overpayment.getType())) {
			return lesserInstallmentCalculate(overpaymentAmount, mortage, overpaymentMonts);
		} else if(OverpaymentType.SHORTER_PERIOD.equals(overpayment.getType())) {
			return shorterPeriodCalculate(overpaymentAmount, mortage, overpaymentMonts);
		} else {
			throw new IllegalArgumentException("There's no available overpayment type: " + overpayment.getType().toString());
		}
	}

    private OverpaymentResult shorterPeriodCalculate(BigDecimal overpaymentAmount, Mortage mortage, Set<Short> overpaymentMonts) throws IllegalArgumentException {
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

        return null;
    }

    private OverpaymentResult lesserInstallmentCalculate(BigDecimal overpaymentAmount, Mortage mortage, Set<Short> overpaymentMonts) {
	    return null;
    }

    private Set<Short> calculateOverpayMonths(OverpaymentPeriod period, Short months) {
	    switch(period) {
            case YEARLY:
                return calculateYearlyOverpayMonths(months);
            default:
                return null;
        }
    }

    private Set<Short> calculateYearlyOverpayMonths(Short months) {
	    Set<Short> result = new HashSet<Short>(35);
        for(int i = 1; i < 35; i++) {
            result.add((short)(i * 12));
        }
        return result;
    }
}
