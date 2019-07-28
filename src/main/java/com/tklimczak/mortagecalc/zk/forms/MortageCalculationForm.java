package com.tklimczak.mortagecalc.zk.forms;

import com.tklimczak.mortagecalc.domain.enums.MortageType;
import com.tklimczak.mortagecalc.domain.enums.OverpaymentPeriod;
import com.tklimczak.mortagecalc.domain.enums.OverpaymentType;
import com.tklimczak.mortagecalc.domain.models.Mortage;
import com.tklimczak.mortagecalc.domain.models.MortageResult;
import com.tklimczak.mortagecalc.domain.models.Overpayment;
import com.tklimczak.mortagecalc.domain.models.OverpaymentResult;

import java.math.BigDecimal;

public class MortageCalculationForm {
    private Mortage mortage;
    private MortageResult mortageResult;

    private Overpayment overpayment;
    private OverpaymentResult overpaymentResult;

    public MortageCalculationForm() {
        mortage = new Mortage(MortageType.CONSTANT_INSTALLMENT, new BigDecimal("4"), new BigDecimal("250000"), (short)360);
        overpayment = new Overpayment(OverpaymentType.LESSER_INSTALLMENTS, OverpaymentPeriod.MONTHLY, new BigDecimal("500"));
    }

    public Mortage getMortage() {
        return mortage;
    }
    public void setMortage(Mortage mortage) {
        this.mortage = mortage;
    }

    public MortageResult getMortageResult() {
        return mortageResult;
    }
    public void setMortageResult(MortageResult mortageResult) {
        this.mortageResult = mortageResult;
    }

    public Overpayment getOverpayment() {
        return overpayment;
    }
    public void setOverpayment(Overpayment overpayment) {
        this.overpayment = overpayment;
    }

    public OverpaymentResult getOverpaymentResult() {
        return overpaymentResult;
    }
    public void setOverpaymentResult(OverpaymentResult overpaymentResult) {
        this.overpaymentResult = overpaymentResult;
    }
}
