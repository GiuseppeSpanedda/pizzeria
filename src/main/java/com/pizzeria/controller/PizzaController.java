package com.pizzeria.controller;


import com.pizzeria.entity.Pizza;
import com.pizzeria.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pizzas")
public class PizzaController {
    private final PizzaService pizzaService;

    @Autowired
    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @GetMapping
    public List<Pizza> getAllPizzas() {
        return pizzaService.getAllPizzas();
    }

    @GetMapping("/{id}")
    public Optional<Pizza> getPizzaById(@PathVariable Long id) {
        return pizzaService.getPizzaById(id);
    }

    @PostMapping
    public Pizza createPizza(@RequestBody Pizza pizza) {
        return pizzaService.createPizza(pizza);
    }

    @PutMapping("/{id}")
    public Pizza updatePizza(@PathVariable Long id, @RequestBody Pizza updatedPizza) {
        return pizzaService.updatePizza(id, updatedPizza);
    }

    @DeleteMapping("/{id}")
    public void deletePizza(@PathVariable Long id) {
        pizzaService.deletePizza(id);
    }
}
