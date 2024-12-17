package com.pizzeria.service;

import com.pizzeria.entity.User;
import com.pizzeria.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Verifica se l'utente è l'amministratore "admin"
        if ("admin".equals(username)) {
            // Crea e restituisci un oggetto User senza richiedere una password per "admin"
            return org.springframework.security.core.userdetails.User.builder()
                    .username("admin") // Nome utente fisso
                    .password("") // Nessuna password richiesta
                    .roles("ADMIN") // Ruolo amministratore
                    .build();
        }

        // Cerca l'utente nel database
        Optional<User> userOpt = Optional.ofNullable(userRepository.findByUsername(username));

        // Se l'utente non esiste nel database, lanciamo un'eccezione
        if (userOpt.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // Recuperiamo l'utente trovato nel database
        User user = userOpt.get();

        // Restituiamo un oggetto UserDetails usando i dettagli dell'utente trovato nel database
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())  // Nome utente recuperato dal database
                .password(user.getPassword())  // Password dell'utente (già hashata)
                .roles(user.getRole())  // Ruolo dell'utente
                .build();
    }
}
