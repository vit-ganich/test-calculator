package com.calc.webui.pages;
import com.calc.utils.Operations;
import com.calc.webui.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Page object for the Calculator page.
 * Contains webElements and custom methods to perform operations with the webElements
 *
 * @author VHanich
 */
public class CalcPage extends BasePage {

    private By label = By.cssSelector("tbody>tr>td");
    private By value1 = By.cssSelector("input[name='val1']");
    private By value2 = By.cssSelector("input[name='val2']");
    private By addition = By.cssSelector("input[value='add']");
    private By subtraction = By.cssSelector("input[value='sub']");
    private By multiplication = By.cssSelector("input[value='mul']");
    private By division = By.cssSelector("input[value='div']");
    private By calculate = By.cssSelector("input[type='submit']");
    private By RESULT = By.cssSelector("input[name='result']");

    public CalcPage(WebDriver driver) {
        super(driver);
    }

    public void enterFirstValue(String value){
        WebElement textBox = waitForElementIsClickable(value1);
        writeToTextBox(textBox, value);
    }

    public void enterSecondValue(String value){
        WebElement textBox = waitForElementIsClickable(value2);
        writeToTextBox(textBox, value);
    }

    public void selectOperation(Operations operation){
        WebElement operationElem = chooseOperation(operation);
        operationElem.click();
    }

    private WebElement chooseOperation(Operations operation) throws InvalidArgumentException {
        switch (operation){
            case ADDITION:
                return waitForElementIsClickable(addition);
            case DIVISION:
                return waitForElementIsClickable(division);
            case MULTIPLICATION:
                return waitForElementIsClickable(multiplication);
            case SUBTRACTION:
                return waitForElementIsClickable(subtraction);
            default:
                throw new InvalidArgumentException(String.format("Invalid operation: %s", operation));
        }
    }

    public void calculate(){
        WebElement calculate = waitForElementIsClickable(this.calculate);
        calculate.click();
    }

    public String readResult(){
        WebElement result = waitForElementIsClickable(RESULT);
        return result.getAttribute("value");
    }

    public String getLabelText(){
        WebElement label = waitForElementIsVisible(this.label);
        return label.getText();
    }
}
