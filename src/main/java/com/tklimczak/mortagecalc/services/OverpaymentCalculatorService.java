package com.tklimczak.mortagecalc.services;

import com.tklimczak.mortagecalc.domain.models.Mortage;
import com.tklimczak.mortagecalc.domain.models.Overpayment;
import com.tklimczak.mortagecalc.domain.models.OverpaymentResult;

public interface OverpaymentCalculatorService {
	public OverpaymentResult calculate(Overpayment overpayment, Mortage mortage) throws IllegalArgumentException;
}
