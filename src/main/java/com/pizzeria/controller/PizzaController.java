package com.pizzeria.controller;


import com.pizzeria.entity.Pizza;
import com.pizzeria.service.PizzaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

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

    @PostMapping("/addPizzas")
    public ModelAndView createPizza(HttpServletRequest request) {
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));

        // Crea un nuovo oggetto Pizza
        Pizza pizza = new Pizza();
        pizza.setName(name);
        pizza.setPrice(price);

        // Salva la pizza nel database
        pizzaService.create(pizza);

        String confirmationMessage = "Pizza aggiunta con successo.";

        // Ritorna la vista con il messaggio di conferma
        ModelAndView modelAndView = new ModelAndView("addPizzas");
        modelAndView.addObject("confirmationMessage", confirmationMessage);
        return modelAndView;
    }


    @GetMapping("/addPizzas")
    public ModelAndView showPizzaForm() {
        return new ModelAndView("addPizzas");
    }

    @GetMapping("/deletePizza/{id}")
    public ModelAndView deletePizza(@PathVariable(name = "id") Long id) {
        Optional<Pizza> deletedPizza = pizzaService.delete(id);
        List<Pizza> pizzas = pizzaService.getAll();
        ModelAndView modelAndView = new ModelAndView("menu");
        modelAndView.addObject("pizzas", pizzas);
        return modelAndView;
    }

    @GetMapping("/updatePizza/{id}")
    public ModelAndView updatePizza(@PathVariable (name = "id") Long id) {
        ModelAndView editView = new ModelAndView("editPizza");
        Optional<Pizza> optionalPizza = pizzaService.findById(id);
        if (optionalPizza.isPresent()) {
            Pizza pizza = optionalPizza.get(); // Estrai il valore effettivo dall'Optional
            editView.addObject("pizza", pizza);
            return editView;
        } else {
            // Gestisci il caso in cui la pizza non esista, ad esempio reindirizzando a una pagina di errore
            return new ModelAndView("error");
        }
    }

    @PostMapping("/updatePizza/{id}")
    public ModelAndView processUpdatePizza(@PathVariable Long id, @ModelAttribute Pizza updatedPizza) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            pizzaService.update(id, updatedPizza);
            modelAndView.setViewName("redirect:/pizza/menu"); // Reindirizza alla pagina del menu dopo l'aggiornamento
        } catch (Exception e) {
            // Gestisci l'eccezione se l'aggiornamento della pizza fallisce
            modelAndView.setViewName("error");
        }
        return modelAndView;
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
