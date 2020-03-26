package com.calc;

import com.predic8.wsdl.Definitions;
import com.predic8.wsdl.WSDLParser;
import com.predic8.wstool.creator.RequestTemplateCreator;
import com.predic8.wstool.creator.SOARequestCreator;
import groovy.xml.MarkupBuilder;
import org.apache.poi.util.NotImplemented;
import org.testng.annotations.Test;

import java.io.StringWriter;
import java.util.HashMap;

/**
 * An attempt to implement tests for Soap Api testing.
 * Unfortunately, it failed.
 */
public class SoapWSTest {

    @Test
    @NotImplemented
    public void getRequestStructure() {
        String endpointUrl = "http://localhost:8080/testCalc/soapWS";
        WSDLParser parser = new WSDLParser();
        Definitions wsdl = parser.parse(endpointUrl + "?wsdl");

        StringWriter requestBody = new StringWriter();
        HashMap<String, String> formParams = new HashMap<>();

        SOARequestCreator creator = new SOARequestCreator(wsdl, new RequestTemplateCreator(), new MarkupBuilder(requestBody));
        creator.setFormParams(formParams);
        Object request = creator.createRequest("TestCalcSoapImplementationPort", "add", "TestCalcSoapImplementationPortBinding");

        System.out.println(requestBody);
    }
}
