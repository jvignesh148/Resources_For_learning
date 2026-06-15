package org.example.Tests.AdminTest;

import org.example.Listeners.TestListener;
import org.example.Pages.AdminPages.AdminHomePage;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestListener.class)
public class AdminHomePageTest extends AdminBaseTest {
    AdminHomePage page;

    @DataProvider(name = "flightData")
    public Object[][] flightData() {
        return new Object[][] {
                { "DA 897", "Deccan Airlines", "MAA — Chennai", "BOM — Mumbai", "10/06/2026", "17:20", "10/06/2026", "18:15", "180", "20", "5000" },
        };
    }

    @Test(dataProvider = "flightData")
    public void createFlightpageTest (String flightCode, String airlineName, String origin, String destination, String departureDate, String departureTime, String arrivalDate, String arrivalTime, String totalSeats, String tatkalSeats, String baseFare){
        page = new AdminHomePage(driver);
        page.clickSubmitButton();
        page.enterFlightCode(flightCode);
        page.enterAirlineName(airlineName);
        page.selectOrigin(origin);
        page.selectDestination(destination);

        page.enterDepartureDate(departureDate);
        page.enterDepartureTime(departureTime);

        page.enterArrivalDate(arrivalDate);
        page.enterArrivalTime(arrivalTime);

        page.enterTotalSeats(totalSeats);
        page.enterTatkalSeats(tatkalSeats);
        page.enterBaseFare(baseFare);

        page.clickCreateFlight();

    }

}



