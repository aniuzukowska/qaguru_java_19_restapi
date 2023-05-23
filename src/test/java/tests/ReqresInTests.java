package tests;

import models.*;
import models.getSuccessfulSingleUserResponse.GetSuccessfulSingleUserResponseLombokModel;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static specs.GetSingleUserSpecs.*;
import static specs.LoginSpecs.*;
import static specs.UpdateUserSpecs.*;
import static io.qameta.allure.Allure.step;


public class ReqresInTests {
    @Test
    void successfulGetSingleUserTest() {
        GetSuccessfulSingleUserResponseLombokModel response = step("Make request", () ->
                given(getSingleUserRequestSpec)
                .when()
                .get("users/2")
                .then()
                .spec(successfulGetSingleUserResponseSpec)
                .extract().as(GetSuccessfulSingleUserResponseLombokModel.class));

        step("Verify response", () -> {
                assertThat(response.getData().getId()).isEqualTo(2);
                assertThat(response.getData().getFirst_name()).isEqualTo("Janet");});
    }

    @Test
    void unSuccessfulGetSingleUserTest() {
        step("Make request", () ->
                given(getSingleUserRequestSpec)
                .when()
                .get("/users/абв")
                .then()
                .spec(unsuccessfulGetSingleUserResponseSpec));
    }

    @Test
    void successfulUpdateUserTest() {
        UpdateUserBodyLombokModel data = new UpdateUserBodyLombokModel();
        data.setName("morpheus");
        data.setJob("zion resident");

        UpdateUserResponseLombokModel response = step("Make request", () ->
                given(updateUserRequestSpec)
                .body(data)
                .when()
                .put("/users/2")
                .then()
                .spec(successfulUpdateUserResponseSpec)
                .extract().as(UpdateUserResponseLombokModel.class));

        step("Verify response", () -> {
            assertThat(response.getName()).isEqualTo("morpheus");
            assertThat(response.getJob()).isEqualTo("zion resident");});
    }

    @Test
    void successfulLoginTest() {
        LoginBobyLombokModel data = new LoginBobyLombokModel();
        data.setEmail("eve.holt@reqres.in");
        data.setPassword("cityslicka");

        LoginSuccessfulResponseLombokModel response = step("Make request", () ->
                given(loginRequestSpec)
                .body(data)
                .when()
                .post("/login")
                .then()
                .spec(successfulLoginResponseSpec)
                .extract().as(LoginSuccessfulResponseLombokModel.class));

        step("Verify response", () ->
                assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void unSuccessfulLoginTest() {
        LoginBobyLombokModel data = new LoginBobyLombokModel();
        data.setEmail("eve.holt@reqres.in");
        data.setPassword("");

        LoginUnSuccessfulResponseLombokModel response = step("Make request", () ->
                given(loginRequestSpec)
                .body(data)
                .when()
                .post("/login")
                .then()
                .spec(unSuccessfulLoginResponseSpec)
                .extract().as(LoginUnSuccessfulResponseLombokModel.class));

        step("Verify response", () ->
                assertThat(response.getError()).isEqualTo("Missing password"));
    }

}
