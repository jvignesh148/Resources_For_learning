package org.example.Tests.AdminTest;

import org.example.Pages.UserPages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.time.Duration;

public class AdminBaseTest {
    protected static WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        driver.get("http://localhost:4200/");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.setUsername("admin@deccan.com");
        loginPage.setPassword("Admin@123");
        loginPage.clickButton();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("/admin"));
    }

    @AfterClass
    public void tearDown() throws InterruptedException {
        Thread.sleep(3000);
        if (driver != null) {
            driver.quit();
        }
    }
}
