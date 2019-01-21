package com.leass.leass.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.leass.leass.service")
public class UserConfiguration {
}
