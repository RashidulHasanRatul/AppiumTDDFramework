package com.qa;
import com.aventstack.extentreports.Status;
import com.qa.reports.ExtentReport;
import com.qa.tests.utils.TestUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.screenrecording.CanRecordScreen;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class BaseTest {
    protected static AppiumDriver driver;
    protected static Properties prop;
    protected static String dateTime;
    private static AppiumDriverLocalService server;
    protected static ThreadLocal<String> platform = new ThreadLocal<String>();
    protected static ThreadLocal<String> deviceName = new ThreadLocal<String>();

    InputStream inputStream;
    TestUtils utils;

    // Get the value from properties file and set it to capabilities
    public BaseTest() {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public String getPlatform() {
        return platform.get();
    }

    public void setPlatform(String platform2) {
        platform.set(platform2);
    }

    public String getDeviceName() {
        return deviceName.get();
    }

    public void setDeviceName(String deviceName2) {
        deviceName.set(deviceName2);
    }

    @BeforeMethod
    public void beforeMethod() {

        ((CanRecordScreen) driver).startRecordingScreen();
    }

    @AfterMethod
    public void afterMethod(ITestResult result) throws IOException {

        String media = ((CanRecordScreen) driver).stopRecordingScreen();
        if (result.getStatus() == 2) {
            Map<String, String> params = new HashMap<String, String>();
            params = result.getTestContext().getCurrentXmlTest().getAllParameters();

            String dir = "videos" + File.separator + params.get("platformName") + "_" + params.get("platformVersion") + "_" + params.get(("deviceName")) +
                    File.separator + dateTime + File.separator + result.getTestClass().getRealClass().getSimpleName();

            File videoFile = new File(dir);
            if (!videoFile.exists()) {
                videoFile.mkdirs();
            }

            FileOutputStream stream = new FileOutputStream(videoFile + File.separator + result.getName() + ".mp4");
            stream.write(Base64.getDecoder().decode(media));
        }

    }

    @BeforeSuite
    public void beforeSuite() throws Exception {
        server = getAppiumServerDefault();
        // check appium server is running or not
        if (!checkIfAppiumServerIsRunnning(4723)) {
            server.start();
            server.clearOutPutStreams();
            System.out.println("Appium server started");
        } else {
            System.out.println("Appium server is already running");
        }
    }

    public boolean checkIfAppiumServerIsRunnning(int port) throws Exception {
        boolean isAppiumServerRunning = false;
        ServerSocket socket;
        try {
            socket = new ServerSocket(port);
            socket.close();
        } catch (IOException e) {
            System.out.println("1");
            isAppiumServerRunning = true;
        } finally {
            socket = null;
        }
        return isAppiumServerRunning;
    }


    @AfterSuite
    public void afterSuite() {
        server.stop();
        System.out.println("Appium server stopped");
    }

    public AppiumDriverLocalService getAppiumServerDefault() {
        return AppiumDriverLocalService.buildDefaultService();
    }

    @Parameters({"platformName", "platformVersion", "deviceName"})
    @BeforeTest
    public void beforeTest(String platformName, String platformVersion, String deviceName) throws Exception {
        setPlatform(platformName);
        setDeviceName(deviceName);
        utils = new TestUtils();
        dateTime = utils.getDateTime();
        try {
            prop = new Properties();
            String propFileName = "config.properties";
            // Load properties file
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

    public AppiumDriver getDriver() {
        return driver;
    }

    public void waitForVisibility(MobileElement e) {
        WebDriverWait wait = new WebDriverWait(driver, TestUtils.WAIT);
        wait.until(ExpectedConditions.visibilityOf(e));
    }

    public void click(MobileElement e) {
        ExtentReport.getTest().log(Status.INFO, "Click on " + e.getText());
        waitForVisibility(e);
        e.click();
    }

    public void sendKeys(MobileElement e, String text) {
        ExtentReport.getTest().log(Status.INFO, "Send Keys" + e.getText());
        waitForVisibility(e);
        e.sendKeys(text);
    }

    public String getAttribute(MobileElement e, String attribute) {
        waitForVisibility(e);
        return e.getAttribute(attribute);
    }

    public void closeApp() {
        ((InteractsWithApps) driver).closeApp();
    }

    public void launchApp() {
        ((InteractsWithApps) driver).launchApp();
    }

    public MobileElement scrollToElement() {

        return (MobileElement) ((FindsByAndroidUIAutomator) driver).findElementByAndroidUIAutomator(
                "new UiScrollable(new UiSelector()" + ".scrollable(true)).scrollIntoView("
                        + "new UiSelector().description(\"test-Price\"));");
    }

    public String getDateTime() {
        return dateTime;
    }
    @AfterTest
    public void afterTest() {

        driver.quit();
    }
}
