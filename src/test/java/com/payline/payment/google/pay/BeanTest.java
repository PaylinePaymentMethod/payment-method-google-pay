package com.payline.payment.google.pay;

import com.payline.payment.google.pay.bean.PaymentData;
import org.junit.Assert;
import org.junit.Test;

public class BeanTest {
    private static final String GOOD_PAYMENT_DATA = "{\"apiVersionMinor\":0,\"apiVersion\":2,\"paymentMethodData\":{\"description\":\"Visa •••• 5555\",\"tokenizationData\":{\"type\":\"PAYMENT_GATEWAY\",\"token\":\"{\\\"signature\\\":\\\"MEUCIQD95mdvMXJ2487P0kRF9FQ+nmqrOK0ZlV9ACsp46Um3lgIgYlBCZmBwGpn6J5DbNxSeDwHm+EbqM3wJpe9tVvvAEzs\\\\u003d\\\",\\\"protocolVersion\\\":\\\"ECv1\\\",\\\"signedMessage\\\":\\\"{\\\\\\\"encryptedMessage\\\\\\\":\\\\\\\"P4URAUuCT3h2JFdcFOnK74TTiEIVbWKTauPWyE0BxJEYCdiefH5l7FSmpoo726jpktGWv7yWdecQucilV5LW7/DWJuY49hsExtRuo/0YCasPmX5rWyctvqs417VuztdWujmNJJceedQi9H/yhoGY/YFAWicwj/+OlwgBeY8FkmEeEbXtPdrB6cxwVRiE0OBtPAXywnjpre8jpYmh7EdaJIv22a8OTiY2b8n1eKYZcH51abztPwQlt4IpJucAOrs9gtgdcoFIYRE85JvmFQC2y5PgRVJW8/2d6g+XFDQW+JDSvoIrwfG56h/zP/UInaXKOb3/y7PMjuXIrtlTNq+WAnYFKBBvwEpWIFz6N1uMWcYYuK/W6HnmZARV0KSKfDBK8HBOx0j+FyfS8GoWOlxQbOPTjK1hgoMV2H3MPkDIsChpg9tg/rB6X69dTdUASzLdMqHWaGol8FK5PK20\\\\\\\",\\\\\\\"ephemeralPublicKey\\\\\\\":\\\\\\\"BBFkSPgb6C91NzTup2xB5ug+NjpbMDw/SYOZg9F22C0M0RUGd3KdLONGUpIUCtt5O19Bbwc5V/I96WQShiQwYr0\\\\\\\\u003d\\\\\\\",\\\\\\\"tag\\\\\\\":\\\\\\\"EjygRTxM019BhCt4OEyDWEbSjmYl0mjBOuJ4DrpzXT0\\\\\\\\u003d\\\\\\\"}\\\"}\"},\"type\":\"CARD\",\"info\":{\"cardNetwork\":\"VISA\",\"cardDetails\":\"5555\",\"billingAddress\":{\"phoneNumber\":\"+33 6 66 66 66 66\",\"countryCode\":\"FR\",\"postalCode\":\"75000\",\"name\":\"Jean JEAN\"}}},\"shippingAddress\":{\"address3\":\"\",\"sortingCode\":\"\",\"address2\":\"\",\"countryCode\":\"FR\",\"address1\":\"1 rue de la République\",\"postalCode\":\"75000\",\"name\":\"Jean JEAN\",\"locality\":\"Paris\",\"administrativeArea\":\"\"},\"email\":\"jeanjean@gmail.com\"}";
    private static final String LITE_PAYMENT_DATA = "{\"apiVersionMinor\":0,\"apiVersion\":2,\"paymentMethodData\":{\"description\":\"Visaâ€†â€¢â€¢â€¢â€¢â€†5555\",\"tokenizationData\":{\"type\":\"PAYMENT_GATEWAY\",\"token\":\"{\\\"signature\\\":\\\"MEUCIQD95mdvMXJ2487P0kRF9FQ+nmqrOK0ZlV9ACsp46Um3lgIgYlBCZmBwGpn6J5DbNxSeDwHm+EbqM3wJpe9tVvvAEzs\\\\u003d\\\",\\\"protocolVersion\\\":\\\"ECv1\\\",\\\"signedMessage\\\":\\\"{\\\\\\\"encryptedMessage\\\\\\\":\\\\\\\"P4URAUuCT3h2JFdcFOnK74TTiEIVbWKTauPWyE0BxJEYCdiefH5l7FSmpoo726jpktGWv7yWdecQucilV5LW7/DWJuY49hsExtRuo/0YCasPmX5rWyctvqs417VuztdWujmNJJceedQi9H/yhoGY/YFAWicwj/+OlwgBeY8FkmEeEbXtPdrB6cxwVRiE0OBtPAXywnjpre8jpYmh7EdaJIv22a8OTiY2b8n1eKYZcH51abztPwQlt4IpJucAOrs9gtgdcoFIYRE85JvmFQC2y5PgRVJW8/2d6g+XFDQW+JDSvoIrwfG56h/zP/UInaXKOb3/y7PMjuXIrtlTNq+WAnYFKBBvwEpWIFz6N1uMWcYYuK/W6HnmZARV0KSKfDBK8HBOx0j+FyfS8GoWOlxQbOPTjK1hgoMV2H3MPkDIsChpg9tg/rB6X69dTdUASzLdMqHWaGol8FK5PK20\\\\\\\",\\\\\\\"ephemeralPublicKey\\\\\\\":\\\\\\\"BBFkSPgb6C91NzTup2xB5ug+NjpbMDw/SYOZg9F22C0M0RUGd3KdLONGUpIUCtt5O19Bbwc5V/I96WQShiQwYr0\\\\\\\\u003d\\\\\\\",\\\\\\\"tag\\\\\\\":\\\\\\\"EjygRTxM019BhCt4OEyDWEbSjmYl0mjBOuJ4DrpzXT0\\\\\\\\u003d\\\\\\\"}\\\"}\"},\"type\":\"CARD\",\"info\":{\"cardNetwork\":\"VISA\",\"cardDetails\":\"5555\"}},\"email\":\"jeanjean@gmail.com\"}";

