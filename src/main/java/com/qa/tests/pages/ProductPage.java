package com.qa.tests.pages;
import com.qa.tests.BaseTest;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class ProductPage extends BaseTest {

    
    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Cart drop zone\"]/android.view.ViewGroup/android.widget.TextView")
    public MobileElement productTitleTxt;

    public String getProductTitle() {
        return getAttribute(productTitleTxt, "text");
    }


}
