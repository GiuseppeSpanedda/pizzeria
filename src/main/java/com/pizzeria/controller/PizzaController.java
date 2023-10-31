package com.pizzeria.controller;


import com.pizzeria.entity.Pizza;
import com.pizzeria.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pizza")
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

    @GetMapping("/addPizzas")
    public ModelAndView showPizzaForm() {
        return new ModelAndView("addPizzas");
    }


    @PutMapping("/{id}")
    public Pizza updatePizza(@PathVariable Long id, @RequestBody Pizza updatedPizza) {
        return pizzaService.updatePizza(id, updatedPizza);
    }

    @DeleteMapping("/{id}")
    public void deletePizza(@PathVariable Long id) {
        pizzaService.deletePizza(id);
    }

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("index");
        // Puoi aggiungere eventuali dati al model, se necessario.
        // modelAndView.addObject("key", "value");
        return modelAndView;
    }
    @GetMapping("/menu")
    public ModelAndView menu() {
        ModelAndView modelAndView = new ModelAndView("menu");
        // Puoi aggiungere eventuali dati al model, se necessario.
        // modelAndView.addObject("key", "value");
        return modelAndView;
    }
}
