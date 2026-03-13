package com.example.apiBicoCerto.tests.work;

import com.example.apiBicoCerto.config.BaseTest;
import com.example.apiBicoCerto.config.CreateUserHelper;
import com.example.apiBicoCerto.config.UserTestContext;
import com.example.apiBicoCerto.data.WorkDataFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class WorkRegisterTest extends BaseTest {

    static UserTestContext context;
    UserTestContext contextUser;

    @BeforeAll
    public static void setUp(){
        context = CreateUserHelper.createInfoWorkerAndLogin();
    }

    public void setUpUser(){
        contextUser = CreateUserHelper.createUserAndLogin();
    }

    @Test
    public void testRegisterWork(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("multipart/form-data")
                .multiPart("title", WorkDataFactory.validTitleUnique())
                .multiPart("description", WorkDataFactory.validDescription())
                .multiPart("price", WorkDataFactory.validPrice())
                .multiPart("image", new File("src/test/resources/photo.jpg"))

        .when()
                .post("/work/register")

        .then()
                .statusCode(201);
    }

    @Test
    public void testRegisterWorkWithEqualsThenZero(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("multipart/form-data")
                .multiPart("title", WorkDataFactory.validTitle())
                .multiPart("description", WorkDataFactory.validDescription())
                .multiPart("price", WorkDataFactory.priceEqualZero())
                .multiPart("image", new File("src/test/resources/photo.jpg"))

        .when()
                .post("/work/register")

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void testRegisterWorkWithLessThenZero(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("multipart/form-data")
                .multiPart("title", WorkDataFactory.validTitle())
                .multiPart("description", WorkDataFactory.validDescription())
                .multiPart("price", WorkDataFactory.priceLessThanZero())
                .multiPart("image", new File("src/test/resources/photo.jpg"))

        .when()
                .post("/work/register")

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void testRegisterWorkWithEmptyTitle(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("multipart/form-data")
                .multiPart("title", WorkDataFactory.emptyTitle())
                .multiPart("description", WorkDataFactory.validDescription())
                .multiPart("price", WorkDataFactory.validPrice())
                .multiPart("image", new File("src/test/resources/photo.jpg"))

        .when()
                .post("/work/register")

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void testRegisterWorkWithNullPrice(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("multipart/form-data")
                .multiPart("title", WorkDataFactory.validTitle())
                .multiPart("description", WorkDataFactory.validDescription())
                .multiPart("image", new File("src/test/resources/photo.jpg"))

        .when()
                .post("/work/register")

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void testRegisterWorkWithEqualsName(){
        // primeiro cadastro
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("multipart/form-data")
                .multiPart("title", WorkDataFactory.equalsTitle())
                .multiPart("description", WorkDataFactory.validDescription())
                .multiPart("price", WorkDataFactory.validPrice())
                .multiPart("image", new File("src/test/resources/photo.jpg"))

        .when()
                .post("/work/register")

        .then()
                .statusCode(201);

        // segundo cadastro (duplicado)
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("multipart/form-data")
                .multiPart("title", WorkDataFactory.equalsTitle())
                .multiPart("description", WorkDataFactory.validDescription())
                .multiPart("price", WorkDataFactory.validPrice())
                .multiPart("image", new File("src/test/resources/photo.jpg"))

        .when()
                .post("/work/register")

        .then()
                .statusCode(409);
    }

    @Test
    public void testRegisterWorkWithNotPermission(){
        setUpUser();

        given()
                .header("Authorization", "Bearer " + contextUser.token)
                .contentType("multipart/form-data")
                .multiPart("title", WorkDataFactory.validTitle())
                .multiPart("description", WorkDataFactory.validDescription())
                .multiPart("price", WorkDataFactory.validPrice())
                .multiPart("image", new File("src/test/resources/photo.jpg"))

        .when()
                .post("/work/register")

        .then()
                .statusCode(403);
    }

    @Test
    public void testRegisterWorkWithNotAuthentication(){
        given()
                .contentType("multipart/form-data")
                .multiPart("title", WorkDataFactory.validTitle())
                .multiPart("description", WorkDataFactory.validDescription())
                .multiPart("price", WorkDataFactory.validPrice())
                .multiPart("image", new File("src/test/resources/photo.jpg"))

        .when()
                .post("/work/register")

        .then()
                .statusCode(401);
    }

    // Possibilidade de automatizar o teste com usuário inativo
}
