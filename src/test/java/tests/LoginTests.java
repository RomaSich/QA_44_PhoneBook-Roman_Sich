package tests;

import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;

public class LoginTests extends ApplicationManager {

    @Test
    public void loginPositiveTest() {
        boolean result = new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm("rom@gmail.com", "7206@Rom")
                .clickBtnLoginPositive()
                .isElementContactPresent();
        Assert.assertTrue(result);
    }

    @Test
    public void loginNegativeTest_wrongPassword() {
        Assert.assertTrue(new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm("rom@gmail.com", "7206@Rom----")
                .clickBtnLoginNegative()
                .closeAlert()
                .isTextInElementPresent_errorMessage401())
                ;
    }
    @Test
    public void loginNegativeTest_wrongEmail() {
        Assert.assertTrue(new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm("rom@gmai.l.com", "7206@Rom")
                .clickBtnLoginNegative()
                .closeAlert()
                .isTextInElementPresent_errorMessage401())
        ;
    }
}
