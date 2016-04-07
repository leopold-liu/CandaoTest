package com.leopold.test.smoketest;

import org.openqa.selenium.Point;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;

import com.leopold.test.reuse.BaseTestCase;
import com.leopold.test.ui.MyMainPage;
import com.leopold.test.ui.MyOrderPage;

/**
 * Created by leopold on 2016/2/3.
 */
public class OrderSetMeal extends BaseTestCase {
    public String tab_name = "套餐";
    public String setmeal_name = "圣诞双人餐";
    public String yuguo_name = "红汤梭边鱼锅";
    public String xiaoliao_name = "麻酱";
    public String liangcai_name = "美味海藻";
    public String yinpin_name = "土贡梅煎";

    @BeforeClass (alwaysRun = true)
    public void setUp() throws Exception {
        super.setUp();
        toOpenTable();
    }

    @Test
    public void orderSetMeal() throws IOException, InterruptedException {
        waitForActivity(main_activity);

        switchTab(tab_name);
        log("点击菜品分类");

        driver.findElementById(MyMainPage.btn_add).click();
        log("点击点菜按钮");

        driver.findElementByName(yuguo_name).click();

        //下翻
        Point center = driver.findElementById(MyMainPage.LL_TCXZ).getCenter();
        driver.swipe(center.getX(),center.getY()+490,center.getX(),center.getY()-400,1000);
        driver.findElementByName(xiaoliao_name).click();

        //下翻
        driver.swipe(center.getX(),center.getY()+490,center.getX(),center.getY()-400,1000);
        driver.findElementByName(xiaoliao_name).click();

        //下翻
        driver.swipe(center.getX(),center.getY()+490,center.getX(),center.getY()-400,1000);
        driver.findElementByName(liangcai_name).click();

        //下翻
        driver.swipe(center.getX(),center.getY()+490,center.getX(),center.getY()-400,1000);
        driver.findElementByName(yinpin_name).click();
        log("套餐内容选择完毕");

        driver.findElementByName("确定").click();
        log("点击确定,加入购物车");

        driver.findElementById(MyMainPage.btn_YD).click();
        driver.findElementById(MyMainPage.btn_dialog_ok).click();
        log("确认下单");

        //verify Result
        Assert.assertTrue(driver.findElementById(MyOrderPage.tv_ZDJG).isDisplayed());
        log("验证完毕");

        driver.pressKeyCode(AndroidKeyCode.BACK);
        log("返回菜谱页面");
    }

    @AfterClass (alwaysRun = true)
    public void tearDown() throws Exception {
        toCloseTable();
        super.tearDown();
    }
}
