package com.example.apiBicoCerto.tests.login;

import com.example.apiBicoCerto.data.LoginDataFactory;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class LoginTest {

    // Se quiser pegar o token rode este
    @Test
    public void testLogin() {
        String token =
            given()
                    .contentType("application/json")
                    .body(LoginDataFactory.loginValid())

            .when()
                    .post("/auth/login")

            .then()
                    .statusCode(200)
                    .body("token", notNullValue())
                    .body("userType", anyOf(equalTo("CLIENTE"), equalTo("PRESTADOR")))
                    .extract()
                    .path("token");

        System.out.println("Token: " + token);
    }

    @Test
    public void testLoginEmail() {
        given()
                .contentType("application/json")
                .body(LoginDataFactory.loginWithEmailValid())

        .when()
                .post("/auth/login")

        .then()
                .statusCode(200)
                .body("token", notNullValue())
                .body("userType", anyOf(equalTo("CLIENTE"), equalTo("PRESTADOR")));
    }

    @Test
    public void testInvalidUsers() {
        given()
                .contentType("application/json")
                .body(LoginDataFactory.loginWithUserInvalid())

        .when()
                .post("/auth/login")

        .then()
                .log().ifValidationFails()
                .statusCode(401);
    }

    @Test
    public void testInvalidPassword() {
        given()
                .contentType("application/json")
                .body(LoginDataFactory.loginWithPasswordInvalid())

        .when()
                .post("/auth/login")

        .then()
                .log().ifValidationFails()
                .statusCode(401);
    }
}
