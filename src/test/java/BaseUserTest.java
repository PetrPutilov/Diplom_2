import api.client.UserClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.LoginUserRequest;
import model.RegisterUserRequest;
import org.junit.After;
import org.junit.Before;

public class BaseUserTest extends BaseTest {

    private final UserClient userClient = new UserClient();

    protected String accessToken;

    protected RegisterUserRequest registerUserRequest;

    @Before
    public void registerUserRequest() {
        registerUserRequest = new RegisterUserRequest("yippeekiyay@mthfckr.com", "12345678", "John McClane");
    }

    @After
    public void deleteUser() {
        deleteUser(accessToken);
    }

    @Step("register user")
    public Response registerUser(RegisterUserRequest request) {
        return userClient.registerUser(request);
    }

    @Step("login user")
    public Response loginUser(LoginUserRequest request) {
        return userClient.loginUser(request);
    }

    @Step("delete user")
    public Response deleteUser(String accessToken) {
        if (accessToken == null) return null;
        String pureToken = accessToken.substring(7);
        return userClient.deleteUser(pureToken);
    }

}
