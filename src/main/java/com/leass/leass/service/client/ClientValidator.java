package com.leass.leass.service.client;

import com.leass.leass.model.Client;

import java.util.List;

public interface ClientValidator {

    List<String> validate(Client client);
}
