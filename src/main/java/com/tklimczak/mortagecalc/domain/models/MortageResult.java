package com.tklimczak.mortagecalc.domain.models;

import java.math.BigDecimal;
import java.util.List;

public class MortageResult {
    private List<Installment> installments;
    private BigDecimal interestsSum;
    private Mortage mortage;

    public MortageResult(List<Installment> installments, BigDecimal interestsSum, Mortage mortage) {
        this.installments = installments;
        this.interestsSum = interestsSum;
        this.mortage = mortage;
    }

    public List<Installment> getInstallments() {
        return installments;
    }
    public void setInstallments(List<Installment> installments) {
        this.installments = installments;
    }

    public BigDecimal getInterestsSum() {
        return interestsSum;
    }
    public void setInterestsSum(BigDecimal interestsSum) {
        this.interestsSum = interestsSum;
    }

    public Mortage getMortage() {
        return mortage;
    }
    public void setMortage(Mortage mortage) {
        this.mortage = mortage;
    }

    public BigDecimal getWholeSum() {
        if(installments != null && installments.size() > 0) {
            BigDecimal sum = BigDecimal.ZERO;
            for(Installment installment : installments) {
                sum = sum.add(installment.getWhole());
            }
            return sum;
        } else {
            return BigDecimal.ZERO;
        }
    }
}
