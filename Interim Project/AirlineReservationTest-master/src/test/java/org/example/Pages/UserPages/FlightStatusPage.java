package org.example.Pages.UserPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;

public class FlightStatusPage {
    WebDriver driver;

    public FlightStatusPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//input[@formcontrolname='flightNumber']")
    WebElement fightNumber;

    @FindBy(xpath = "//input[@formcontrolname='date']")
    WebElement dateElement;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement submitButton;

    @FindBy(xpath = "//div[@class='status-result']")
    WebElement resultCard;

    @FindBy(xpath = "//div[contains(@class,'error') or contains(@class,'no-result')]")
    WebElement errorMessage;

    public void setFightNumber(String flightNo){
        fightNumber.sendKeys(flightNo);
    }

    public void setDateElement(String date){
        dateElement.sendKeys(date);
    }

    public void setSubmitButton(){
        submitButton.click();
    }

    public boolean isErrorShown() {
        try {
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    public boolean isDisplayed() {
        try {
            List<WebElement> results = driver.findElements(
                    By.xpath("//div[@class='status-result']"));
            return !results.isEmpty() && results.get(0).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
