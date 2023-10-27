package com.pizzeria.service;

import com.pizzeria.entity.Pizza;
import com.pizzeria.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PizzaService {
    private final PizzaRepository pizzaRepository;

    @Autowired
    public PizzaService(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    public List<Pizza> getAllPizzas() {
        return pizzaRepository.findAll();
    }

    public Optional<Pizza> getPizzaById(Long id) {
        return pizzaRepository.findById(id);
    }

    public Pizza createPizza(Pizza pizza) {
        return pizzaRepository.save(pizza);
    }

    public Pizza updatePizza(Long id, Pizza updatedPizza) {
        if (pizzaRepository.existsById(id)) {
            updatedPizza.setId(id);
            return pizzaRepository.save(updatedPizza);
        }
        return null;
    }

    public void deletePizza(Long id) {
        pizzaRepository.deleteById(id);
    }
}
