package api.client;

import io.restassured.response.Response;
import model.MakeOrderRequest;

import static io.restassured.RestAssured.given;

public class UserOrderClient {

    public Response makeOrder(MakeOrderRequest request) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(request)
                .when()
                .post("/api/orders");
    }

    public Response makeOrder(MakeOrderRequest request, String accessToken) {
        return given()
                .header("Content-type", "application/json")
                .auth().oauth2(accessToken)
                .and()
                .body(request)
                .when()
                .post("/api/orders");
    }

    public Response getIngredients() {
        return given()
                .when()
                .get("/api/ingredients");
    }

    public Response getUserOrders() {
        return given()
                .when()
                .get("/api/orders");
    }

    public Response getUserOrders(String accessToken) {
        return given()
                .auth().oauth2(accessToken)
                .when()
                .get("/api/orders");
    }
}
