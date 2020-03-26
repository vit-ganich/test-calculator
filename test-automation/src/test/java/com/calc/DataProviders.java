package com.calc;

import com.calc.utils.DataReader;
import com.calc.utils.Operations;
import org.testng.annotations.DataProvider;

import java.io.IOException;

public class DataProviders {
    @DataProvider
    public static Object[][] getPositiveCases() throws IOException {
        DataReader reader = new DataReader("src/test/resources/testData.xls", "TestSuite1");
        return reader.readExcel();
    }

    @DataProvider
    public static Object[] operations() {
        return Operations.operations;
    }

    @DataProvider
    public static Object[][] getNegativeValues() throws IOException {
        DataReader reader = new DataReader("src/test/resources/testData.xls", "TestSuiteNeg1");
        return reader.readExcel();
    }

    @DataProvider
    public Object[][] getOverflowValues() throws IOException {
        DataReader reader = new DataReader("src/test/resources/testData.xls", "TestSuiteNeg2");
        return reader.readExcel();
    }
}
