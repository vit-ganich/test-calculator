package com.calc.webui.pages;
import com.calc.webui.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/** Page object for the Calculator page.
 * Contains webElements and custom methods to perform operations with the webElements
 *
 * @author VHanich
 */
public class CalcPage extends BasePage {

    private By LABEL = By.cssSelector("tbody>tr>td");
    private By VALUE1 = By.cssSelector("input[name='val1']");
    private By VALUE2 = By.cssSelector("input[name='val2']");
    private By ADDITION = By.cssSelector("input[value='add']");
    private By SUBTRACTION = By.cssSelector("input[value='sub']");
    private By MULTIPLICATION = By.cssSelector("input[value='mul']");
    private By DIVISION = By.cssSelector("input[value='div']");
    private By CALCULATE = By.cssSelector("input[type='submit']");
    private By RESULT = By.cssSelector("input[name='result']");

    public CalcPage(WebDriver driver) {
        super(driver);
    }

    public void enterFirstValue(String value){
        WebElement textBox = waitForElementIsClickable(VALUE1);
        writeToTextBox(textBox, value);
    }

    public void enterSecondValue(String value){
        WebElement textBox = waitForElementIsClickable(VALUE2);
        writeToTextBox(textBox, value);
    }

    public void selectOperation(String operation){
        WebElement operationElem = chooseOperation(operation);
        operationElem.click();
    }

    /** Gets the Operation radio button.
     * Operations: addition, division, subtraction and multiplication
     *
     * @param operation String operation from the test data
     * @return          Radio button webElement
     * @throws InvalidArgumentException Throws the exception if the operation is invalid
     */
    private WebElement chooseOperation(String operation) throws InvalidArgumentException{
        switch (operation.toLowerCase().trim()){
            case "addition":
                return waitForElementIsClickable(ADDITION);
            case "division":
                return waitForElementIsClickable(DIVISION);
            case "multiplication":
                return waitForElementIsClickable(MULTIPLICATION);
            case "subtraction":
                return waitForElementIsClickable(SUBTRACTION);
            default:
                throw new InvalidArgumentException(String.format("Invalid operation: %s", operation));
        }
    }

    public void calculate(){
        WebElement calculate = waitForElementIsClickable(CALCULATE);
        calculate.click();
    }

    public String readResult(){
        WebElement result = waitForElementIsClickable(RESULT);
        return result.getAttribute("value");
    }

    public String getLabelText(){
        WebElement label = waitForElementIsVisible(LABEL);
        return label.getText();
    }
}
