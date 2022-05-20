package com.qa.tests.pages;

import com.qa.BaseTest;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class LoginPage extends BaseTest {
    @AndroidFindBy(accessibility = "test-Username")
    public MobileElement UserNameTextField;
    @AndroidFindBy(xpath = "//android.widget.EditText[@content-desc=\"test-Password\"]")
    public MobileElement PasswordTextField;
    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-LOGIN\"]")
    public MobileElement LoginButton;
    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Error message\"]/android.widget.TextView")
    public MobileElement ErrorText;

    public LoginPage enterUserName(String userName) {
        System.out.println("Login With"+userName);
        sendKeys(UserNameTextField, userName);
        return this;
    }
    public LoginPage enterPassWord(String password) {
        System.out.println("Password is "+password);
        sendKeys(PasswordTextField, password);
        return this;
    }

    public ProductPage pressLoginButton() {
        System.out.println("Press Login Button");
        click(LoginButton);
        return  new ProductPage();
    }

    public ProductPage login(String userName, String passWord){
        enterUserName(userName);
        enterPassWord(passWord);
       return pressLoginButton();

    }

   public String getErrorText() {
        String ErrorTxt = ErrorText.getText();
       System.out.println("Error Text is "+ErrorTxt);
        return getAttribute(ErrorText, "text");
   }
}

