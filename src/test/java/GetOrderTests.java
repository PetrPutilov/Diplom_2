import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;

public class GetOrderTests extends BaseUserOrderSteps {

    @Test
    @DisplayName("get order for authorized user")
    public void getOrderWithAuth() {
        Response registerUserResponse = registerUser(registerUserRequest);
        setAccessToken(registerUserResponse.getBody().as(RegisterUserResponse.class).getAccessToken());

        IngredientsResponse ingredientsResponse = getIngredients().as(IngredientsResponse.class);
        Ingredient burgerIngredient = ingredientsResponse.getData().get(0);
        MakeOrderRequest makeOrderRequest = new MakeOrderRequest(List.of(burgerIngredient.get_id()));

        makeOrder(makeOrderRequest, getAccessToken());

        Response getOrdersResponse = getUserOrders(getAccessToken());

        getOrdersResponse.then().statusCode(200)
                .and().assertThat().body("success", equalTo(true));

        Assert.assertEquals(1, getOrdersResponse.as(GetOrdersResponse.class).getOrders().size());
        Assert.assertEquals(burgerIngredient.get_id(), getOrdersResponse.as(GetOrdersResponse.class).getOrders().get(0).getIngredients().get(0));
    }

    @Test
    @DisplayName("get order for non authorized user")
    public void getOrderWithNoAuth() {
        Response registerUserResponse = registerUser(registerUserRequest);
        setAccessToken(registerUserResponse.getBody().as(RegisterUserResponse.class).getAccessToken());

        IngredientsResponse ingredientsResponse = getIngredients().as(IngredientsResponse.class);
        Ingredient burgerIngredient = ingredientsResponse.getData().get(0);
        MakeOrderRequest makeOrderRequest = new MakeOrderRequest(List.of(burgerIngredient.get_id()));

        makeOrder(makeOrderRequest, getAccessToken());

        Response getOrdersResponse = getUserOrders();

        getOrdersResponse.then().statusCode(401)
                .and().assertThat().body("success", equalTo(false));
    }

}
