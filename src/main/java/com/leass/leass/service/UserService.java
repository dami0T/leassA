package com.leass.leass.service;


import com.leass.leass.model.Client;
import com.leass.leass.model.User;

public interface UserService {

    void save(User user, String role);

    public User findUserByEmail(String email);

    void createUser(Client client);

    User getRequiredLoggedUser();

    Boolean adminRole();
}
