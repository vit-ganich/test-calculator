package com.calc.webui.base;

import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.*;

/**
 * Base class for web UI tests.
 * Contains setUp and tearDown methods and common fields.
 *
 * @author VHanich
 */
public class BaseTest {

    private static final String URL = "http://localhost:8080/testCalc/webUI";
    protected WebDriver driver;

    @BeforeClass
    @Parameters("browser")
    public void setUpClass(@Optional("chrome") String browser)  {
        driver = getDriver(browser);
    }

    private WebDriver getDriver(String browser) throws InvalidArgumentException {
        if (driver != null){
            return driver;
        }
        if (browser.trim().equalsIgnoreCase("chrome")){
            return chromeDriver();
        }
        else if (browser.trim().equalsIgnoreCase("firefox")){
            return firefoxDriver();
        }
        throw new InvalidArgumentException(String.format("Unsupported browser type: %s", browser));
    }

    /**
     * Create an instance of Chrome driver
     * @return  new ChromeDriver
     */
    private ChromeDriver chromeDriver(){
        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver.exe");
        return new ChromeDriver();
    }

    /**
     * Create an instance of Firefox driver
     * @return  new FirefoxDriver
     */
    private FirefoxDriver firefoxDriver(){
        System.setProperty("webdriver.firefox.driver","src/main/resources/geckodriver.exe");
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true);
        return new FirefoxDriver();
    }

    @BeforeMethod
    public void setUpMethod(){
        driver.get(URL);
    }

    @AfterClass
    public void tearDownClass(){
        driver.quit();
    }
}
