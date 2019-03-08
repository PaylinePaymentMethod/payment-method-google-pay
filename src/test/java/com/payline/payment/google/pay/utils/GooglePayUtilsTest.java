package com.payline.payment.google.pay.utils;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;

public class GooglePayUtilsTest {

    @Test
    public void createStringAmount() {
        BigInteger int1 = BigInteger.ZERO;
        BigInteger int2 = BigInteger.ONE;
        BigInteger int3 = BigInteger.TEN;
        BigInteger int4 = BigInteger.valueOf(100);
        BigInteger int5 = BigInteger.valueOf(1000);

        Assert.assertEquals("0.00", GooglePayUtils.createStringAmount(int1));
        Assert.assertEquals("0.01", GooglePayUtils.createStringAmount(int2));
        Assert.assertEquals("0.10", GooglePayUtils.createStringAmount(int3));
        Assert.assertEquals("1.00", GooglePayUtils.createStringAmount(int4));
        Assert.assertEquals("10.00", GooglePayUtils.createStringAmount(int5));
    }
}