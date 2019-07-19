package com.tklimczak.mortagecalc.controllers;

import com.tklimczak.mortagecalc.domain.models.Mortage;
import com.tklimczak.mortagecalc.domain.models.MortageResult;
import com.tklimczak.mortagecalc.domain.models.OverpaymentRequest;
import com.tklimczak.mortagecalc.domain.models.OverpaymentResult;
import com.tklimczak.mortagecalc.services.InstallmentsCalculatorService;
import com.tklimczak.mortagecalc.services.OverpaymentCalculatorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value="Mortage calculation operations")
@RestController
@RequestMapping("/mortage")
public class MortageCalculatorController {

	@Autowired
	private transient OverpaymentCalculatorService overpaymentService;
	@Autowired
	private transient InstallmentsCalculatorService installmentsService;

	@ApiOperation(value = "Calculate overpay for you mortage", response = OverpaymentResult.class)
	@CrossOrigin
	@PostMapping("/overpay")
	ResponseEntity<OverpaymentResult> calculateOverpayment(@RequestBody OverpaymentRequest request) {
		if(request.getOverpayment() == null || request.getMortage() == null) {
			return ResponseEntity.badRequest().build();
		}
		final OverpaymentResult result = overpaymentService.calculate(request.getOverpayment(), request.getMortage());
		return ResponseEntity.ok(result);
	}

	@ApiOperation(value = "Calculate installments and interests for you mortage", response = MortageResult.class)
	@CrossOrigin
	@PostMapping("/calc")
	ResponseEntity<MortageResult> calculateMortage(@RequestBody Mortage mortage) {
		if(mortage.getType() == null || mortage.getAmount() == null || mortage.getInterest() == null || mortage.getMonths() == null) {
			return ResponseEntity.badRequest().build();
		}
		final MortageResult result = installmentsService.calculateInstallments(mortage);
		return ResponseEntity.ok(result);
	}
}
