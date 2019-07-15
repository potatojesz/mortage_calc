package com.tklimczak.mortagecalc.domain.models;

public class CalculationRequest {
	private Mortage mortage;
	private Overpayment overpayment;

	public Mortage getMortage() {
		return mortage;
	}
	public void setMortage(Mortage mortage) {
		this.mortage = mortage;
	}

	public Overpayment getOverpayment() {
		return overpayment;
	}
	public void setOverpayment(Overpayment overpayment) {
		this.overpayment = overpayment;
	}
}
