package org.example.Pages.UserPages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class HomePage {
    WebDriver driver;

    public HomePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//div[@class='class-dropdown']/select")
    WebElement drop;

    @FindBy(css = "input[formcontrolname='from']")
    WebElement from;

    @FindBy(css = "input[formcontrolname='to']")
    WebElement to;

    @FindBy(xpath = "//input[@type='date']")
    WebElement date;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement button;

    @FindBy(xpath = "//button[@class='book-btn']")
    WebElement tatkalElement;

    @FindBy(xpath = "//a[contains(text(),'Flight Status')]")
    WebElement flightStatusPage;

    @FindBy(xpath = "//a[contains(text(),'My Booking')]")
    WebElement myBookingPage;


    public void setDrop(String dropdown){
        Select select = new Select(drop);
        select.selectByValue(dropdown);
    }

    public void setFrom(String from1){
        from.sendKeys(from1);
    }

    public void setTo(String to1){
        to.sendKeys(to1);
    }

    public void setDate(String date1){
        date.sendKeys(date1);
    }

    public void clickButton(){
        button.click();
    }

    public void setTatkalElement(){
        tatkalElement.click();
    }

    public void setFlightStatusPage(){
        flightStatusPage.click();
    }

    public void setMyBookingPage(){
        myBookingPage.click();
    }
}
