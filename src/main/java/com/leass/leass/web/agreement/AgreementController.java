package com.leass.leass.web.agreement;

import com.leass.leass.model.Agreement;
import com.leass.leass.model.Client;
import com.leass.leass.model.User;
import com.leass.leass.service.UserService;
import com.leass.leass.service.agreement.AgreementDto;
import com.leass.leass.service.agreement.AgreementService;
import com.leass.leass.service.client.ClientService;
import com.leass.leass.service.invoice.InvoiceService;
import com.leass.leass.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class AgreementController {

    @Autowired
    AgreementService agreementService;

    @Autowired
    ClientService clientService;

    @Autowired
    UserService userService;

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    ProductService productService;

    ArrayList wrapper;
    ArrayList products;
    AgreementDto agreementDto = new AgreementDto();
    Boolean visible;

    @RequestMapping(value = "/listagreement", method = RequestMethod.GET)
    public ModelAndView agreementListPage() {
        ModelAndView model = new ModelAndView();
        visible =  userService.adminRole();
        wrapper = new ArrayList<>();
        wrapper.addAll(new ArrayList<Agreement>(agreementService.findAll()));
        model.addObject("visible", visible);
        model.addObject("agreementList", wrapper);
        model.setViewName("pages/agreement/agreementListPage");
        return model;
    }

    @RequestMapping(value = "/agreement/{id}", method = RequestMethod.GET)
    public ModelAndView agreementPage(@PathVariable("id") Long id) {
        ModelAndView model = new ModelAndView();
        visible =  userService.adminRole();
        AgreementDto agreementDto = agreementService.getAgreementById(id.longValue());
        model.addObject("agreement", agreementDto);
        model.addObject("visible", visible);
        model.setViewName("pages/agreement/agreementViewPage");
        return model;
    }

    @RequestMapping(value = {"/agreementEdit", "/agreementEdit/{id}"}, method = RequestMethod.GET)
    public String editAgreement(@PathVariable(required = false, name = "id") Long id, ModelMap model) {
        visible =  userService.adminRole();
        if (id != null) {
            agreementDto = agreementService.getAgreementById(id);
        }
        wrapper = new ArrayList<>();
        wrapper.addAll(new ArrayList<Client>(clientService.findAll()));
        model.addAttribute("agreement", agreementDto);
        model.addAttribute("allClients", wrapper);
        model.addAttribute("visible", visible);
        return "pages/agreement/agreementEditPage";

    }


    @RequestMapping(value = "/updateAgreement", method = RequestMethod.POST)
    public ModelAndView saveAgreement(@ModelAttribute AgreementDto agreement, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        visible =  userService.adminRole();

        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            modelAndView.setViewName("agreementViewPage");
        } else {

            agreementService.saveDto(agreement);
            modelAndView.addObject("successMessage", "Agreement save");
            modelAndView.addObject("agreement", agreement);
            modelAndView.setViewName("pages/agreement/agreementViewPage");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/addAgreement", method = RequestMethod.GET)
    public ModelAndView addAgreement(ModelMap model) {
        visible =  userService.adminRole();
        wrapper = new ArrayList<>();
        products = new ArrayList();
        wrapper.addAll(new ArrayList<Client>(clientService.findAll()));
        products.addAll(new ArrayList(productService.findByRent(true)));
        ModelAndView modelAndView = new ModelAndView();
        AgreementDto agreement = new AgreementDto();
        modelAndView.addObject("agreement", agreement);
        model.addAttribute("allClients", wrapper);
        model.addAttribute("allProducts", products);
        modelAndView.setViewName("pages/agreement/addAgreementPage");
        return modelAndView;
    }

    @RequestMapping(value = "/addAgreement", method = RequestMethod.POST)
    public ModelAndView addAgreement(@ModelAttribute AgreementDto agreement, BindingResult result) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        visible =  userService.adminRole();
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            modelAndView.setViewName("addAgreementPage");
        } else {
            String value = agreement.getAmountOfInstallments().toString();
            AgreementDto agreement1 = agreementService.createAgreement(agreement, value);
            invoiceService.generateFirstInvoice(agreement1);
            modelAndView.addObject("agreement", agreement1);
            modelAndView.addObject("successMessage", "Umowa zapisana wygenerowano pierwszą fakturę");
            modelAndView.setViewName("pages/agreement/agreementViewPage");
        }
        return modelAndView;
    }
}