    @Test
    public void createPaymentData(){
        PaymentData paymentData = new PaymentData.Builder().fromJson(GOOD_PAYMENT_DATA);

        Assert.assertNotNull(paymentData);
        Assert.assertNotNull(paymentData.getApiVersion());
        Assert.assertNotNull(paymentData.getApiVersionMinor());
        Assert.assertNotNull(paymentData.getEmail());
        Assert.assertNotNull(paymentData.getPaymentMethodData());
        Assert.assertNotNull(paymentData.getPaymentMethodData().getDescription());
        Assert.assertNotNull(paymentData.getPaymentMethodData().getType());
        Assert.assertNotNull(paymentData.getPaymentMethodData().getTokenizationData());
        Assert.assertNotNull(paymentData.getPaymentMethodData().getTokenizationData().getType());
        Assert.assertNotNull(paymentData.getPaymentMethodData().getTokenizationData().getToken());
        Assert.assertNotNull(paymentData.getPaymentMethodData().getInfo());
        Assert.assertNotNull(paymentData.getPaymentMethodData().getInfo().getCardDetails());
        Assert.assertNotNull(paymentData.getPaymentMethodData().getInfo().getCardNetwork());
        Assert.assertNotNull(paymentData.getPaymentMethodData().getInfo().getBillingAddress());
        Assert.assertNotNull(paymentData.getPaymentMethodData().getInfo().getBillingAddress().getCountryCode());
        Assert.assertNotNull(paymentData.getPaymentMethodData().getInfo().getBillingAddress().getName());
        Assert.assertNotNull(paymentData.getPaymentMethodData().getInfo().getBillingAddress().getPhoneNumber());
        Assert.assertNotNull(paymentData.getPaymentMethodData().getInfo().getBillingAddress().getPostalCode());
    }

    @Test
    public void createLitePaymentData(){
        PaymentData paymentData = new PaymentData.Builder().fromJson(LITE_PAYMENT_DATA);

        Assert.assertNotNull(paymentData);
        Assert.assertNotNull(paymentData.getApiVersion());
        Assert.assertNotNull(paymentData.getApiVersionMinor());
        Assert.assertNotNull(paymentData.getEmail());
        Assert.assertNotNull(paymentData.getPaymentMethodData());
        Assert.assertNotNull(paymentData.getPaymentMethodData().getDescription());
        Assert.assertNotNull(paymentData.getPaymentMethodData().getType());
        Assert.assertNotNull(paymentData.getPaymentMethodData().getTokenizationData());
        Assert.assertNotNull(paymentData.getPaymentMethodData().getTokenizationData().getType());
        Assert.assertNotNull(paymentData.getPaymentMethodData().getTokenizationData().getToken());
        Assert.assertNotNull(paymentData.getPaymentMethodData().getInfo());
        Assert.assertNotNull(paymentData.getPaymentMethodData().getInfo().getCardDetails());
        Assert.assertNotNull(paymentData.getPaymentMethodData().getInfo().getCardNetwork());
    }

}
