package com.qa.tests;

import com.qa.BaseTest;
import com.qa.tests.pages.LoginPage;
import com.qa.tests.pages.ProductPage;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.*;
import java.io.InputStream;

import java.lang.reflect.Method;

public class LoginTests extends BaseTest {
    LoginPage loginPage;
    ProductPage productPage;
    JSONObject loginUsers;
    JSONObject expectedText;
    InputStream ExpectedDatais;
    InputStream datais;


    @BeforeClass
    public void beforeClass() throws Exception {

        try {
            System.out.println("Before Class");
            String dataFile = "data/loginUsers.json";
            String ExptectedDataFile = "data/Strings.json";
            ExpectedDatais = getClass().getClassLoader().getResourceAsStream(ExptectedDataFile);
            datais = getClass().getClassLoader().getResourceAsStream(dataFile);
            JSONTokener ExpectedDataTockener = new JSONTokener(ExpectedDatais);
            JSONTokener tokener = new JSONTokener(datais);
            loginUsers = new JSONObject(tokener);
            expectedText = new JSONObject(ExpectedDataTockener);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           if (datais != null) {
              datais.close();
           }
        }

        closeApp();
        launchApp();

    }

    @AfterClass
    public void afterClass() {
        System.out.println("After Class");
    }


    @BeforeMethod
    public void beforeMethod(Method method) {
    	System.out.println("This is Before Method");
    	loginPage = new LoginPage();
        System.out.println("\n"+"+++++++++++++++++ Start of Test +++++++++++++++++"+"\n"+"Test Name: " + method.getName());

    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("After Method");
    }

    @Test
    public void invalidUsername() {
        System.out.println("This is Invalid UserName Block");
        loginPage.enterUserName(loginUsers.getJSONObject("invalidUser").getString("username"));
        loginPage.enterPassWord(loginUsers.getJSONObject("invalidUser").getString("password"));
        loginPage.pressLoginButton();

        String actualErrorMessage = loginPage.getErrorText();
        String expectedErrorMessage = expectedText.getJSONObject("LoginPageExpectedData").getString("LoginErrorWarningText");
        System.out.println("Actual Error Message: " + actualErrorMessage + "\n" + "Expected Error Message: " + expectedErrorMessage);
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);

    }


    @Test
    public void invalidPassword() {
    	System.out.println("This is Invalid Password Block");
        loginPage.enterUserName(loginUsers.getJSONObject("invalidPassword").getString("username"));
        loginPage.enterPassWord(loginUsers.getJSONObject("invalidPassword").getString("password"));
        loginPage.pressLoginButton();

        String actualErrorMessage = loginPage.getErrorText();
        String expectedErrorMessage = expectedText.getJSONObject("LoginPageExpectedData").getString("LoginErrorWarningText");
        System.out.println("Actual Error Message: " + actualErrorMessage + "\n" + "Expected Error Message: " + expectedErrorMessage);
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }

    @Test

    public void validLogin() {
     	System.out.println("This is valid Password Block");
        loginPage.enterUserName(loginUsers.getJSONObject("validUser").getString("username"));
        loginPage.enterPassWord(loginUsers.getJSONObject("validUser").getString("password"));
        productPage = loginPage.pressLoginButton();
        String actualProductTitle = productPage.getProductTitle();
       String expectedProductTitle = expectedText.getJSONObject("LoginPageExpectedData").getString("LoginSuccessText");
       System.out.println("Actual Product Title: " + actualProductTitle + "\n" + "Expected Product Title: " + expectedProductTitle);
        Assert.assertEquals(actualProductTitle, expectedProductTitle);
    }


}




