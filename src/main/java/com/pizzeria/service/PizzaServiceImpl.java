package com.pizzeria.service;

import com.pizzeria.entity.Ingrediente;
import com.pizzeria.entity.Ordine;
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
        Optional<Pizza> pizzaOpt = pizzaRepository.findById(id);
        if (pizzaOpt.isPresent()) {
            Pizza pizza = pizzaOpt.get();
            // Rimuovi le relazioni con gli ingredienti
            for (Ingrediente ingrediente : pizza.getIngredienti()) {
                ingrediente.getPizzas().remove(pizza);
            }
            pizza.getIngredienti().clear();

            // Rimuovi le relazioni con gli ordini
            for (Ordine ordine : pizza.getOrdini()) {
                ordine.getPizze().remove(pizza);
            }
            pizza.getOrdini().clear();
            pizzaRepository.delete(pizza);
        }
        return pizzaOpt;
    }

    public double calculateUpdatedPrice(Pizza pizza) {
        // Inizializza il prezzo con il prezzo base della pizza
        double updatedPrice = 0;

        // Aggiungi il prezzo di ogni ingrediente selezionato
        for (Ingrediente ingrediente : pizza.getIngredienti()) {
            updatedPrice += ingrediente.getPrice();
        }

        return updatedPrice;
    }

}
