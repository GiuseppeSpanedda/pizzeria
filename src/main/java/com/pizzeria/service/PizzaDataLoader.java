package com.pizzeria.service;
import com.pizzeria.entity.Pizza;
import com.pizzeria.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PizzaDataLoader implements CommandLineRunner {

    @Autowired
    private PizzaRepository exampleRepository;

    @Override
    public void run(String... args) throws Exception {
        exampleRepository.save(new Pizza("Vegetariana", 6.5));
        exampleRepository.save(new Pizza("Diavola", 5.5));
    }
}
