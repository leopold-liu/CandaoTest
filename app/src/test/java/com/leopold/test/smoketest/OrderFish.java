package com.leopold.test.smoketest;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.leopold.test.base.BaseTestCase;
import com.leopold.test.ui.MyMainPage;
import com.leopold.test.ui.MyOrderPage;

/**
 * Created by leopold on 2016/2/3.
 */
public class OrderFish extends BaseTestCase {
    public String tab_name = "鱼锅";

    @BeforeClass (alwaysRun = true)
    public void setUp() throws Exception {
        super.setUp();
        toOpenTable();
    }

    @Test
    public void orderFish() throws IOException, InterruptedException {
        waitForActivity(main_activity);
        driver.findElementByName(tab_name).click();
        log("点击菜品分类");
        driver.findElementById(MyMainPage.btn_add).click();
        log("点击点菜按钮");
        driver.findElementById(MyMainPage.img_increase).click();
        driver.findElementById(MyMainPage.img_add_fish).click();

        Pattern p = Pattern.compile("\\d+");
        Matcher m;
        //锅底价格
        m = p.matcher(driver.findElementById(MyMainPage.tv_price_guodi).getAttribute("text"));
        m.find();
        int GDJG = Integer.parseInt(m.group());
        //鱼的价格
        m = p.matcher(driver.findElementById(MyMainPage.tv_price_fish).getAttribute("text"));
        m.find();
        int YJG = Integer.parseInt(m.group());
        driver.findElementByName("确定").click();
        log("点鱼锅");

        driver.findElementById(MyMainPage.btn_YD).click();
        driver.findElementById(MyMainPage.btn_order_dlg_ok).click();
        log("确认下单");

        //账单价格
        m = p.matcher(driver.findElementById(MyOrderPage.tv_ZDJG).getAttribute("text"));
        m.find();
        int ZDJG = Integer.parseInt(m.group());
        //verify Result
        Assert.assertTrue(ZDJG==(GDJG*2 + YJG*3));
        log("验证完毕");

    }

    @AfterClass (alwaysRun = true)
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
