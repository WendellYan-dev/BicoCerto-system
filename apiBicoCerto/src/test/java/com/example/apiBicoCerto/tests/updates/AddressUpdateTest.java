package com.example.apiBicoCerto.tests.updates;

import com.example.apiBicoCerto.config.BaseTest;
import com.example.apiBicoCerto.config.CreateUserHelper;
import com.example.apiBicoCerto.config.UserTestContext;
import com.example.apiBicoCerto.data.UpdateAddressDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;

public class AddressUpdateTest extends BaseTest {

    UserTestContext context;

    @BeforeEach
    public void setUp(){
        context = CreateUserHelper.createUserAndLogin();
    }

    @Test
    public void testUpdateAddressValid(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("application/json")
                .body(UpdateAddressDataFactory.updateAddressValid())

        .when()
                .patch("user/updateAddress/" + context.addressId)

        .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("postalCode", equalTo("49503456"))
                .body("street", equalTo("Rua D"))
                .body("neighborhood", equalTo("Centro"))
                .body("state", equalTo("SE"))
                .body("number", equalTo("1042"))
                .body("complement", equalTo("Perto do Parque"))
                .body("isPrimary", equalTo(true));
    }

    @Test
    public void testUpdateWithAdressValidWithOutToken(){
        given()
                //.header("Authorization", "Bearer " + context.token)
                .contentType("application/json")
                .body(UpdateAddressDataFactory.updateAddressValid())

        .when()
                .patch("user/updateAddress/" + context.addressId)

        .then()
                .log().ifValidationFails()
                .statusCode(401);
    }

    @Test
    public void testUpdateAddressWithInvalidToken(){

        given()
                .header("Authorization", "Bearer token_invalido")
                .contentType("application/json")
                .body(UpdateAddressDataFactory.updateAddressValid())

        .when()
                .patch("user/updateAddress/" + context.addressId)

        .then()
                .log().ifValidationFails()
                .statusCode(403);
    }

    @Test
    public void testUpdateAddressWithComplementNull(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("application/json")
                .body(UpdateAddressDataFactory.updateWithComplementNull())

        .when()
                .patch("user/updateAddress/" + context.addressId)

        .then()
                .statusCode(200)
                .body("complement", anyOf(equalTo(null), equalTo("")));
    }

    @Test
    public void testUpdateAddressWithIsPrimaryFalse(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("application/json")
                .body(UpdateAddressDataFactory.updateWithIsPrimaryFalse())

        .when()
                .patch("user/updateAddress/" + context.addressId)

        .then()
                .statusCode(200)
                .body("isPrimary", equalTo(false));
    }

    @Test
    public void testUpdateAddressWithInvalidPostalCode(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("application/json")
                .body(UpdateAddressDataFactory.updateWithInvalidPostalCode())

        .when()
                .patch("user/updateAddress/" + context.addressId)

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void testUpdateAddressWithInvalidStreet(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("application/json")
                .body(UpdateAddressDataFactory.updateWithInvalidStreet())

        .when()
                .patch("user/updateAddress/" + context.addressId)

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void updateAddressWithInvalidNeighborhood(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("application/json")
                .body(UpdateAddressDataFactory.updateWithInvalidNeighborhood())

        .when()
                .patch("user/updateAddress/" + context.addressId)

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void testUpdateAddressWithInvalidState(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("application/json")
                .body(UpdateAddressDataFactory.updateWithInvalidState())

        .when()
                .patch("user/updateAddress/" + context.addressId)

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void testUpdateAddressWithInvalidNumber(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("application/json")
                .body(UpdateAddressDataFactory.updateWithInvalidNumber())

        .when()
                .patch("user/updateAddress/" + context.addressId)

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void testUpdateAddressWithInvalidIsPrimary(){
        given()
                .header("Authorization", "Bearer " + context.token)
                .contentType("application/json")
                .body(UpdateAddressDataFactory.updateWithInvalidIsPrimary())

        .when()
                .patch("user/updateAddress/" + context.addressId)

        .then()
                .log().ifValidationFails()
                .statusCode(400);
    }
}
