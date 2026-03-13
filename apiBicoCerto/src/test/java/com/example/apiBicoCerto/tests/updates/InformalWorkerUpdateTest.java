package com.example.apiBicoCerto.tests.updates;

import com.example.apiBicoCerto.config.CreateUserHelper;
import com.example.apiBicoCerto.config.UserTestContext;
import com.example.apiBicoCerto.data.UpdateInformalWorkerDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class InformalWorkerUpdateTest {

    UserTestContext context;

    @BeforeEach
    public void setUp(){
        context = CreateUserHelper.createInfoWorkerAndLogin();
    }

    @Test
    public void testUpdateInfoWorkerValid(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("application/json")
                .body(UpdateInformalWorkerDataFactory.updateInfoWorkerValid())

        .when()
                .patch("/informalWorker/updateProfile")

        .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("serviceCategory", equalTo("JARDINEIRO"))
                .body("aboutMe", equalTo("Tenho 5 anos de experiência como jardineiro"))
                .body("localService", equalTo("Itabaiana - SE"));
    }

    @Test
    public void testUpdateInfoWorkerWithoutToken(){

        given()
                .contentType("application/json")
                .body(UpdateInformalWorkerDataFactory.updateInfoWorkerValid())

        .when()
                .patch("/informalWorker/updateProfile")

        .then()
                .log().ifValidationFails()
                .statusCode(401);
    }

    @Test
    public void testUpdateInfoWorkerWithInvalidToken(){

        given()
                .auth().oauth2("token_invalido")
                .contentType("application/json")
                .body(UpdateInformalWorkerDataFactory.updateInfoWorkerValid())

        .when()
                .patch("/informalWorker/updateProfile")

        .then()
                .log().ifValidationFails()
                .statusCode(401);
    }

    @Test
    public void testUpdateInfoWorkerWithInvalidServiceCategory(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("application/json")
                .body(UpdateInformalWorkerDataFactory.updateWithInvalidServiceCaterory())

        .when()
                .patch("/informalWorker/updateProfile")

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void testUpdateInfoWorkerWithInvalidAboutMe(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("application/json")
                .body(UpdateInformalWorkerDataFactory.updateWithEmptyAboutMe())

        .when()
                .patch("/informalWorker/updateProfile")

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void testUpdateInfoWorkerWithInvalidLocalService(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("application/json")
                .body(UpdateInformalWorkerDataFactory.updateWithEmptyLocalService())

        .when()
                .patch("/informalWorker/updateProfile")

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }
}
