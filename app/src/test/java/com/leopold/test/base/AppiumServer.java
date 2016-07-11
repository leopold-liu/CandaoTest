package com.leopold.test.base;

import java.io.IOException;

/**
 * Created by leopold on 2016/7/11.
 */
public class AppiumServer {
    public AppiumServer(String p, String bp, String udid){
        try {
            Runtime.getRuntime().exec("appium -p "+p+" -bp "+bp+" -U "+udid+" --session-override --platform-name Android --automation-name Appium --no-reset --dont-stop-app-on-reset");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
