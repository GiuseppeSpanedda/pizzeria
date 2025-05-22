package com.pizzeria.controller;

import com.pizzeria.entity.Pizza;
import com.pizzeria.service.PizzaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final PizzaService pizzaService;

    @Autowired
    public CartController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
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

    @GetMapping("/showCart")
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
}
