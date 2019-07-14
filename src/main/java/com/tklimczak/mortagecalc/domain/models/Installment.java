package com.tklimczak.mortagecalc.domain.models;

import java.math.BigDecimal;

public class Installment {
	private Short month;
	private BigDecimal amountLeft;
	private BigDecimal capital;
	private BigDecimal interest;
	
	public Installment(Short month, BigDecimal capital, BigDecimal interest) {
		this.setMonth(month);
		this.setCapital(capital);
		this.setInterest(interest);
	}

	public Short getMonth() {
		return month;
	}
	public void setMonth(Short month) {
		this.month = month;
	}

	public BigDecimal getAmount() {
		return getCapital().add(getInterest());
	}

	public BigDecimal getCapital() {
		return capital;
	}
	public void setCapital(BigDecimal capital) {
		this.capital = capital;
	}

	public BigDecimal getInterest() {
		return interest;
	}
	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	public BigDecimal getAmountLeft() {
		return amountLeft;
	}
	public void setAmountLeft(BigDecimal amountLeft) {
		this.amountLeft = amountLeft;
	}
}
