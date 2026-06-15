package org.example.Tests.UserTest;

import org.example.Listeners.TestListener;
import org.example.Pages.UserPages.FlightStatusPage;
import org.example.Pages.UserPages.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

@Listeners(TestListener.class)
public class FlightStatusTest extends AuthenticatedBaseTest {

    FlightStatusPage flightStatusPage;


    @BeforeMethod
    public void goToFlightStatusPage() {
        driver.get("http://localhost:4200/flight-status");
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//input[@formcontrolname='flightNumber']")
                ));
        flightStatusPage = new FlightStatusPage(driver);
    }

    @DataProvider(name = "setFlightData")
    public Object[][] setFlightData() {
        return new Object[][]{
                {"DA 901", "15-06-2026"}
        };
    }

    @DataProvider(name = "invalidFlightData")
    public Object[][] invalidFlightData() {
        return new Object[][]{
                {"XX 999", "12-06-2026"},
                {"@@@@",   "12-06-2026"},
                {"DA 234", "01-01-2020"},
        };
    }


    @Test(dataProvider = "setFlightData")
    public void flightStatusCheck(String flightNo, String date) {
        flightStatusPage.setFightNumber(flightNo);
        flightStatusPage.setDateElement(date);
        flightStatusPage.setSubmitButton();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[@class='status-result']")));
        Assert.assertEquals(
                driver.findElement(By.xpath("//div[@class='flight-no-big']")).getText(),
                flightNo, "Flight number on result should match searched flight");
    }

    @Test
    public void invalidFlightCodeShouldShowNoResult() {
        flightStatusPage.setFightNumber("XX 999");
        flightStatusPage.setDateElement("12-06-2026");
        flightStatusPage.setSubmitButton();
        Assert.assertFalse(flightStatusPage.isDisplayed(),
                "Invalid flight code should not show result card");
    }


    @Test
    public void emptyFlightNumberShouldNotSubmit() {
        flightStatusPage.setDateElement("12-06-2026");
        flightStatusPage.setSubmitButton();
        Assert.assertTrue(driver.getCurrentUrl().contains("/flight-status"),
                "Empty flight number should not navigate away");
    }


    @Test
    public void emptyDateShouldNotSubmit() {
        flightStatusPage.setFightNumber("DA 234");
        flightStatusPage.setSubmitButton();
        Assert.assertTrue(driver.getCurrentUrl().contains("/flight-status"),
                "Empty date should not navigate away");
    }


    @Test
    public void bothEmptyShouldNotSubmit() {
        flightStatusPage.setSubmitButton();
        Assert.assertTrue(driver.getCurrentUrl().contains("/flight-status"),
                "Empty form should not navigate away");
    }


    @Test
    public void specialCharactersInFlightNumber() {
        flightStatusPage.setFightNumber("@@##$$");
        flightStatusPage.setDateElement("12-06-2026");
        flightStatusPage.setSubmitButton();
        Assert.assertFalse(flightStatusPage.isDisplayed(),
                "Special characters should not return a result");
    }

    @Test
    public void pastDateShouldShowNoResult() {
        flightStatusPage.setFightNumber("DA 234");
        flightStatusPage.setDateElement("01-01-2020");
        flightStatusPage.setSubmitButton();
        Assert.assertFalse(flightStatusPage.isDisplayed(),
                "Past date should not return a result");
    }

    @Test(dataProvider = "invalidFlightData")
    public void invalidCombinationsShouldNotShowResult(String flightNo, String date) {
        flightStatusPage.setFightNumber(flightNo);
        flightStatusPage.setDateElement(date);
        flightStatusPage.setSubmitButton();
        Assert.assertFalse(flightStatusPage.isDisplayed(),
                "Invalid input should not show result: " + flightNo + " / " + date);
    }
}