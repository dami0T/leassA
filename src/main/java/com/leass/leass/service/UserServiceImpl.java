package com.leass.leass.service;

import com.leass.leass.model.Client;
import com.leass.leass.model.Role;
import com.leass.leass.model.User;
import com.leass.leass.model.enums.UserTypeEnum;
import com.leass.leass.repository.RoleRepository;
import com.leass.leass.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static java.util.Arrays.asList;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void createUser(Client client) {
        User user = new User();
        user.setActive(1);
        user.setEmail(client.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode("Test!234"));
        user.setName(client.getName());
        user.setLastName(client.getSurname());
        user.setClient(client);
        Role role = roleRepository.findByRole(UserTypeEnum.CLIENT.getValue());
        user.setRoles(asList(role));
        userRepository.save(user);
    }

    @Override
    public User getRequiredLoggedUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = findUserByEmail(auth.getName());
        return user;
    }

    @Override
    public Boolean adminRole() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = findUserByEmail(auth.getName());

        if(user.getRoles().get(0).getRole().contains(UserTypeEnum.EMPLOYE.getValue())) {
          return true;
        }
        return false;
    }

    @Override
    public void save(User user, String role) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole(role);
        user.setRoles(new ArrayList<>(asList(userRole)));
        userRepository.save(user);
    }

}
