package tests;

import dto.ContactDtoLombok;
import dto.UserDto;
import manager.ApplicationManager;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.AddPage;
import pages.ContactPage;
import pages.HomePage;
import pages.LoginPage;
import utils.HeaderMenuItem;

import java.util.List;

import static utils.RandomUtils.*;
import static pages.BasePage.clickButtonsOnHeader;


public class AddContactsTests extends ApplicationManager {

    UserDto user = new UserDto("rom@gmail.com", "7206@Rom");
    AddPage addPage;


    @BeforeMethod
    public void login() {
        new HomePage(getDriver());
        LoginPage loginPage = clickButtonsOnHeader(HeaderMenuItem.LOGIN);
        loginPage.typeLoginForm(user).clickBtnLoginPositive();
        addPage = clickButtonsOnHeader(HeaderMenuItem.ADD);

    }


    @Test
    public void addNewContactPositiveTest() {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5)).
                lastName(generateString(10)).
                phone(generatePhone(10)).
                email(generateEmail(12)).
                address(generateString(20)).
                description(generateString(10))
                .build();
        ContactPage contactPage = new ContactPage(getDriver());
        addPage.fillContactForm(contact);
        addPage.clickBtnSaveContact();
        Assert.assertTrue(contactPage.isLastPhoneEquals(contact.getPhone()));
    }
    @Test
    public void ContactAddedPositiveTest() {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5)).
                lastName(generateString(10)).
                phone(generatePhone(10)).
                email(generateEmail(12)).
                address(generateString(20)).
                description(generateString(10))
                .build();

        addPage.fillContactForm(contact);
        addPage.clickBtnSaveContact();
        ContactPage contactPage = new ContactPage(getDriver());
        List<String> initialPhones = contactPage.getAllContactPhones();
        Assert.assertTrue(contactPage.isPhoneAddedToList(contact.getPhone(), initialPhones));
    }

    @Test
    public void addNewContactNegativeTestPhoneError(){
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5)).
                lastName(generateString(10)).
                phone(generateString(10)).
                email(generateEmail(10)).
                address(generateString(20)).
                description(generateString(10))
                .build();
        addPage.fillContactForm(contact);
        addPage.clickBtnSaveContact();
        addPage.closeAlert();
    }

    @Test
    public void addNewContactNegativeTestEmailError() {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(6)).
                lastName(generateString(10)).
                phone(generatePhone(10)).
                email(generatePhone(10)).
                address(generateString(20)).
                description(generateString(10))
                .build();
        addPage.fillContactForm(contact);
        addPage.clickBtnSaveContact();
        addPage.closeAlert();
    }
}
