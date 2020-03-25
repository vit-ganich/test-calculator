package com.calc.webui.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Class with common methods for all page objects
 *
 * @author VHanich
 */
public class BasePage {

    private int timeoutSeconds = 5;
    private WebDriver driver;

    public BasePage(WebDriver driver){
        this.driver = driver;
    }

    /**
     * Waits for element to be clickable and returns it
     *
     * @param locator   search criteria to find the element
     * @return  webElement
     */
    protected WebElement waitForElementIsClickable(By locator){
        WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Waits for element to be visible and returns it
     *
     * @param locator   search criteria to find the element
     * @return  webElement
     */
    protected WebElement waitForElementIsVisible(By locator){
        WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Waits for element has attribute with specified text
     *
     * @param locator   search criteria to find the element
     * @param attribute the attribute
     * @param text  the text the attribute contains
     * @return  boolean
     */
    protected boolean waitForElementAttributeContainsText(By locator, String attribute, String text){
        WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
        return wait.until(ExpectedConditions.attributeToBe(locator, attribute, text));
    }

    /**
     * Writes to text box element
     *
     * @param textBox   the text box webElement
     * @param value the value to write into the text box
     */
    protected void writeToTextBox(WebElement textBox, String value){
        textBox.clear();
        textBox.sendKeys(value);
    }
}
