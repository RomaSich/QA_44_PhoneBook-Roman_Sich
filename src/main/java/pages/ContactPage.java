package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
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
    @FindBy(xpath = "//div[@class='contact-item_card__2SOIM']/h3")
    List<WebElement> lastPhoneInContactList;


    public boolean isElementContactPresent() {
        return btnContact.isDisplayed();
    }

    public boolean isLastPhoneEquals(String phone) {
        return lastPhoneInList.getText().equals(phone);
    }

   public int getNumberOfContacts()
   {
       return listOfContacts.size();
   }
    public List<String> getAllContactPhones() {
        List<String> phones = new ArrayList<>();
        for (WebElement phoneElement : listOfContacts) {
            phones.add(phoneElement.getText());
        }
        return phones;
    }
    public boolean isPhoneAddedToList(String newPhone, List<String> initialPhones) {
        List<String> updatedPhones = getAllContactPhones();
        updatedPhones.removeAll(initialPhones);
        return updatedPhones.contains(newPhone);
    }
}
