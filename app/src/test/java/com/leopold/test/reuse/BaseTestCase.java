package com.leopold.test.reuse;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;

import com.leopold.test.data.BasicData;
import com.leopold.test.ui.MyLoadingPage;
import com.leopold.test.ui.MyMainPage;
import com.leopold.test.ui.MyOpenTablePage;
import com.leopold.test.ui.MyServicePage;

public class BaseTestCase {
    public String device_name = "7PAIKZGMVGOJOFIZ";
    public String package_name = "com.kaiying.newspicyway";
    public String loading_activity = "candao.module.loading.LoadingActivity";
    public String main_activity = "candao.module.customer.CustomerActivity";
    public String order_activity = "candao.module.customer.shopcart.views.IAccountsActivity";

    public AndroidDriver<AndroidElement> driver;
    public DesiredCapabilities capabilities;
    public WebDriverWait wait;
    public File screenshot_dir;
    public File screenshot_file;
    public FileOutputStream fos_screenshot;

    @BeforeTest (alwaysRun =true)
    public void init() throws MalformedURLException {
        initDriver();

        try {
            wait.until(new ExpectedCondition<WebElement>(){
                @Override
                public WebElement apply(WebDriver d) {
                    return driver.findElementById(MyLoadingPage.tv_dialogText);
                }});
            driver.findElementById(MyLoadingPage.tv_dialogSure).click();
            driver.findElementById(MyLoadingPage.et_ip1).clear();
            driver.findElementById(MyLoadingPage.et_ip1).sendKeys(BasicData.ip1);
            driver.findElementById(MyLoadingPage.et_ip2).clear();
            driver.findElementById(MyLoadingPage.et_ip2).sendKeys(BasicData.ip2);
            driver.findElementById(MyLoadingPage.et_ip3).clear();
            driver.findElementById(MyLoadingPage.et_ip3).sendKeys(BasicData.ip3);
            driver.findElementById(MyLoadingPage.et_ip4).clear();
            driver.findElementById(MyLoadingPage.et_ip4).sendKeys(BasicData.ip4);
            driver.findElementById(MyLoadingPage.btn_OK).click();
        }
        catch (Exception e) {
            log("当前服务器IP已设置为某个分店地址");
            driver.findElementById(MyMainPage.btn_logo).click();
            driver.findElementById(MyMainPage.btn_administrator).click();
            driver.findElementById(MyMainPage.et_pw).sendKeys(BasicData.administrator_pw);
            driver.findElementById(MyMainPage.btn_ok).click();
            driver.findElementById(MyLoadingPage.et_ip1).clear();
            driver.findElementById(MyLoadingPage.et_ip1).sendKeys(BasicData.ip1);
            driver.findElementById(MyLoadingPage.et_ip2).clear();
            driver.findElementById(MyLoadingPage.et_ip2).sendKeys(BasicData.ip2);
            driver.findElementById(MyLoadingPage.et_ip3).clear();
            driver.findElementById(MyLoadingPage.et_ip3).sendKeys(BasicData.ip3);
            driver.findElementById(MyLoadingPage.et_ip4).clear();
            driver.findElementById(MyLoadingPage.et_ip4).sendKeys(BasicData.ip4);
            driver.findElementById(MyLoadingPage.btn_OK).click();

            log("当前服务器IP已设置为"+BasicData.ip1+"."+BasicData.ip2+"."+BasicData.ip3+"."+BasicData.ip4);
        }
    }

