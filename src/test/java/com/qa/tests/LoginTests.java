package com.qa.tests;

import com.qa.tests.pages.LoginPage;
import com.qa.tests.pages.ProductPage;
import org.testng.Assert;
import org.testng.annotations.*;

import java.lang.reflect.Method;

public class LoginTests extends BaseTest {
    LoginPage loginPage;
    ProductPage productPage;

    @BeforeClass
    public void beforeClass() {
        System.out.println("Before Class");
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
        loginPage.enterUserName("invalid");
        loginPage.enterPassWord("secret_sauce");
        loginPage.pressLoginButton();

        String actualErrorMessage = loginPage.getErrorText();
        String expectedErrorMessage = "Username and password do not match any user in this service.";
        System.out.println("Actual Error Message: " + actualErrorMessage + "\n" + "Expected Error Message: " + expectedErrorMessage);
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }


    @Test
    public void invalidPassword() {
    	System.out.println("This is Invalid Password Block");
        loginPage.enterUserName("standard_user");
        loginPage.enterPassWord("invalid");
        loginPage.pressLoginButton();

        String actualErrorMessage = loginPage.getErrorText();
        String expectedErrorMessage = "Username and password do not match any user in this service.";
        System.out.println("Actual Error Message: " + actualErrorMessage + "\n" + "Expected Error Message: " + expectedErrorMessage);
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }

    @Test

    public void validLogin() {
     	System.out.println("This is valid Password Block");
        loginPage.enterUserName("standard_user");
        loginPage.enterPassWord("secret_sauce");
        productPage = loginPage.pressLoginButton();
        String actualProductTitle = productPage.getProductTitle();
       String expectedProductTitle = "PRODUCTS";
       System.out.println("Actual Product Title: " + actualProductTitle + "\n" + "Expected Product Title: " + expectedProductTitle);
        Assert.assertEquals(actualProductTitle, expectedProductTitle);
    }


}




