package com.payline.payment.google.pay.service.impl;

import com.payline.pmapi.bean.notification.response.NotificationResponse;
import com.payline.pmapi.bean.notification.response.impl.IgnoreNotificationResponse;
import org.junit.Assert;
import org.junit.Test;

public class NotifcationServiceImplTest {

    private NotificationServiceImpl service = new NotificationServiceImpl();

    @Test
    public void parse(){
        NotificationResponse response = service.parse(null);
        Assert.assertNotNull(response);
        Assert.assertEquals(IgnoreNotificationResponse.class, response.getClass());
    }

    @Test
    public void notifyTransactionStatus(){
        service.notifyTransactionStatus(null);
    }
}
