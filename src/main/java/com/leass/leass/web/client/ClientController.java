package com.leass.leass.web.client;

import com.leass.leass.model.Agreement;
import com.leass.leass.model.Client;
import com.leass.leass.service.UserService;
import com.leass.leass.service.client.ClientDto;
import com.leass.leass.service.client.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;

@Controller
public class ClientController {

    @Autowired
    ClientService clientService;

    @Autowired
    UserService userService;


    ArrayList wrapper ;
    ClientDto clientDto = new ClientDto();

    @RequestMapping(value = "/listclient", method = RequestMethod.GET)
    public ModelAndView clientListPage() {
        ModelAndView model = new ModelAndView();
        wrapper = new ArrayList<>();
        wrapper.addAll(new ArrayList<Client>(clientService.findAll()));
        model.addObject("clientList", wrapper);
        model.setViewName("/pages/client/clientListPage");
        return model;
    }

    @RequestMapping(value = "/client/{id}", method = RequestMethod.GET)
    public ModelAndView clientPage(@PathVariable("id") Long id) {
        ModelAndView model = new ModelAndView();
        ClientDto clientDto = clientService.getClientById(id.longValue());
        model.addObject("client", clientDto);
        model.setViewName("/pages/client/clientViewPage");
        return model;
    }

    @RequestMapping(value = {"/clientEdit" , "/clientEdit/{id}"}, method = RequestMethod.GET)
    public String editClient(@PathVariable(required = false, name = "id") Long id, ModelMap model) {
        if(id != null) {
            clientDto = clientService.getClientById(id);
        }
        model.addAttribute("client", clientDto);
        return "/pages/client/clientEditPage";

    }


    @RequestMapping(value="/updateClient",method=RequestMethod.POST)
    public ModelAndView saveClient(@ModelAttribute ClientDto client, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        if (result.hasErrors()) {
            modelAndView.setViewName("registration");
        }else {

            clientService.saveDto(client);
            modelAndView.addObject("successMessage", "Client save");
            modelAndView.addObject("client", client);
            modelAndView.setViewName("/pages/client/clientViewPage");
        }
        return modelAndView;
    }

    @RequestMapping(value="/addClient", method = RequestMethod.GET)
    public ModelAndView addClient(){
        ModelAndView modelAndView = new ModelAndView();
        Client client = new Client();
        modelAndView.addObject("client", client);
        modelAndView.setViewName("/pages/client/addClientPage");
        return modelAndView;
    }

    @RequestMapping(value = "/addClient", method = RequestMethod.POST)
    public ModelAndView addClient(@ModelAttribute Client client, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            modelAndView.setViewName("/pages/client/addClientPage");
        } else {
            client.setCreateDate(new Date());
            clientService.save(client);
            userService.createUser(client);
            modelAndView.addObject("successMessage", "Zapisono pomyślnie");
            modelAndView.setViewName("/pages/client/clientViewPage");
        }
        return modelAndView;
    }

}
