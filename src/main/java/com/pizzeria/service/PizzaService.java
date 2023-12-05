package com.pizzeria.service;

import com.pizzeria.entity.Pizza;

import java.util.List;
import java.util.Optional;

public interface PizzaService {
    List<Pizza> getAll();

    Optional<Pizza> findById(Long id);

    Pizza create(Pizza pizza);

    Pizza update(Long id, Pizza updatedPizza);

    void delete(Long id);
}
