package com.calc;

import com.calc.utils.DataReader;
import com.calc.utils.Operations;
import com.calc.utils.OperationsHelper;
import org.json.JSONObject;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.calc.utils.OperationsHelper.*;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

/**
 * Class with Rest API tests using REST Assured library.
 *
 * @author VHanich & REST Assured docs http://rest-assured.io/
 */
public class RestApiTest {
    private final String URL = "http://localhost:8080/testCalc/restWS";

    @DataProvider
    public Object[][] getExcelData() throws IOException {
        DataReader reader = new DataReader("src/test/resources/testDataPositive.xls", "TestSuite1");
        return reader.readExcel();
    }

    @Test(testName = "send GET", dataProvider = "getExcelData")
    public void sendGetRequest(Operations operation, double value1, double value2, double expectedResult) {
        String operand = chooseRequestOperand(operation, true);
        String requestUrl = String.format("%s/%s?val1=%s&val2=%s", URL, operand, (int)value1, (int)value2);

        when()
                .get(requestUrl)
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("result", equalTo((int)expectedResult));
    }

    @Test(testName = "send POST", dataProvider = "getExcelData")
    public void sendPostRequest(Operations operation, double value1, double value2, double expectedResult) {
        String requestUrl = String.format("%s/compute", URL);

        JSONObject jsonObj = new JSONObject()
                .put("val1", (int)value1)
                .put("val2", (int)value2)
                .put("operation", chooseRequestOperand(operation, false));

        given()
                .contentType("application/json")
                .body(jsonObj.toString())
        .when()
                .post(requestUrl)
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("result", equalTo((int)expectedResult));
    }

    @DataProvider
    public static Object[] operations() {
        return OperationsHelper.operations;
    }

    @Test(testName = "random GET", dataProvider = "operations", invocationCount = 10)
    public void sendRandomGetRequest(Operations operation){
        int value1 = randomValue();
        int value2 = randomValue();
        int expected = performOperation(operation, value1, value2);
        String operand = chooseRequestOperand(operation, true);

        String requestUrl = String.format("%s/%s?val1=%s&val2=%s", URL, operand, value1, value2);

        when()
                .get(requestUrl)
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("result", equalTo(expected));

        Reporter.log(String.format("%s: %s, %s = %s", operation, value1, value2, expected));
    }

    @Test(testName = "random POST", dataProvider = "operations", invocationCount = 10)
    public void sendRandomPostRequest(Operations operation) {
        int value1 = randomValue();
        int value2 = randomValue();
        int expected = performOperation(operation, value1, value2);

        String requestUrl = String.format("%s/compute", URL);

        JSONObject jsonObj = new JSONObject()
                .put("val1", value1)
                .put("val2", value2)
                .put("operation", chooseRequestOperand(operation, false));

        given()
                .contentType("application/json")
                .body(jsonObj.toString())
                .when()
                .post(requestUrl)
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("result", equalTo(expected));

        Reporter.log(String.format("%s: %s, %s = %s", operation, value1, value2, expected));
    }
}
