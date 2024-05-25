package com.pizzeria.controller;


import com.pizzeria.entity.Ingrediente;
import com.pizzeria.entity.Pizza;
import com.pizzeria.repository.IngredienteRepository;
import com.pizzeria.service.PizzaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.ArrayList;
import java.util.Arrays;
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

    @Autowired
    private IngredienteRepository ingredienteRepository;
    @GetMapping
    public List<Pizza> getAllPizzas() {
        return pizzaService.getAll();
    }

    @GetMapping("/addPizzas")
    public ModelAndView showCreatePizzaForm() {
        List<Ingrediente> ingredienti = ingredienteRepository.findAll();
        ModelAndView addPizzas = new ModelAndView("addPizzas");
        addPizzas.addObject("ingredienti", ingredienti);
        return addPizzas;
    }

    @PostMapping("/addPizzas")
    public ModelAndView createPizza(HttpServletRequest request) {
        String name = request.getParameter("name");
        double price = 0;
        String[] ingredientiArray = request.getParameterValues("ingre");
        List<String> ingredienti = ingredientiArray != null ? Arrays.asList(ingredientiArray) : new ArrayList<>();
        ArrayList<Ingrediente> ingredienteArrayList = new ArrayList<>();
        for (String ingrediente : ingredienti) {
            Ingrediente i = ingredienteRepository.findByNome(ingrediente);
            ingredienteArrayList.add(i);
            price += i.getPrice();
        }

        // Crea un nuovo oggetto Pizza
        Pizza pizza = new Pizza();
        pizza.setName(name.toUpperCase());
        pizza.setPrice(price);
        pizza.setIngredienti(ingredienteArrayList); // Associa gli ingredienti alla pizza

        // Salva la pizza nel database
        pizzaService.create(pizza);

        String confirmationMessage = "Nuova Pizza: "+pizza.getName() +" aggiunta, prezzo: "+pizza.getPrice();

        // Ritorna la vista con il messaggio di conferma
        ModelAndView modelAndView = new ModelAndView("addPizzas");
        modelAndView.addObject("confirmationMessage", confirmationMessage);
        modelAndView.addObject("ingredienti", ingredienteRepository.findAll()); // Ripassa gli ingredienti alla vista
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
            // Gestisci il caso in cui la pizza non esista, ad esempio reindirizzando a una pagina di errore
            return new ModelAndView("error");
        }
    }


    @PostMapping("/updatePizza/{id}")
    public ModelAndView processUpdatePizza(@PathVariable Long id, @ModelAttribute Pizza updatedPizza) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            // Calcola il prezzo aggiornato della pizza prendendo in considerazione gli ingredienti selezionati
            double updatedPrice = calculateUpdatedPrice(updatedPizza);
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

    private double calculateUpdatedPrice(Pizza pizza) {
        // Inizializza il prezzo con il prezzo base della pizza
        double updatedPrice = 0;

        // Aggiungi il prezzo di ogni ingrediente selezionato
        for (Ingrediente ingrediente : pizza.getIngredienti()) {
            updatedPrice += ingrediente.getPrice();
        }

        return updatedPrice;
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
