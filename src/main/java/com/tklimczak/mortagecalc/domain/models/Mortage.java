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

	public void validate() throws IllegalArgumentException {
		if(getMonths() == null || getAmount() == null || getInterest() == null || getType() == null) {
			throw new NullPointerException("Mortage with null values is not applicable");
		}
		if(getMonths() <= 0) {
			throw new IllegalArgumentException("Mortage should be at least 1 month long!");
		}
		if(getAmount().signum() <= 0) {
			throw new IllegalArgumentException("Mortage amount can't be less than 0!");
		}
		if(getInterest().signum() < 0) {
			throw new IllegalArgumentException("Mortage interest rate can't be less than 0!");
		}
	}
}
