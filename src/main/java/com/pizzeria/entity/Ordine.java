package com.pizzeria.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ordini")
@Data
@Getter
@Setter
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

}
