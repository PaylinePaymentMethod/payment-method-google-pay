package com.payline.payment.google.pay.utils;


import org.junit.Assert;
import org.junit.Test;

public class PluginUtilsTest {

    @Test
    public void truncateOk() {

        Assert.assertEquals("", PluginUtils.truncate("foo", 0));
        Assert.assertEquals("foo", PluginUtils.truncate("foo", 3));
        Assert.assertEquals("foo", PluginUtils.truncate("foo", 5));
        Assert.assertEquals("fo", PluginUtils.truncate("foo", 2));
    }
}
