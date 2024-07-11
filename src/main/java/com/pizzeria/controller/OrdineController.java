package com.pizzeria.controller;

import com.pizzeria.entity.Ingrediente;
import com.pizzeria.entity.Ordine;
import com.pizzeria.entity.Pizza;
import com.pizzeria.repository.IngredienteRepository;
import com.pizzeria.repository.PizzaRepository;
import com.pizzeria.service.OrdineService;
import com.pizzeria.service.PizzaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ordine")
public class OrdineController {

    private final OrdineService ordineService;

    @Autowired
    public OrdineController(OrdineService ordineService) {
        this.ordineService = ordineService;
    }

    @Autowired
    private PizzaRepository pizzaRepository;

    @GetMapping("/ordini")
    public ModelAndView ordini() {
        List<Ordine> ordinis = ordineService.getAll();
        ModelAndView modelAndView = new ModelAndView("ordini");
        modelAndView.addObject("ordinis", ordinis);
        return modelAndView;
    }

}
