package com.tklimczak.mortagecalc.controllers;

import com.tklimczak.mortagecalc.domain.models.Mortage;
import com.tklimczak.mortagecalc.domain.models.MortageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tklimczak.mortagecalc.domain.models.OverpaymentRequest;
import com.tklimczak.mortagecalc.domain.models.OverpaymentResult;
import com.tklimczak.mortagecalc.services.InstallmentsCalculatorService;
import com.tklimczak.mortagecalc.services.OverpaymentCalculatorService;

@RestController
@RequestMapping("/mortage")
public class MortageCalculatorController {

	@Autowired
	private transient OverpaymentCalculatorService overpaymentService;
	@Autowired
	private transient InstallmentsCalculatorService installmentsService;

	@CrossOrigin
	@PostMapping("/overpay")
	ResponseEntity<OverpaymentResult> calculateOverpayment(@RequestBody OverpaymentRequest request) {
		if(request.getOverpayment() == null || request.getMortage() == null) {
			return ResponseEntity.badRequest().build();
		}
		final OverpaymentResult result = overpaymentService.calculate(request.getOverpayment(), request.getMortage());
		return ResponseEntity.ok(result);
	}

	@CrossOrigin
	@PostMapping("/calc")
	ResponseEntity<MortageResult> calculateMortage(@RequestBody Mortage mortage) {
		final MortageResult result = installmentsService.calculateInstallments(mortage);
		return ResponseEntity.ok(result);
	}
}
