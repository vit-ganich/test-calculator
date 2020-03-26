package com.calc.restapi;

import com.calc.DataProviders;
import com.calc.utils.OperationsEnum;
import org.testng.annotations.Test;

import static com.calc.utils.Operations.*;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

/**
 * Class with Rest API GET requests tests using REST Assured library.
 *
 * @author VHanich & REST Assured docs http://rest-assured.io/
 */
public class RestApiGet extends BaseRequest {

    @Test(testName = "GET with pict values", dataProvider = "getPositiveCases", dataProviderClass = DataProviders.class)
    public void sendGetRequest(String operation, double value1, double value2, double expectedResult) {
        String operand = chooseRequestOperand(OperationsEnum.valueOf(operation), true);
        String requestUrl = String.format("%s/%s?val1=%s&val2=%s", URL, operand, (int)value1, (int)value2);

        when()
                .get(requestUrl)
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("result", equalTo((int)expectedResult));
    }

    @Test(testName = "GET with random values", dataProvider = "operations", dataProviderClass = DataProviders.class,
            invocationCount = 10)
    public void sendRandomGetRequest(OperationsEnum operation){
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
    }

    @Test(testName = "GET invalid Integer", dataProvider = "getNegativeValues", dataProviderClass = DataProviders.class)
    public void sendGetNegativeValue(String operation, String value1){
        int value2 = randomValue();
        String operand = chooseRequestOperand(OperationsEnum.valueOf(operation), true);
        String requestUrl = String.format("%s/%s?val1=%s&val2=%s", URL, operand, value1, value2);

        when()
                .get(requestUrl)
                .then()
                .assertThat()
                .statusCode(404);
    }
}
