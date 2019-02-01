package com.leass.leass.repository;

import com.leass.leass.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Override
    List<User> findAll();

}
