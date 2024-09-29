package tests;

import dto.ContactDtoLombok;
import dto.UserDto;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ContactPage;
import pages.HomePage;
import pages.LoginPage;
import utils.HeaderMenuItem;

import java.lang.reflect.Method;

import static pages.BasePage.clickButtonsOnHeader;
import static utils.RandomUtils.*;
import static utils.RandomUtils.generateString;

public class EditContactTests extends ApplicationManager {

    UserDto user = new UserDto("rom@gmail.com", "7206@Rom");
    ContactPage contactPage;


    @BeforeMethod
    public void login() {
        logger.info("start method --> login");
        new HomePage(getDriver());
        LoginPage loginPage = clickButtonsOnHeader(HeaderMenuItem.LOGIN);
        loginPage.typeLoginForm(user).clickBtnLoginPositive();
        contactPage = clickButtonsOnHeader(HeaderMenuItem.CONTACTS);
    }

    @Test
    public void editContactTest(Method method) {
        String lastPhone = contactPage.lastPhoneNumber();
        logger.info("start --> " + method.getName());
        contactPage.clickLastPhone();
        contactPage.clickEditContact();
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5)).
                lastName(generateString(10)).
                phone(generatePhone(10)).
                email(generateEmail(10)).
                address(generateString(10)).
                description(generateString(10))
                .build();
        contactPage.fillContactForm(contact);
        contactPage.clickBtnSave();
        Assert.assertTrue(contactPage.isLastPhoneEquals(contact.getPhone()));
    }
}