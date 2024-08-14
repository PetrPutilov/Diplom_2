import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;

public class MakeOrderTests extends BaseUserOrderSteps {

    @Test
    @DisplayName("make order with authorization")
    public void orderWithAuth() {
        Response registerUserResponse = registerUser(registerUserRequest);
        setAccessToken(registerUserResponse.getBody().as(RegisterUserResponse.class).getAccessToken());

        IngredientsResponse ingredientsResponse = getIngredients().as(IngredientsResponse.class);

        MakeOrderRequest makeOrderRequest = new MakeOrderRequest(List.of(ingredientsResponse.getData().get(0).get_id()));

        Response makeOrderResponse = makeOrder(makeOrderRequest, getAccessToken());

        makeOrderResponse.then().statusCode(200)
                .and().assertThat().body("success", equalTo(true));

        Assert.assertEquals(registerUserRequest.getName(), makeOrderResponse.as(MakeOrderResponse.class).getOrder().getOwner().getName());
    }

    @Test
    @DisplayName("make order with authorization and ensure ingredients are ok")
    public void ingredientsAreOk() {
        Response registerUserResponse = registerUser(registerUserRequest);
        setAccessToken(registerUserResponse.getBody().as(RegisterUserResponse.class).getAccessToken());

        IngredientsResponse ingredientsResponse = getIngredients().as(IngredientsResponse.class);
        Ingredient burgerIngredient = ingredientsResponse.getData().get(0);
        MakeOrderRequest makeOrderRequest = new MakeOrderRequest(List.of(burgerIngredient.get_id()));

        Response makeOrderResponse = makeOrder(makeOrderRequest, getAccessToken());

        makeOrderResponse.then().statusCode(200)
                .and().assertThat().body("success", equalTo(true));

        Assert.assertFalse(makeOrderResponse.as(MakeOrderResponse.class).getOrder().getIngredients().isEmpty());
        Assert.assertEquals(burgerIngredient.get_id(), makeOrderResponse.as(MakeOrderResponse.class).getOrder().getIngredients().get(0).get_id());
    }

    @Test
    @DisplayName("make order with no authorization")
    public void orderWithNoAuth() {

        IngredientsResponse ingredientsResponse = getIngredients().as(IngredientsResponse.class);

        MakeOrderRequest makeOrderRequest = new MakeOrderRequest(List.of(ingredientsResponse.getData().get(0).get_id()));

        Response makeOrderResponse = makeOrder(makeOrderRequest);

        makeOrderResponse.then().statusCode(200)
                .and().assertThat().body("success", equalTo(true));

        Assert.assertNotNull(makeOrderResponse.as(MakeOrderResponse.class).getOrder().getNumber());
        Assert.assertNull(makeOrderResponse.as(MakeOrderResponse.class).getOrder().getOwner());
    }

    @Test
    @DisplayName("make order with no ingredients")
    public void orderWithNoIngredients() {

        MakeOrderRequest makeOrderRequest = new MakeOrderRequest(List.of());

        Response makeOrderResponse = makeOrder(makeOrderRequest);

        makeOrderResponse.then().statusCode(400)
                .and().assertThat().body("success", equalTo(false));
    }

    @Test
    @DisplayName("make order with wrong ingredients")
    public void orderWithWrongIngredients() {
        Faker faker = new Faker();

        MakeOrderRequest makeOrderRequest = new MakeOrderRequest(List.of(faker.idNumber().invalid()));

        Response makeOrderResponse = makeOrder(makeOrderRequest);

        makeOrderResponse.then().statusCode(500);

    }

}
