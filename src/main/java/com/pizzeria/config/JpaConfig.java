package com.pizzeria.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Configuration
@EnableJpaRepositories(basePackages = "com.pizzeria.repository")
@EntityScan(basePackages = "com.pizzeria.entity")
public class JpaConfig {
    // Altre configurazioni JPA
}

