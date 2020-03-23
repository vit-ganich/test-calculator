package com.calc.webui.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

/**
 * Base class for web UI tests.
 * Contains setUp and tearDown methods and common fields.
 *
 * @author VHanich
 */
public class BaseTest {

    private String url = "http://localhost:8080/testCalc/webUI";
    protected WebDriver driver;

    @BeforeClass
    public void setUpClass(){
        driver = new ChromeDriver();
        //driver.manage().window().maximize();
    }

    @BeforeMethod
    public void setUpMethod(){
        driver.get(url);
    }

    @AfterClass
    public void tearDownClass(){
        driver.quit();
    }
}
