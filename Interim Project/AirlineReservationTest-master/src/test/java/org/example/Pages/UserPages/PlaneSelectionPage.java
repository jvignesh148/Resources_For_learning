package org.example.Pages.UserPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class PlaneSelectionPage {

    WebDriver driver;

    public PlaneSelectionPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button[@class='select-btn']")
    WebElement selectButton;

    @FindBy(xpath = "//button[@class='seat seat-economy']")
    WebElement seatSelect;

    @FindBy(xpath = "//button[@class='btn-continue']")
    WebElement continueButton;

    @FindBy(tagName = "h1")
    WebElement verification;

    @FindBy(xpath = "//button[@class='seat seat-tatkal seat-economy']")
    WebElement tatkalSeatButton;

    public void clickButton(){
        selectButton.click();
    }

    public void setSelectButton(){
        seatSelect.click();
    }

    public void setContinueButton(){
        continueButton.click();
    }

    public void validate(){
        Assert.assertEquals(verification.getText(),"Guest Details");
    }

    public void setTatkalSeatButton(){
        tatkalSeatButton.click();
    }
}
