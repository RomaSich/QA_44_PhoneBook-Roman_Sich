package tests;

import dto.UserDto;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AddPage;
import pages.ContactPage;
import pages.HomePage;
import pages.LoginPage;
import utils.HeaderMenuItem;

import static pages.BasePage.clickButtonsOnHeader;

public class RemoveContactsTest extends ApplicationManager {
    UserDto user = new UserDto("rom@gmail.com", "7206@Rom");
    ContactPage contactPage;


    @BeforeMethod
    public void login() {
        new HomePage(getDriver());
        LoginPage loginPage = clickButtonsOnHeader(HeaderMenuItem.LOGIN);
        loginPage.typeLoginForm(user).clickBtnLoginPositive();
        contactPage = clickButtonsOnHeader(HeaderMenuItem.CONTACTS);
    }
@Test
    public void removeContactTest() {
    String lastPhone = contactPage.lastPhoneNumber();
        contactPage.clickLastPhone();
        contactPage.removeContact();
       Assert.assertTrue(contactPage.isPhonePresentInList(lastPhone));
    }
}
