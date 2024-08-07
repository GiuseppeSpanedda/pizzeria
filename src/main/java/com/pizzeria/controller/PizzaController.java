package com.pizzeria.controller;


import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.pizzeria.entity.Ingrediente;
import com.pizzeria.entity.Ordine;
import com.pizzeria.entity.Pizza;
import com.pizzeria.repository.IngredienteRepository;
import com.pizzeria.repository.OrdineRepository;
import com.pizzeria.service.PizzaService;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.grammars.hql.HqlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizza")
public class PizzaController {
    private final PizzaService pizzaService;

    @Autowired
    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Autowired
    private OrdineRepository ordineRepository;

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
        pizza.setName(name);
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

    @PostMapping("/addToCart/{id}")
    public ModelAndView addToCart(@PathVariable Long id, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("redirect:/pizza/menu");

        // Trova la pizza dal servizio
        Optional<Pizza> optionalPizza = pizzaService.findById(id);

        if (optionalPizza.isPresent()) {
            Pizza pizza = optionalPizza.get();

            // Ottieni il carrello dalla sessione
            List<Pizza> cart = (List<Pizza>) request.getSession().getAttribute("cart");

            // Se il carrello non esiste nella sessione, crea un nuovo carrello
            if (cart == null) {
                cart = new ArrayList<>();
                request.getSession().setAttribute("cart", cart);
            }

            // Aggiungi la pizza al carrello
            cart.add(pizza);

            // Aggiorna la sessione con il carrello aggiornato
            request.getSession().setAttribute("cart", cart);
        }

        return modelAndView;
    }

    @GetMapping("/cart")
    public ModelAndView showCart(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("cart");
        List<Pizza> cart = (List<Pizza>) request.getSession().getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }
        modelAndView.addObject("cart", cart);
        return modelAndView;
    }

    @GetMapping("/removeFromCart/{id}")
    public ModelAndView removeFromCart(@PathVariable Long id, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("redirect:/pizza/cart");
        List<Pizza> cart = (List<Pizza>) request.getSession().getAttribute("cart");
        if (cart != null) {
            Pizza pizzaToRemove = cart.stream()
                    .filter(pizza -> pizza.getId().equals(id))
                    .findFirst()
                    .orElse(null);
            if (pizzaToRemove != null) {
                cart.remove(pizzaToRemove); // Rimuove l'elemento trovato
                request.getSession().setAttribute("cart",cart);
            }

        }
        return modelAndView;
    }


    @PostMapping("/processOrder")
    @Transactional
    public ModelAndView processOrder(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            List<Pizza> cart = (List<Pizza>) request.getSession().getAttribute("cart");
            if (cart != null && !cart.isEmpty()) {
                LocalDateTime now = LocalDateTime.now();
                // Crea un nuovo ordine per questa pizza
                Ordine order = new Ordine(now);

                for (Pizza pizza : cart) {
                    // Carica la pizza dal repository per assicurarti di avere un'istanza gestita
                    Pizza managedPizza = pizzaService.findById(pizza.getId())
                            .orElseThrow(() -> new IllegalArgumentException("Pizza not found"));
                    // Aggiungi la pizza all'ordine
                    order.getPizze().add(managedPizza);

                }
                // Salvataggio dell'ordine
                ordineRepository.save(order);

                // Rimuovi il carrello dalla sessione dopo il salvataggio
                request.getSession().removeAttribute("cart");
                modelAndView.setViewName("redirect:/pizza/menu");
            } else {
                modelAndView.setViewName("redirect:/pizza/cart");
                modelAndView.addObject("message", "Cart is empty");
            }
        } catch (Exception e) {
            modelAndView.setViewName("error");
            modelAndView.addObject("message", e.getMessage());
        }
        return modelAndView;
    }



}
