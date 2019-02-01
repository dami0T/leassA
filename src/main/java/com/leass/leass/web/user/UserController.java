package com.leass.leass.web.user;

import com.leass.leass.model.User;
import com.leass.leass.repository.UserRepository;
import com.leass.leass.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    ArrayList wrapper ;


    @RequestMapping(value = "/listuser", method = RequestMethod.GET)
    public ModelAndView invoiceListPage() {
        ModelAndView model = new ModelAndView();
        wrapper = new ArrayList<>();
        wrapper.addAll(new ArrayList<User>(userService.findAll()));
        model.addObject("userList", wrapper);
        model.setViewName("/pages/user/userListPage");
        return model;
    }
}
