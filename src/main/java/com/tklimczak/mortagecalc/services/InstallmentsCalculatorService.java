package com.tklimczak.mortagecalc.services;

import java.math.BigDecimal;
import java.util.List;

import com.tklimczak.mortagecalc.domain.models.Installment;
import com.tklimczak.mortagecalc.domain.models.Mortage;
import com.tklimczak.mortagecalc.domain.models.MortageResult;

public interface InstallmentsCalculatorService {
    public MortageResult calculateInstallments(Mortage mortage) throws IllegalArgumentException;
    public BigDecimal calculateInterests(List<Installment> installments);
    public BigDecimal calculateCapitalPaid(List<Installment> installments);
}
