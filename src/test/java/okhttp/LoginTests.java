package okhttp;

import dto.ContactsDto;
import dto.ErrorMessageDto;
import dto.TokenDto;
import dto.UserDto;
import interfaces.BaseApi;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import static utils.PropertiesReader.getProperty;
import static utils.RandomUtils.generateEmail;
import static utils.RandomUtils.generateString;


public class LoginTests implements BaseApi {

    SoftAssert softAssert = new SoftAssert();

    @Test
    public void loginPositiveTest() {
        UserDto user = new UserDto(getProperty("data.properties","email")
                ,getProperty("data.properties","password"));
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + LOGIN_PATH)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(response.isSuccessful());
    }

    @Test
    public void loginPositiveTest_ExRes200_validateToken() throws IOException {
        UserDto user = new UserDto(getProperty("data.properties","email")
                ,getProperty("data.properties","password"));
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + LOGIN_PATH)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (response.isSuccessful()) {
            TokenDto token = GSON.fromJson(response.body().string(), TokenDto.class);
            System.out.println(token.getToken());
            Assert.assertEquals(response.code(), 200);
        } else {
            ErrorMessageDto errorMessage = GSON.fromJson(response.body().string(), ErrorMessageDto.class);
            System.out.println(errorMessage.getError());
            Assert.fail("code -->" + response.code());
        }
    }
    @Test
    public void registrationNegativeTest_ExRes401() throws IOException {
        UserDto user = new UserDto(generateEmail(10), "7206Rom");
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + LOGIN_PATH)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ErrorMessageDto errorMessage = GSON.fromJson(response.body().string(), ErrorMessageDto.class);
        softAssert.assertEquals(response.code(), 401,"response code");
        softAssert.assertEquals(errorMessage.getStatus(),401,"status");
        softAssert.assertEquals(errorMessage.getError(),"Unauthorized","error");
        softAssert.assertTrue(errorMessage.getMessage().toString().contains("Login or Password incorrect"),"message valid");
        System.out.println(errorMessage.getMessage().toString());
        softAssert.assertAll();
    }
}
