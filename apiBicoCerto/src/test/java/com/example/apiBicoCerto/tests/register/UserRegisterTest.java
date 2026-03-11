package com.example.apiBicoCerto.tests.register;

import com.example.apiBicoCerto.config.BaseTest;
import com.example.apiBicoCerto.data.UserDataFactory;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.File;

import static io.restassured.RestAssured.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRegisterTest extends BaseTest {

    @Test
    public void testRegisterUser() {
        given()
                .contentType("multipart/form-data")
                .multiPart("User", UserDataFactory.userValid(), "application/json")

        .when()
                .post("/user/register")

        .then()
                .statusCode(201);
    }

    // Teste para criar um usuário fixo para testar o login
    @Test
    @Order(1)
    public void testRegisterUserForTestLogin() {
        given()
                .contentType("multipart/form-data")
                .multiPart("User", UserDataFactory.userForTestLogin(), "application/json")

        .when()
                .post("/user/register")

        .then()
                .statusCode(201);
    }

    @Test
    @Order(2)
    public void testRegisterUserWithExistingUserName() {
        given()
                .contentType("multipart/form-data")
                .multiPart("User", UserDataFactory.userWithExistingUserName(), "application/json")

        .when()
                .post("/user/register")

        .then()
                .statusCode(409);
    }

    @Test
    @Order(3)
    public void testRegisterUserWithExistingEmail() {
        given()
                .contentType("multipart/form-data")
                .multiPart("User", UserDataFactory.userWithExistingUserName(), "application/json")

        .when()
                .post("/user/register")

        .then()
                .statusCode(409);
    }

    @Test
    public void testEmptyComplement() {
        given()
                .contentType("multipart/form-data")
                .multiPart("User", UserDataFactory.userWithEmptyComplement(), "application/json")

        .when()
                .post("/user/register")

        .then()
                .statusCode(201);
    }

    @Test
    public void testRegisterUserWithCnpj() {

        given()
                .contentType("multipart/form-data")
                .multiPart("User", UserDataFactory.userValidWithCnpj(), "application/json")

        .when()
                .post("/user/register")

        .then()
                .statusCode(201);

    }

    @Test
    public void testInvalidCpf() {
        given()
                .contentType("multipart/form-data")
                .multiPart("User", UserDataFactory.userWithInvalidCpf(), "application/json")

        .when()
                .post("/user/register")

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void testUnderageBirthDate() {
        given()
                .contentType("multipart/form-data")
                .multiPart("User", UserDataFactory.userWithUnderageBirthDate(), "application/json")

        .when()
                .post("/user/register")

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void testTooOldBirthDate() {
        given()
                .contentType("multipart/form-data")
                .multiPart("User", UserDataFactory.userWithTooOldBirthDate(), "application/json")

        .when()
                .post("/user/register")

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void testInvalidPassword() {
        given()
                .contentType("multipart/form-data")
                .multiPart("User", UserDataFactory.userWithInvalidPassword(), "application/json")

        .when()
                .post("/user/register")

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void testInvalidPhone() {
        given()
                .contentType("multipart/form-data")
                .multiPart("User", UserDataFactory.userWithInvalidPhone(), "application/json")

        .when()
                .post("/user/register")

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void testInvalidPostalCode() {
        given()
                .contentType("multipart/form-data")
                .multiPart("User", UserDataFactory.userWithInvalidPostalCode(), "application/json")

        .when()
                .post("/user/register")

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public  void testRequiredFieldIsNull(){
        given()
                .contentType("multipart/form-data")
                .multiPart("User", UserDataFactory.userWithNullPhone(), "application/json")

        .when()
                .post("/user/register")

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }
}
