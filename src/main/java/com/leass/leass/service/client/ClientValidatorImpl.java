package com.leass.leass.service.client;

import com.leass.leass.model.Client;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientValidatorImpl implements ClientValidator{

    @Override
    public List<String> validate(Client client) {
        List<String> errors = new ArrayList<>();
        if (client.getName().isEmpty()){
            errors.add("Wprowadź nazwę klienta");
        }
        if (client.getShortName().isEmpty()){
            errors.add("Wprowadź nazwę krótką klienta");
        }
        if (client.getForename().isEmpty()){
            errors.add("Wprowadź imię klienta");
        }
        if (client.getSurname().isEmpty()){
            errors.add("Wprowadź nazwisko klienta");
        }
        if (client.getPesel().isEmpty()){
            errors.add("Wprowadź pesel klienta");
        }
        if (client.getEmail().isEmpty()){
            errors.add("Nie podano adresu e-mail");
        }
        if (client.getPost().isEmpty() || client.getPostCode().isEmpty() || client.getStreet().isEmpty()){
            errors.add("Wprowadź dane zamieszkania");
        }
        return errors;
    }
}
