package com.tklimczak.mortagecalc.domain.models;

import java.math.BigDecimal;
import java.util.List;

public class OverpaymentResult {
	private Mortage mortage;
	private Overpayment overpayment;
	private List<Installment> installments;
	private BigDecimal interest;
	private Short newMonths;

	public OverpaymentResult(Mortage mortage, Overpayment overpayment, List<Installment> installments, BigDecimal interest, Short newMonths) {
		this.mortage = mortage;
		this.installments = installments;
		this.overpayment = overpayment;
		this.interest = interest;
		this.newMonths = newMonths;
	}
	
	public Mortage getMortage() {
		return mortage;
	}
	public void setMortage(Mortage mortage) {
		this.mortage = mortage;
	}

	public List<Installment> getInstallments() {
		return installments;
	}
	public void setInstallments(List<Installment> installments) {
		this.installments = installments;
	}

	public BigDecimal getInterest() {
		return interest;
	}
	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	public Short getNewMonths() {
		return newMonths;
	}
	public void setNewMonths(Short newMonths) {
		this.newMonths = newMonths;
	}

	public Overpayment getOverpayment() {
		return overpayment;
	}
	public void setOverpayment(Overpayment overpayment) {
		this.overpayment = overpayment;
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

