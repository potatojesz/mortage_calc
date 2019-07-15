package com.tklimczak.mortagecalc.domain.models;

import com.tklimczak.mortagecalc.domain.enums.OverpaymentPeriod;
import com.tklimczak.mortagecalc.domain.enums.OverpaymentType;

import java.math.BigDecimal;

public class Overpayment {
    private OverpaymentType type;
    private OverpaymentPeriod period;
    private BigDecimal overpayAmount;

    public Overpayment(OverpaymentType type, OverpaymentPeriod period, BigDecimal overpayAmount) {
        this.type = type;
        this.period = period;
        this.overpayAmount = overpayAmount;
    }

    public OverpaymentType getType() {
        return type;
    }
    public void setType(OverpaymentType type) {
        this.type = type;
    }

    public OverpaymentPeriod getPeriod() {
        return period;
    }
    public void setPeriod(OverpaymentPeriod period) {
        this.period = period;
    }

    public BigDecimal getOverpayAmount() {
        return overpayAmount;
    }
    public void setOverpayAmount(BigDecimal overpayAmount) {
        this.overpayAmount = overpayAmount;
    }
}
