package com.leass.leass.web.user;

import com.leass.leass.model.Client;
import com.leass.leass.model.Role;
import com.leass.leass.model.User;
import com.leass.leass.model.enums.UserTypeEnum;
import com.leass.leass.repository.RoleRepository;
import com.leass.leass.repository.UserRepository;
import com.leass.leass.service.UserService;
import com.leass.leass.service.agreement.AgreementDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RoleRepository roleRepository;

    ArrayList wrapper;

    User user = new User();


    @RequestMapping(value = "/listuser", method = RequestMethod.GET)
    public ModelAndView userListPage() {
        ModelAndView model = new ModelAndView();
        wrapper = new ArrayList<>();
        wrapper.addAll(new ArrayList<User>(userService.findAll()));
        model.addObject("userList", wrapper);
        model.setViewName("pages/user/userListPage");
        return model;
    }

    @RequestMapping(value = {"/userEdit", "/userEdit/{id}"}, method = RequestMethod.GET)
    public String editUser(@PathVariable(required = false, name = "id") Long id, ModelMap model) {
        if (id != null) {
            user = userService.findById(id);
        }
        Long roleId = 0L;
        wrapper = new ArrayList<>();
        wrapper.addAll(new ArrayList<Role>(roleRepository.findAll()));
        model.addAttribute("user", user);
        model.addAttribute("roles", wrapper);

        return "pages/user/userEditPage";

    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public ModelAndView saveAgreement(@ModelAttribute User user, @ModelAttribute("roles.role") String role, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();

        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            modelAndView.setViewName("agreementViewPage");
        } else {
            roleRepository.findByRole(role);
            user.setRoles(Arrays.asList(roleRepository.findByRole(role)));
            userService.save(user);
            modelAndView.addObject("successMessage", "Zapisano zmiany");
            modelAndView.addObject("user", user);
            modelAndView.setViewName("pages/user/userViewPage");
        }
        return modelAndView;
    }

    @RequestMapping(value = {"/changePassword"}, method = RequestMethod.GET)
    public String changePassword(ModelMap model) {

        user = userService.getRequiredLoggedUser();
        String password = "";
        String newPassword = "";
        model.addAttribute("user", user);
        model.addAttribute("password", password);
        model.addAttribute("newPassword", newPassword);

        return "pages/user/changePasswordPage";
    }

    @RequestMapping(value = "/changePasswordUser", method = RequestMethod.POST)
    public ModelAndView changePassword(@ModelAttribute("password") String password, @ModelAttribute("newPassword") String newPassword, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();


        if (StringUtils.isNotEmpty(password) && StringUtils.isNotEmpty(newPassword)) {
            if (password.equals(newPassword)) {
                user.setPassword(password);
                userService.save(user);
                modelAndView.addObject("successMessage", "Zapisano zmiany");
                modelAndView.addObject("user", user);
                modelAndView.setViewName("pages/user/userViewPage");
            }
        } else {
            result.addError(new ObjectError(newPassword, "Blablabla"));
            System.out.println(result.getAllErrors());
            modelAndView.addObject("user", user);
            modelAndView.setViewName("pages/user/changePasswordPage");
        }
        return modelAndView;
    }
}
