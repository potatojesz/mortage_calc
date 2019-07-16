package com.tklimczak.mortagecalc.domain.models;

import java.math.BigDecimal;

public class Installment {
	private Short month;
	private BigDecimal amountLeft;
	private BigDecimal capitalPart;
	private BigDecimal interestPart;
	private BigDecimal overpayPart;

	public Installment(Short month, BigDecimal capitalPart, BigDecimal interestPart, BigDecimal amountLeft) {
		this.setMonth(month);
		this.setCapitalPart(capitalPart);
		this.setInterestPart(interestPart);
		this.setAmountLeft(amountLeft);
	}

    public Installment(Short month, BigDecimal capitalPart, BigDecimal interestPart, BigDecimal amountLeft, BigDecimal overpayPart) {
        this.setMonth(month);
        this.setCapitalPart(capitalPart);
        this.setInterestPart(interestPart);
        this.setAmountLeft(amountLeft);
        this.setOverpayPart(overpayPart == null ? BigDecimal.ZERO : overpayPart);
    }

	public Short getMonth() {
		return month;
	}
	public void setMonth(Short month) {
		this.month = month;
	}

	public BigDecimal getAmount() {
		return getCapitalPart().add(getInterestPart());
	}

	public BigDecimal getCapitalPart() {
		return capitalPart;
	}
	public void setCapitalPart(BigDecimal capitalPart) {
		this.capitalPart = capitalPart;
	}

	public BigDecimal getInterestPart() {
		return interestPart;
	}
	public void setInterestPart(BigDecimal interestPart) {
		this.interestPart = interestPart;
	}

	public BigDecimal getAmountLeft() {
		return amountLeft;
	}
	public void setAmountLeft(BigDecimal amountLeft) {
		this.amountLeft = amountLeft;
	}

	public BigDecimal getWhole() {
		return capitalPart.add(interestPart).add(overpayPart);
	}

    public BigDecimal getOverpayPart() {
        return overpayPart;
    }
    public void setOverpayPart(BigDecimal overpayPart) {
        this.overpayPart = overpayPart;
    }
}
