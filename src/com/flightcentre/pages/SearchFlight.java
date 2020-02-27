package com.flightcentre.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * @author ujjwal keshri
 * @date 27/02/2020
 */
public class SearchFlight {

    WebDriver driver;
    JavascriptExecutor js;

    By alertPopUp = By.cssSelector("div.ol-decline-section");

    @FindBy(css = "div.ol-decline-section")
    WebElement alertPopDismiss;

    @FindBy(css = "button[data-component-part='FlightTab']")
    WebElement flightTab;

    @FindBy(css = "label[data-component-part='TripType.ReturnRadio']")
    WebElement returnTripRadioButton;

    @FindBy(xpath = "//label[text()='Flying from']/following-sibling::div/input")
    WebElement flyingFromInput;

    @FindBy(xpath = "//li[@data-component='AirportAutoComplete.Item']")
    List<WebElement> toFromAirportSelect;

    @FindBy(xpath = "//label[text()='Flying to']/following-sibling::div/input")
    WebElement flyingTo;

    @FindBy(css = "input[name='departDate']")
    WebElement departureDate;

    @FindBy(xpath = "//div[@role='presentation']/button[@tabindex='0']")
    List<WebElement> dates;

    @FindBy(css = "div[role=document] p")
    WebElement monthYear;

    @FindBy(xpath = "//p/../following-sibling::button")
    WebElement nextMonth;

    @FindBy(css = "input[name='arriveDate']")
    WebElement arrivalDate;

    @FindBy(xpath = "//button[contains(@class,'fcl-search__button')]")
    WebElement searchFlightButton;

    public void disablePopupIfVisible(){
        if(driver.findElements(alertPopUp).size() !=0) {
            if (alertPopDismiss.isDisplayed())
                alertPopDismiss.click();
        }
    }

    public SearchFlight(WebDriver driver){
        this.driver = driver;
        js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver,this);
    }

    public void clickFlightTab(){
        flightTab.click();
    }

    public void clickReturnJourneyRadioButton(){
        returnTripRadioButton.click();
    }

    public void setFlyingFromAirport(String departureAirportCode){
        flyingFromInput.sendKeys(departureAirportCode.toUpperCase());
    }

    public void selectCorrectAirport(String airport) throws Exception {
        try {
            boolean isAirportFound = false;
            for (WebElement suggestion : toFromAirportSelect) {
                if (suggestion.getAttribute("textContent").contains(airport.toUpperCase())) {
                    isAirportFound = true;
                    suggestion.click();
                    break;
                }
            }
            if(!isAirportFound){
                throw new Exception("Airport " +airport+" not found please enter correct airport");
            }
        }catch (Exception e){
            throw e;
        }
    }

    public void setFlyingToAirport(String arrivalAirport){
        flyingTo.sendKeys(arrivalAirport.toUpperCase());
    }

    public void setDepartureDate(String month, String date){
        departureDate.click();
        boolean isFound = false;
        while(!isFound) {
            if (monthYear.getText().contains(month)) {
                for (WebElement d : dates) {
                    if(d.getAttribute("textContent").equals(date)){
                        d.click();
                        break;
                    }
                }
                isFound = true;
            } else
                nextMonth.click();
        }
    }

    public void setArrivalDate(String month, String date) throws InterruptedException {
        int tryCount = 0;

        while(tryCount<5) {
            try {
                dates = driver.findElements(By.xpath("//div[@role='presentation']/button[@tabindex='0']"));
                boolean isFound = false;
                while (!isFound) {
                    if (monthYear.getText().contains(month)) {
                        for (WebElement d : dates) {
                            if (d.getAttribute("textContent").equals(date)) {
                                d.click();
                                break;
                            }
                        }
                        isFound = true;
                    } else
                        nextMonth.click();
                }
                if(isFound)
                    break;
            }catch (StaleElementReferenceException e){
                Thread.sleep(100);
                tryCount++;
            }
        }
    }

    public void clickSearchFlight(){
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(searchFlightButton));
        searchFlightButton.click();
    }
}
