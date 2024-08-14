import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.RegisterUserResponse;
import model.UpdateUserRequest;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class UserUpdateTests extends BaseUserSteps {

    @Test
    @DisplayName("update user with authorization")
    public void updateUser() {
        Faker faker = new Faker();
        String newUserName = faker.name().fullName();
        Response registerUserResponse = registerUser(registerUserRequest);
        setAccessToken(registerUserResponse.getBody().as(RegisterUserResponse.class).getAccessToken());

        Response userInfoResponse = updateUser(new UpdateUserRequest(faker.internet().emailAddress(), registerUserRequest.getPassword(), newUserName), getAccessToken());
        userInfoResponse.then().statusCode(200)
                .and().assertThat().body("success", equalTo(true));

                getUser(getAccessToken()).then().statusCode(200)
                        .and().body("user.name", equalTo(newUserName));
    }

    @Test
    @DisplayName("update user with no authorization")
    public void updateUserWithNoAuth() {
        Response registerUserResponse = registerUser(registerUserRequest);
        setAccessToken(registerUserResponse.getBody().as(RegisterUserResponse.class).getAccessToken());

        Response userInfoResponse = updateUser(new UpdateUserRequest(registerUserRequest.getEmail(), registerUserRequest.getPassword(), "Hans Gruber"), "");
        userInfoResponse.then().statusCode(401)
                .and().assertThat().body("success", equalTo(false));
    }

}
