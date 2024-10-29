package okhttp;

import dto.*;
import interfaces.BaseApi;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;

import static utils.PropertiesReader.getProperty;
import static utils.RandomUtils.*;
import static utils.RandomUtils.generateString;

public class POSTaddNewContacts implements BaseApi {
    TokenDto token;
    SoftAssert softAssert = new SoftAssert();

    @BeforeClass
    public void loginUser() {
        UserDto user = new UserDto(getProperty("data.properties", "email"),
                getProperty("data.properties", "password"));
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + LOGIN_PATH)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
            token = GSON.fromJson(response.body().string(), TokenDto.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (token != null) {
            System.out.println(token.toString());
        } else {
            System.out.println("Something went wrong !!!");
        }
    }

    @Test
    public void addContactPositiveTest() throws IOException {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5)).
                lastName(generateString(10)).
                phone(generatePhone(10)).
                email(generateEmail(12)).
                address(generateString(20)).
                description(generateString(10))
                .build();
        RequestBody requestBody = RequestBody.create(GSON.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + GET_ALL_CONTACTS_PATH)
                .addHeader("Authorization", "Bearer " + token.getToken())
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
            if (response.isSuccessful()) {
                ResponseMessageDto responseMessage = GSON.fromJson(response.body().string(), ResponseMessageDto.class);
                System.out.println("Response message: " + responseMessage.message);
            } else {
                System.out.println("Something went wrong. Status code: " + response.code());
            }
       Assert.assertTrue(response.isSuccessful());
    }
//    @Test
//    public void addContactNegativeTest_ExRes403() throws IOException {
//        ContactDtoLombok contact = ContactDtoLombok.builder()
//                .name(generateString(5))
//                .lastName(generateString(10))
//                .phone(generatePhone(10))
//                .email(generateEmail(12))
//                .address(generateString(20))
//                .description(generateString(10))
//                .build();
//
//        RequestBody requestBody = RequestBody.create(GSON.toJson(contact), JSON);
//        Request request = new Request.Builder()
//                .url(BASE_URL + GET_ALL_CONTACTS_PATH)
//                .post(requestBody)
//                .build();
//        Response response;
//        try {
//            response = OK_HTTP_CLIENT.newCall(request).execute();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        ErrorMessageDto errorMessage = GSON.fromJson(response.body().string(),ErrorMessageDto.class);
//        softAssert.assertEquals(response.code(),403, "response code");
//        //softAssert.assertEquals(errorMessage.getStatus(),403,"status");
//        System.out.println(errorMessage.getMessage().toString());
//        softAssert.assertAll();
//    }
}

