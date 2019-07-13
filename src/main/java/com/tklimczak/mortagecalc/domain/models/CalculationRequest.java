package com.tklimczak.mortagecalc.domain.models;

import java.math.BigDecimal;

import com.tklimczak.mortagecalc.domain.enums.OverpaymentPeriod;
import com.tklimczak.mortagecalc.domain.enums.OverpaymentType;

public class CalculationRequest {
	private OverpaymentType type;
	private OverpaymentPeriod period;
	private Mortage mortage;
	private BigDecimal overpayAmount;

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
	public Mortage getMortage() {
		return mortage;
	}
	public void setMortage(Mortage mortage) {
		this.mortage = mortage;
	}
	public BigDecimal getOverpayAmount() {
		return overpayAmount;
	}
	public void setOverpayAmount(BigDecimal overpayAmount) {
		this.overpayAmount = overpayAmount;
	}
}
