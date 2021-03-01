package com.payline.payment.google.pay.service.impl;

import com.google.crypto.tink.apps.paymentmethodtoken.PaymentMethodTokenRecipient;
import com.payline.payment.google.pay.utils.Utils;
import com.payline.pmapi.bean.payment.request.PaymentRequest;
import com.payline.pmapi.bean.payment.response.PaymentModeCard;
import com.payline.pmapi.bean.payment.response.PaymentResponse;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseDoPayment;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseFailure;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.security.GeneralSecurityException;

import static com.payline.payment.google.pay.utils.GooglePayConstants.PAYMENTDATA_TOKENDATA;
import static com.payline.payment.google.pay.utils.GooglePayConstants.PAYMENT_REQUEST_PAYMENT_DATA_KEY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class PaymentServiceImplTest {

    @Spy
    @InjectMocks
    private PaymentServiceImpl service = new PaymentServiceImpl();

    private static final String GOOD_PAYMENT_DATA = "{\"apiVersionMinor\":0,\"apiVersion\":2,\"paymentMethodData\":{\"description\":\"Visa •••• 5555\",\"tokenizationData\":{\"type\":\"PAYMENT_GATEWAY\",\"token\":\"{\\\"signature\\\":\\\"MEUCIQD95mdvMXJ2487P0kRF9FQ+nmqrOK0ZlV9ACsp46Um3lgIgYlBCZmBwGpn6J5DbNxSeDwHm+EbqM3wJpe9tVvvAEzs\\\\u003d\\\",\\\"protocolVersion\\\":\\\"ECv1\\\",\\\"signedMessage\\\":\\\"{\\\\\\\"encryptedMessage\\\\\\\":\\\\\\\"P4URAUuCT3h2JFdcFOnK74TTiEIVbWKTauPWyE0BxJEYCdiefH5l7FSmpoo726jpktGWv7yWdecQucilV5LW7/DWJuY49hsExtRuo/0YCasPmX5rWyctvqs417VuztdWujmNJJceedQi9H/yhoGY/YFAWicwj/+OlwgBeY8FkmEeEbXtPdrB6cxwVRiE0OBtPAXywnjpre8jpYmh7EdaJIv22a8OTiY2b8n1eKYZcH51abztPwQlt4IpJucAOrs9gtgdcoFIYRE85JvmFQC2y5PgRVJW8/2d6g+XFDQW+JDSvoIrwfG56h/zP/UInaXKOb3/y7PMjuXIrtlTNq+WAnYFKBBvwEpWIFz6N1uMWcYYuK/W6HnmZARV0KSKfDBK8HBOx0j+FyfS8GoWOlxQbOPTjK1hgoMV2H3MPkDIsChpg9tg/rB6X69dTdUASzLdMqHWaGol8FK5PK20\\\\\\\",\\\\\\\"ephemeralPublicKey\\\\\\\":\\\\\\\"BBFkSPgb6C91NzTup2xB5ug+NjpbMDw/SYOZg9F22C0M0RUGd3KdLONGUpIUCtt5O19Bbwc5V/I96WQShiQwYr0\\\\\\\\u003d\\\\\\\",\\\\\\\"tag\\\\\\\":\\\\\\\"EjygRTxM019BhCt4OEyDWEbSjmYl0mjBOuJ4DrpzXT0\\\\\\\\u003d\\\\\\\"}\\\"}\"},\"type\":\"CARD\",\"info\":{\"cardNetwork\":\"VISA\",\"cardDetails\":\"5555\",\"billingAddress\":{\"phoneNumber\":\"+33 6 66 66 66 66\",\"countryCode\":\"FR\",\"postalCode\":\"75000\",\"name\":\"Jean JEAN\"}}},\"shippingAddress\":{\"address3\":\"\",\"sortingCode\":\"\",\"address2\":\"\",\"countryCode\":\"FR\",\"address1\":\"1 rue de la République\",\"postalCode\":\"75000\",\"name\":\"Jean JEAN\",\"locality\":\"Paris\",\"administrativeArea\":\"\"},\"email\":\"jeanjean@gmail.com\"}";
    private static final String GOOD_PAYMENT_DATA_MC = "{\"apiVersionMinor\":0,\"apiVersion\":2,\"paymentMethodData\":{\"description\":\"Visa •••• 5555\",\"tokenizationData\":{\"type\":\"PAYMENT_GATEWAY\",\"token\":\"{\\\"signature\\\":\\\"MEUCIQD95mdvMXJ2487P0kRF9FQ+nmqrOK0ZlV9ACsp46Um3lgIgYlBCZmBwGpn6J5DbNxSeDwHm+EbqM3wJpe9tVvvAEzs\\\\u003d\\\",\\\"protocolVersion\\\":\\\"ECv1\\\",\\\"signedMessage\\\":\\\"{\\\\\\\"encryptedMessage\\\\\\\":\\\\\\\"P4URAUuCT3h2JFdcFOnK74TTiEIVbWKTauPWyE0BxJEYCdiefH5l7FSmpoo726jpktGWv7yWdecQucilV5LW7/DWJuY49hsExtRuo/0YCasPmX5rWyctvqs417VuztdWujmNJJceedQi9H/yhoGY/YFAWicwj/+OlwgBeY8FkmEeEbXtPdrB6cxwVRiE0OBtPAXywnjpre8jpYmh7EdaJIv22a8OTiY2b8n1eKYZcH51abztPwQlt4IpJucAOrs9gtgdcoFIYRE85JvmFQC2y5PgRVJW8/2d6g+XFDQW+JDSvoIrwfG56h/zP/UInaXKOb3/y7PMjuXIrtlTNq+WAnYFKBBvwEpWIFz6N1uMWcYYuK/W6HnmZARV0KSKfDBK8HBOx0j+FyfS8GoWOlxQbOPTjK1hgoMV2H3MPkDIsChpg9tg/rB6X69dTdUASzLdMqHWaGol8FK5PK20\\\\\\\",\\\\\\\"ephemeralPublicKey\\\\\\\":\\\\\\\"BBFkSPgb6C91NzTup2xB5ug+NjpbMDw/SYOZg9F22C0M0RUGd3KdLONGUpIUCtt5O19Bbwc5V/I96WQShiQwYr0\\\\\\\\u003d\\\\\\\",\\\\\\\"tag\\\\\\\":\\\\\\\"EjygRTxM019BhCt4OEyDWEbSjmYl0mjBOuJ4DrpzXT0\\\\\\\\u003d\\\\\\\"}\\\"}\"},\"type\":\"CARD\",\"info\":{\"cardNetwork\":\"MASTERCARD\",\"cardDetails\":\"5555\",\"billingAddress\":{\"phoneNumber\":\"+33 6 66 66 66 66\",\"countryCode\":\"FR\",\"postalCode\":\"75000\",\"name\":\"Jean JEAN\"}}},\"shippingAddress\":{\"address3\":\"\",\"sortingCode\":\"\",\"address2\":\"\",\"countryCode\":\"FR\",\"address1\":\"1 rue de la République\",\"postalCode\":\"75000\",\"name\":\"Jean JEAN\",\"locality\":\"Paris\",\"administrativeArea\":\"\"},\"email\":\"jeanjean@gmail.com\"}";
    private static final String LIGHT_PAYMENT_DATA = "{\"apiVersionMinor\":0,\"apiVersion\":2,\"paymentMethodData\":{\"description\":\"Visa •••• 5555\",\"tokenizationData\":{\"type\":\"PAYMENT_GATEWAY\",\"token\":\"{\\\"signature\\\":\\\"MEUCIQD95mdvMXJ2487P0kRF9FQ+nmqrOK0ZlV9ACsp46Um3lgIgYlBCZmBwGpn6J5DbNxSeDwHm+EbqM3wJpe9tVvvAEzs\\\\u003d\\\",\\\"protocolVersion\\\":\\\"ECv1\\\",\\\"signedMessage\\\":\\\"{\\\\\\\"encryptedMessage\\\\\\\":\\\\\\\"P4URAUuCT3h2JFdcFOnK74TTiEIVbWKTauPWyE0BxJEYCdiefH5l7FSmpoo726jpktGWv7yWdecQucilV5LW7/DWJuY49hsExtRuo/0YCasPmX5rWyctvqs417VuztdWujmNJJceedQi9H/yhoGY/YFAWicwj/+OlwgBeY8FkmEeEbXtPdrB6cxwVRiE0OBtPAXywnjpre8jpYmh7EdaJIv22a8OTiY2b8n1eKYZcH51abztPwQlt4IpJucAOrs9gtgdcoFIYRE85JvmFQC2y5PgRVJW8/2d6g+XFDQW+JDSvoIrwfG56h/zP/UInaXKOb3/y7PMjuXIrtlTNq+WAnYFKBBvwEpWIFz6N1uMWcYYuK/W6HnmZARV0KSKfDBK8HBOx0j+FyfS8GoWOlxQbOPTjK1hgoMV2H3MPkDIsChpg9tg/rB6X69dTdUASzLdMqHWaGol8FK5PK20\\\\\\\",\\\\\\\"ephemeralPublicKey\\\\\\\":\\\\\\\"BBFkSPgb6C91NzTup2xB5ug+NjpbMDw/SYOZg9F22C0M0RUGd3KdLONGUpIUCtt5O19Bbwc5V/I96WQShiQwYr0\\\\\\\\u003d\\\\\\\",\\\\\\\"tag\\\\\\\":\\\\\\\"EjygRTxM019BhCt4OEyDWEbSjmYl0mjBOuJ4DrpzXT0\\\\\\\\u003d\\\\\\\"}\\\"}\"},\"type\":\"CARD\",\"info\":{\"cardNetwork\":\"VISA\",\"cardDetails\":\"5555\",\"billingAddress\":{\"phoneNumber\":\"+33 6 66 66 66 66\",\"countryCode\":\"FR\",\"postalCode\":\"75000\"}}},\"shippingAddress\":{\"address3\":\"\",\"sortingCode\":\"\",\"address2\":\"\",\"countryCode\":\"FR\",\"address1\":\"1 rue de la République\",\"postalCode\":\"75000\",\"name\":\"Jean JEAN\",\"locality\":\"Paris\",\"administrativeArea\":\"\"},\"email\":\"jeanjean@gmail.com\"}";
    private static final String GOOD_RESPONSE_DATA = "{\"gatewayMerchantId\":\"gatewayMerchantId\",\"messageExpiration\":\"1545645423302\",\"messageId\":\"AH2EjtdtS-iyj7y_HfF5auUIINqe6-SRJf8UtQr5oj7KrJE3EkfxfdZVVfhHyAWmc-y_-UlYjYwBDOwg_3vm6xFf7EZc9fZvwuXpi5AxKK1XRZQ_tlbtOdo6YoYiP4MUadyK9bghDA4N\",\"paymentMethod\":\"CARD\",\"paymentMethodDetails\":{\"expirationYear\":2023,\"expirationMonth\":12,\"pan\":\"4111111111111111\"}}";
    private static final String GOOD_RESPONSE_DATA_WITH_ECI = "{\"gatewayMerchantId\":\"gatewayMerchantId\",\"messageExpiration\":\"1545645423302\",\"messageId\":\"AH2EjtdtS-iyj7y_HfF5auUIINqe6-SRJf8UtQr5oj7KrJE3EkfxfdZVVfhHyAWmc-y_-UlYjYwBDOwg_3vm6xFf7EZc9fZvwuXpi5AxKK1XRZQ_tlbtOdo6YoYiP4MUadyK9bghDA4N\",\"paymentMethod\":\"CARD\",\"paymentMethodDetails\":{\"expirationYear\":2023,\"expirationMonth\":12,\"pan\":\"4111111111111111\", \"eciIndicator\":\"01\"}}";
    private static final String PAN = "4111111111111111";
    private static final String BRAND_VISA = "VISA";
    private static final String BRAND_MC = "MASTERCARD";
    private static final String NAME = "Jean JEAN";
    private static final String ECI = "01";
    private static final String DIRECT_MESSAGE = "{\n" +
            "  \"signature\": \"MEQCIGnR6FL55NLTTy9d/cR1YAMaISPZIz7q4vHyZab6Gb4gAiBZlS4aH0t+sLhZmN+MLpQFjST8KiQqQz9rgUc5LUNJSQ==\",\n" +
            "  \"protocolVersion\": \"ECv1\",\n" +
            "  \"signedMessage\": \"{\\\"encryptedMessage\\\":\\\"fc0hvWDNnk8niyeJYitv+Sfd/gddhytNelx1fYrW50heG1TbRFHQT8I0hKzgHr+iG02bozXy1FDpuBj1iuz0wMU+XU26JfST8GL/dE7AbC1eRZ3RnCz/7GT3qzOZPhb4A75xNqddn4sesKpe/zApKeC4p18xNqvuVXWmo5HRI9rX1U5bn2KuqR2B12Ow9CMJvGp+9IZ5HF5+Xy9GJsfS7td4dPEp0Yw/d0CkHsg/6WilhgDXT5kAlMrwyq4k89SLJWpWCxwPDapi3s1UKmhfh51JEKTNxio/ilAj8r7bRnGx5zy9d4BUFwlaPqL1ZcpsZxYvH3K2Re+4wAQ\\\\u003d\\\",\\\"ephemeralPublicKey\\\":\\\"BJPHmxA2O1Gsm9JML5p+ucYdsxUcYwB2bXcc6vtQxG35smMjnNbsv2R0HyJ64cSohrAiuSFFptjQP9Ec4lBrYlQ\\\\u003d\\\",\\\"tag\\\":\\\"TkZk2HvX4SPYqI7PB2oC+cuXBfyYIzx6K31JQrS8Gjs\\\\u003d\\\"}\"\n" +
            "}";

    @Test
    public void paymentRequestWithoutEciVISA() throws GeneralSecurityException {
        PaymentRequest request = Utils.createCompletePaymentBuilder().build();
        request.getPaymentFormContext().getPaymentFormParameter().put(PAYMENT_REQUEST_PAYMENT_DATA_KEY, GOOD_PAYMENT_DATA);

        doReturn(GOOD_RESPONSE_DATA).when(service).getDecryptedData(anyString(), anyString(), any(), anyBoolean());

        PaymentResponse response = service.paymentRequest(request);

        Assert.assertNotNull(response);
        Assert.assertEquals(PaymentResponseDoPayment.class, response.getClass());
        PaymentResponseDoPayment responseDoPayment = (PaymentResponseDoPayment) response;
        Assert.assertEquals(PaymentModeCard.class, responseDoPayment.getPaymentMode().getClass());
        PaymentModeCard modeCard = (PaymentModeCard) responseDoPayment.getPaymentMode();

        Assert.assertEquals(PAN, modeCard.getCard().getPan());
        Assert.assertEquals(BRAND_VISA, modeCard.getCard().getBrand());
        Assert.assertEquals(NAME, modeCard.getCard().getHolder());
        Assert.assertEquals("05", modeCard.getPaymentData3DS().getEci());
    }

    @Test
    public void paymentRequestWithoutEciMasterCard() throws GeneralSecurityException {
        PaymentRequest request = Utils.createCompletePaymentBuilder().build();
        request.getPaymentFormContext().getPaymentFormParameter().put(PAYMENT_REQUEST_PAYMENT_DATA_KEY, GOOD_PAYMENT_DATA_MC);

        doReturn(GOOD_RESPONSE_DATA).when(service).getDecryptedData(anyString(), anyString(), any(), anyBoolean());

        PaymentResponse response = service.paymentRequest(request);

        Assert.assertNotNull(response);
        Assert.assertEquals(PaymentResponseDoPayment.class, response.getClass());
        PaymentResponseDoPayment responseDoPayment = (PaymentResponseDoPayment) response;
        Assert.assertEquals(PaymentModeCard.class, responseDoPayment.getPaymentMode().getClass());
        PaymentModeCard modeCard = (PaymentModeCard) responseDoPayment.getPaymentMode();

        Assert.assertEquals(PAN, modeCard.getCard().getPan());
        Assert.assertEquals(BRAND_MC, modeCard.getCard().getBrand());
        Assert.assertEquals(NAME, modeCard.getCard().getHolder());
        Assert.assertEquals("02", modeCard.getPaymentData3DS().getEci());
    }

    @Test
    public void paymentRequestWithECI() throws GeneralSecurityException {
        PaymentRequest request = Utils.createCompletePaymentBuilder().build();
        request.getPaymentFormContext().getPaymentFormParameter().put(PAYMENT_REQUEST_PAYMENT_DATA_KEY, GOOD_PAYMENT_DATA);

        doReturn(GOOD_RESPONSE_DATA_WITH_ECI).when(service).getDecryptedData(anyString(), anyString(), any(), anyBoolean());

        PaymentResponse response = service.paymentRequest(request);

        Assert.assertNotNull(response);
        Assert.assertEquals(PaymentResponseDoPayment.class, response.getClass());
        PaymentResponseDoPayment responseDoPayment = (PaymentResponseDoPayment) response;
        Assert.assertEquals(PaymentModeCard.class, responseDoPayment.getPaymentMode().getClass());
        PaymentModeCard modeCard = (PaymentModeCard) responseDoPayment.getPaymentMode();

        Assert.assertEquals(PAN, modeCard.getCard().getPan());
        Assert.assertEquals(BRAND_VISA, modeCard.getCard().getBrand());
        Assert.assertEquals(NAME, modeCard.getCard().getHolder());
        Assert.assertEquals(ECI, modeCard.getPaymentData3DS().getEci());
    }


    @Test
    public void paymentRequestDirectMode() throws GeneralSecurityException {
        PaymentRequest request = Utils.createCompletePaymentBuilder().build();
        request.getPaymentFormContext().getPaymentFormParameter().put(PAYMENTDATA_TOKENDATA, DIRECT_MESSAGE);

        doReturn(GOOD_RESPONSE_DATA).when(service).getDecryptedData(anyString(), anyString(), any(), anyBoolean());

        PaymentResponse response = service.paymentRequest(request);

        Assert.assertNotNull(response);
        Assert.assertEquals(PaymentResponseDoPayment.class, response.getClass());
        PaymentResponseDoPayment responseDoPayment = (PaymentResponseDoPayment) response;
        Assert.assertEquals(PaymentModeCard.class, responseDoPayment.getPaymentMode().getClass());
        PaymentModeCard modeCard = (PaymentModeCard) responseDoPayment.getPaymentMode();

        Assert.assertEquals(PAN, modeCard.getCard().getPan());
    }


    @Test
    public void paymentRequestKO() throws GeneralSecurityException {
        PaymentRequest request = Utils.createCompletePaymentBuilder().build();
        request.getPaymentFormContext().getPaymentFormParameter().put(PAYMENT_REQUEST_PAYMENT_DATA_KEY, GOOD_PAYMENT_DATA);


        doThrow(new GeneralSecurityException()).when(service).getDecryptedData(anyString(), anyString(), any(), anyBoolean());
        PaymentResponse response = service.paymentRequest(request);

        Assert.assertNotNull(response);
        Assert.assertEquals(PaymentResponseFailure.class, response.getClass());
    }

    @Test
    public void paymentRequestNoBuyer() throws GeneralSecurityException {
        PaymentRequest request = Utils.createCompletePaymentBuilder().build();
        request.getPaymentFormContext().getPaymentFormParameter().put(PAYMENT_REQUEST_PAYMENT_DATA_KEY, LIGHT_PAYMENT_DATA);

        doReturn(GOOD_RESPONSE_DATA).when(service).getDecryptedData(anyString(), anyString(), any(), anyBoolean());

        PaymentResponse response = service.paymentRequest(request);

        Assert.assertNotNull(response);
        Assert.assertEquals(PaymentResponseDoPayment.class, response.getClass());
        PaymentResponseDoPayment responseDoPayment = (PaymentResponseDoPayment) response;
        Assert.assertEquals(PaymentModeCard.class, responseDoPayment.getPaymentMode().getClass());
        PaymentModeCard modeCard = (PaymentModeCard) responseDoPayment.getPaymentMode();

        Assert.assertEquals(PAN, modeCard.getCard().getPan());
        Assert.assertEquals(BRAND_VISA, modeCard.getCard().getBrand());
        Assert.assertEquals("", modeCard.getCard().getHolder());
    }

    @Test
    public void testGetDecryptedDataWithInvalidKey()  {
        String privateKey = "cHJpdmF0ZUtleQ==";
        String oldPrivateKey = "b2xkUHJpdmF0ZUtleQ==";
        assertThrows(IllegalArgumentException.class, () -> service.getDecryptedData("myToken", privateKey, oldPrivateKey, true));
    }

    @Test
    public void testAddPrivateKeyWithGeneralSecurityException() {
        final PaymentMethodTokenRecipient.Builder builder = new PaymentMethodTokenRecipient.Builder();
        assertEquals(builder, service.addPrivateKey(builder, "oldKey", "invalidKey"));
    }

    @Test
    public void testAddPrivateKeyWithNullKey() {
        final PaymentMethodTokenRecipient.Builder builder = new PaymentMethodTokenRecipient.Builder();
        assertEquals(builder, service.addPrivateKey(builder, "oldKey", null));
    }

}
