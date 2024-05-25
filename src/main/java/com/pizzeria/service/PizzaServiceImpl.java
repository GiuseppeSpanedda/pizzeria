package com.pizzeria.service;

import com.pizzeria.entity.Pizza;
import com.pizzeria.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PizzaServiceImpl implements PizzaService {
    private final PizzaRepository pizzaRepository;

    @Autowired
    public PizzaServiceImpl(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    @Override
    public List<Pizza> getAll() {
        return pizzaRepository.findAll();
    }

    @Override
    public Optional<Pizza> findById(Long id) {
        return pizzaRepository.findById(id);
    }


    @Override
    public Pizza create(Pizza pizza) {
        return pizzaRepository.save(pizza);
    }

    @Override
    public Pizza update(Long id, Pizza updatedPizza) {
        if (pizzaRepository.existsById(id)) {
            updatedPizza.setId(id);
            return pizzaRepository.save(updatedPizza);
        }
        return null;
    }

    @Override
    public Optional<Pizza> delete(Long id) {
        pizzaRepository.deleteById(id);
        return null;
    }

}
