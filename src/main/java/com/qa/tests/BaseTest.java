package com.qa.tests;
import com.qa.tests.utils.TestUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;


public class BaseTest {
    protected static AppiumDriver driver;
    protected static Properties prop;
    InputStream inputStream;

    // Get Value from testng.xml file and set it to capabilities properties
    // Get the value from properties file and set it to capabilities
    public BaseTest (){
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
       
    }

    @Parameters({"platformName", "platformVersion", "deviceName"})
    @BeforeTest
    public void beforeTest(String platformName, String platformVersion, String deviceName) throws Exception {

        try {
            prop = new Properties();
            String propFileName = "config.properties";
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            prop.load(inputStream);

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
            capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, prop.getProperty("androidAutomationName"));
            capabilities.setCapability(MobileCapabilityType.UDID, "emulator-5554");
            capabilities.setCapability("appPackage", prop.getProperty("androidAppPackage"));
            capabilities.setCapability("appActivity", prop.getProperty("androidAppActivity"));
            capabilities.setCapability("avd", "Pixel_4_API_27");
            capabilities.setCapability("avdLaunchTimeout", "180000");
            URL appURL = getClass().getClassLoader().getResource(prop.getProperty("androidAppLocation"));
            capabilities.setCapability(MobileCapabilityType.APP, appURL);
            URL url = new URL(prop.getProperty("appiumServerURL"));
            driver = new AndroidDriver(url, capabilities);
            String sessionId = driver.getSessionId().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void waitForVisibility(MobileElement e) {
        WebDriverWait wait = new WebDriverWait(driver, TestUtils.WAIT);
        wait.until(ExpectedConditions.visibilityOf(e));
    }

    public void click(MobileElement e) {
        waitForVisibility(e);
        e.click();
    }

    public void sendKeys(MobileElement e, String text) {
        waitForVisibility(e);
        e.sendKeys(text);
    }

    public String getAttribute(MobileElement e, String attribute) {
        waitForVisibility(e);
       return e.getAttribute(attribute);
    }

    @AfterTest
    public void afterTest() {
    driver.quit();
    }
}
