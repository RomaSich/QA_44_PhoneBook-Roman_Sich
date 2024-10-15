package tests;

import manager.ApplicationManager;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import utils.TestNGListener;
import static utils.TakeScreenShot.takeScreenShot;
import java.lang.reflect.Method;
import static utils.PropertiesReader.getProperty;
@Listeners(TestNGListener.class)
public class LoginTests extends ApplicationManager {

    @Test
    public void loginPositiveTest(Method method) {

        boolean result = new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm(getProperty("data.properties","email"),getProperty("data.properties","password"))
                .clickBtnLoginPositive()
                .isElementContactPresent();
        takeScreenShot((TakesScreenshot) getDriver());
        Assert.assertTrue(result);
    }

    @Test
    public void loginNegativeTest_wrongPassword() {
        takeScreenShot((TakesScreenshot) getDriver());
        Assert.assertTrue(new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm("rom@gmail.com", "7206@Rom----")
                .clickBtnLoginNegative()
                .closeAlert()
                .isTextInElementPresent_errorMessage())

        ;
    }

    @Test
    public void loginNegativeTest_wrongEmail() {
        Assert.assertTrue(new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm("romgmai.l.com", getProperty("data.properties","password"))
                .clickBtnLoginNegative()
                .closeAlert()
                .isTextInElementPresent_errorMessage())
        ;
    }
}
