package com.webui.tests;

import com.webui.base.BaseTest;
import com.webui.pages.CalcPage;
import com.webui.base.DataReader;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.io.IOException;

public class UITests extends BaseTest {

    @DataProvider
    public Object[][] getExcelData() throws IOException {
        DataReader reader = new DataReader("testData/testData.xls", "TestSuite1");
        return reader.readExcel();
    }

    @Test(testName = "testCalculator", dataProvider = "getExcelData")
    public void testCalculator(String operation, double value1, double value2, double expectedResult) throws InterruptedException {

        //Excel converts all integer values to numerical (double), and we have strings like '10.0'
        //So we need to convert them to int (to get rid of zero after dot).
        //Finally, the integer value is being converted to string.
        String firstVal = String.valueOf((int)value1);
        String secondVal = String.valueOf((int)value2);
        String expected = String.valueOf((long)expectedResult);

        CalcPage page = new CalcPage(driver);

        page.enterFirstValue(firstVal);
        page.enterSecondValue(secondVal);
        page.selectOperation(operation);
        page.calculate();

        Thread.sleep(500); // To slow tests down a bit
        String actual = page.readResult();

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void verifyCalculatorLabel(){
        CalcPage page = new CalcPage(driver);
        String actual = page.getLabelText();
        String expected = "Ataccama TestCalc";
        Assert.assertEquals(actual, expected, "Invalid label");
    }

    @Ignore
    @Test(testName = "debugTest", dataProvider = "getExcelData")
    public void testDebug(String operation, double value1, double value2, double expectedResult) throws InterruptedException {

        String firstVal = String.valueOf((int) value1);
        String secondVal = String.valueOf((int) value2);
        String expected = String.valueOf((long)expectedResult);

        System.out.println(operation);
        System.out.println(firstVal);
        System.out.println(secondVal);
        System.out.println(expected);
    }
}
