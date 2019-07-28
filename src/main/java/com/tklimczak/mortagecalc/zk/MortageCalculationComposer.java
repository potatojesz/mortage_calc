package com.tklimczak.mortagecalc.zk;

import com.tklimczak.mortagecalc.domain.enums.MortageType;
import com.tklimczak.mortagecalc.domain.enums.OverpaymentPeriod;
import com.tklimczak.mortagecalc.domain.enums.OverpaymentType;
import com.tklimczak.mortagecalc.domain.models.*;
import com.tklimczak.mortagecalc.services.InstallmentsCalculatorService;
import com.tklimczak.mortagecalc.services.OverpaymentCalculatorService;
import com.tklimczak.mortagecalc.services.impl.InstallmentsCalculatorServiceLocal;
import com.tklimczak.mortagecalc.services.impl.OverpaymentCalculatorServiceLocal;
import com.tklimczak.mortagecalc.zk.forms.MortageCalculationForm;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.GenericAutowireComposer;
import org.zkoss.zul.*;

import java.math.BigDecimal;

public class MortageCalculationComposer extends GenericAutowireComposer {
    private transient OverpaymentCalculatorService overpaymentCalculatorService = new OverpaymentCalculatorServiceLocal();
    private transient InstallmentsCalculatorService installmentsCalculatorService = new InstallmentsCalculatorServiceLocal();
    private MortageCalculationForm mortageCalculationForm = new MortageCalculationForm();

    //kredyt
    private Decimalbox dbxAmount;
    private Decimalbox dbxInterest;
    private Intbox ibxMonths;
    private Radiogroup installmentTypeRadiogroup;
    private Radio radioConstant;
    private Radio radioDeclining;
    private Listbox installmentsListbox;
    private Button execMortage;
    private Label mortageMonthsSum;
    private Label mortageWholeSum;
    private Label mortageInterestSum;

    //nadpłata
    private Decimalbox dbxAmountOverpay;
    private Radiogroup overpaymentTypeRadiogroup;
    private Radio radioLesser;
    private Radio radioShorter;
    private Radiogroup overpaymentPeriodRadiogroup;
    private Radio radioMonthly;
    private Radio radioYearly;
    private Listbox overpaymentListbox;
    private Button execOverpayment;
    private Label overpayMonthsSum;
    private Label overpayWholeSum;
    private Label overpayInterestSum;
    private Label overpaySum;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        final Mortage mortage = mortageCalculationForm.getMortage();
        mortageCalculationForm.setMortageResult(installmentsCalculatorService.calculateInstallments(mortage));
        final Overpayment overpayment = mortageCalculationForm.getOverpayment();
        mortageCalculationForm.setOverpaymentResult(overpaymentCalculatorService.calculate(overpayment,mortage));

