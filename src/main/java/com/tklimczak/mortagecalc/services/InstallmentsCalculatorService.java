package com.tklimczak.mortagecalc.services;

import com.tklimczak.mortagecalc.domain.models.Mortage;
import com.tklimczak.mortagecalc.domain.models.MortageResult;

public interface InstallmentsCalculatorService {
    public MortageResult calculateInstallments(Mortage mortage) throws IllegalArgumentException;
}
