package org.example.Tests.UserTest;

import org.example.Pages.UserPages.LoginPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;

import java.time.Duration;

public class AuthenticatedBaseTest extends BaseTest {

    @BeforeClass(dependsOnMethods = "setUp")
    public void login() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.setUsername("vignesh@gmail.com");
        loginPage.setPassword("Vignesh@1234");
        loginPage.clickButton();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("/home"));
    }
}