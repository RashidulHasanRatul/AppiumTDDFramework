package com.qa.tests;

import com.qa.BaseTest;
import com.qa.tests.pages.LoginPage;
import com.qa.tests.pages.ProductDetailsPage;
import com.qa.tests.pages.ProductPage;
import com.qa.tests.pages.SettingsPage;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.InputStream;
import java.lang.reflect.Method;

public class ProductTests extends BaseTest {
    LoginPage loginPage;
    ProductPage productPage;
    ProductDetailsPage productDetailsPage;
    SettingsPage settingsPage;
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
        System.out.println("\n" + "+++++++++++++++++ Start of Test +++++++++++++++++" + "\n" + "Test Name: " + method.getName());
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("After Method");
    }

    @Test
    public void ValidateProductsOnProductPage() {
        SoftAssert sa = new SoftAssert();
        String Username = loginUsers.getJSONObject("validUser").getString("username");
        String PassWord = loginUsers.getJSONObject("validUser").getString("password");
        productPage = loginPage.login(Username, PassWord);
        String SLBTitle = productPage.getSLBTitle();
        sa.assertEquals(SLBTitle, expectedText.getJSONObject("ProductPageExpectedData").getString("SLBTitle"));
        String SLBPrice = productPage.getSLBPrice();
        sa.assertEquals(SLBPrice, expectedText.getJSONObject("ProductPageExpectedData").getString("SLBPrice"));
        settingsPage = productPage.pressSettingsBtn();
        loginPage = settingsPage.pressLogoutBtn();
        sa.assertAll();


    }

    @Test
    public void ValidateProductsOnProductDetailsPage() {
        SoftAssert sa = new SoftAssert();
        String Username = loginUsers.getJSONObject("validUser").getString("username");
        String PassWord = loginUsers.getJSONObject("validUser").getString("password");
        productPage = loginPage.login(Username, PassWord);
        productDetailsPage = productPage.pressSLBTitle();
        String SLBTitle = productDetailsPage.getSLBTitle();
        sa.assertEquals(SLBTitle, expectedText.getJSONObject("ProductDetailsPageExpectedData").getString("SLBTitle"));
        String SLBTxt = productDetailsPage.getSLBTxt();
        sa.assertEquals(SLBTxt, expectedText.getJSONObject("ProductDetailsPageExpectedData").getString("SLBTxt"));
        productDetailsPage.scrollToElement();
        String SLBPrice = productDetailsPage.getSLBPrice();
        sa.assertEquals(SLBPrice, expectedText.getJSONObject("ProductDetailsPageExpectedData").getString("SLBPrice"));
        productPage = productDetailsPage.pressBackToProductPage();
        settingsPage = productPage.pressSettingsBtn();
        loginPage = settingsPage.pressLogoutBtn();
        sa.assertAll();


    }

}




