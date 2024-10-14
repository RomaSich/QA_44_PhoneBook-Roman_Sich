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
    //============================================
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
    //===============================================
    @FindBy(xpath = "//div[@class='contact-item-detailed_card__50dTS']/h2")
    WebElement contactCardNameLastName;
    @FindBy(xpath = "//div[@class='contact-item-detailed_card__50dTS']")
    WebElement contactCardPhoneEmailAddress;
    @FindBy(xpath = "//div[@class='contact-item-detailed_card__50dTS']/h3")
    WebElement contactCardDescription;

    public ContactPage fillContactForm(ContactDtoLombok contact)
    {
        btnEditContact.click();
        inputName.clear();
        inputName.sendKeys(contact.getName());
        inputLastName.clear();
        inputLastName.sendKeys(contact.getLastName());
        inputPhone.clear();
        inputPhone.sendKeys(contact.getPhone());
        inputEmail.clear();
        inputEmail.sendKeys(contact.getEmail());
        inputAddress.clear();
        inputAddress.sendKeys(contact.getAddress());
       inputDescription.clear();
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
    public int getContactNumber()
    {
     return driver.findElements(By.xpath("//div[@class='contact-item_card__2SOIM'][last()]/h3")).size();

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

    public void clickLastPhone(){clickWait(lastPhoneInList,10);}
    public void clickBtnRemoveContact()
    {
        clickWait(btnRemoveContact,10);
    }
    public void clickBtnSave()
    {
        clickWait(btnSave,10);
    }

    public ContactDtoLombok getContactFromDetailedCard()
    {
        pause(2);
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(contactCardNameLastName.getText().split(" ")[0])
                .lastName(contactCardNameLastName.getText().split(" ")[1])
                .phone(contactCardPhoneEmailAddress.getText().split("\n")[1])
                .email(contactCardPhoneEmailAddress.getText().split("\n")[2])
                .address(contactCardPhoneEmailAddress.getText().split("\n")[3])
                .description(contactCardDescription.getText().split(": ")[1])
                .build();
        return contact;
    }

}
