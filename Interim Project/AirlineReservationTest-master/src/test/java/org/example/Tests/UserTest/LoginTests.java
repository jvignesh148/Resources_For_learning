package org.example.Tests.UserTest;

import org.example.Listeners.TestListener;
import org.example.Pages.UserPages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.time.Duration;

@Listeners(TestListener.class)
public class LoginTests extends BaseTest {

    @BeforeMethod
    public void checker() {
        if (driver.getCurrentUrl().contains("/login")) {
            return;
        }
        driver.findElement(By.xpath("//button[@class='nav-logout']")).click();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//input[@formcontrolname='email']")));
    }

    //-----------------------------------------------------------------------------------
    //                                  Data Provider
    //-----------------------------------------------------------------------------------

    @DataProvider(name = "validLoginData")
    public Object[][] validLoginData() {
        return new Object[][]{
                {"vignesh@gmail.com", "Vignesh@1234"}
        };
    }

    @DataProvider(name = "invalidLoginData")
    public Object[][] invalidLoginData() {
        return new Object[][]{
                {"Saran@gmail.com", "nobodyDeccan@6789"},
                {"nobody@deccan.com", "Saran@2004"},
                {"nobody@deccan.com", "nobodyDeccan@6789"},
                {"", "Saran@2004"},
                {"Saran@gmail.com", ""},
                {"", ""}
        };
    }

    //-----------------------------------------------------------------------------------
    //                                  Test Method
    //-----------------------------------------------------------------------------------

    @Test(priority = 1, dataProvider = "validLoginData")
    public void testValidLogin(String username, String password) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.setUsername(username);
        loginPage.setPassword(password);
        loginPage.clickButton();
        new WebDriverWait(driver, Duration.ofSeconds(4))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//span[contains(text(),'WELCOME')]")));
        Assert.assertEquals(driver.getCurrentUrl(), "http://localhost:4200/home");
    }

    @Test(priority = 2, dataProvider = "invalidLoginData")
    public void testInvalidLogin(String username, String password) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.setUsername(username);
        loginPage.setPassword(password);
        loginPage.clickButton();
        Assert.assertEquals(driver.getCurrentUrl(), "http://localhost:4200/login");
    }
}