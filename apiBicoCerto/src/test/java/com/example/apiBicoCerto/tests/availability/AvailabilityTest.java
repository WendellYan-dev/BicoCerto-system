package com.example.apiBicoCerto.tests.availability;

import com.example.apiBicoCerto.config.BaseTest;
import com.example.apiBicoCerto.config.CreateUserHelper;
import com.example.apiBicoCerto.config.UserTestContext;
import com.example.apiBicoCerto.data.AvailabilityDataFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

// Contém o register e o delete
public class AvailabilityTest extends BaseTest {

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
    public void testRegisterAvailability(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("application/json")
                .body(AvailabilityDataFactory.availabilityValid())

        .when()
                .post("/availability/register")

        .then()
                .statusCode(201);
    }

    // Teste para horários encostador => 07:00 - 09:00 e 09:00 - 12:00
    @Test
    public void testRegisterAvailabilityTwo(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("application/json")
                .body(AvailabilityDataFactory.availabilityValidTwo())

        .when()
                .post("/availability/register")

        .then()
                .statusCode(201);
    }

    @Test
    public void testRegisterAvailabilityWithConflict(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("application/json")
                .body(AvailabilityDataFactory.availabilityValidForTestConflict())

        .when()
                .post("/availability/register")

        .then()
                .statusCode(201);


        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("application/json")
                .body(AvailabilityDataFactory.availabilityInvalidConflict())

        .when()
                .post("/availability/register")

        .then()
                .log().ifValidationFails()
                .statusCode(409);
    }

    @Test
    public void testRegisterAvailabilityWithInvalidHour(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("application/json")
                .body(AvailabilityDataFactory.availabilityInvalidHour())

        .when()
                .post("/availability/register")

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void testRegisterAvailabilityWithEmptyField(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("application/json")
                .body(AvailabilityDataFactory.availabilityInvalidWithFieldEmpty())

        .when()
                .post("/availability/register")

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void testRegisterAvailabilityWithNullField(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("application/json")
                .body(AvailabilityDataFactory.availabilityInvalidWithFieldNull())

        .when()
                .post("/availability/register")

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void testRegisterAvailabilityWithOutToken(){
        given()
                .contentType("application/json")
                .body(AvailabilityDataFactory.availabilityValid())

        .when()
                .post("/availability/register")

        .then()
                .log().ifValidationFails()
                .statusCode(401);
    }

    @Test
    public void testRegisterAvailabilityWithNotPermission(){
        setUpUser();

        given()
                .header("Authorization", "Bearer " + contextUser.token)
                .contentType("application/json")
                .body(AvailabilityDataFactory.availabilityValid())

        .when()
                .post("/availability/register")

        .then()
                .log().ifValidationFails()
                .statusCode(403);
    }

    @Test
    public void testDeleteAvailabilityValid(){
        System.out.println(context.token);

        List<Integer> ids =
            given()
                    .header("Authorization", "Bearer " + context.token)
                    .contentType("application/json")
                    .body(AvailabilityDataFactory.availabilityForDelete())

            .when()
                    .post("/availability/register")

            .then()
                    .log().ifValidationFails()
                    .statusCode(201)
                    .extract()
                    .path("");

        int id = ids.get(0);
        System.out.println("id:" + id);
        System.out.println(context.token);

        given()
                .log().all()
                .header("Authorization", "Bearer " + context.token)
                .contentType("application/json")
                .body(AvailabilityDataFactory.deleteAvailabilityValid(id))

        .when()
                .delete("/availability/delete")

        .then()
                .log().all()
                //.log().ifValidationFails()
                .statusCode(204);
    }
}
