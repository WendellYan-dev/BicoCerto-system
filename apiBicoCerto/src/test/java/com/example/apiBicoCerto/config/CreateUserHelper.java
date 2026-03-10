package com.example.apiBicoCerto.config;

import com.example.apiBicoCerto.data.InformalWorkerDataFactory;
import com.example.apiBicoCerto.data.LoginDataFactory;
import com.example.apiBicoCerto.data.UserDataFactory;

import static io.restassured.RestAssured.given;

public class CreateUserHelper {

    public static UserTestContext createUserAndLogin(){

        Integer addressId =
                given()
                        .contentType("multipart/form-data")
                        .multiPart("User",
                                UserDataFactory.userForTestLogin(), "application/json")

                .when()
                        .post("/user/register")

                .then()
                        .log().body()
                        .statusCode(201)
                        .extract()
                        .jsonPath()
                        .getInt("addresses[0].id");

        String token =
                given()
                        .contentType("application/json")
                        .body(LoginDataFactory.loginValid())

                .when()
                        .post("/auth/login")

                .then()
                        .extract()
                        .jsonPath().getString("token");

        return new UserTestContext(token, addressId);
    }

    public static UserTestContext createInfoWorkerAndLogin(){

        Integer addressId =
                given()
                        .contentType("multipart/form-data")
                        .multiPart("User",
                                InformalWorkerDataFactory.informalWorkerValid(), "application/json")

                        .when()
                        .post("/informalWorker/register")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract()
                        .jsonPath()
                        .getInt("addresses[0].id");

        String token =
                given()
                        .contentType("application/json")
                        .body(LoginDataFactory.loginValid())

                        .when()
                        .post("/auth/login")

                        .then()
                        .extract()
                        .jsonPath().getString("token");

        return new UserTestContext(token, addressId);
    }
}
