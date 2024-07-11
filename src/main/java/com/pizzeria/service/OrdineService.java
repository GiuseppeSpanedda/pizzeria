package com.pizzeria.service;

import com.pizzeria.entity.Ordine;
import com.pizzeria.entity.Pizza;

import java.util.List;
import java.util.Optional;

public interface OrdineService {
    List<Ordine> getAll();

    Optional<Ordine> findById(Long id);

    Ordine create(Ordine ordine);

    Ordine update(Long id, Ordine updatedOrdine);

    Optional<Ordine> delete(Long id);
}