package com.example.apiBicoCerto.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Profile("test") // só funciona quando a aplicação roda no profile de teste
public class TestController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DeleteMapping("/reset")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resetDatabase()  {
        jdbcTemplate.execute("TRUNCATE TABLE users RESTART IDENTITY CASCADE");
        jdbcTemplate.execute("TRUNCATE TABLE informalWorker RESTART IDENTITY CASCADE");
        jdbcTemplate.execute("TRUNCATE TABLE address RESTART IDENTITY CASCADE");
    }
}
