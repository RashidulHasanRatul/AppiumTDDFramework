package com.qa.tests.pages;

import com.qa.BaseTest;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class ProductDetailsPage extends BaseTest {
    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[1]")
    public MobileElement SLBTitle;
    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[2]")
    public MobileElement SLBText;
    @AndroidFindBy(accessibility = "test-Price")
    public MobileElement SLBPrice;


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

    public String getSLBPrice() {
        System.out.println("SLBTxt is "+SLBPrice.getText());
        return getAttribute(SLBPrice, "text");
    }

    public ProductDetailsPage scrollToSLBPrice() {
        System.out.println("SLBTxt is "+SLBPrice.getText());
        scrollToElement();
        return this;
    }

    public ProductPage pressBackToProductPage(){
        click(backToProductBtn);
        return new ProductPage();
    }

}
