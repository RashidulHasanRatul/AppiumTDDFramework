package com.qa.tests.pages;

import com.qa.BaseTest;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class ProductDetailsPage extends BaseTest {
    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Cart drop zone\"]/android.view.ViewGroup/android.widget.TextView")
    public MobileElement SLBTitle;
    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[2]")
    public MobileElement SLBText;

    @AndroidFindBy(accessibility = "test-BACK TO PRODUCTS")
    public MobileElement backToProductBtn;


    public String getSLBTitle() {
        System.out.println("SLBTittle is "+SLBTitle.getText());
        return getAttribute(SLBTitle, "text");
    }
    public String getSLBTxt() {
        System.out.println("SLBTxt is "+SLBText.getText());
        return getAttribute(SLBText, "text");
    }

    public ProductPage pressBackToProductPage(){
        click(backToProductBtn);
        return new ProductPage();
    }

}
