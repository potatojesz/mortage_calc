package com.tklimczak.mortagecalc.domain.models;

import java.math.BigDecimal;
import java.util.List;

public class Overpayment {
	private Mortage mortage;
	private List<Installment> installments;
	private BigDecimal interestSaving;
	private Short newMonths;

	public Overpayment(Mortage mortage, List<Installment> installments) {
		this.mortage = mortage;
		this.installments = installments;
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

	public BigDecimal getInterestSaving() {
		return interestSaving;
	}
	public void setInterestSaving(BigDecimal interestSaving) {
		this.interestSaving = interestSaving;
	}

	public Short getNewMonths() {
		return newMonths;
	}
	public void setNewMonths(Short newMonths) {
		this.newMonths = newMonths;
	}
}
