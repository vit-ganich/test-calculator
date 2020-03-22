package com.calc;

import com.calc.datareader.DataReader;
import com.calc.webui.base.BaseTest;
import com.calc.webui.pages.CalcPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

/** Class with UI tests using Selenium webDriver
 *
 */
public class UiTest extends BaseTest {

    @DataProvider
    public Object[][] getExcelData() throws IOException {
        DataReader reader = new DataReader("src/test/resources/testDataPositive.xls", "TestSuite1");
        return reader.readExcel();
    }

    @Test(testName = "testCalculator", dataProvider = "getExcelData")
    public void testCalculator(String operation, double value1, double value2, double expectedResult) throws InterruptedException {
        
        CalcPage page = new CalcPage(driver);

        page.selectOperation(operation);
        page.enterFirstValue(String.valueOf((int)value1));
        page.enterSecondValue(String.valueOf((int)value2));
        page.calculate();

        Thread.sleep(100); // To slow tests down a bit

        String actual = page.readResult();
        String expected = String.valueOf((long)expectedResult);

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void verifyCalculatorLabel(){
        CalcPage page = new CalcPage(driver);
        String actual = page.getLabelText();
        String expected = "Ataccama TestCalc";
        Assert.assertEquals(actual, expected, "Invalid label");
    }
}
