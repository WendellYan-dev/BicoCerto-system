package com.example.apiBicoCerto.tests.updates;

import com.example.apiBicoCerto.config.BaseTest;
import com.example.apiBicoCerto.config.CreateUserHelper;
import com.example.apiBicoCerto.config.UserTestContext;
import com.example.apiBicoCerto.data.UpdateUserDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;

import static io.restassured.RestAssured.given;

public class UserUpdateTest extends BaseTest {

    UserTestContext context;

    @BeforeEach
    public void setUp(){
        context = CreateUserHelper.createUserAndLogin();
    }

    @Test
    public void testUpdateUserValid(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("multipart/form-data")
                .multiPart("userUpdate"
                        , UpdateUserDataFactory.updateUserValid(), "application/json")

        .when()
                .patch("/user/updateProfile")

        .then()
                .log().ifValidationFails()
                .statusCode(200);
    }

    @Test
    public void testUpdateUserWithPhoto(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("multipart/form-data")
                .multiPart("userUpdate"
                        , UpdateUserDataFactory.updateUserValid(), "application/json")
                .multiPart("profilePhoto", new File("src/test/resources/photo.jpg"))

        .when()
                .patch("/user/updateProfile")

        .then()
                .log().ifValidationFails()
                .statusCode(200);
    }

    @Test
    public void testUpdateUserWithoutToken(){

        given()
                .contentType("multipart/form-data")
                .multiPart("userUpdate"
                        , UpdateUserDataFactory.updateUserValid()
                        , "application/json")

        .when()
                .patch("/user/updateProfile")

        .then()
                .log().ifValidationFails()
                .statusCode(401);
    }

    @Test
    public void testUpdateUserWithInvalidToken(){

        given()
                .header("Authorization", "Bearer token_invalido")
                .contentType("multipart/form-data")
                .multiPart("userUpdate"
                        , UpdateUserDataFactory.updateUserValid()
                        , "application/json")

        .when()
                .patch("/user/updateProfile")

        .then()
                .log().ifValidationFails()
                .statusCode(401);
    }

    @Test
    public void testUpdateUserWithInvalidPhoneNumber(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("multipart/form-data")
                .multiPart("userUpdate"
                        , UpdateUserDataFactory.updateWithInvalidPhoneNumber()
                        , "application/json")

        .when()
                .patch("/user/updateProfile")

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void testUpdateUserWithUnderageBirthDate(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("multipart/form-data")
                .multiPart("userUpdate"
                        , UpdateUserDataFactory.updateWithUnderageBirthDate()
                        , "application/json")

        .when()
                .patch("/user/updateProfile")

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void testUpdateUserWithTooOldBirthDate(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("multipart/form-data")
                .multiPart("userUpdate"
                        , UpdateUserDataFactory.updateWithTooOldBirthDate()
                        , "application/json")

        .when()
                .patch("/user/updateProfile")

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void testUpdateUserWithInvalidPassword(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("multipart/form-data")
                .multiPart("userUpdate"
                        , UpdateUserDataFactory.updateWithInvalidPassword()
                        , "application/json")

        .when()
                .patch("/user/updateProfile")

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void testUpdateUserWithPasswordEquals(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("multipart/form-data")
                .multiPart("userUpdate"
                        , UpdateUserDataFactory.updateWithPasswordEquals()
                        , "application/json")

        .when()
                .patch("/user/updateProfile")

        .then()
                .log().ifValidationFails()
                .statusCode(422);
    }

    @Test
    public void testUpdateUserWithEmptyFirstName(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("multipart/form-data")
                .multiPart("userUpdate"
                        , UpdateUserDataFactory.updateWithEmptyFirstName()
                        , "application/json")

        .when()
                .patch("/user/updateProfile")

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void testUpdateUserWithoutPassword(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("multipart/form-data")
                .multiPart("userUpdate"
                        , UpdateUserDataFactory.updateWithoutPassword()
                        , "application/json")

        .when()
                .patch("/user/updateProfile")

        .then()
                .log().ifValidationFails()
                .statusCode(200);
    }

    @Test
    public void testUpdateUserWithoutFirstName(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("multipart/form-data")
                .multiPart("userUpdate"
                        , UpdateUserDataFactory.updateWithoutFirstName()
                        , "application/json")

        .when()
                .patch("/user/updateProfile")

        .then()
                .log().ifValidationFails()
                .statusCode(200);
    }
}
