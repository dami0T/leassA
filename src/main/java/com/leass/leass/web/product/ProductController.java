package com.leass.leass.web.product;

import com.leass.leass.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
public class ProductController {

    @RequestMapping(value="/addProduct", method = RequestMethod.GET)
    public ModelAndView addProduct(ModelMap model){
        ModelAndView modelAndView = new ModelAndView();
        Product product = new Product();
        modelAndView.addObject("product", product);
        modelAndView.setViewName("/pages/product/addProductPage");
        return modelAndView;
    }

    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    public ModelAndView addProduct( BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            modelAndView.setViewName("/pages/payment/addProductPage");
        } else {

            modelAndView.addObject("successMessage", "Wpłata zapisana");
            modelAndView.setViewName("/pages/payment/addProductPage");
        }
        return modelAndView;
    }
}