    @BeforeClass (alwaysRun = true)
    public void setUp() throws Exception {

        initDriver();

        try {
            wait.until(new ExpectedCondition<WebElement>(){
                @Override
                public WebElement apply(WebDriver d) {
                    return driver.findElementById(MyLoadingPage.tv_dialogText);
                }});
            driver.findElementById(MyLoadingPage.tv_dialogSure).click();
            driver.findElementById(MyLoadingPage.et_ip1).clear();
            driver.findElementById(MyLoadingPage.et_ip1).sendKeys(BasicData.ip1);
            driver.findElementById(MyLoadingPage.et_ip2).clear();
            driver.findElementById(MyLoadingPage.et_ip2).sendKeys(BasicData.ip2);
            driver.findElementById(MyLoadingPage.et_ip3).clear();
            driver.findElementById(MyLoadingPage.et_ip3).sendKeys(BasicData.ip3);
            driver.findElementById(MyLoadingPage.et_ip4).clear();
            driver.findElementById(MyLoadingPage.et_ip4).sendKeys(BasicData.ip4);
            driver.findElementById(MyLoadingPage.btn_OK).click();
        }
        catch (Exception e) {
            log("当前服务器IP地址已正确设置");
        }

        waitForActivity(main_activity);

        //init for take screenshot
        screenshot_dir = new File(System.getProperty("user.dir")+"/build/test_report/screenshot");
        if (!screenshot_dir.isDirectory()){
            screenshot_dir.mkdirs();
        }
        screenshot_file = new File(screenshot_dir,getClass().getSimpleName()+".png");
        fos_screenshot = new FileOutputStream(screenshot_file);
    }

    @AfterClass (alwaysRun = true)
    public void tearDown() throws Exception {
        takeScreenShot();
        fos_screenshot.close();
        if (null != driver){
            driver.quit();
        }
    }

    public void initDriver() throws MalformedURLException {
        // set up driver if it is null
        if (null == driver) {
            capabilities = new DesiredCapabilities();
            capabilities.setCapability("deviceName", device_name);
            capabilities.setCapability("appPackage", package_name);
            capabilities.setCapability("appActivity", loading_activity);
            capabilities.setCapability("unicodeKeyboard", "True");
            capabilities.setCapability("resetKeyboard", "True");

            driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

            //timeouts
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            //wait for candao.module.customer.CustomerActivity
            wait = new WebDriverWait(driver, 10);
            }
        }
    public void takeScreenShot(){
        try {
            fos_screenshot.write(driver.getScreenshotAs(OutputType.BYTES));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void log(String s){
        Reporter.log(s);
    }

    public void toOpenTable(){
        waitForActivity(main_activity);
        try {
            //设置等待时间为2秒, 判断当前是否已经开台.
            driver.manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS);
            driver.findElementById(MyMainPage.tv_table);
            clickLogoBtn();
            returnDish();
            Thread.sleep(5000);
            driver.pressKeyCode(AndroidKeyCode.BACK);
        }catch (Exception e){
            openTable();
        }finally {
            driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        }
    }

    public void openTable(){
        clickLogoBtn();
        driver.findElementById("com.kaiying.newspicyway:id/gv_chose_waiter").
                findElementsById("com.kaiying.newspicyway:id/ll_waitergv").get(0).
                findElementById("com.kaiying.newspicyway:id/waiter_name").click();
        org.testng.Reporter.log("开台页面选择服务员");
        driver.findElementById("com.kaiying.newspicyway:id/et_service").click();
        driver.tap(1,280,750,0);
        log("开台页面选择餐台");
        driver.findElementById("com.kaiying.newspicyway:id/gallery_man").
                findElementsByClassName("android.widget.LinearLayout").get(0).
                findElementByName("8").click();
        log("开台页面选择就餐人数");
        driver.findElementById(MyOpenTablePage.btn_youth).click();
        log("开台页面选择就餐年龄");
        //driver.findElementByName("免费").click();
        //log("餐具免费");
        driver.findElementById(MyOpenTablePage.btn_ok).click();
        log("确认开台");

        //result verify
        Assert.assertTrue(driver.findElementById(MyMainPage.tv_table).getAttribute("text").equals(BasicData.table_name));
        log("验证完毕");
    }

    public void toCloseTable(){
        waitForActivity(main_activity);

        try {
            //设置等待时间为2秒, 判断当前是否已经开台.
            driver.manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS);
            driver.findElementById(MyMainPage.tv_table);
            closeTable();
        }catch (Exception e){
            log("当前已清台");
        }finally {
            driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        }
    }

