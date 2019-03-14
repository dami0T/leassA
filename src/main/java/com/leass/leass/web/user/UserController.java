package com.leass.leass.web.user;

import com.leass.leass.model.Client;
import com.leass.leass.model.Role;
import com.leass.leass.model.User;
import com.leass.leass.model.enums.UserTypeEnum;
import com.leass.leass.repository.RoleRepository;
import com.leass.leass.repository.UserRepository;
import com.leass.leass.service.UserService;
import com.leass.leass.service.agreement.AgreementDto;
import com.leass.leass.service.client.PasswordValidator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Autowired
    PasswordValidator passwordValidator;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

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
        model.addAttribute("user", user);

        return "pages/user/changePasswordPage";
    }

    @RequestMapping(value = "/changePasswordUser", method = RequestMethod.POST)
    public ModelAndView changePassword(@ModelAttribute User user, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();

        List<String> errors = passwordValidator.validate(user);

        if (CollectionUtils.isEmpty(errors)) {
            String password = bCryptPasswordEncoder.encode(user.getNewPassword());
            user.setPassword(password);
                userService.save(user);
                modelAndView.addObject("successMessage", "Zapisano zmiany");
                modelAndView.addObject("user", user);
                modelAndView.setViewName("pages/user/userViewPage");
        } else {
            System.out.println(result.getAllErrors());
            modelAndView.addObject("user", user);
            modelAndView.addObject("errors", errors);
            modelAndView.setViewName("pages/user/changePasswordPage");
        }
        return modelAndView;
    }
}
