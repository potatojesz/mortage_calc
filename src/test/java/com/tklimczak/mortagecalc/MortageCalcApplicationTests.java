package com.tklimczak.mortagecalc;

import com.tklimczak.mortagecalc.controllers.MortageCalculatorController;
import com.tklimczak.mortagecalc.services.InstallmentsCalculatorService;
import com.tklimczak.mortagecalc.services.OverpaymentCalculatorService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MortageCalcApplicationTests {

	@Autowired
	private MortageCalculatorController mortageCalculatorController;
	@Autowired
	private InstallmentsCalculatorService installmentsCalculatorService;
	@Autowired
	private OverpaymentCalculatorService overpaymentCalculatorService;

	@Test
	public void contextLoads() {
		Assert.assertNotNull(mortageCalculatorController);
		Assert.assertNotNull(installmentsCalculatorService);
		Assert.assertNotNull(overpaymentCalculatorService);
	}

}
