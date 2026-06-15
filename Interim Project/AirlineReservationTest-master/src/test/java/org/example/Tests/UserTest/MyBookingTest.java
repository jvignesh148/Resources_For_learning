package org.example.Tests.UserTest;

import org.example.Listeners.TestListener;
import org.example.Pages.UserPages.HomePage;
import org.example.Pages.UserPages.MyBookingsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.List;

@Listeners(TestListener.class)
public class MyBookingTest extends AuthenticatedBaseTest {
    MyBookingsPage myBookingsPage;
    HomePage homePage;

    @Test
    public void cancelBookingValidation()  {
        homePage = new HomePage(driver);
        myBookingsPage = new MyBookingsPage(driver);
        homePage.setMyBookingPage();

        List<WebElement> li = myBookingsPage.setBookingCard();
        Assert.assertFalse(li.isEmpty(), "No booking cards were found on the page");

        String pnr = "";
        for (WebElement ele : li) {
            List<WebElement> buttons = ele.findElements(By.xpath(".//button"));

            if (!buttons.isEmpty()) {
                pnr = ele.findElement(By.xpath(".//div[@class='pnr-value']")).getText();
                buttons.get(0).click();
                break;
            }
        }

        driver.switchTo().alert().accept();

        Assert.assertFalse(pnr.isEmpty(), "No cancellable booking with a button was found");

        myBookingsPage.setCancelBooking();

        List<WebElement> cancelList = myBookingsPage.setCancelledBooking();
        boolean foundInCancelled = false;
        for (WebElement ele : cancelList) {
            if (ele.getText().equals(pnr)) {
                foundInCancelled = true;
                break;
            }
        }

        Assert.assertTrue(foundInCancelled,
                "Cancelled booking PNR " + pnr + " was not found in the cancelled list");
    }
}