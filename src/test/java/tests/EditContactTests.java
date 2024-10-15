package tests;

import dto.ContactDtoLombok;
import dto.UserDto;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.ContactPage;
import pages.HomePage;
import pages.LoginPage;
import utils.HeaderMenuItem;
import utils.RetryAnalyzer;
import utils.TestNGListener;
import static utils.PropertiesReader.getProperty;

import java.lang.reflect.Method;

import static pages.BasePage.clickButtonsOnHeader;
import static utils.RandomUtils.*;
import static utils.RandomUtils.generateString;
@Listeners(TestNGListener.class)
public class EditContactTests extends ApplicationManager {

   //UserDto user = new UserDto("rom@gmail.com", "7206@Rom");
    UserDto user = new UserDto(getProperty("data.properties", "email")
           , getProperty("data.properties","password"));
    ContactPage contactPage;


    @BeforeMethod
    public void login() {
        logger.info("start method --> login");
        new HomePage(getDriver());
        LoginPage loginPage = clickButtonsOnHeader(HeaderMenuItem.LOGIN);
        contactPage = loginPage.typeLoginForm(user).clickBtnLoginPositive();

    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void editContactTest(Method method) {
       logger.info("start --> " + method.getName());

        ContactDtoLombok newContact = ContactDtoLombok.builder()
                .name("new-"+generateString(5)).
                lastName("new-"+generateString(10)).
                phone("054"+generatePhone(7)).
                email("new-"+generateEmail(6)).
                address("new-"+generateString(10))
                .description("new-"+generateString(10))
                .build();
        contactPage.clickLastPhone();
        contactPage.fillContactForm(newContact);
        contactPage.clickBtnSave();
        ContactDtoLombok contact = contactPage.getContactFromDetailedCard();
        Assert.assertEquals(newContact, contact);
    }
}
