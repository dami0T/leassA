package com.leass.leass.web.payment;

import com.leass.leass.model.Agreement;
import com.leass.leass.model.Invoice;
import com.leass.leass.model.Payment;
import com.leass.leass.service.agreement.AgreementService;
import com.leass.leass.service.invoice.InvoiceService;
import com.leass.leass.service.payment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @Autowired
    AgreementService agreementService;

    @Autowired
    InvoiceService invoiceService;

    ArrayList wrapper;
    Payment payment = new Payment();

    @RequestMapping(value = "/listpayment", method = RequestMethod.GET)
    public ModelAndView paymentListPage() {
        ModelAndView model = new ModelAndView();
        wrapper = new ArrayList<>();
        wrapper.addAll(new ArrayList<Payment>(paymentService.findAll()));
        model.addObject("paymentList", wrapper);
        model.setViewName("/pages/payment/paymentListPage");
        return model;
    }

    @RequestMapping(value="/addPayment", method = RequestMethod.GET)
    public ModelAndView addPayment(ModelMap model){
        wrapper = new ArrayList<>();
        wrapper.addAll(new ArrayList<Agreement>(agreementService.findAll()));
        ModelAndView modelAndView = new ModelAndView();
        Payment payment = new Payment();
        modelAndView.addObject("payment", payment);
        model.addAttribute("allAgreement", wrapper);
        modelAndView.setViewName("/pages/payment/addPaymentPage");
        return modelAndView;
    }

    @RequestMapping(value = "/addPayment", method = RequestMethod.POST)
    public ModelAndView addPayment(@ModelAttribute Payment payment, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            modelAndView.setViewName("/pages/payment/addPaymentPage");
        } else {

            paymentService.save(payment);
            modelAndView.addObject("successMessage", "Wpłata zapisana");
            modelAndView.setViewName("/pages/payment/addPaymentPage");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/passPayment", method = RequestMethod.POST)
    public ModelAndView passPayment(@RequestParam(required=false) Long id, ServletWebRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        Payment payment = paymentService.load(id);
        List<Long> invoiceIds = new ArrayList<>();

        if(request.getParameterValues("idChecked") != null){
            for(String idCheckedStr : request.getParameterValues("idChecked")){
                Long idrate = Long.valueOf(idCheckedStr);
                invoiceIds.add(idrate);
            }
        }
            paymentService.passPayment(payment, invoiceIds);
            modelAndView.addObject("successMessage", "Agreement save");
            modelAndView.setViewName("/pages/payment/passPaymentPage");
            modelAndView.addObject("payment", payment);

        return modelAndView;
    }

    @RequestMapping(value = {"/passPayment", "/passPayment/{id}"}, method = RequestMethod.GET)
    public String editPayment(@PathVariable(required = false, name = "id") Long id, ModelMap model) {
        if (id != null) {
            payment = paymentService.load(id);
        }
        wrapper = new ArrayList<>();
        wrapper.addAll(new ArrayList<Invoice>(invoiceService.findByAgreementId(payment.getAgreement().getId())));
//        model.addAttribute("agreement", agreementDto);
        model.addAttribute("invoiceList", wrapper);
        model.addAttribute("payment", payment);
        return "/pages/payment/passPaymentPage";

    }
}
