package com.example.apiBicoCerto.config;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import static io.restassured.RestAssured.given;

public class BaseTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @AfterAll
    public static void resetDatabase(){
        given()

        .when()
                .delete("/test/reset")

        .then()
                .statusCode(204);
    }
}
