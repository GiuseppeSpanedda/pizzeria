package com.pizzeria.entity;

import lombok.Data;
import jakarta.persistence.*;
import lombok.Setter;
import org.springframework.security.config.annotation.authentication.ProviderManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer;


@Entity
@Table(name = "users")
@Data
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private  String password;
    private String role;

}

