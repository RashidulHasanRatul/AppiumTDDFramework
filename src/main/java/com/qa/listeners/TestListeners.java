package com.qa.listeners;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.qa.BaseTest;
import com.qa.reports.ExtentReport;
import org.openqa.selenium.OutputType;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;

public class TestListeners implements ITestListener {

    BaseTest baseTest;

    @Override
    public void onTestStart(ITestResult result) {
        BaseTest base = new BaseTest();
        ExtentReport.startTest(result.getName(), result.getMethod().getDescription()).assignCategory(base.getPlatform() + "_" + base.getDeviceName()).assignAuthor("Rashidul Hasan,SDET @ FinSource Limited");
    }


    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentReport.getTest().log(Status.PASS, "Test Passed");

    }

    @Override
    public void onTestFailure(ITestResult result) {

        if (result.getThrowable() != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            result.getThrowable().printStackTrace(pw);
            System.out.println(sw.toString());

        }
        // Get Screenshot
        BaseTest baseTest = new BaseTest();
        File file = baseTest.getDriver().getScreenshotAs(OutputType.FILE);
        byte[] encoded = null;
        try {
            encoded = Base64.getEncoder().encode(FileUtils.readFileToByteArray(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // copy file to screenshot folder
        Map<String, String> params = new HashMap<String, String>();
        params = result.getTestContext().getCurrentXmlTest().getAllParameters();
        String imagePath = "screenshots" + File.separator + params.get("platformName") + "_" + params.get("platformVersion") + "_" + params.get(("deviceName")) +
                File.separator + baseTest.getDateTime() + File.separator + result.getTestClass().getRealClass().getSimpleName() + File.separator + result.getName() + ".png";
        try {
            FileUtils.copyFile(file, new File(imagePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        ExtentReport.getTest().fail("Test Failed", MediaEntityBuilder.createScreenCaptureFromPath(imagePath).build());
        ExtentReport.getTest().fail("Test Failed", MediaEntityBuilder.createScreenCaptureFromBase64String(new String(encoded, StandardCharsets.US_ASCII)).build());
        ExtentReport.getTest().fail(result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReport.getTest().log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {


    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentReport.getReporter().flush();
    }
}
