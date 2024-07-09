package com.pizzeria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        // Esegui gli script SQL per popolare le tabelle
        jdbcTemplate.execute("INSERT INTO INGREDIENTI  (nome, price) VALUES ('Mozzarella', '1.5')");
        jdbcTemplate.execute("INSERT INTO INGREDIENTI  (nome, price) VALUES ('Pomodoro', '1')");
        jdbcTemplate.execute("INSERT INTO INGREDIENTI  (nome, price) VALUES ('Patatine', '1.5')");
        jdbcTemplate.execute("INSERT INTO INGREDIENTI  (nome, price) VALUES ('Salame', '2')");
        jdbcTemplate.execute("INSERT INTO INGREDIENTI  (nome, price) VALUES ('Melanzane', '1.20')");
        jdbcTemplate.execute("INSERT INTO INGREDIENTI  (nome, price) VALUES ('Zucchine', '1.30')");
        jdbcTemplate.execute("INSERT INTO INGREDIENTI  (nome, price) VALUES ('Peperoni', '1')");
        jdbcTemplate.execute("INSERT INTO INGREDIENTI  (nome, price) VALUES ('Cipolle', '1')");

        jdbcTemplate.execute("INSERT INTO PIZZE (name, price) VALUES ('Vegetariana', '7')");
        jdbcTemplate.execute("INSERT INTO PIZZE (name, price) VALUES ('Diavola', '4.5')");
        jdbcTemplate.execute("INSERT INTO PIZZE (name, price) VALUES ('Americana', '4')");

        jdbcTemplate.execute("INSERT INTO PIZZA_INGREDIENTI (PIZZA_ID, INGREDIENTI_ID) VALUES ('1', '1')");
        jdbcTemplate.execute("INSERT INTO PIZZA_INGREDIENTI (PIZZA_ID, INGREDIENTI_ID) VALUES ('1', '2')");
        jdbcTemplate.execute("INSERT INTO PIZZA_INGREDIENTI (PIZZA_ID, INGREDIENTI_ID) VALUES ('1', '5')");
        jdbcTemplate.execute("INSERT INTO PIZZA_INGREDIENTI (PIZZA_ID, INGREDIENTI_ID) VALUES ('1', '6')");
        jdbcTemplate.execute("INSERT INTO PIZZA_INGREDIENTI (PIZZA_ID, INGREDIENTI_ID) VALUES ('1', '7')");
        jdbcTemplate.execute("INSERT INTO PIZZA_INGREDIENTI (PIZZA_ID, INGREDIENTI_ID) VALUES ('1', '8')");
        jdbcTemplate.execute("INSERT INTO PIZZA_INGREDIENTI (PIZZA_ID, INGREDIENTI_ID) VALUES ('2', '1')");
        jdbcTemplate.execute("INSERT INTO PIZZA_INGREDIENTI (PIZZA_ID, INGREDIENTI_ID) VALUES ('2', '2')");
        jdbcTemplate.execute("INSERT INTO PIZZA_INGREDIENTI (PIZZA_ID, INGREDIENTI_ID) VALUES ('2', '4')");
        jdbcTemplate.execute("INSERT INTO PIZZA_INGREDIENTI (PIZZA_ID, INGREDIENTI_ID) VALUES ('3', '1')");
        jdbcTemplate.execute("INSERT INTO PIZZA_INGREDIENTI (PIZZA_ID, INGREDIENTI_ID) VALUES ('3', '2')");
        jdbcTemplate.execute("INSERT INTO PIZZA_INGREDIENTI (PIZZA_ID, INGREDIENTI_ID) VALUES ('3', '3')");

    }
}
