package com.pizzeria.controller;

import com.pizzeria.entity.Ingrediente;
import com.pizzeria.entity.Ordine;
import com.pizzeria.entity.Pizza;
import com.pizzeria.repository.IngredienteRepository;
import com.pizzeria.repository.OrdineRepository;
import com.pizzeria.repository.PizzaRepository;
import com.pizzeria.service.OrdineService;
import com.pizzeria.service.PizzaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrdineController {

    private final OrdineService ordineService;
    private final PizzaService pizzaService;

    @Autowired
    public OrdineController(OrdineService ordineService, PizzaService pizzaService) {
        this.ordineService = ordineService;
        this.pizzaService = pizzaService;
    }

    @GetMapping("/orders")
    public ModelAndView ordini() {
        List<Ordine> ordini = ordineService.getAllWithPizze(); // Assumi che ci sia un metodo che carica gli ordini con le pizze
        ModelAndView modelAndView = new ModelAndView("orders");
        modelAndView.addObject("orders", ordini);
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
                 ordineService.create(order);

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
