package com.pizzeria.service;

import com.pizzeria.entity.Ingrediente;
import com.pizzeria.entity.Pizza;
import com.pizzeria.repository.IngredienteRepository;
import com.pizzeria.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IngredienteDataLoader implements CommandLineRunner {

    @Autowired
    private IngredienteRepository ingredienteRepository;


    @Override
    public void run(String... args) throws Exception {
        // Popola la tabella degli ingredienti
       /*Eventualmente si può popolare la tabella così.
        ingredienteRepository.save(new Ingrediente("Patatine".toUpperCase(), 1.5));
        ingredienteRepository.save(new Ingrediente("Salame".toUpperCase(), 1.5));
        ingredienteRepository.save(new Ingrediente("Melanzane".toUpperCase(), 1));
        ingredienteRepository.save(new Ingrediente("Zucchine".toUpperCase(), 1));
        ingredienteRepository.save(new Ingrediente("Peperoni".toUpperCase(), 1));
        ingredienteRepository.save(new Ingrediente("Radicchio".toUpperCase(), 1));
        */

    }
}
