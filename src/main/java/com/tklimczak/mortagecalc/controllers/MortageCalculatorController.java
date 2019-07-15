package com.tklimczak.mortagecalc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tklimczak.mortagecalc.domain.models.CalculationRequest;
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
	ResponseEntity<OverpaymentResult> calculateOverpayment(@RequestBody CalculationRequest request) {
		if(request.getOverpayment() == null || request.getMortage() == null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(overpaymentService.calculate(request.getOverpayment(), request.getMortage()));
	}
}
