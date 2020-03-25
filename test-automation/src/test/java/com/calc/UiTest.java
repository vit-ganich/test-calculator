package com.calc;

import com.calc.utils.DataReader;
import com.calc.utils.OperationsHelper;
import com.calc.webui.base.BaseTest;
import com.calc.utils.Operations;
import com.calc.webui.pages.CalcPage;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.calc.utils.OperationsHelper.performOperation;
import static com.calc.utils.OperationsHelper.randomValue;

/**
 * Class with UI tests using Selenium webDriver
 */
public class UiTest extends BaseTest {

    @DataProvider
    public Object[][] getExcelData() throws IOException {
        DataReader reader = new DataReader("src/test/resources/testDataPositive.xls", "TestSuite1");
        return reader.readExcel();
    }

    @Test(testName = "test calculator", dataProvider = "getExcelData")
    public void testCalculator(String operation, double value1, double value2, double expectedResult) {
        CalcPage page = new CalcPage(driver);
        page.selectOperation(Operations.valueOf(operation));
        page.enterFirstValue(String.valueOf((int)value1));
        page.enterSecondValue(String.valueOf((int)value2));
        page.calculate();

        String actual = page.readResult();
        String expected = String.valueOf((long)expectedResult);

        Assert.assertEquals(actual, expected);

        Reporter.log(String.format("%s: %s, %s = %s", operation, value1, value2, expected));
    }

    @DataProvider
    public static Object[] operations() { return OperationsHelper.operations; }

    @Test(testName = "random operations", dataProvider = "operations", invocationCount = 10)
    public void testRandomAddition(Operations operation){
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

        Reporter.log(String.format("%s: %s, %s = %s", operation, value1, value2, expected));
    }

    @Test(testName = "verify label")
    public void verifyCalculatorLabel(){
        CalcPage page = new CalcPage(driver);
        String actual = page.getLabelText();
        Assert.assertEquals(actual, "Ataccama TestCalc", "Invalid label");
    }
}
