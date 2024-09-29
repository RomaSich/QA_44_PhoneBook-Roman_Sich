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

public class ContactPage extends BasePage {
    public ContactPage(WebDriver driver) {
        setDriver(driver);
        PageFactory.initElements(
                new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(xpath = "//a[text()='CONTACTS']")
    WebElement btnContact;
    @FindBy(xpath = "//div[@class='contact-item_card__2SOIM'][last()]/h3")
    WebElement lastPhoneInList;
    @FindBy(xpath = "//div[@class='contact-item_card__2SOIM']")
    List<WebElement> listOfContacts;
    @FindBy(xpath = "//button[text()='Remove']")
    WebElement btnRemoveContact;
    @FindBy(xpath = "//button[text()='Edit']")
    WebElement btnEditContact;

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
    @FindBy(xpath = "//input[@placeholder='desc']")
    WebElement inputDescription;
    @FindBy(xpath = "//button[text()='Save']")
    WebElement btnSave;

    public ContactPage fillContactForm(ContactDtoLombok contact)
    {
        inputName.sendKeys(contact.getName());
        inputLastName.sendKeys(contact.getLastName());
        inputPhone.sendKeys(contact.getPhone());
        inputEmail.sendKeys(contact.getEmail());
        inputAddress.sendKeys(contact.getAddress());
        inputDescription.sendKeys(contact.getDescription());
        return this;
    }
    public boolean isElementContactPresent() {
        return btnContact.isDisplayed();
    }

    public boolean isLastPhoneEquals(String phone) {
        return lastPhoneInList.getText().equals(phone);
    }
    public String lastPhoneNumber() {
        if (!listOfContacts.isEmpty()) {
            return listOfContacts.get(listOfContacts.size() - 1).getText();
        }
        return lastPhoneNumber();
    }
    public boolean isPhonePresentInList(String phone) {
       try {
           listOfContacts = driver.findElements(By.xpath("//div[@class='contact-item_card__2SOIM']"));

        for(WebElement element : listOfContacts) {
            if (element.getText().equals(phone)) {
                return true;
            }
        }
        }catch (StaleElementReferenceException e)
       {
           return isPhonePresentInList(phone);
       }return false;
    }

    public boolean urlContainsAdd(){
        return urlContains("add", 3);
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

    public void clickLastPhone()
    {
        clickWait(lastPhoneInList,10);
        lastPhoneInList.click();
    }
    public void removeContact()
    {
        clickWait(btnRemoveContact,10);
        btnRemoveContact.click();
    }
    public void clickEditContact()
    {
        clickWait(btnEditContact, 10);
    }
    public void clickBtnSave()
    {
        pause(5);
        clickWait(btnSave,10);
    }
}
