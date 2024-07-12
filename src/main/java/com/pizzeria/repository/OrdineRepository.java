package com.pizzeria.repository;


import com.pizzeria.entity.Ordine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdineRepository extends JpaRepository<Ordine, Long> {

    @Query("SELECT DISTINCT o FROM Ordine o LEFT JOIN FETCH o.pizze")
    List<Ordine> findAllWithPizze();
}
