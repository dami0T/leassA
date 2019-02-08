package com.leass.leass.web.product;

import com.leass.leass.model.Product;
import com.leass.leass.service.UserService;
import com.leass.leass.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;


    Boolean visible;


    @RequestMapping(value="/addProduct", method = RequestMethod.GET)
    public ModelAndView addProduct(ModelMap model){
        ModelAndView modelAndView = new ModelAndView();
        Product product = new Product();
        visible = userService.adminRole();
        modelAndView.addObject("visible", visible);
        modelAndView.addObject("product", product);
        modelAndView.setViewName("pages/product/addProductPage");
        return modelAndView;
    }

    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    public ModelAndView addProduct(@ModelAttribute Product product, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        visible = userService.adminRole();
        modelAndView.addObject("visible", visible);
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            modelAndView.setViewName("pages/payment/addProductPage");
        } else {
            productService.save(product);
            modelAndView.addObject("product", product);
            modelAndView.addObject("successMessage", "Produkt zapisany pomyślnie");
            modelAndView.setViewName("pages/product/productViewPage");
        }
        return modelAndView;
    }
}
