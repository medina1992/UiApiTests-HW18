package api;

import models.CredentialsModel;
import models.LoginResponseModel;

import static io.restassured.RestAssured.given;
import static specs.BaseSpecs.requestSpec;
import static specs.BaseSpecs.responseSpec;

public class AuthorizationRequests {

    public static LoginResponseModel login(CredentialsModel credentials) {
        return given(requestSpec)
                .body(credentials)
                .when()
                .post("/Account/v1/Login")
                .then()
                .spec(responseSpec(200))
                .extract().as(LoginResponseModel.class);
    }
}