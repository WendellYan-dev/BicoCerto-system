package com.example.apiBicoCerto.tests.updates;

import com.example.apiBicoCerto.config.CreateUserHelper;
import com.example.apiBicoCerto.config.UserTestContext;
import com.example.apiBicoCerto.data.UpdateUserDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class UserUpdateTest {

    UserTestContext context;

    @BeforeEach
    public void setUp(){
        context = CreateUserHelper.createUserAndLogin();
    }

    @Test
    public void testUpdateUserValid(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("application/json")
                .body(UpdateUserDataFactory.updateUserValid())

            .when()
                .patch("user/updateProfile")

            .then()
                .log().ifValidationFails()
                .statusCode(200);
    }

    @Test
    public void testUpdateUserWithoutToken(){

        given()
                .contentType("application/json")
                .body(UpdateUserDataFactory.updateUserValid())

        .when()
                .patch("user/updateProfile")

        .then()
                .log().ifValidationFails()
                .statusCode(401);
    }

    @Test
    public void testUpdateUserWithInvalidToken(){

        given()
                .header("Authorization", "Bearer token_invalido")
                .contentType("application/json")
                .body(UpdateUserDataFactory.updateUserValid())

        .when()
                .patch("user/updateProfile")

        .then()
                .log().ifValidationFails()
                .statusCode(403);
    }

    // Aqui testa se o formato é válido e não se já existe outro existente no sistema
    @Test
    public void testUpdateUserWithInvalidEmail(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("application/json")
                .body(UpdateUserDataFactory.updateWithInvalidEmail())

        .when()
                .patch("user/updateProfile")

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void testUpdateUserWithInvalidPhoneNumber(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("application/json")
                .body(UpdateUserDataFactory.updateWithInvalidPhoneNumber())

        .when()
                .patch("user/updateProfile")

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void testUpdateUserWithUnderageBirthDate(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("application/json")
                .body(UpdateUserDataFactory.updateWithUnderageBirthDate())

        .when()
                .patch("user/updateProfile")

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void testUpdateUserWithTooOldBirthDate(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("application/json")
                .body(UpdateUserDataFactory.updateWithTooOldBirthDate())

        .when()
                .patch("user/updateProfile")

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void testUpdateUserWithInvalidPassword(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("application/json")
                .body(UpdateUserDataFactory.updateWithInvalidPassword())

        .when()
                .patch("user/updateProfile")

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void testUpdateUserWithPasswordEquals(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("application/json")
                .body(UpdateUserDataFactory.updateWithPasswordEquals())

        .when()
                .patch("user/updateProfile")

        .then()
                .log().ifValidationFails()
                .statusCode(422);
    }

    @Test
    public void testUpdateUserWithEmptyFirstName(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("application/json")
                .body(UpdateUserDataFactory.updateWithEmptyFirstName())

        .when()
                .patch("user/updateProfile")

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void testUpdateUserWithoutPassword(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("application/json")
                .body(UpdateUserDataFactory.updateWithoutPassword())

        .when()
                .patch("user/updateProfile")

        .then()
                .log().ifValidationFails()
                .statusCode(200);
    }

    @Test
    public void testUpdateUserWithoutFirstName(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("application/json")
                .body(UpdateUserDataFactory.updateWithoutFirstName())

        .when()
                .patch("user/updateProfile")

        .then()
                .log().ifValidationFails()
                .statusCode(200);
    }
}
