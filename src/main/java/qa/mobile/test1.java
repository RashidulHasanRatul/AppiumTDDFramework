package qa.mobile;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class test1 {
    AppiumDriver driver;

    @Test
    public void invalidUsername() {
        MobileElement userNameTextField = (MobileElement) driver.findElementByXPath("//android.widget.EditText[@content-desc=\"test-Username\"]");
        MobileElement ele = (MobileElement) driver.findElementByAccessibilityId("test-Username");
        MobileElement passwordTextField = (MobileElement) driver.findElementByXPath("//android.widget.EditText[@content-desc=\"test-Password\"]");
        MobileElement loginButton = (MobileElement) driver.findElementByXPath("//android.view.ViewGroup[@content-desc=\"test-LOGIN\"]");
        MobileElement errorText = (MobileElement) driver.findElementByAccessibilityId("test-Error message");
        //  Username and password do not match any user in this service.
       // userNameTextField.click();
        ele.sendKeys("invalid");

//        userNameTextField.sendKeys("invalid");
//        passwordTextField.sendKeys("secret_sauce");
//        loginButton.click();
//        String errorTextValue = errorText.getText();
//        System.out.println(errorTextValue);
        //Assert.assertEquals(errorTextValue, "Username and password do not match any user in this service.");
    }

    @Test
    public void invalidPassword() {
        MobileElement userNameTextField = (MobileElement) driver.findElementByAccessibilityId("test-Username");
        MobileElement passwordTextField = (MobileElement) driver.findElementByAccessibilityId("test-Password");
        MobileElement loginButton = (MobileElement) driver.findElementByAccessibilityId("test-LOGIN");
        MobileElement errorText = (MobileElement) driver.findElementByAccessibilityId("test-Error message");
        userNameTextField.sendKeys("standard_user");
        passwordTextField.sendKeys("invalid");
        loginButton.click();
        String errorTextValue = errorText.getText();
        System.out.println(errorTextValue);
        Assert.assertEquals(errorTextValue, "Username and password do not match any user in this service.");
    }
    @Test

    public void validLogin() {

        MobileElement userNameTextField = (MobileElement) driver.findElementByAccessibilityId("test-Username");
        MobileElement passwordTextField = (MobileElement) driver.findElementByAccessibilityId("test-Password");
        MobileElement loginButton = (MobileElement) driver.findElementByAccessibilityId("test-LOGIN");
        MobileElement productTitle = (MobileElement) driver.findElementByXPath("//android.view.ViewGroup[@content-desc=\"test-Cart drop zone\"]/android.view.ViewGroup/android.widget.TextView");
        String expectedProductTittle = productTitle.getText();
        String actualProductTittle = "PRODUCT";
        userNameTextField.sendKeys("standard_user");
        passwordTextField.sendKeys("secret_sauce");
        loginButton.click();


        System.out.println(expectedProductTittle);
        Assert.assertEquals(actualProductTittle, expectedProductTittle);
    }

    @BeforeClass
    public void beforeClass() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "8.1.0");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel 4");
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        capabilities.setCapability(MobileCapabilityType.UDID, "emulator-5554");
        capabilities.setCapability("appPackage", "com.swaglabsmobileapp");
        capabilities.setCapability("appActivity", "com.swaglabsmobileapp.SplashActivity");
        capabilities.setCapability("avd", "Pixel_4_API_27");
        capabilities.setCapability("avdLaunchTimeout", "180000");
        String AndroidAppUrl = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "Android.SauceLabs.Mobile.Sample.app.2.7.1.apk";
        //capabilities.setCapability(MobileCapabilityType.APP, AndroidAppUrl);

        URL url = new URL("http://0.0.0.0:4723/wd/hub");

        driver = new AndroidDriver(url, capabilities);
        String sessionId = driver.getSessionId().toString();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }

}
