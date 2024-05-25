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
        jdbcTemplate.execute("INSERT INTO INGREDIENTI  (nome, price) VALUES ('mozzarella', '1.5')");
        jdbcTemplate.execute("INSERT INTO INGREDIENTI  (nome, price) VALUES ('pomodoro', '1')");
        jdbcTemplate.execute("INSERT INTO INGREDIENTI  (nome, price) VALUES ('patatine', '1.5')");
        jdbcTemplate.execute("INSERT INTO INGREDIENTI  (nome, price) VALUES ('salame', '2')");
        jdbcTemplate.execute("INSERT INTO INGREDIENTI  (nome, price) VALUES ('melanzane', '1.20')");
        jdbcTemplate.execute("INSERT INTO INGREDIENTI  (nome, price) VALUES ('zucchine', '1.30')");
        jdbcTemplate.execute("INSERT INTO INGREDIENTI  (nome, price) VALUES ('peperoni', '1')");
        jdbcTemplate.execute("INSERT INTO INGREDIENTI  (nome, price) VALUES ('cipolle', '1')");

        jdbcTemplate.execute("INSERT INTO PIZZE (name, price) VALUES ('vegetariana', '7')");
        jdbcTemplate.execute("INSERT INTO PIZZE (name, price) VALUES ('diavola', '4.5')");
        jdbcTemplate.execute("INSERT INTO PIZZE (name, price) VALUES ('americana', '4')");

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
