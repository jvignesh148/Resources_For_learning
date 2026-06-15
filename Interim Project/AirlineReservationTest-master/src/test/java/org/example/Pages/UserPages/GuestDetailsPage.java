package org.example.Pages.UserPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class GuestDetailsPage {

    WebDriver driver;

    public GuestDetailsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@formcontrolname='firstName']")
    WebElement firstName;

    @FindBy(xpath = "//input[@formcontrolname='lastName']")
    WebElement lastName;

    @FindBy(xpath = "//input[@formcontrolname='age']")
    WebElement age;

    @FindBy(xpath = "//select[@formcontrolname='gender']")
    WebElement gender;

    @FindBy(xpath = "//input[@formcontrolname='passportNumber']")
    WebElement passportNumber;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement submitButton;

    @FindBy(xpath = "//button[@class='btn-confirm']")
    WebElement confirmButton;

    public void setFirstName(String name) {
        firstName.sendKeys(name);
    }

    public void setLastName(String name) {
        lastName.sendKeys(name);
    }

    public void setAge(String ageValue) {
        age.sendKeys(ageValue);
    }

    public void setGender(String genderValue) {
        Select s = new Select(gender);
        s.selectByValue(genderValue);
    }

    public void setPassportNumber(String passport) {
        passportNumber.sendKeys(passport);
    }

    public void clickSubmit() {
        submitButton.click();
    }

    public void clickConfirm() {
        confirmButton.click();
    }

    public void fillGuestDetails(String fName, String lName, String ageValue, String genderValue, String passport) {
        setFirstName(fName);
        setLastName(lName);
        setAge(ageValue);
        setGender(genderValue);
        setPassportNumber(passport);
        clickSubmit();
        clickConfirm();
    }
}