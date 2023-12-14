package com.pizzeria.controller;


import com.pizzeria.entity.Pizza;
import com.pizzeria.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        return pizzaService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Pizza> getPizzaById(@PathVariable Long id) {
        return pizzaService.findById(id);
    }

    @PostMapping("/addPizzas")
    public Pizza createPizza(@RequestBody Pizza pizza) {
        System.out.println("Request received to create pizza");
        return pizzaService.create(pizza);
    }

    @GetMapping("/addPizzas")
    public ModelAndView showPizzaForm() {
        return new ModelAndView("addPizzas");
    }


    @PutMapping("/{id}")
    public Pizza updatePizza(@PathVariable Long id, @RequestBody Pizza updatedPizza) {
        return pizzaService.update(id, updatedPizza);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePizza(@PathVariable Long id) {
        try {
            pizzaService.delete(id);
            return ResponseEntity.ok("Pizza eliminata con successo.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Errore durante l'eliminazione della pizza.");
        }
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
        List<Pizza> pizzas = pizzaService.getAll();
        ModelAndView modelAndView = new ModelAndView("menu");
        modelAndView.addObject("pizzas", pizzas);
        return modelAndView;
    }

}
