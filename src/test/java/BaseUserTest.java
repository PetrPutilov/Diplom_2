import api.client.UserClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.LoginUserRequest;
import model.RegisterUserRequest;
import model.UpdateUserRequest;
import org.junit.After;
import org.junit.Before;

public class BaseUserTest extends BaseTest {

    private final UserClient userClient = new UserClient();

    private String accessToken;

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

    @Step("update user")
    public Response updateUser(UpdateUserRequest request, String accessToken) {
        return userClient.updateUser(request, accessToken);
    }

    @Step("get user")
    public Response getUser(String accessToken) {
        return userClient.getUser(accessToken);
    }

    @Step("delete user")
    public Response deleteUser(String accessToken) {
        if (accessToken == null) return null;
        return userClient.deleteUser(accessToken);
    }

    protected void setAccessToken(String accessToken){
        if (accessToken == null) {
            this.accessToken = null;
            return;
        }
        if (accessToken.contains("Bearer ")){
            this.accessToken = accessToken.substring(7);
        } else {
            this.accessToken = accessToken;
        }
    }

    protected String getAccessToken() {
        return accessToken;
    }

}
