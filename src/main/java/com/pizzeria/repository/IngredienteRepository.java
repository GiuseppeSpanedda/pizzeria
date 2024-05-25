package com.pizzeria.repository;


import com.pizzeria.entity.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {
    Ingrediente findByNome(String nome);

}
