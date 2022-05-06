package com.qa.listeners;

import com.qa.BaseTest;
import org.openqa.selenium.OutputType;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

public class TestListeners implements ITestListener {

    BaseTest baseTest;

    @Override
    public void onTestStart(ITestResult result) {

    }

    @Override
    public void onTestSuccess(ITestResult result) {

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
    }

    @Override
    public void onTestSkipped(ITestResult result) {


    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {


    }

    @Override
    public void onFinish(ITestContext context) {

    }
}
