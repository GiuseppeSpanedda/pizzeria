package com.pizzeria.entity;
import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "pizze")
@AllArgsConstructor
@Data
public class Pizza implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "pizza_ingredienti",
            joinColumns = @JoinColumn(name = "pizza_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredienti_id")
    )
    private List<Ingrediente> ingredienti = new ArrayList<>();

    @ManyToMany(mappedBy = "pizze", fetch = FetchType.LAZY)
    private List<Ordine> ordini = new ArrayList<>();

    public Pizza() {
    }
    public Pizza(String name, double price) {
        this.name = name;
        this.price = price;
    }
    public Pizza(String name, double price, List<Ingrediente> ingredienti) {
        this.name = name;
        this.price = price;
        this.ingredienti = ingredienti;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Ingrediente> getIngredienti() {
        return ingredienti;
    }

    public void setIngredienti(List<Ingrediente> ingredienti) {
        this.ingredienti = ingredienti;
    }

    public List<Ordine> getOrdini() {
        return ordini;
    }

    public void setOrdini(List<Ordine> ordini) {
        this.ordini = ordini;
    }

}
