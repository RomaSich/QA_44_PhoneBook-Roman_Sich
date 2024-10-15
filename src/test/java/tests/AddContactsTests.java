package tests;

import data_provider.DPAddContact;
import dto.ContactDtoLombok;
import dto.UserDto;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.AddPage;
import pages.ContactPage;
import pages.HomePage;
import pages.LoginPage;
import utils.HeaderMenuItem;
import utils.TestNGListener;

import static utils.RandomUtils.*;
import static pages.BasePage.*;
import static utils.PropertiesReader.getProperty;
@Listeners(TestNGListener.class)


public class AddContactsTests extends ApplicationManager {

    UserDto user = new UserDto(getProperty("data.properties","email"),getProperty("data.properties","password"));
    AddPage addPage;
    ContactPage contactPage;


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
        int start = contactPage.getContactNumber();
        String lastPhone = contactPage.lastPhoneNumber();
        Assert.assertEquals(start, contactPage.getContactNumber());
    }

    @Test
    public void addNewContactNegativeTestPhoneError() {
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

       Assert.assertTrue(addPage.btnSaveNoUse());
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
       Assert.assertTrue(addPage.isAlertPresent(2));
    }

    @Test
    public void addNewContactNegativeTestNameIsNull() {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(0)).
                lastName(generateString(10)).
                phone(generatePhone(10)).
                email(generateEmail(10)).
                address(generateString(20)).
                description(generateString(10))
                .build();
        addPage.fillContactForm(contact);
        addPage.clickBtnSaveContact();
        Assert.assertTrue(addPage.btnSaveNoUse());
    }

    @Test
    public void addNewContactNegativeTestAddressEmpty() {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5)).
                lastName(generateString(10)).
                phone(generatePhone(10)).
                email(generateEmail(10)).
                address(generateString(0)).
                description(generateString(10))
                .build();
        Assert.assertTrue(addPage
                .fillContactForm(contact)
                .clickBtnSaveContact()
                .urlContainsAdd());
    }

    @Test(dataProvider = "addNewContactDP", dataProviderClass = DPAddContact.class)
    public void addNewContactNegativeTest_wrongEmailDP(ContactDtoLombok contact) {
        System.out.println("--> " + contact);
        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContact()
                .isAlertPresent(5))
        ;
    }

    @Test(dataProvider = "addNewContactDPFile", dataProviderClass = DPAddContact.class)
    public void addNewContactNegativeTest_wrongEmailDPFile(ContactDtoLombok contact) {
        System.out.println("--> " + contact);
        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContact()
                .isAlertPresent(5))
        ;
    }
    @Test(dataProvider = "addNewContactDPPhone", dataProviderClass = DPAddContact.class)
    public void addNewContactNegativeTestPhoneErrorDP(ContactDtoLombok contact) {
        addPage.fillContactForm(contact);
        addPage.clickBtnSaveContact();
        addPage.closeAlert();
        Assert.assertTrue(addPage.btnSaveNoUse());
    }
}
