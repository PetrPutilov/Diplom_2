import api.client.UserOrderClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.MakeOrderRequest;

public class BaseUserOrderTest extends BaseUserTest {
    private final UserOrderClient userOrderClient = new UserOrderClient();

    @Step("make order with no authorization")
    public Response makeOrder(MakeOrderRequest request) {
        return userOrderClient.makeOrder(request);
    }

    @Step("make order with authorization")
    public Response makeOrder(MakeOrderRequest request, String accessToken) {
        return userOrderClient.makeOrder(request, accessToken);
    }

    @Step("get ingredients")
    public Response getIngredients() {
        return userOrderClient.getIngredients();
    }
}