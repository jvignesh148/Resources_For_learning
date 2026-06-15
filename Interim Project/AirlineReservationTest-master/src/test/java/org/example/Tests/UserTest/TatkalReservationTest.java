package org.example.Tests.UserTest;

import org.example.Listeners.TestListener;
import org.example.Pages.UserPages.GuestDetailsPage;
import org.example.Pages.UserPages.HomePage;
import org.example.Pages.UserPages.PlaneSelectionPage;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestListener.class)
public class TatkalReservationTest extends AuthenticatedBaseTest {

    HomePage homePage;
    HomePageTest homePageTest;
    PlaneSelectionPage planeSelectionPage;
    GuestDetailsPage guestDetailsPage;


    @DataProvider
    public Object[][] guestDataMethod() {
        return new Object[][]{
                {"Virat", "Kohli", "38", "MALE", "9788376597"}
        };
    }

    @Test(priority = 1)
    public void tatkalTest(){
        homePage = new HomePage(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,900)");

        homePage.setTatkalElement();

    }

    @Test(priority = 2)
    public void seatSelection() {
        planeSelectionPage = new PlaneSelectionPage(driver);
        planeSelectionPage.setTatkalSeatButton();
        planeSelectionPage.setContinueButton();

        planeSelectionPage.validate();
    }

    @Test(dataProvider = "guestDataMethod", priority = 3)
    public void guestDetails(String fName, String lName, String age, String gender, String passport) {
        guestDetailsPage = new GuestDetailsPage(driver);
        guestDetailsPage.setFirstName(fName);
        guestDetailsPage.setLastName(lName);
        guestDetailsPage.setAge(age);
        guestDetailsPage.setGender(gender); 
        guestDetailsPage.setPassportNumber(passport);
        guestDetailsPage.clickSubmit();
        guestDetailsPage.clickConfirm();
    }

}
