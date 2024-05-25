package com.pizzeria.service;

import com.pizzeria.entity.Ingrediente;
import com.pizzeria.repository.IngredienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class IngredienteDataLoader implements CommandLineRunner {

    @Autowired
    private IngredienteRepository exampleRepository;

    @Override
    public void run(String... args) throws Exception {
        exampleRepository.save(new Ingrediente("Patatine".toUpperCase(), 1.5));
        exampleRepository.save(new Ingrediente("Salame".toUpperCase(), 1.5));
        exampleRepository.save(new Ingrediente("Melanzane".toUpperCase(), 1));
        exampleRepository.save(new Ingrediente("Zucchine".toUpperCase(), 1));
        exampleRepository.save(new Ingrediente("Peperoni".toUpperCase(), 1));
        exampleRepository.save(new Ingrediente("Radicchio".toUpperCase(), 1));
    }
}
