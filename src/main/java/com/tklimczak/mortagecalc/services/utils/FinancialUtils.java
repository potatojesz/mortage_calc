package com.tklimczak.mortagecalc.services.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public abstract class FinancialUtils {
    public static BigDecimal calculateInterestFactor(BigDecimal interest) throws IllegalArgumentException {
        if(interest == null) {
            throw new NullPointerException("Null value of interest has been passed");
        }
        if(interest.signum() < 0) {
            throw new IllegalArgumentException("Interest rate cant be less than 0");
        }
        final BigDecimal interestPercentage = interest.divide(new BigDecimal("100"));
        return interestPercentage.divide(new BigDecimal("12"), 8, RoundingMode.HALF_DOWN);
    }

    public static BigDecimal calculateAnnuity(BigDecimal interestRate,
                                 short numberOfPayments,
                                 BigDecimal principal,
                                 BigDecimal futureValue,
                                 boolean paymentsDueAtBeginningOfPeriod) {

        final BigDecimal n = new BigDecimal(numberOfPayments);
        if (BigDecimal.ZERO.equals(interestRate)) {
            return (futureValue.add(principal)).divide(n, MathContext.DECIMAL128).negate();
        } else {
            final BigDecimal r1 = interestRate.add(BigDecimal.ONE);
            final BigDecimal pow = r1.pow(numberOfPayments);

            final BigDecimal divisor;
            if (paymentsDueAtBeginningOfPeriod) {
                divisor = r1.multiply(BigDecimal.ONE.subtract(pow));
            } else {
                divisor = BigDecimal.ONE.subtract(pow);
            }
            return (principal.multiply(pow).add(futureValue)).multiply(interestRate).divide(divisor, MathContext.DECIMAL128);
        }
    }
}
