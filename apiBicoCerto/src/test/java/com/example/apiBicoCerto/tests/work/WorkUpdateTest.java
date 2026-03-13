package com.example.apiBicoCerto.tests.work;

import com.example.apiBicoCerto.config.BaseTest;
import com.example.apiBicoCerto.config.CreateUserHelper;
import com.example.apiBicoCerto.config.UserTestContext;
import com.example.apiBicoCerto.data.UpdateWorkDataFactory;
import com.example.apiBicoCerto.data.WorkDataFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class WorkUpdateTest extends BaseTest {

    static int workId;
    static UserTestContext context;
    UserTestContext contextUser;

    public static void registerWork(){

        workId =
            given()
                    .header("Authorization", "Bearer " + context.token)
                    .multiPart("title", WorkDataFactory.validTitleUnique())
                    .multiPart("description", WorkDataFactory.validDescription())
                    .multiPart("price", WorkDataFactory.validPrice())
                    .multiPart("image", new File("src/test/resources/photo.jpg"))

            .when()
                    .post("/work/register")

            .then()
                    .log().all()
                    .statusCode(201)
                    .extract()
                    .as(Integer.class);
    }

    @BeforeAll
    public static void setUp(){
        context = CreateUserHelper.createInfoWorkerAndLogin();
        registerWork();
    }

    public void setUpUser(){
        contextUser = CreateUserHelper.createUserAndLogin();
    }


    // --- Testes ---
    @Test
    public void testUpdateWork(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .multiPart("title", UpdateWorkDataFactory.updateValidTitle())
                .multiPart("description", UpdateWorkDataFactory.updateValidDescription())
                .multiPart("price", UpdateWorkDataFactory.updateValidPrice())

        .when()
                .patch("/work/edit/" + workId)

        .then()
                .log().all()
                .statusCode(204);
    }

    @Test
    public void testUpdateWorkWithEqualsThanZero(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .multiPart("title", UpdateWorkDataFactory.updateValidTitle())
                .multiPart("description", UpdateWorkDataFactory.updateValidDescription())
                .multiPart("price", UpdateWorkDataFactory.updatePriceEqualZero())

        .when()
            .patch("/work/edit/" + workId)

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void testUpdateWorkWithLessToZero(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .multiPart("title", UpdateWorkDataFactory.updateValidTitle())
                .multiPart("description", UpdateWorkDataFactory.updateValidDescription())
                .multiPart("price", UpdateWorkDataFactory.updatePriceLessThanZero())

        .when()
                .patch("/work/edit/" + workId)

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void testUpdateWorkWithEmptyTitle(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .multiPart("title", UpdateWorkDataFactory.updateEmptyTitle())
                .multiPart("description", UpdateWorkDataFactory.updateValidDescription())
                .multiPart("price", UpdateWorkDataFactory.updatePriceEqualZero())

        .when()
                .patch("/work/edit/" + workId)

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void testUpdateWorkWithNullDescription(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .multiPart("title", UpdateWorkDataFactory.updateValidTitle())
                .multiPart("price", UpdateWorkDataFactory.updatePriceEqualZero())

        .when()
                .patch("/work/edit/" + workId)

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void testUpdateWorkWithEqualsName(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .multiPart("title", UpdateWorkDataFactory.updateEqualsTitle())
                .multiPart("description", UpdateWorkDataFactory.updateValidDescription())
                .multiPart("price", UpdateWorkDataFactory.updateValidPrice())

        .when()
                .patch("/work/edit/" + workId)

        .then()
                .statusCode(204);

        // 2 update com o mesmo nome anterior
        given()
                .header("Authorization", "Bearer " + context.token)
                .multiPart("title", UpdateWorkDataFactory.updateEqualsTitle())
                .multiPart("description", UpdateWorkDataFactory.updateValidDescription())
                .multiPart("price", UpdateWorkDataFactory.updateValidPrice())

        .when()
                .patch("/work/edit/" + workId)

        .then()
                .log().ifValidationFails()
                .statusCode(409);
    }

    @Test
    public void testUpdaterWorkWithNotPermission(){
        setUpUser();

        given()
                .header("Authorization", "Bearer " + contextUser.token)
                .multiPart("title", UpdateWorkDataFactory.updateValidTitle())
                .multiPart("description", UpdateWorkDataFactory.updateValidDescription())
                .multiPart("price", UpdateWorkDataFactory.updatePriceEqualZero())

        .when()
                .patch("/work/edit/" + workId)

        .then()
                .log().ifValidationFails()
                .statusCode(403);
    }

    @Test
    public void testUpdateWorkWithNotAuthentication(){
        given()
                .multiPart("title", UpdateWorkDataFactory.updateValidTitle())
                .multiPart("description", UpdateWorkDataFactory.updateValidDescription())
                .multiPart("price", UpdateWorkDataFactory.updatePriceEqualZero())

        .when()
                .patch("/work/edit/" + workId)

        .then()
                .log().ifValidationFails()
                .statusCode(401);
    }

    @Test
    public void testDeleteWorkWithNotPermission(){
        setUpUser();

        given()
                .header("Authorization", "Bearer " + contextUser.token)
        .when()
                .delete("/work/delete/" + workId)

        .then()
                .log().ifValidationFails()
                .statusCode(403);
    }

    @Test
    public void testDeleteWorkWithOutTokenAuthentication(){
        given()

        .when()
                .delete("/work/delete/" + workId)

        .then()
                .log().ifValidationFails()
                .statusCode(401);
    }

    /* Está funcionando
    @AfterAll
    public static void testDeleteWork(){
        given()
                .header("Authorization", "Bearer " + context.token)

        .when()
                .delete("/work/delete/" + workId)

        .then()
                .statusCode(204);
    }*/
}
