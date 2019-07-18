package com.tklimczak.mortagecalc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tklimczak.mortagecalc.controllers.MortageCalculatorController;
import com.tklimczak.mortagecalc.domain.enums.MortageType;
import com.tklimczak.mortagecalc.domain.enums.OverpaymentPeriod;
import com.tklimczak.mortagecalc.domain.enums.OverpaymentType;
import com.tklimczak.mortagecalc.domain.models.Mortage;
import com.tklimczak.mortagecalc.domain.models.Overpayment;
import com.tklimczak.mortagecalc.domain.models.OverpaymentRequest;
import com.tklimczak.mortagecalc.services.InstallmentsCalculatorService;
import com.tklimczak.mortagecalc.services.OverpaymentCalculatorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = MortageCalculatorController.class)
public class MortageCalculatorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OverpaymentCalculatorService overpaymentService;
    @MockBean
    private InstallmentsCalculatorService installmentsService;

    @Test
    public void overpaymentTest() throws Exception {
        final Mortage mortage = new Mortage(MortageType.CONSTANT_INSTALLMENT, BigDecimal.ZERO, BigDecimal.ZERO, (short)360);
        final Overpayment overpayment = new Overpayment(OverpaymentType.LESSER_INSTALLMENTS, OverpaymentPeriod.YEARLY, BigDecimal.ZERO);
        OverpaymentRequest requestContent = new OverpaymentRequest();
        requestContent.setMortage(mortage);
        requestContent.setOverpayment(overpayment);

        mockMvc.perform(post("/mortage/overpay")
                .contentType("application/json")
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(requestContent)))
                .andExpect(status().isOk());
    }

    @Test
    public void overpaymentNullTest() throws Exception {
        final OverpaymentRequest requestContent = new OverpaymentRequest();

        mockMvc.perform(post("/mortage/overpay")
                .contentType("application/json")
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(requestContent)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void mortageTest() throws Exception {
        final Mortage requestContent = new Mortage(MortageType.CONSTANT_INSTALLMENT, BigDecimal.ZERO, BigDecimal.ZERO, (short)360);

        mockMvc.perform(post("/mortage/calc")
                .contentType("application/json")
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(requestContent)))
                .andExpect(status().isOk());
    }

    @Test
    public void mortageNullTest() throws Exception {
        final Mortage requestContent = new Mortage(null, null, null, null);

        mockMvc.perform(post("/mortage/calc")
                .contentType("application/json")
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(requestContent)))
                .andExpect(status().isBadRequest());
    }
}
