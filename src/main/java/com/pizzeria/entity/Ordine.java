package com.pizzeria.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ordini")
@Data
public class Ordine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dataOrdine;

    @ManyToMany(mappedBy = "ordini")
    private List<Pizza> pizzas = new ArrayList<>();

    public Ordine() {}

    public Ordine(Long id) {
        this.id = id;
    }

    public Ordine(LocalDateTime dataOrdine, List<Pizza> pizzas) {
        this.dataOrdine = dataOrdine;
        this.pizzas = pizzas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