    public void closeTable() throws InterruptedException {
        clickLogoBtn();
        returnDish();
        Thread.sleep(5000);
        log("整桌退菜");
        driver.findElementById(MyServicePage.tv_TH).click();
        driver.findElementById(MyServicePage.btn_QT).click();
        driver.findElementByName("确定").click();
        log("清台");

        try {
            //设置等待时间为2秒, 判断当前是否已经开台.
            driver.manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS);
            driver.findElementById(MyMainPage.tv_table);
            Assert.assertFalse(false, "验证清台失败");
        }catch (Exception e){
            log("验证清台成功");
        }finally {
            driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        }
    }

    public void returnDish(){
        driver.findElementById(MyServicePage.img_TC).click();
        log("点击整桌退菜按钮");
        try {
            driver.findElementByName("确定").click();
            driver.findElementById(MyServicePage.et_Manager).sendKeys(BasicData.manager);
            driver.findElementById(MyServicePage.et_pw).sendKeys(BasicData.manager_pw);
            driver.findElementByName("确定").click();
            log("5.输入经理账号退菜");
        }catch (Exception e){
            log("当前没有点菜");
        }
    }

    public void clickLogoBtn(){
        driver.findElementById(MyMainPage.btn_logo).click();
        log("点击开台按钮");
        driver.findElementById(MyMainPage.et_pw).sendKeys(BasicData.waiter_pw);
        log("输入服务员密码0");
        driver.findElementById(MyMainPage.btn_ok).click();
        log("点击确认按钮");
    }

    public void waitForActivity(final String activity){
        wait.until(new ExpectedCondition<Boolean>(){
            @Override
            public Boolean apply(WebDriver d) {
                return activity.equals(driver.currentActivity());
            }});
    }

    public void waitForElement(final String id){
        wait.until(new ExpectedCondition<WebElement>(){
            @Override
            public WebElement apply(WebDriver d) {
                return driver.findElementById(id);
            }});
    }

    public void waitForCondition(final Boolean b){
        wait.until(new ExpectedCondition<Boolean>(){
            @Override
            public Boolean apply(WebDriver d) {
                return b;
            }});
    }

    public void switchPage(String tab_name,String dish_name){
        waitForActivity(main_activity);

        AndroidElement actionbar = driver.findElementById(MyMainPage.RL_ActionBar);
        Point centerTab = actionbar.getCenter();

        boolean exist = false;

        try {
            driver.findElementByName(tab_name).click();
        } catch (Exception e){
            while (!exist) {
                driver.swipe(centerTab.getX()+600,centerTab.getY(),centerTab.getX()-300,centerTab.getY(),1000);
                List<MobileElement> list = driver.findElementById(MyMainPage.LL_TabBar)
                        .findElementsByClassName("android.widget.TextView");
                while (list.iterator().hasNext()) {
                    if (list.iterator().next().getText().equals(tab_name)) {
                        exist = true;
                        driver.findElementByName(tab_name).click();
                    }
                }
            }
        }
        log("切换到指定菜品分类");

        AndroidElement dish_image = driver.findElementById(MyMainPage.img_one_page);
        Point centerImage = dish_image.getCenter();
        while (!driver.findElementById(MyMainPage.tv_dish_name_one_page).getText().equals(dish_name)){
            driver.swipe(centerImage.getX()+500,centerImage.getY(),centerImage.getX()-500,centerImage.getY(),1000);
        }
        log("滑动翻页至指定菜品");
    }

    public void switchTab(String tab_name){
        waitForActivity(main_activity);

        AndroidElement actionbar = driver.findElementById(MyMainPage.RL_ActionBar);
        Point centerTab = actionbar.getCenter();

        boolean exist = false;
        try {
            driver.findElementByName(tab_name).click();
        } catch (Exception e){
            while (!exist) {
                driver.swipe(centerTab.getX()+400,centerTab.getY(),centerTab.getX()-500,centerTab.getY(),1000);
                List<MobileElement> list = driver.findElementById(MyMainPage.LL_TabBar)
                        .findElementsByClassName("android.widget.TextView");
                for (MobileElement aList : list) {
                    if (aList.getText().equals(tab_name)) {
                        exist = true;
                        driver.findElementByName(tab_name).click();
                    }
                }
            }
        }
        log("切换到指定菜品分类");
    }
}