package com.tklimczak.mortagecalc.services;

import java.math.BigDecimal;

import com.tklimczak.mortagecalc.domain.enums.OverpaymentPeriod;
import com.tklimczak.mortagecalc.domain.enums.OverpaymentType;
import com.tklimczak.mortagecalc.domain.models.Mortage;
import com.tklimczak.mortagecalc.domain.models.Overpayment;

public interface OverpaymentCalculatorService {
	public Overpayment calculate(OverpaymentType type, OverpaymentPeriod period, BigDecimal overpayAmount, Mortage mortage);
}
