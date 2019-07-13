package com.tklimczak.mortagecalc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tklimczak.mortagecalc.domain.models.CalculationRequest;
import com.tklimczak.mortagecalc.domain.models.Overpayment;
import com.tklimczak.mortagecalc.services.OverpaymentCalculatorService;

@RestController
@RequestMapping("/mortage")
public class OverpaymentCalculatorController {

	@Autowired
	private transient OverpaymentCalculatorService service;

	@CrossOrigin
	@PostMapping("/calc")
	ResponseEntity<Overpayment> createAppointment(@RequestBody CalculationRequest request) {
		return ResponseEntity.ok(service.calculate(request.getType(), request.getPeriod(), request.getOverpayAmount(), request.getMortage()));
	}
}
