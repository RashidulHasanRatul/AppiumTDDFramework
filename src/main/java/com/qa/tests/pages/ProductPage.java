package com.qa.tests.pages;
import com.qa.MenuPage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class ProductPage extends MenuPage {

    
    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Cart drop zone\"]/android.view.ViewGroup/android.widget.TextView")
    public MobileElement productTitleTxt;

    @AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc=\"test-Item title\"])[1]")
    public MobileElement SLBTitle;
    @AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc=\"test-Price\"])[1]")
    public MobileElement SLBPrice;

    public String getProductTitle() {

        return getAttribute(productTitleTxt, "text");
    }
    public String getSLBTitle() {
        return getAttribute(SLBTitle, "text");
    }
    public String getSLBPrice() {
        return getAttribute(SLBPrice, "text");
    }

    public ProductDetailsPage pressSLBTitle(){
        click(SLBTitle);
        return new ProductDetailsPage();
    }

}
