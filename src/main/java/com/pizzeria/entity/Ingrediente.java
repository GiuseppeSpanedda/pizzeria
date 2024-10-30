package com.pizzeria.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "ingredienti")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class Ingrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private double price;

    @ManyToMany(mappedBy = "ingredienti")
    private List<Pizza> pizzas = new ArrayList<>();

    public Ingrediente(String name, double price) {
        this.nome = name;
        this.price = price;
    }

}
