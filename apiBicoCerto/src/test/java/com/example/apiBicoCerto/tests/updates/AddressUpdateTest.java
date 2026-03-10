package com.example.apiBicoCerto.tests.updates;

import com.example.apiBicoCerto.config.BaseTest;
import com.example.apiBicoCerto.config.CreateUserHelper;
import com.example.apiBicoCerto.config.CreateUserHelper;
import com.example.apiBicoCerto.config.UserTestContext;
import com.example.apiBicoCerto.data.UpdateAddressDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class AddressUpdateTest extends BaseTest {

    UserTestContext context;

    @BeforeEach
    public void setUp(){
        context = CreateUserHelper.createUserAndLogin();
    }

    @Test
    public void updateAddressValid(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("application/json")
                .body(UpdateAddressDataFactory.updateAddressValid())

        .when()
                .patch("user/updateAddress/" + context.addressId)

        .then()
                .log().body()
                .statusCode(200)
                .body("postalCode", equalTo("49503456"))
                .body("street", equalTo("Rua D"))
                .body("neighborhood", equalTo("Centro"))
                .body("state", equalTo("SE"))
                .body("number", equalTo("1042"))
                .body("complement", equalTo("Perto do Parque"))
                .body("isPrimary", equalTo(true));
    }
}
