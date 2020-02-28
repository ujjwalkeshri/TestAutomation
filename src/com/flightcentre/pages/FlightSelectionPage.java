package com.flightcentre.pages;

import com.flightcentre.utility.CommonActions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * @author ujjwal keshri
 * @date 28/02/2020
 */
public class FlightSelectionPage {

    WebDriver driver;
    WebDriverWait wait;

    @FindBy(css = "div[class*='outboundSearchInfo']")
    WebElement departureDetails;

    @FindBy(css = "div[class*='inboundSearchInfo']")
    WebElement arrivalDetails;

    @FindBy(css = "div[class*='flightSortButton']")
    WebElement sortFlights;

    @FindBy(css = "li[data-value='priceasc']")
    WebElement priceCheapest;

    /*@FindBy(css = "div[class*='journeyInfo'] button")
    List<WebElement> listOfFlights;*/

    By listOfFlights = By.cssSelector("div[class*='journeyInfo'] button");

   /* @FindBy(css = "div[class*='fareFamilies'] button")
    List<WebElement> flightOptions;*/

    By flightOptions = By.cssSelector("div[class*='fareFamilies'] button");

    By contentLoad = By.cssSelector("div[class*='test-departingInterstitialContent']");

    @FindBy(css = "iframe[title='Usabilla Feedback Form']")
    WebElement feedbackFormFrame;

    @FindBy(css = "a#close")
    WebElement closeFeedBackPopup;

    public FlightSelectionPage(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
        PageFactory.initElements(driver, this);
    }

    public String getDepartureDetails(){
        return departureDetails.getAttribute("textContent");
    }

    public String getArrivalDetails(){
        return arrivalDetails.getAttribute("textContent");
    }

    public void clickFlightSortOption(){
        sortFlights.click();
    }

    public void sortFlightByPrice(){
        clickFlightSortOption();
        wait.until(ExpectedConditions.elementToBeClickable(priceCheapest));
        priceCheapest.click();
    }

    public void selectFirstFlight() {
        List<WebElement> listFlights = driver.findElements(listOfFlights);
        wait.until(ExpectedConditions.elementToBeClickable(listFlights.get(0)));
        listFlights.get(0).click();
        List<WebElement> listOfFlightOptions = driver.findElements(flightOptions);
        wait.until(ExpectedConditions.elementToBeClickable(listOfFlightOptions.get(0)));
        listOfFlightOptions.get(0).click();
    }

    public void closeFeedbackPopup(){
        wait.until(ExpectedConditions.visibilityOf(feedbackFormFrame));
        driver.switchTo().frame(feedbackFormFrame);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", closeFeedBackPopup);
       // closeFeedBackPopup.click();
        driver.switchTo().parentFrame();
    }

    public void selectCheapestFlights() throws InterruptedException {
        this.sortFlightByPrice();
        selectFirstFlight();
        new CommonActions().isAllElementLoaded(driver, contentLoad);
        closeFeedbackPopup();
        this.sortFlightByPrice();
        selectFirstFlight();
    }
}
