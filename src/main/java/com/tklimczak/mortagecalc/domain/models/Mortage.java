package com.tklimczak.mortagecalc.domain.models;

import java.math.BigDecimal;

import com.tklimczak.mortagecalc.domain.enums.MortageType;

public class Mortage {
	private MortageType type;
	private BigDecimal interest;
	private BigDecimal amount;
	private Short months;

	public Mortage(MortageType type, BigDecimal interest, BigDecimal amount, Short months) {
		this.type = type;
		this.interest = interest;
		this.amount = amount;
		this.months = months;
	}
	
	public MortageType getType() {
		return type;
	}
	public void setType(MortageType type) {
		this.type = type;
	}
	
	public BigDecimal getInterest() {
		return interest;
	}
	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public Short getMonths() {
		return months;
	}
	public void setMonths(Short months) {
		this.months = months;
	}
}
