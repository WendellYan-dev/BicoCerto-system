package com.example.apiBicoCerto.tests.register;

import com.example.apiBicoCerto.config.BaseTest;
import com.example.apiBicoCerto.data.InformalWorkerDataFactory;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class InformalWorkerRegisterTest extends BaseTest {

    @Test
    public void testRegisterInformalWorker() {
        given()
                .contentType("multipart/form-data")
                .multiPart("User", InformalWorkerDataFactory.informalWorkerValid()
                        , "application/json")
                .multiPart("profilePhoto", new File("src/test/resources/photo.jpg"))

                .when()
                .post("/informalWorker/register")

                .then()
                .statusCode(201);
    }

    @Test
    public void testInvalidCategory(){
        given()
                .contentType("multipart/form-data")
                .multiPart("User", InformalWorkerDataFactory.informalWorkerWithInvalidCategory()
                        , "application/json")

                .when()
                .post("/informalWorker/register")

                .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void testEmptyAboutMe(){
        given()
                .contentType("multipart/form-data")
                .multiPart("User", InformalWorkerDataFactory.informalWorkerWithEmptyAboutMe()
                        , "application/json")

                .when()
                .post("/informalWorker/register")

                .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void testEmptyLocalService(){
        given()
                .contentType("multipart/form-data")
                .multiPart("User", InformalWorkerDataFactory.informalWorkerWithEmptyLocalService()
                        , "application/json")

                .when()
                .post("/informalWorker/register")

                .then()
                .log().ifValidationFails()
                .statusCode(400);
    }
}
