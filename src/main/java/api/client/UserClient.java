package api.client;

import io.restassured.response.Response;
import model.LoginUserRequest;
import model.RegisterUserRequest;

import static io.restassured.RestAssured.given;

public class UserClient {
    public Response registerUser(RegisterUserRequest request) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(request)
                .when()
                .post("/api/auth/register");
    }

    public Response loginUser(LoginUserRequest request) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(request)
                .when()
                .post("/api/auth/login");
    }

    public Response deleteUser(String accessToken) {
        return given()
                .header("Content-type", "application/json")
                .auth().oauth2(accessToken)
                .when()
                .delete("/api/auth/user");
    }
}
