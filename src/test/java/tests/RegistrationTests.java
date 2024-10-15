package tests;

import dto.UserDto;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import utils.TestNGListener;

import static utils.RandomUtils.generateString;
import static utils.RandomUtils.generateEmail;
import java.util.Random;
import static utils.PropertiesReader.getProperty;
@Listeners(TestNGListener.class)

public class RegistrationTests extends ApplicationManager {

    @Test
    public void registrationPositiveTest(){
        int i = new Random().nextInt(1000);
        String email = "roma" + i + "@gmail.com";
        Assert.assertTrue(new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm(email, getProperty("data.properties","password"))
                .clickBtnRegistrationPositive()
                .isElementContactPresent());
    }
    @Test
    public void registrationNegativeTest_wrongEmail(){
        String email = generateString(10);
        UserDto user = new UserDto(email,getProperty("data.properties","password"));
       new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm(user)
                .clickBtnRegistrationNegative()
               .closeAlert()
               .isTextInElementPresent_errorMessage("Registration failed with code 400");
    }
}
