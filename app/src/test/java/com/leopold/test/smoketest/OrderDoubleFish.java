package com.leopold.test.smoketest;

import junit.framework.Assert;

import org.openqa.selenium.Point;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;

import com.leopold.test.reuse.BaseTestCase;
import com.leopold.test.ui.MyMainPage;
import com.leopold.test.ui.MyOrderPage;

/**
 * Created by leopold on 2016/2/3.
 */
public class OrderDoubleFish extends BaseTestCase {
    public String tab_name = "鱼锅";
    public String dish_name = "创意双拼鱼锅";

    @BeforeClass (alwaysRun = true)
    public void setUp() throws Exception {
        super.setUp();
        toOpenTable();
    }

    @Test
    public void orderDoubleFish() throws IOException, InterruptedException {
        waitForActivity(main_activity);
        switchPage(tab_name,dish_name);
//        switchTab(tab_name);
//        log("点击菜品分类");
//
//        AndroidElement dish_image = driver.findElementById(MyMainPage.img_one_page);
//        Point center = dish_image.getCenter();
//        while (!(dish_name.equals(driver.findElementById(MyMainPage.tv_dish_name_one_page).getAttribute("text")))){
//            //swipe from right to left
//            driver.swipe(center.getX()+500,center.getY(),center.getX()-500,center.getY(),1000);
//        }
        log("滑动翻页至创意双拼鱼锅");

        driver.findElementById(MyMainPage.btn_add).click();
        log("点击点菜按钮");

        driver.findElementsById(MyMainPage.tv_SPG).get(0).click();
        driver.findElementsById(MyMainPage.tv_SPG).get(1).click();
        log("选择双拼锅");

        //获取锅底价格和鱼的价格
        Pattern p = Pattern.compile("\\d+");
        Matcher m;

        m = p.matcher(driver.findElementsById(MyMainPage.tv_price_guodi_SPG).get(0).getAttribute("text"));
        m.find();
        int GDJG1 = Integer.parseInt(m.group());

        m = p.matcher(driver.findElementsById(MyMainPage.tv_price_guodi_SPG).get(1).getAttribute("text"));
        m.find();
        int GDJG2 = Integer.parseInt(m.group());

        m = p.matcher(driver.findElementsById(MyMainPage.tv_price_fish_SPG).get(0).getAttribute("text"));
        m.find();
        int YJG1 = Integer.parseInt(m.group());

        m = p.matcher(driver.findElementsById(MyMainPage.tv_price_fish_SPG).get(1).getAttribute("text"));
        m.find();
        int YJG2 = Integer.parseInt(m.group());

        driver.findElementByName("确定").click();
        log("点击确定,加入购物车");

        driver.findElementById(MyMainPage.btn_YD).click();
        driver.findElementById(MyMainPage.btn_dialog_ok).click();
        log("确认下单");

        //账单价格
        m = p.matcher(driver.findElementById(MyOrderPage.tv_ZDJG).getAttribute("text"));
        m.find();
        int ZDJG = Integer.parseInt(m.group());

        //verify Result
        Assert.assertTrue(ZDJG==(GDJG1 + GDJG2 + YJG1 + YJG2));
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
