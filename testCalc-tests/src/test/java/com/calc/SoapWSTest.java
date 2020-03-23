package com.calc;

import com.predic8.wsdl.Definitions;
import com.predic8.wsdl.WSDLParser;
import com.predic8.wstool.creator.RequestTemplateCreator;
import com.predic8.wstool.creator.SOARequestCreator;
import groovy.xml.MarkupBuilder;
import org.testng.annotations.Test;

import java.io.StringWriter;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class SoapWSTest {

    /* Add request example
    <Envelope xmlns="http://schemas.xmlsoap.org/soap/envelope/">
        <Body>
            <add xmlns="http://soap.testcalc.qa.ataccama.com/">
                <val1>[int]</val1>
                <val2>[int]</val2>
            </add>
        </Body>
    </Envelope>
     */
    @Test
    public void getRequestStructure() {
        String endpointUrl = "http://localhost:8080/testCalc/soapWS";
        WSDLParser parser = new WSDLParser();
        Definitions wsdl = parser.parse(endpointUrl + "?wsdl");

        StringWriter requestBody = new StringWriter();
        HashMap<String, String> formParams = new HashMap<>();
        formParams.put("xpath:/add/val1", "10");
        formParams.put("xpath:/add/val2", "10");

        SOARequestCreator creator = new SOARequestCreator(wsdl, new RequestTemplateCreator(), new MarkupBuilder(requestBody));
        creator.setFormParams(formParams);
        Object request = creator.createRequest("TestCalcSoapImplementationPort", "add", "TestCalcSoapImplementationPortBinding");

        System.out.println(requestBody);
    }

    @Test
    public void sendRequest(){
        StringBuilder requestString = new StringBuilder();
        requestString.append("<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n")
                .append("<Body>\n")
                .append("<add xmlns=\"http://soap.testcalc.qa.ataccama.com/\">\n")
                .append("<val1>3</val1>\n")
                .append("<val2>5</val2>\n")
                .append("</Body>\n")
                .append("</Envelope>\n");
        System.out.println(requestString);
        String endpointUrl = "http://localhost:8080/testCalc/soapWS";
        given()
                .body(requestString)
        .when()
                .post(endpointUrl);
    }
}
