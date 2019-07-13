package com.tklimczak.mortagecalc.services.impl;

import java.math.BigDecimal;
import java.security.InvalidParameterException;

import org.springframework.stereotype.Service;

import com.tklimczak.mortagecalc.domain.enums.OverpaymentPeriod;
import com.tklimczak.mortagecalc.domain.enums.OverpaymentType;
import com.tklimczak.mortagecalc.domain.models.Mortage;
import com.tklimczak.mortagecalc.domain.models.Overpayment;
import com.tklimczak.mortagecalc.services.OverpaymentCalculatorService;

@Service
public class OverpaymentCalculatorServiceLocal implements OverpaymentCalculatorService {

	@Override
	public Overpayment calculate(OverpaymentType type, OverpaymentPeriod period, BigDecimal overpayAmount, Mortage mortage) {
		if(OverpaymentType.LESSER_INSTALLMENTS.equals(type)) {
			return lesserInstallmentCalculate(period, overpayAmount, mortage);
		} else if(OverpaymentType.SHORTER_PERIOD.equals(type)) {
			return shorterPeriodCalculate(period, overpayAmount, mortage);
		} else {
			if(type == null) {
				throw new NullPointerException("Overpayment type cannot be null");
			} else {
				throw new InvalidParameterException("There's no available overpayment type: " + type.toString());
			}
		}
	}

	private Overpayment shorterPeriodCalculate(OverpaymentPeriod period, BigDecimal overpayAmount, Mortage mortage) {
		// TODO Auto-generated method stub
		return null;
	}

	private Overpayment lesserInstallmentCalculate(OverpaymentPeriod period, BigDecimal overpayAmount, Mortage mortage) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
