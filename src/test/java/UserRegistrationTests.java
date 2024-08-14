import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.RegisterUserResponse;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class UserRegistrationTests extends BaseUserSteps {

    @Test
    @DisplayName("register unique user")
    public void registerUniqueUser() {
        Response registerUserResponse = registerUser(registerUserRequest);
        setAccessToken(registerUserResponse.getBody().as(RegisterUserResponse.class).getAccessToken());
        registerUserResponse.then().statusCode(200)
                .and().assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("register already registered user")
    public void registerAlreadyRegisteredUser() {
        Response registerUserResponse = registerUser(registerUserRequest);
        setAccessToken(registerUserResponse.getBody().as(RegisterUserResponse.class).getAccessToken());
        Response registerUserBadResponse = registerUser(registerUserRequest);

        registerUserBadResponse.then().statusCode(403)
                .and().assertThat().body("success", equalTo(false));
    }

    @Test
    @DisplayName("register user without required field")
    public void registerUniqueUserWithBadRequest() {
        registerUserRequest.setPassword(null);
        Response registerUserResponse = registerUser(registerUserRequest);

        registerUserResponse.then().statusCode(403)
                .and().assertThat().body("success", equalTo(false));
    }

}
