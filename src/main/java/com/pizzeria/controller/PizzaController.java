package com.pizzeria.controller;


import com.pizzeria.dto.form.PizzaForm;
import com.pizzeria.entity.Ingrediente;
import com.pizzeria.entity.Pizza;
import com.pizzeria.repository.IngredienteRepository;
import com.pizzeria.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizza")
public class PizzaController {
    private final PizzaService pizzaService;
    private final IngredienteRepository ingredienteRepository;

    @Autowired
    public PizzaController(PizzaService pizzaService, IngredienteRepository ingredienteRepository) {
        this.pizzaService = pizzaService;
        this.ingredienteRepository = ingredienteRepository;
    }

    @GetMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @GetMapping("/menu")
    public ModelAndView menu() {
        List<Pizza> pizzas = pizzaService.getAll();
        ModelAndView modelAndView = new ModelAndView("menu");
        modelAndView.addObject("pizzas", pizzas);
        return modelAndView;
    }

    @GetMapping("/addPizzas")
    public ModelAndView showCreatePizzaForm() {
        List<Ingrediente> ingredienti = ingredienteRepository.findAll();
        ModelAndView addPizzas = new ModelAndView("addPizzas");
        addPizzas.addObject("ingredienti", ingredienti);
        return addPizzas;
    }

    @PostMapping("/addPizzas")
    public ModelAndView createPizza(@ModelAttribute PizzaForm pizzaForm) {
        String name = pizzaForm.getName();
        List<String> ingredientiNomi = pizzaForm.getIngredients() != null ? pizzaForm.getIngredients() : new ArrayList<>();

        List<Ingrediente> ingredienti = new ArrayList<>();
        double price = 0.0;

        for (String nomeIngrediente : ingredientiNomi) {
            Ingrediente ingrediente = ingredienteRepository.findByNome(nomeIngrediente);
            if (ingrediente != null) {
                ingredienti.add(ingrediente);
                price += ingrediente.getPrice();
            }
        }

        Pizza pizza = new Pizza();
        pizza.setName(name);
        pizza.setPrice(price);
        pizza.setIngredienti(ingredienti);

        pizzaService.create(pizza);

        ModelAndView modelAndView = new ModelAndView("addPizzas");
        modelAndView.addObject("confirmationMessage", "Nuova Pizza: " + name + " aggiunta, prezzo: " + price);
        modelAndView.addObject("ingredienti", ingredienteRepository.findAll());

        return modelAndView;
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
    public ModelAndView updatePizza(@PathVariable(name = "id") Long id) {
        ModelAndView editView = new ModelAndView("editPizza");
        Optional<Pizza> optionalPizza = pizzaService.findById(id);
        if (optionalPizza.isPresent()) {
            Pizza pizza = optionalPizza.get(); // Estrai il valore effettivo dall'Optional
            List<Ingrediente> allIngredienti = ingredienteRepository.findAll(); // Recupera tutti gli ingredienti disponibili
            editView.addObject("pizza", pizza);
            editView.addObject("allIngredienti", allIngredienti);
            return editView;
        } else {
            return new ModelAndView("error");
        }
    }

    @PostMapping("/updatePizza/{id}")
    public ModelAndView processUpdatePizza(@PathVariable Long id, @ModelAttribute Pizza updatedPizza) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            // Calcola il prezzo aggiornato della pizza prendendo in considerazione gli ingredienti selezionati
            double updatedPrice = pizzaService.calculateUpdatedPrice(updatedPizza);
            updatedPizza.setPrice(updatedPrice);

            // Effettua l'aggiornamento della pizza
            pizzaService.update(id, updatedPizza);
            modelAndView.setViewName("redirect:/pizza/menu"); // Reindirizza alla pagina del menu dopo l'aggiornamento
        } catch (Exception e) {
            // Gestisci l'eccezione se l'aggiornamento della pizza fallisce
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }
}
