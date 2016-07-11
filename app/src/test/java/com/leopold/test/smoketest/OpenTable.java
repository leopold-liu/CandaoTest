package com.leopold.test.smoketest;


import com.leopold.test.base.BaseTestCase;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

/**
 * Created by leopold on 2016年1月31日.
 */
public class OpenTable extends BaseTestCase {

    @Test
    public void testOpenTable(){
        toOpenTable();
    }

    @AfterClass (alwaysRun = true)
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