        MortageResult mortageResult = mortageCalculationForm.getMortageResult();
        OverpaymentResult overpaymentResult = mortageCalculationForm.getOverpaymentResult();
        fillComponents(mortageResult, overpaymentResult);
        listenEvents();
    }

    private void listenEvents() {
        execMortage.addEventListener("onClick", new EventListener<Event>() {
            @Override
            public void onEvent(Event event) throws Exception {
                mortageCalculation();
            }
        });
        execOverpayment.addEventListener("onClick", new EventListener<Event>() {
            @Override
            public void onEvent(Event event) throws Exception {
                overpaymentCalculation();
            }
        });
    }

    private void mortageCalculation() {
        try {
            BigDecimal amount = dbxAmount.getValue();
            BigDecimal interest = dbxInterest.getValue();
            int months = ibxMonths.getValue();
            mortageCalculationForm.setMortage(new Mortage(MortageType.CONSTANT_INSTALLMENT, interest, amount, (short) months));
            mortageCalculationForm.setMortageResult(installmentsCalculatorService.calculateInstallments(mortageCalculationForm.getMortage()));
            installmentsListbox.setModel(new ListModelList(mortageCalculationForm.getMortageResult().getInstallments()));
            mortageMonthsSum.setValue(mortageCalculationForm.getMortageResult().getMortage().getMonths().toString());
            mortageWholeSum.setValue(mortageCalculationForm.getMortageResult().getWholeSum().toString());
            mortageInterestSum.setValue(mortageCalculationForm.getMortageResult().getInterestsSum().toString());
        } catch(Exception ex) {
            throw new IllegalArgumentException("Nie wpisałeś poprawnych wartości?");
        }
    }

    private void overpaymentCalculation() {
        try {
            mortageCalculation();
            BigDecimal overpayment = dbxAmountOverpay.getValue();
            OverpaymentType type = overpaymentTypeRadiogroup.getSelectedItem().getId().equals("radioLesser") ?
                    OverpaymentType.LESSER_INSTALLMENTS : OverpaymentType.SHORTER_PERIOD ;
            OverpaymentPeriod period = overpaymentPeriodRadiogroup.getSelectedItem().getId().equals("radioMonthly") ?
                    OverpaymentPeriod.MONTHLY : OverpaymentPeriod.YEARLY ;
            mortageCalculationForm.setOverpayment(new Overpayment(type, period, overpayment));
            mortageCalculationForm.setOverpaymentResult(overpaymentCalculatorService.calculate(mortageCalculationForm.getOverpayment(), mortageCalculationForm.getMortage()));
            overpaymentListbox.setModel(new ListModelList(mortageCalculationForm.getOverpaymentResult().getInstallments()));
            overpayMonthsSum.setValue(mortageCalculationForm.getOverpaymentResult().getNewMonths().toString());
            overpayWholeSum.setValue(mortageCalculationForm.getOverpaymentResult().getWholeSum().toString());
            overpayInterestSum.setValue(mortageCalculationForm.getOverpaymentResult().getInterest().toString());
        } catch(Exception ex) {
            throw new IllegalArgumentException("Nie wpisałeś poprawnych wartości?");
        }
    }

    private void fillComponents(MortageResult mortageResult, OverpaymentResult overpaymentResult) {
        fillMortageComponents(mortageResult);
        fillOverpaymentComponents(overpaymentResult);
    }

    private void fillOverpaymentComponents(OverpaymentResult overpaymentResult) {
        Overpayment overpayment = overpaymentResult.getOverpayment();
        dbxAmountOverpay.setValue(overpayment.getOverpayAmount());
        if(OverpaymentType.LESSER_INSTALLMENTS.equals(overpayment.getType())) {
            overpaymentTypeRadiogroup.setSelectedItem(radioLesser);
        } else {
            overpaymentTypeRadiogroup.setSelectedItem(radioShorter);
        }
        if(OverpaymentPeriod.MONTHLY.equals(overpayment.getPeriod())) {
            overpaymentPeriodRadiogroup.setSelectedItem(radioMonthly);
        } else {
            overpaymentPeriodRadiogroup.setSelectedItem(radioYearly);
        }
        overpaymentListbox.setItemRenderer(overpaymentRenderer);
        overpaymentCalculation();
    }

    private void fillMortageComponents(MortageResult mortageResult) {
        Mortage mortage = mortageResult.getMortage();
        dbxAmount.setValue(mortage.getAmount());
        dbxInterest.setValue(mortage.getInterest());
        ibxMonths.setValue(Integer.valueOf(mortage.getMonths()));
        if(MortageType.CONSTANT_INSTALLMENT.equals(mortage.getType())) {
            installmentTypeRadiogroup.setSelectedItem(radioConstant);
        } else {
            installmentTypeRadiogroup.setSelectedItem(radioDeclining);
        }
        installmentsListbox.setItemRenderer(installmentsRenderer);
        mortageCalculation();
    }

    private static final ListitemRenderer<Installment> installmentsRenderer = new ListitemRenderer<Installment>() {
        @Override
        public void render(Listitem listitem, Installment installment, int i) throws Exception {
            Listcell cell = new Listcell(installment.getMonth().toString());
            cell.setParent(listitem);
            cell = new Listcell(installment.getWhole().toString());
            cell.setParent(listitem);
            cell = new Listcell(installment.getCapitalPart().toString());
            cell.setParent(listitem);
            cell = new Listcell(installment.getInterestPart().toString());
            cell.setParent(listitem);
            cell = new Listcell(installment.getAmountLeft().toString());
            cell.setParent(listitem);
        }
    };

    private static final ListitemRenderer<Installment> overpaymentRenderer = new ListitemRenderer<Installment>() {
        @Override
        public void render(Listitem listitem, Installment installment, int i) throws Exception {
            Listcell cell = new Listcell(installment.getMonth().toString());
            cell.setParent(listitem);
            cell = new Listcell(installment.getWhole().toString());
            cell.setParent(listitem);
            cell = new Listcell(installment.getCapitalPart().toString());
            cell.setParent(listitem);
            cell = new Listcell(installment.getInterestPart().toString());
            cell.setParent(listitem);
            cell = new Listcell(installment.getOverpayPart().toString());
            cell.setParent(listitem);
            cell = new Listcell(installment.getAmountLeft().toString());
            cell.setParent(listitem);
        }
    };
}
