package com.qa.tests.pages;

import com.qa.tests.BaseTest;
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
        sendKeys(UserNameTextField, userName);
        return this;
    }
    public LoginPage enterPassWord(String password) {
        sendKeys(PasswordTextField, password);
        return this;
    }

    public ProductPage pressLoginButton() {
        click(LoginButton);
        return  new ProductPage();
    }

   public String getErrorText() {
        return getAttribute(ErrorText, "text");
   }
}

