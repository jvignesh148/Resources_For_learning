package org.example.airlinereservation.test;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTest {

    WebDriver driver;

    @BeforeTest
    public void testSetup(){
        driver = new EdgeDriver();
        driver.get("http://localhost:4200/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Test(priority = 1)
    public void CheckLogin() throws InterruptedException {

        WebElement username = driver.findElement(By.xpath("//input[@placeholder='Username']"));
        username.sendKeys("Saran@gmail.com");

        WebElement pass = driver.findElement(By.xpath("//input[@type='password']"));
        pass.sendKeys("Saran@2004");

        WebElement button = driver.findElement(By.cssSelector("button[type='submit']"));
        button.click();

        Thread.sleep(2000);
        String linktext = driver.getCurrentUrl();

        Assert.assertEquals(linktext,"http://localhost:4200/home");
    }

    @Test(priority = 2)
    public void checkBookTicket(){
        WebElement dropdown = driver.findElement(By.xpath("//div[@class='class-dropdown']/select"));
        Select select = new Select(dropdown);

        select.selectByValue("PREMIUM_ECONOMY");

        WebElement flightCodeFrom = driver.findElement(By.cssSelector("input[formcontrolname='from']"));
        flightCodeFrom.sendKeys("MAA");

        WebElement flightCodeTo= driver.findElement(By.cssSelector("input[formcontrolname='to']"));
        flightCodeTo.sendKeys("COK");

        WebElement dateInput = driver.findElement(By.xpath("//input[@type='date']"));
        dateInput.sendKeys("16-05-2026");

        WebElement button = driver.findElement(By.xpath("//button[@type='submit']"));
        button.click();
    }

    @Test(priority = 3)
    public void seatSelection() {

        WebElement seatConfimButton = driver.findElement(By.xpath("//button[@class='select-btn']"));
        seatConfimButton.click();

        WebElement seatSelect = driver.findElement(By.xpath("//button[@class='seat seat-economy']"));
        seatSelect.click();

        WebElement seatContinueButton = driver.findElement(By.xpath("//button[@class='btn-continue']"));
        seatContinueButton.click();
    }

    @Test(priority = 4)
    public void enterGuestDetails(){

        WebElement firstName = driver.findElement(By.xpath("//input[@formcontrolname='firstName']"));
        firstName.sendKeys("Saranya");

        WebElement second = driver.findElement(By.xpath("//input[@formcontrolname='lastName']"));
        second.sendKeys("Roshika");

        WebElement age = driver.findElement(By.xpath("//input[@formcontrolname='age']"));
        age.sendKeys("21");

        WebElement gender = driver.findElement(By.xpath("//select[@formcontrolname='gender']"));
        Select s = new Select(gender);
        s.selectByValue("MALE");

        WebElement passportNumber = driver.findElement(By.xpath("//input[@formcontrolname='passportNumber']"));
        passportNumber.sendKeys("1234567890");

        driver.findElement(By.xpath("//button[@type='submit']")).click();

        driver.findElement(By.xpath("//button[@class='btn-confirm']")).click();


    }

    @AfterTest
    public void closeTest(){
        System.out.println("Success");
    }
}
