package com.calc.webui;

import com.calc.DataProviders;
import com.calc.utils.OperationsEnum;
import com.calc.webui.base.BaseTest;
import com.calc.webui.pages.CalcPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.calc.utils.Operations.performOperation;
import static com.calc.utils.Operations.randomValue;

/**
 * Class with positive UI tests using Selenium webDriver
 */
public class UiTestPositive extends BaseTest {

    @Test(testName = "pict data test", dataProvider = "getPositiveCases", dataProviderClass = DataProviders.class)
    public void testPictValues(String operation, double value1, double value2, double expectedResult) {
        CalcPage page = new CalcPage(driver);
        page.selectOperation(OperationsEnum.valueOf(operation));
        page.enterFirstValue(String.valueOf((int)value1));
        page.enterSecondValue(String.valueOf((int)value2));
        page.calculate();

        String actual = page.readResult();
        String expected = String.valueOf((long)expectedResult);

        Assert.assertEquals(actual, expected);
    }

    @Test(testName = "random values test", dataProvider = "operations", invocationCount = 10,
            dataProviderClass = DataProviders.class)
    public void testRandomValues(OperationsEnum operation){
        int value1 = randomValue();
        int value2 = randomValue();

        CalcPage page = new CalcPage(driver);
        page.selectOperation(operation);
        page.enterFirstValue(String.valueOf(value1));
        page.enterSecondValue(String.valueOf(value2));
        page.calculate();

        String actual = page.readResult();
        String expected = String.valueOf(performOperation(operation, value1, value2));

        Assert.assertEquals(actual, expected, String.format("%s: %s, %s = %s, not %s",
                operation, value1, value2, expected, actual));
    }

    @Test(testName = "verify label")
    public void verifyCalculatorLabel(){
        CalcPage page = new CalcPage(driver);
        String actual = page.getLabelText();
        Assert.assertEquals(actual, "Ataccama TestCalc", "Invalid label");
    }
}
