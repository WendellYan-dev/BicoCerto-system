package com.example.apiBicoCerto.tests.register;

import com.example.apiBicoCerto.config.BaseTest;
import com.example.apiBicoCerto.data.UserDataFactory;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class UserRegisterTest extends BaseTest {

    @Test
    public void testRegisterUser() {
        given()
                .contentType("application/json")
                .body(UserDataFactory.userValid())

        .when()
                .post("/user/register")

        .then()
                .statusCode(201)
                .body("userName", notNullValue())
                .body("email", notNullValue());
    }

    @Test
    public void testEmptyComplement() {
        given()
                .contentType("application/json")
                .body(UserDataFactory.userWithEmptyComplement())

        .when()
                .post("/user/register")

        .then()
                .statusCode(201)
                .body("addresses[0].complement", nullValue());
    }

    @Test
    public void testRegisterUserWithCnpj() {

        given()
                .contentType("application/json")
                .body(UserDataFactory.userValidWithCnpj())

        .when()
                .post("/user/register")

        .then()
                .statusCode(201)
                .body("cnpj", notNullValue())
                .body("cpf", nullValue());
    }

    @Test
    public void testInvalidCpf() {
        given()
                .contentType("application/json")
                .body(UserDataFactory.userWithInvalidCpf())

        .when()
                .post("/user/register")

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void testUnderageBirthDate() {
        given()
                .contentType("application/json")
                .body(UserDataFactory.userWithUnderageBirthDate())

        .when()
                .post("/user/register")

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void testTooOldBirthDate() {
        given()
                .contentType("application/json")
                .body(UserDataFactory.userWithTooOldBirthDate())

        .when()
                .post("/user/register")

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void testInvalidPassword() {
        given()
                .contentType("application/json")
                .body(UserDataFactory.userWithInvalidPassword())

        .when()
                .post("/user/register")

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void testInvalidPhone() {
        given()
                .contentType("application/json")
                .body(UserDataFactory.userWithInvalidPhone())

        .when()
                .post("/user/register")

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void testInvalidPostalCode() {
        given()
                .contentType("application/json")
                .body(UserDataFactory.userWithInvalidPostalCode())

        .when()
                .post("/user/register")

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public  void testRequiredFieldIsNull(){
        given()
                .contentType("application/json")
                .body(UserDataFactory.userWithNullPhone())

        .when()
                .post("/user/register")

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }
}
