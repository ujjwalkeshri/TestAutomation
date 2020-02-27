package com.flightcentre.tests;

import com.flightcentre.pages.SearchFlight;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

/**
 * @author ujjwal keshri
 * @date 27/02/2020
 */
public class SearchFlightTest {

    WebDriver driver;
    SearchFlight objSearchFlight;
    String baseUrl = "https://www.flightcentre.com.au/";

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "./lib/chromedriver32.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-infobars");
        driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        driver.get(baseUrl);
    }

    @Test
    public void searchFlight() throws Exception {

        try {
            objSearchFlight = new SearchFlight(driver);
            objSearchFlight.disablePopupIfVisible();
            objSearchFlight.clickFlightTab();
            objSearchFlight.clickReturnJourneyRadioButton();
            objSearchFlight.setFlyingFromAirport("SYD");
            objSearchFlight.selectCorrectAirport("SYD");

            objSearchFlight.setFlyingToAirport("BNE");
            objSearchFlight.selectCorrectAirport("BNE");

            objSearchFlight.setDepartureDate("March", "10");
            objSearchFlight.setArrivalDate("March","15");

            objSearchFlight.clickSearchFlight();
            
        } catch (Throwable t) {
            throw t;
        }

    }

    @After
    public void cleanUp(){
        //driver.quit();
    }

}
