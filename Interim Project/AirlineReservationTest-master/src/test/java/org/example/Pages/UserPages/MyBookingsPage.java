package org.example.Pages.UserPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MyBookingsPage {
    WebDriver driver;

    public MyBookingsPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//article[@class='booking-card']")
    List<WebElement> bookingCard;

    @FindBy(xpath = "//button[contains(text(),' Cancelled ')]")
    WebElement cancelBooking;

    @FindBy(xpath = "//div[@class='pnr-value']")
    List<WebElement> cancelledElement;

    public List<WebElement> setBookingCard(){
        return bookingCard;
    }

    public void setCancelBooking(){
        cancelBooking.click();
    }

    public List<WebElement> setCancelledBooking(){
        return cancelledElement;
    }
}
