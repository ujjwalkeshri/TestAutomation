package com.flightcentre.tests;

import com.flightcentre.pages.FlightSelectionPage;
import com.flightcentre.pages.HomePage;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author ujjwal keshri
 * @date 27/02/2020
 */
public class SearchFlightTest {

    WebDriver driver;
    HomePage searchFlight;
    FlightSelectionPage flightSelectionPage;
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

        searchFlight = new HomePage(driver);
        flightSelectionPage = new FlightSelectionPage(driver);

        String searchWindow = driver.getWindowHandle();
        System.out.println("Main window = " + searchWindow);
        searchFlight.searchReturnJourneyFlight("SYD", "BNE", "March", "10",
                "March", "20");
        Set<String> allWindows = driver.getWindowHandles();
        System.out.println("Number of windows = " + allWindows.size());
        for (String childWindow : allWindows) {
            System.out.println("Searching Window = " + childWindow);
            if (!childWindow.equals(searchWindow)) {
                driver.switchTo().window(childWindow);
                System.out.println("switched to child window");
                System.out.println(flightSelectionPage.getDepartureDetails());
                System.out.println(flightSelectionPage.getArrivalDetails());
                Assert.assertTrue("Departure details does not match the expected details",
                        flightSelectionPage.getDepartureDetails().contains("SYDBNE"));

                Assert.assertTrue("Arrival details does not match the expected details",
                        flightSelectionPage.getArrivalDetails().contains("BNESYD"));

                flightSelectionPage.selectCheapestFlights();
                break;
            }
        }
    }

    @After
    public void cleanUp() throws InterruptedException {
        Thread.sleep(5000);
       // driver.quit();
    }

}
