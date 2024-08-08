import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.LoginUserRequest;
import model.RegisterUserResponse;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class UserLoginTests extends BaseUserTest {

    @Test
    @DisplayName("login existing user")
    public void loginExistingUser() {
        Response registerUserResponse = registerUser(registerUserRequest);
        accessToken = registerUserResponse.getBody().as(RegisterUserResponse.class).getAccessToken();

        Response loginUserResponse = loginUser(new LoginUserRequest(registerUserRequest.getEmail(), registerUserRequest.getPassword()));
        loginUserResponse.then().statusCode(200)
                .and().assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("login with wrong credentials")
    public void loginWithWrongCreds() {
        Response registerUserResponse = registerUser(registerUserRequest);
        accessToken = registerUserResponse.getBody().as(RegisterUserResponse.class).getAccessToken();

        Response loginUserResponse = loginUser(new LoginUserRequest("blbla@mail.ru", "000"));
        loginUserResponse.then().statusCode(401)
                .and().assertThat().body("success", equalTo(false));
    }
}
