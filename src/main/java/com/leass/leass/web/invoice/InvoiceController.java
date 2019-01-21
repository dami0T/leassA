package com.leass.leass.web.invoice;

import com.leass.leass.model.Invoice;
import com.leass.leass.service.invoice.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    ArrayList wrapper ;


    @RequestMapping(value = "/listinvoice", method = RequestMethod.GET)
    public ModelAndView invoiceListPage() {
        ModelAndView model = new ModelAndView();
        wrapper = new ArrayList<>();
        wrapper.addAll(new ArrayList<Invoice>(invoiceService.findAll()));
        model.addObject("invoiceList", wrapper);
        model.setViewName("/pages/invoice/invoiceListPage");
        return model;
    }
}
