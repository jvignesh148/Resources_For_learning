package org.example.Tests.UserTest;

import org.example.Listeners.TestListener;
import org.example.Pages.UserPages.LoginPage;
import org.example.Pages.UserPages.RegisterPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

@Listeners(TestListener.class)
public class RegisterTest extends BaseTest {

    RegisterPage registerPage = new RegisterPage(driver);
    LoginPage loginPage;

    @BeforeClass
    public void navigateToRegister() {
        loginPage = new LoginPage(driver);
        loginPage.clickRegisterButton();
    }

    @BeforeMethod
    public void refreshPage() {
        driver.navigate().refresh();
    }

    //-----------------------------------------------------------------------------------
    //                                  Data Provider
    //-----------------------------------------------------------------------------------

    @DataProvider(name = "validRegisterData")
    public Object[][] validRegisterData() {
        return new Object[][]{
                {"Virat", "Kohli", "Abd1817@gmail.com", "7976643270", "Virat@1234", "Virat@1234"}
        };
    }

    @DataProvider(name = "invalidEmailData")
    public Object[][] invalidEmailData() {
        return new Object[][]{
                {"Virat", "Kohli", "notanemail", "9876543210", "Virat@1234", "Virat@1234"},
                {"Virat", "Kohli", "missing@", "9876543210", "Virat@1234", "Virat@1234"},
                {"Virat", "Kohli", "@nodomain.com", "9876543210", "Virat@1234", "Virat@1234"}
        };
    }

    @DataProvider(name = "missingFirstNameData")
    public Object[][] missingFirstNameData() {
        return new Object[][]{
                {"", "Kohli", "virat@gmail.com", "9876543210", "Virat@1234", "Virat@1234"}
        };
    }

    @DataProvider(name = "missingLastNameData")
    public Object[][] missingLastNameData() {
        return new Object[][]{
                {"Virat", "", "virat@gmail.com", "8789062515", "Virat@1234", "Virat@1234"}
        };
    }

    //-----------------------------------------------------------------------------------
    //                                  Test methods
    //-----------------------------------------------------------------------------------


    @Test(dataProvider = "validRegisterData")
    public void validRegistrationShouldSucceed(String fn, String ln, String mail,
                                               String ph, String pass, String confirmPass) {

        registerPage.register(fn, ln, mail, ph, pass, confirmPass);
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.not(
                        ExpectedConditions.urlContains("/register")));
        Assert.assertFalse(driver.getCurrentUrl().contains("/register"),
                "Successful registration should navigate away from register page");
    }

    @Test
    public void emptyFormShouldNotSubmit() {
        registerPage = new RegisterPage(driver);
        registerPage.clickRegister();
        Assert.assertTrue(driver.getCurrentUrl().contains("/register"),
                "Empty form should not navigate away");
    }

    @Test(dataProvider = "missingFirstNameData")
    public void missingFirstNameShouldNotSubmit(String fn, String ln, String mail,
                                                String ph, String pass, String confirmPass) {
        registerPage.register(fn, ln, mail, ph, pass, confirmPass);
        Assert.assertTrue(driver.getCurrentUrl().contains("/register"),
                "Missing first name should not submit");
    }

    @Test(dataProvider = "missingLastNameData")
    public void missingLastNameShouldNotSubmit(String fn, String ln, String mail,
                                               String ph, String pass, String confirmPass) {
        registerPage.register(fn, ln, mail, ph, pass, confirmPass);
        Assert.assertTrue(driver.getCurrentUrl().contains("/register"),
                "Missing last name should not submit");
    }

    @Test(dataProvider = "invalidEmailData")
    public void invalidEmailShouldNotSubmit(String fn, String ln, String mail,
                                            String ph, String pass, String confirmPass) {
        registerPage.register(fn, ln, mail, ph, pass, confirmPass);
        Assert.assertTrue(driver.getCurrentUrl().contains("/register"),
                "Invalid email should not submit: " + mail);
    }



}