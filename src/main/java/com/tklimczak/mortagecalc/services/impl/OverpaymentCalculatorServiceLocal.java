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
	@Override
	public OverpaymentResult calculate(Overpayment overpayment, Mortage mortage) throws IllegalArgumentException {
        Set<Short> overpaymentMonts = null;
        if(!overpayment.getPeriod().equals(OverpaymentPeriod.MONTHLY)) {
            overpaymentMonts = calculateOverpayMonths(overpayment.getPeriod(), mortage.getMonths());
        }

		if(OverpaymentType.LESSER_INSTALLMENTS.equals(overpayment.getType())) {
			return lesserInstallmentCalculate(overpayment, mortage, overpaymentMonts);
		} else if(OverpaymentType.SHORTER_PERIOD.equals(overpayment.getType())) {
			return shorterPeriodCalculate(overpayment, mortage, overpaymentMonts);
		} else {
			throw new IllegalArgumentException("There's no available overpayment type: " + overpayment.getType().toString());
		}
	}

    private OverpaymentResult lesserInstallmentCalculate(Overpayment overpayment, Mortage mortage, Set<Short> overpaymentMonts) throws IllegalArgumentException {
        mortage.validate();
        List<Installment> installments = new ArrayList<Installment>(mortage.getMonths());
        final RoundingMode RMD = RoundingMode.HALF_DOWN;
        final RoundingMode RMU = RoundingMode.HALF_UP;
        final BigDecimal interest = mortage.getInterest();
        final BigDecimal amount = mortage.getAmount();
        final BigDecimal factorInterest = FinancialUtils.calculateInterestFactor(interest);

        BigDecimal overpay = overpayment.getOverpayAmount();
        BigDecimal amountLeft = amount;
        short month = 1;

        while(amountLeft.signum() > 0) {
            final BigDecimal annuity = FinancialUtils.calculateAnnuity(factorInterest, (short)(mortage.getMonths() - (month - 1)), amountLeft, BigDecimal.ZERO, false)
                    .setScale(2, RMU)
                    .negate();
            BigDecimal interestInstallmentPart = amountLeft.multiply(factorInterest).setScale(2, RMD);
            BigDecimal wholeInstallment = calculateWholeInstallment(overpay, amountLeft, annuity, interestInstallmentPart);
            BigDecimal capitalInstallmentPart = amountLeft.compareTo(overpay) == 0 ? BigDecimal.ZERO :
                    wholeInstallment.subtract(interestInstallmentPart).subtract(overpay).setScale(2, RMU);
            if(capitalInstallmentPart.signum() < 0) {
                capitalInstallmentPart = BigDecimal.ZERO;
            }
            if(capitalInstallmentPart.compareTo(amountLeft) > 0) {
                capitalInstallmentPart = amountLeft;
            } else {
                if(capitalInstallmentPart.add(overpay).compareTo(amountLeft) > 0) {
                    overpay = amountLeft.subtract(capitalInstallmentPart);
                }
            }
            Installment installment = new Installment(month, capitalInstallmentPart, interestInstallmentPart, amountLeft,
                    shouldOverpay(overpaymentMonts, month) ? overpay : null);
            installments.add(installment);
            if(shouldOverpay(overpaymentMonts, month)) {
                amountLeft = amountLeft.subtract(overpay);
            }
            amountLeft = amountLeft.subtract(capitalInstallmentPart);
            month++;
        }

        return new OverpaymentResult(mortage, overpayment, installments, FinancialUtils.calculateInterests(installments), --month);
    }

    private BigDecimal calculateWholeInstallment(BigDecimal overpay, BigDecimal amountLeft, BigDecimal annuity, BigDecimal interestInstallmentPart) {
        final BigDecimal amountLeftWithInterestPart = amountLeft.add(interestInstallmentPart);
	    BigDecimal wholeInstallment;
        if(amountLeft.compareTo(overpay) == 0) {
            wholeInstallment = interestInstallmentPart.add(overpay);
        } else {
            if(annuity.compareTo(amountLeftWithInterestPart) > 0) {
                wholeInstallment = amountLeftWithInterestPart.add(overpay);
            } else {
                wholeInstallment = annuity.add(overpay);
            }
        }
        if(wholeInstallment.compareTo(amountLeftWithInterestPart) > 0) {
            wholeInstallment = amountLeftWithInterestPart;
        } else {
            if(amountLeft.compareTo(overpay) == 0) {
                wholeInstallment = interestInstallmentPart.add(overpay);
            } else {
                if(annuity.compareTo(amountLeftWithInterestPart) > 0) {
                    wholeInstallment = amountLeftWithInterestPart.add(overpay);
                } else {
                    wholeInstallment = annuity.add(overpay);
                }
            }
        }
        return wholeInstallment;
    }

    private OverpaymentResult shorterPeriodCalculate(Overpayment overpayment, Mortage mortage, Set<Short> overpaymentMonts) throws IllegalArgumentException {
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

        BigDecimal overpay = overpayment.getOverpayAmount();
        BigDecimal amountLeft = amount;
        short month = 1;

        while(amountLeft.signum() > 0) {
            BigDecimal interestInstallmentPart = amountLeft.multiply(factorInterest).setScale(2, RMD);
            BigDecimal wholeInstallment = annuity.compareTo(amountLeft.add(interestInstallmentPart)) > 0 ? amountLeft.add(interestInstallmentPart) : annuity.setScale(2, RMD);
            BigDecimal capitalInstallmentPart = wholeInstallment.subtract(interestInstallmentPart).setScale(2, RMU);
            if(capitalInstallmentPart.compareTo(amountLeft) > 0) {
                capitalInstallmentPart = amountLeft;
            } else {
                if(capitalInstallmentPart.add(overpay).compareTo(amountLeft) > 0) {
                    overpay = amountLeft.subtract(capitalInstallmentPart);
                }
            }
            Installment installment = new Installment(month, capitalInstallmentPart, interestInstallmentPart, amountLeft,
                    shouldOverpay(overpaymentMonts, month) ? overpay : null);
            installments.add(installment);
            if(shouldOverpay(overpaymentMonts, month)) {
                amountLeft = amountLeft.subtract(overpay);
            }
            amountLeft = amountLeft.subtract(capitalInstallmentPart);
            month++;
        }

        return new OverpaymentResult(mortage, overpayment, installments, FinancialUtils.calculateInterests(installments), --month);
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

    private boolean shouldOverpay(Set<Short> overpaymentMonts, short month) {
        if(overpaymentMonts == null || overpaymentMonts.contains(month)) {
            return true;
        }
        return false;
    }
}
