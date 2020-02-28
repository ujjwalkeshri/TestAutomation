package com.flightcentre.utility;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

/**
 * @author ujjwal keshri
 * @date 28/02/2020
 */
public class CommonActions {

    public boolean isElementExists(WebDriver driver, By element){
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        boolean isElementExits;
        isElementExits = driver.findElements(element).size() != 0;
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        return isElementExits;
    }

    public void isAllElementLoaded(WebDriver driver, By element) throws InterruptedException {
        boolean isElement = isElementExists(driver, element);
        while(isElement){
            Thread.sleep(500);
            isElement = isElementExists(driver, element);
        }
    }
}
