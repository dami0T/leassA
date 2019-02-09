package com.leass.leass.web;


import com.leass.leass.model.Agreement;
import com.leass.leass.model.Client;
import com.leass.leass.model.Invoice;
import com.leass.leass.model.User;
import com.leass.leass.model.enums.UserTypeEnum;
import com.leass.leass.service.UserService;
import com.leass.leass.service.agreement.AgreementCriteria;
import com.leass.leass.service.agreement.AgreementService;
import com.leass.leass.service.client.ClientDto;
import com.leass.leass.service.invoice.InvoiceService;
import liquibase.util.CollectionUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private AgreementService agreementService;

    ArrayList wrapper ;
    static Map<Object,Object> map = new HashMap<>();
    static List<List<Map<Object,Object>>> list = new ArrayList<List<Map<Object,Object>>>();
    static List<Map<Object,Object>> dataPoints1 = new ArrayList<Map<Object,Object>>();

    @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/login/login");
        return modelAndView;
    }


    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("pages/login/registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@ModelAttribute String role,
            @Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("/pages/login/registration");
        } else {
            role = UserTypeEnum.EMPLOYE.getValue();
            userService.save(user, role);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("pages/login/registration");

        }
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView loginUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("login");
        } else {
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("home");

        }
        return modelAndView;
    }

    @RequestMapping(value="/home", method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        wrapper = new ArrayList<>();

        if(user.getRoles().get(0).getRole().contains(UserTypeEnum.EMPLOYE.getValue())){
            modelAndView.setViewName("pages/login/dashboard");
            return modelAndView;
        }


        List<String[]> value = new ArrayList<String[]>();
        AgreementCriteria agreementCriteria = new AgreementCriteria();
        agreementCriteria.setClientId(user.getClient().getId());
        List<Agreement> agreement = agreementService.findByQuery(agreementCriteria);
        String balance="0";
        String balanceLeft="0";
        String month="0";
        if(CollectionUtils.isNotEmpty(agreement)) {
            wrapper.addAll(new ArrayList<Invoice>(invoiceService.findByAgreementId(agreement.get(0).getId())));
            balance = agreement.get(0).getCurrentBalance().toString();
            balanceLeft = agreement.get(0).getCurrentBalanceLeft().toString();
            month = String.valueOf(agreement.get(0).getMonthLeft());

            value.add(new String[]{"Remaining", agreement.get(0).getCurrentBalanceLeft().toString()});
            map = new HashMap<Object,Object>(); map.put("name", "Wartość"); map.put("y", balance);dataPoints1.add(map);
            map = new HashMap<Object,Object>(); map.put("name", "Wpłacono"); map.put("y", balanceLeft);dataPoints1.add(map);
            list.add(dataPoints1);
        }
        modelAndView.addObject("invoiceList", wrapper);
        modelAndView.addObject("balance", balance);
        modelAndView.addObject("balanceLeft", balanceLeft);
        modelAndView.addObject("month", month);
        ModelMap modelMap = modelAndView.getModelMap();
        modelMap.addAttribute("dataPointsList", list);
        modelAndView.setViewName("pages/login/start");
        return modelAndView;
    }

}