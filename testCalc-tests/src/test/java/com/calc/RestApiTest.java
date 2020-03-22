package com.calc;

import com.calc.datareader.DataReader;
import org.json.JSONObject;
import org.openqa.selenium.InvalidArgumentException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

/** Class with Rest API tests using REST Assured library.
 *
 * @author VHanich & REST Assured docs http://rest-assured.io/
 */
public class RestApiTest {
    String url = "http://localhost:8080/testCalc/restWS";

    @DataProvider
    public Object[][] getExcelData() throws IOException {
        DataReader reader = new DataReader("src/test/resources/testDataPositive.xls", "TestSuite1");
        return reader.readExcel();
    }

    @Test(testName = "sendGetRequest", dataProvider = "getExcelData")
    public void sendGetRequest(String operation, double value1, double value2, double expectedResult) {
        // http://localhost:8080/testCalc/restWS/subtract?val1=-3&val2=-4
        String operand = chooseOperand(operation, true);
        String requestUrl = String.format("%s/%s?val1=%s&val2=%s", url, operand, (int)value1, (int)value2);
        when()
                .get(requestUrl)
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("result", equalTo((int)expectedResult));
    }

    @Test(testName = "sendPostRequest", dataProvider = "getExcelData")
    public void sendPostRequest(String operation, double value1, double value2, double expectedResult) throws InterruptedException {
        String requestUrl = String.format("%s/compute", url);
        JSONObject jsonObj = new JSONObject()
                .put("val1", (int)value1)
                .put("val2", (int)value2)
                .put("operation", chooseOperand(operation, false));
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
        Thread.sleep(500);
    }

    /** Choose the valid operation code for GET and POST requests.
     * For GET requests isGet flag should be True.
     * Actually the methods with flags are not recommended, but in this particular case
     * it's acceptable and makes the code shorter.
     * If the logic is complex, this approach should be avoided.
     *
     * @param operation Operand from the test data
     * @param isGet     Flag for switching from GET to POST operation codes
     * @return          String operation code
     * @throws InvalidArgumentException Throws the exception if the operation is invalid
     */
    private String chooseOperand(String operation, Boolean isGet) throws InvalidArgumentException {
        switch (operation.toLowerCase().trim()) {
            case "addition":
                return "add";
            case "division":
                return isGet ? "divide" : "div";
            case "multiplication":
                return isGet ? "multiply": "mul";
            case "subtraction":
                return isGet ? "subtract" : "sub";
            default:
                throw new InvalidArgumentException(String.format("Invalid operation: '%s'", operation));
        }
    }
}
