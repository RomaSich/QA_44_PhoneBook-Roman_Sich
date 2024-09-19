package tests;

import dto.UserDto;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import static utils.RandomUtils.generetString;
import static utils.RandomUtils.generateEmail;
import java.util.Random;


public class RegistrationTests extends ApplicationManager {

    @Test
    public void registrationPositiveTest(){
        int i = new Random().nextInt(1000);
        String email = "roma" + i + "@gmail.com";
        Assert.assertTrue(new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm(email, "7206@Pom")
                .clickBtnRegistrationPositive()
                .isElementContactPresent());
    }
    @Test
    public void registrationNegativeTest_wrongEmail(){
        String email = generetString(10);
        UserDto user = new UserDto(email,"7206@Pom");
       new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm(user)
                .clickBtnRegistrationNegative()
               .closeAlert()
               .isTextInElementPresent_errorMessage("Registration failed with code 400");
    }
}
