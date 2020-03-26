package com.calc.restapi;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * Class with common fields and methods for Rest API tests.
 */
public class BaseRequest {
    protected final String URL = "http://localhost:8080/testCalc/restWS";

    /**
     * Send POST request, assert response code and extract response.
     * It has the assertion, it's not recommended but I needed a quick solution.
     * @param body  string body
     * @param url   request url
     * @param code  response code to assert
     * @return  response
     */
    protected ExtractableResponse<Response> sendPostAssertCodeExtractResponse(String body, String url, int code){
        return given()
                .contentType("application/json")
                .body(body)
                .when()
                .post(url)
                .then()
                .assertThat()
                .statusCode(code)
                .extract();
    }
}
