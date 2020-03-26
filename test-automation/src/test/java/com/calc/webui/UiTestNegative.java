package com.calc.webui;

import com.calc.DataProviders;
import com.calc.utils.OperationsEnum;
import com.calc.webui.base.BaseTest;
import com.calc.webui.pages.CalcPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.calc.utils.Operations.randomValue;

/**
 * Class with negative UI tests using Selenium webDriver
 */
public class UiTestNegative extends BaseTest {

    @Test(testName = "invalid integer test", dataProvider = "getNegativeValues", dataProviderClass = DataProviders.class)
    public void testInvalidIntegers(String operation, String value1) {
        CalcPage page = new CalcPage(driver);

        page.selectOperation(OperationsEnum.valueOf(operation));
        page.enterFirstValue(value1);
        page.enterSecondValue(String.valueOf(randomValue()));
        page.calculate();

        String actual = page.readPageText();
        StringBuilder expected = new StringBuilder();
        expected.append("HTTP Status 500 – Internal Server Error\n")
                .append("Type Exception Report\n")
                .append(String.format("Message For input string: \"%s\"\n", value1))
                .append("Description The server encountered an unexpected condition that prevented it from fulfilling the request.\n");
        Assert.assertTrue(actual.contains(expected), actual);
    }

    @Test(testName = "overflow integer test", dataProvider = "getOverflowValues", invocationCount = 10,
            dataProviderClass = DataProviders.class)
    public void testOverflowIntegers(String operation, String value2){
        CalcPage page = new CalcPage(driver);

        page.selectOperation(OperationsEnum.valueOf(operation));
        page.enterFirstValue(String.valueOf(randomValue()));
        page.enterSecondValue(value2);
        page.calculate();

        String actual = page.readPageText();
        StringBuilder expected = new StringBuilder();
        expected.append("HTTP Status 500 – Internal Server Error\n")
                .append("Type Exception Report\n")
                .append(String.format("Message For input string: \"%s\"\n", value2))
                .append("Description The server encountered an unexpected condition that prevented it from fulfilling the request.\n");
        Assert.assertTrue(actual.contains(expected), actual);
    }
}
