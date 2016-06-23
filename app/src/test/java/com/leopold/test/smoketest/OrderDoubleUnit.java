package com.leopold.test.smoketest;


import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.leopold.test.reuse.BaseTestCase;
import com.leopold.test.ui.MyMainPage;
import com.leopold.test.ui.MyOrderPage;

/**
 * Created by leopold on 2016/2/3.
 */
public class OrderDoubleUnit extends BaseTestCase {
    public String tab_name = "荤杂类";
    public String dish_name = "梅林午餐肉";

    @BeforeClass (alwaysRun = true)
    public void setUp() throws Exception {
        super.setUp();
        toOpenTable();
    }

    @Test
    public void orderDoubleUnit() throws IOException, InterruptedException {
        waitForActivity(main_activity);
        driver.findElementByName(tab_name).click();
        log("点击菜品分类");

        driver.findElementsById(MyMainPage.btn_add).get(2).click();
        log("点击点菜按钮");

        driver.findElementsById(MyMainPage.img_increase).get(0).click();
        driver.findElementsById(MyMainPage.img_increase).get(1).click();
        log("选择多计量单位");

        //获取小份和大份的价格
        int JG1 = Integer.parseInt(driver.findElementsById(MyMainPage.tv_price_fish).get(0).getAttribute("text"));
        int JG2 = Integer.parseInt(driver.findElementsById(MyMainPage.tv_price_fish).get(1).getAttribute("text"));

        driver.findElementByName("确定").click();
        log("点击确定,加入购物车");

        driver.findElementById(MyMainPage.btn_YD).click();
        driver.findElementById(MyMainPage.btn_order_dlg_ok).click();
        log("确认下单");

        //账单价格
        Pattern p = Pattern.compile("\\d+");
        Matcher m;
        m = p.matcher(driver.findElementById(MyOrderPage.tv_ZDJG).getAttribute("text"));
        m.find();
        int ZDJG = Integer.parseInt(m.group());

        //verify Result
        Assert.assertTrue(ZDJG==(JG1 + JG2));
        log("验证完毕");

    }

    @AfterClass (alwaysRun = true)
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
