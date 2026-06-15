package org.example.Pages.AdminPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class AdminBooking {
    WebDriver driver;

    public AdminBooking(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//a[@class='topnav-tab']")
    WebElement navElement;

    @FindBy(xpath = "//button[@class='btn-link']")
    WebElement viewPassengersClick;

    @FindBy(xpath = "//div[@class='bookings-header']")
    WebElement bookingCheck;

    public void setNavElement(){
        navElement.click();
    }

    public void setViewPassengersClick(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//h1[contains(text(),'Bookings')]"))));
        viewPassengersClick.click();
    }

    public void setBookingCheck(){
        String[] bookingText = bookingCheck.getText().split(" ");
        for(String s:bookingText){
            if(s.matches("[0-9]+")){
                int numericValue = Integer.parseInt(s);
                Assert.assertTrue(numericValue>=1);
            }
        }


    }
}
