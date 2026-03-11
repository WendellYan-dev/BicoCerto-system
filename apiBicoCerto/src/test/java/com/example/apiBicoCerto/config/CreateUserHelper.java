package com.example.apiBicoCerto.config;

import com.example.apiBicoCerto.data.InformalWorkerDataFactory;
import com.example.apiBicoCerto.data.LoginDataFactory;
import com.example.apiBicoCerto.data.UserDataFactory;

import static io.restassured.RestAssured.given;

public class CreateUserHelper {

    public static UserTestContext createUserAndLogin() {

        UserTestDTO user = UserDataFactory.userValidComplex();

        Integer addressId = register("/user/register", user.json());
        String token = login(user.email(), user.password());

        return new UserTestContext(token, addressId);
    }

    public static UserTestContext createInfoWorkerAndLogin() {

        UserTestDTO worker = InformalWorkerDataFactory.informalWorkerValidComplex();

        Integer addressId = register("/informalWorker/register", worker.json());
        String token = login(worker.email(), worker.password());

        return new UserTestContext(token, addressId);
    }

    private static Integer register(String path, String json) {

        return
                given()
                        .contentType("multipart/form-data")
                        .multiPart("User", json, "application/json")

                .when()
                        .post(path)

                .then()
                        .statusCode(201)
                        .extract()
                        .path("addresses[0].id");

    }

    private static String login(String email, String password) {

        return
                given()
                        .contentType("application/json")
                        .body(LoginDataFactory.loginWithParameters(email, password))

                .when()
                        .post("/auth/login")

                .then()
                        .extract()
                        .path("token");
    }
}
