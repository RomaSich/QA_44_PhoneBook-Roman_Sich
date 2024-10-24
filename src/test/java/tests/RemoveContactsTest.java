package tests;


import dto.UserDto;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.ContactPage;
import pages.HomePage;
import pages.LoginPage;
import utils.HeaderMenuItem;
import utils.TestNGListener;

import java.lang.reflect.Method;

import static pages.BasePage.clickButtonsOnHeader;
import static utils.PropertiesReader.getProperty;
@Listeners(TestNGListener.class)
public class RemoveContactsTest extends ApplicationManager {
    UserDto user = new UserDto(getProperty("data.properties","email"),getProperty("data.properties","password"));
    ContactPage contactPage;


    @BeforeMethod
    public void login() {
        logger.info("start method --> login");
        new HomePage(getDriver());
        LoginPage loginPage = clickButtonsOnHeader(HeaderMenuItem.LOGIN);
        contactPage = loginPage.typeLoginForm(user).clickBtnLoginPositive();
    }

    @Description("positive methode remove contact")
    @Owner("QA Roman")
    @Test
    public void removeContactTest(Method method) {
       int before = contactPage.getContactNumber();
        System.out.println("-->"+before);
        logger.info("start --> " + method.getName());
        Allure.step("click last Phone");
        contactPage.clickLastPhone();
        Allure.step("click Remove contact");
        contactPage.clickBtnRemoveContact();
        int after = contactPage.getContactNumber();
        System.out.println("-->"+after);
        Assert.assertEquals(before , after-1);
    }

}
