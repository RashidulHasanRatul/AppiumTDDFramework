package com.qa.tests;

import com.qa.tests.pages.LoginPage;
import com.qa.tests.pages.ProductPage;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.Method;

public class LoginTests extends BaseTest {
    LoginPage loginPage;
    ProductPage productPage;
    InputStream datais;
    JSONObject loginUsers;

    @BeforeClass
    public void beforeClass() throws Exception {
        try {
            System.out.println("Before Class");
            String dataFile = "data/loginUsers.json";
            datais = getClass().getClassLoader().getResourceAsStream(dataFile);
            JSONTokener tokener = new JSONTokener(datais);
            loginUsers = new JSONObject(tokener);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           if (datais != null) {
              datais.close();
           }
        }



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
        String expectedErrorMessage = "Username and password do not match any user in this service.";
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
        String expectedErrorMessage = "Username and password do not match any user in this service.";
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
       String expectedProductTitle = "PRODUCTS";
       System.out.println("Actual Product Title: " + actualProductTitle + "\n" + "Expected Product Title: " + expectedProductTitle);
        Assert.assertEquals(actualProductTitle, expectedProductTitle);
    }


}




