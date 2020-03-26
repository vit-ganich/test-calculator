package com.calc.restapi;

import com.calc.DataProviders;
import com.calc.utils.OperationsEnum;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.calc.utils.Operations.*;

/**
 * Class with Rest API Post requests tests using REST Assured library.
 *
 * @author VHanich & REST Assured docs http://rest-assured.io/
 */
public class RestApiPost extends BaseRequest {

    @Test(testName = "POST pict values", dataProvider = "getPositiveCases", dataProviderClass = DataProviders.class)
    public void sendPostRequest(String operation, double value1, double value2, double expectedResult) {
        String requestUrl = String.format("%s/compute", URL);

        JSONObject jsonObj = new JSONObject()
                .put("val1", (int)value1)
                .put("val2", (int)value2)
                .put("operation", chooseRequestOperand(OperationsEnum.valueOf(operation), false));

        var response = sendPostAssertCodeExtractResponse(jsonObj.toString(), requestUrl, 200);
        int actual = response.path("result");
        Assert.assertEquals(actual, (int)expectedResult);
    }

    @Test(testName = "POST random values", dataProvider = "operations", invocationCount = 10,
            dataProviderClass = DataProviders.class)
    public void sendRandomPostRequest(OperationsEnum operation) {
        int value1 = randomValue();
        int value2 = randomValue();
        int expected = performOperation(operation, value1, value2);

        String requestUrl = String.format("%s/compute", URL);

        JSONObject jsonObj = new JSONObject()
                .put("val1", value1)
                .put("val2", value2)
                .put("operation", chooseRequestOperand(operation, false));

        var response = sendPostAssertCodeExtractResponse(jsonObj.toString(), requestUrl, 200);
        int actual = response.path("result");

        Assert.assertEquals(actual, expected);
    }

    @Test(testName = "POST invalid Integer", dataProvider = "getNegativeValues", dataProviderClass = DataProviders.class)
    public void sendPostNegativeValue(String operation, String value2){
        int value1 = randomValue();

        String requestUrl = String.format("%s/compute", URL);

        JSONObject jsonObj = new JSONObject()
                .put("val1", value1)
                .put("val2", value2)
                .put("operation", chooseRequestOperand(OperationsEnum.valueOf(operation), false));

        String actual = sendPostAssertCodeExtractResponse(jsonObj.toString(), requestUrl, 400).toString();
        String expected = String.format(
                "Can not construct instance of java.lang.Integer from String value '%s': not a valid Integer value",
                value2);

        Assert.assertEquals(actual, expected);
    }

    @Test(testName = "POST overflow value", dataProvider = "getOverflowValues", dataProviderClass = DataProviders.class)
    public void sendPostOverflowValue(String operation, String value2){
        int value1 = randomValue();

        String requestUrl = String.format("%s/compute", URL);

        JSONObject jsonObj = new JSONObject()
                .put("val1", value1)
                .put("val2", value2)
                .put("operation", chooseRequestOperand(OperationsEnum.valueOf(operation), false));

        String actual = sendPostAssertCodeExtractResponse(jsonObj.toString(), requestUrl, 400).toString();
        String expected = String.format(
                "Integer from String value '%s': Overflow: numeric value (%s) out of range of Integer (-2147483648 - 2147483647)",
                value2, value2);

        Assert.assertEquals(actual, expected);
    }
}
