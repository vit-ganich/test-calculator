package com.restws;

import com.datareader.DataReader;
import org.json.JSONObject;
import org.openqa.selenium.InvalidArgumentException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

public class RestApiTest {
    String url = "http://localhost:8080/testCalc/restWS";

    @DataProvider
    public Object[][] getExcelData() throws IOException {
        DataReader reader = new DataReader("testData/testDataPositive.xls", "TestSuite1");
        return reader.readExcel();
    }

    @Test(testName = "sendGetRequest", dataProvider = "getExcelData")
    public void sendGetRequest(String operation, double value1, double value2, double expectedResult) {
        // http://localhost:8080/testCalc/restWS/subtract?val1=-3&val2=-4
        String operand = chooseOperandGet(operation);
        String requestUrl = String.format("%s/%s?val1=%s&val2=%s", url, operand, (int)value1, (int)value2);
        when().
                get(requestUrl).
                then().
                assertThat().
                statusCode(200).and().
                body("result", equalTo((int)expectedResult));
    }

    @Test(testName = "sendGetRequest", dataProvider = "getExcelData")
    public void sendPostRequest(String operation, double value1, double value2, double expectedResult) {
        // http://localhost:8080/testCalc/restWS/subtract?val1=-3&val2=-4

        String requestUrl = String.format("%s/compute", url);
        // TODO: fix exception
        JSONObject requestJson = new JSONObject();
        requestJson.put("val1", (int)value1);
        requestJson.put("val2", (int)value2);
        requestJson.put("operation", chooseOperandGet(operation));
        when().
                post(requestUrl, requestJson).
                then().
                assertThat().
                statusCode(200).and().
                body("result", equalTo((int)expectedResult));
    }

    private String chooseOperandGet(String operation) throws InvalidArgumentException {
        switch (operation.toLowerCase().trim()) {
            case "addition":
                return "add";
            case "division":
                return "divide";
            case "multiplication":
                return "multiply";
            case "subtraction":
                return "subtract";
            default:
                throw new InvalidArgumentException(String.format("Invalid operation: '%s'", operation));
        }
    }

    private String chooseOperandPost(String operation) throws InvalidArgumentException {
        switch (operation.toLowerCase().trim()) {
            case "addition":
                return "add";
            case "division":
                return "div";
            case "multiplication":
                return "mul";
            case "subtraction":
                return "sub";
            default:
                throw new InvalidArgumentException(String.format("Invalid operation: '%s'", operation));
        }
    }
}
