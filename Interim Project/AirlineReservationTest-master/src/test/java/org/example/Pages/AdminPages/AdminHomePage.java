package org.example.Pages.AdminPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AdminHomePage {
    WebDriver driver;
    public AdminHomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button[@class='btn btn-primary']")
    WebElement submitButton;

    @FindBy(xpath = "//input[contains(@placeholder,'DA 203')]")
    WebElement flightCode;

    @FindBy(xpath = "//input[@formcontrolname='airlineName']")
    WebElement airlineName;

    @FindBy(xpath = "//select[@formcontrolname='originCode']")
    WebElement origin;

    @FindBy(xpath = "//select[@formcontrolname='destCode']")
    WebElement destination;

    @FindBy(xpath = "//input[@formcontrolname='departureDate']")
    WebElement departureDate;

    @FindBy(xpath = "//input[@formcontrolname='departureTime']")
    WebElement departureTime;

    @FindBy(xpath = "//input[@formcontrolname='arrivalDate']")
    WebElement arrivalDate;

    @FindBy(xpath = "//input[@formcontrolname='arrivalTime']")
    WebElement arrivalTime;

    @FindBy(xpath = "//input[@formcontrolname='totalSeats']")
    WebElement totalSeats;

    @FindBy(xpath = "//input[@formcontrolname='tatkalQuotaSeats']")
    WebElement tatkalSeats;

    @FindBy(xpath = "//input[@formcontrolname='baseFare']")
    WebElement baseFare;

    @FindBy(xpath = "//button[normalize-space()='Create flight']")
    WebElement createBtn;


    public void enterFlightCode(String code) {
        flightCode.sendKeys(code);
    }

    public void enterAirlineName(String name) {
        airlineName.sendKeys(name);
    }

    public void selectOrigin(String originValue) {
        origin.sendKeys(originValue);  // or Select class if needed
    }

    public void selectDestination(String destValue) {
        destination.sendKeys(destValue);
    }

    public void enterDepartureDate(String date) {
        departureDate.sendKeys(date);
    }

    public void enterDepartureTime(String time) {
        departureTime.sendKeys(time); // HH:mm
    }

    public void enterArrivalDate(String date) {
        arrivalDate.sendKeys(date);
    }

    public void enterArrivalTime(String time) {
        arrivalTime.sendKeys(time);
    }

    public void enterTotalSeats(String seats) {
        totalSeats.clear();
        totalSeats.sendKeys(seats);
    }

    public void enterTatkalSeats(String seats) {
        tatkalSeats.clear();
        tatkalSeats.sendKeys(seats);
    }

    public void enterBaseFare(String fare) {
        baseFare.clear();
        baseFare.sendKeys(fare);
    }

    public void clickCreateFlight() {
        createBtn.click();
    }

    public void clickSubmitButton(){
        submitButton.click();
    }

}


