package com.leopold.test.smoketest;


import org.testng.Assert;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidElement;

import com.leopold.test.reuse.BaseTestCase;
import com.leopold.test.ui.MySearchPage;

/**
 * Created by leopold on 2016/2/3.
 */
public class SearchDish extends BaseTestCase {

    public String keywords = "鱼锅";
    public String dish_name = "红汤梭边鱼锅";

    @Test
    public void search(){
        waitForActivity(main_activity);
        driver.findElementByName("搜索").click();
        AndroidElement search_text = driver.findElementById(MySearchPage.et_search);
        search_text.click();
        log("点击搜索按钮");
        search_text.sendKeys(keywords);
        log("搜索菜品");

        //verify result
        Assert.assertTrue(driver.findElementByName(dish_name).isDisplayed());
        log("验证完毕");
    }
}
