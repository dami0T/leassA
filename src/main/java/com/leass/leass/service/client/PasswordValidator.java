package com.leass.leass.service.client;

import com.leass.leass.model.User;

import java.util.List;

public interface PasswordValidator {

    List<String> validate(User user);

}
