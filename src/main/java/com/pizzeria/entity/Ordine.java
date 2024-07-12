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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "ordine_pizza",
            joinColumns = @JoinColumn(name = "ordine_id"),
            inverseJoinColumns = @JoinColumn(name = "pizza_id")
    )
    private List<Pizza> pizze = new ArrayList<>();

    public Ordine() {}

    public Ordine(LocalDateTime dataOrdine) {
        this.dataOrdine = dataOrdine;
    }

    public Ordine(LocalDateTime dataOrdine, List<Pizza> pizza) {
        this.dataOrdine = dataOrdine;
        this.pizze = pizza;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Pizza> getPizze() {
        return pizze;
    }

    public void setPizze(List<Pizza> pizze) {
        this.pizze = pizze;
    }
}
