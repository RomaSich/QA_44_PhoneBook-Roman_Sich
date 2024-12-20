package pages;

import dto.ContactDtoLombok;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AddPage extends BasePage{

    public AddPage(WebDriver driver) {
        setDriver(driver);
        PageFactory.initElements(
                new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(xpath = "//input[@placeholder='Name']")
    WebElement inputName;
    @FindBy(xpath = "//input[@placeholder='Last Name']")
    WebElement inputLastName;
    @FindBy(xpath = "//input[@placeholder='Phone']")
    WebElement inputPhone;
    @FindBy(xpath = "//input[@placeholder='email']")
    WebElement inputEmail;
    @FindBy(xpath = "//input[@placeholder='Address']")
    WebElement inputAddress;
    @FindBy(xpath = "//input[@placeholder='description']")
    WebElement inputDescription;
    @FindBy(xpath = "//button/b")
    WebElement btnSaveContact;


    public AddPage fillContactForm(ContactDtoLombok newContact)
    {
        inputName.sendKeys(newContact.getName());
        inputLastName.sendKeys(newContact.getLastName());
        inputPhone.sendKeys(newContact.getPhone());
        inputEmail.sendKeys(newContact.getEmail());
        inputAddress.sendKeys(newContact.getAddress());
        inputDescription.sendKeys(newContact.getDescription());
        return this;
    }
    public ContactPage clickBtnSaveContact()
    {
        btnSaveContact.click();
        return new  ContactPage(driver);
    }

    public AddPage closeAlert() {
        Alert alert = new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.alertIsPresent());
        System.out.println(alert.getText());
        alert.accept();
        return this;
    }

    public boolean btnSaveNoUse()
    {
        clickWait(By.xpath("//button/b"),10);
        return btnSaveContact.isEnabled();
    }
    public boolean isAlertPresent(int time) {
        try {
            Alert alert = new WebDriverWait(driver, Duration.ofSeconds(time))
                    .until(ExpectedConditions.alertIsPresent());
            System.out.println(alert.getText());
            alert.accept();
            return true;
        } catch (TimeoutException e) {
            e.printStackTrace();
            return false;
        }
    }
}
