package tests;

import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;

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
        Assert.assertTrue(new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm("email", "7206@Pom")
                .clickBtnRegistrationNegative()
                .isTextInElementPresent_errorMessage400());
    }
}
