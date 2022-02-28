package com.payline.payment.google.pay.service.impl;

import com.payline.payment.google.pay.exception.PluginException;
import com.payline.payment.google.pay.utils.GooglePayUtils;
import com.payline.payment.google.pay.utils.InvalidDataException;
import com.payline.pmapi.bean.common.FailureCause;
import com.payline.pmapi.bean.payment.ContractConfiguration;
import com.payline.pmapi.bean.payment.ContractProperty;
import com.payline.pmapi.bean.paymentform.bean.PaymentFormLogo;
import com.payline.pmapi.bean.paymentform.bean.form.PartnerWidgetForm;
import com.payline.pmapi.bean.paymentform.request.PaymentFormConfigurationRequest;
import com.payline.pmapi.bean.paymentform.request.PaymentFormLogoRequest;
import com.payline.pmapi.bean.paymentform.response.configuration.PaymentFormConfigurationResponse;
import com.payline.pmapi.bean.paymentform.response.configuration.impl.PaymentFormConfigurationResponseFailure;
import com.payline.pmapi.bean.paymentform.response.configuration.impl.PaymentFormConfigurationResponseSpecific;
import com.payline.pmapi.bean.paymentform.response.logo.PaymentFormLogoResponse;
import com.payline.pmapi.bean.paymentform.response.logo.impl.PaymentFormLogoResponseFile;
import com.payline.pmapi.service.PaymentFormConfigurationService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import static com.payline.payment.google.pay.utils.GooglePayConstants.*;
import static com.payline.payment.google.pay.utils.Utils.*;
import static com.payline.payment.google.pay.utils.Utils.createDefaultPaymentFormConfigurationRequest;
import static com.payline.payment.google.pay.utils.constants.LogoConstants.LOGO_FILE_NAME;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PaymentFormConfigurationServiceImplTest {

    @Spy
    @InjectMocks
    private PaymentFormConfigurationServiceImpl service = new PaymentFormConfigurationServiceImpl();

    @Mock
    Properties properties;

    @Test
    public void testGetPaymentFormConfiguration() throws IOException {
        PaymentFormConfigurationResponse response = service.getPaymentFormConfiguration(createDefaultPaymentFormConfigurationRequest());
        Assert.assertTrue(response instanceof PaymentFormConfigurationResponseSpecific);
        PaymentFormConfigurationResponseSpecific responseSpecific = (PaymentFormConfigurationResponseSpecific) response;

        PartnerWidgetForm widgetForm = (PartnerWidgetForm) responseSpecific.getPaymentForm();

        // check good creation of script before import
        String script = widgetForm.getLoadingScriptBeforeImport();
        Assert.assertFalse(script.contains(JS_PARAM_TAG_PAYMENTMETHOD_TYPE));
        Assert.assertFalse(script.contains(JS_PARAM_TAG_EMAIL_REQUIRED));
        Assert.assertFalse(script.contains(JS_PARAM_TAG_SHIPPING_ADDRESS_REQUIRED));
        Assert.assertFalse(script.contains(JS_PARAM_TAG_BILLING_ADDRESS_REQUIRED));
        Assert.assertFalse(script.contains(JS_PARAM_TAG_SHIPPING_PHONE));
        Assert.assertFalse(script.contains(JS_PARAM_TAG_BILLING_ADDRESS_FORMAT));
        Assert.assertFalse(script.contains(JS_PARAM_TAG_BILLING_PHONE));
        Assert.assertFalse(script.contains(JS_PARAM_TAG_ALLOWED_CARD_NETWORKS));
        Assert.assertFalse(script.contains(JS_PARAM_TAG_ALLOWED_AUTH_METHODS));
        Assert.assertFalse(script.contains(JS_PARAM_TAG_TYPE));
        Assert.assertFalse(script.contains(JS_PARAM_TAG_GATEWAY));
        Assert.assertFalse(script.contains(JS_PARAM_TAG_GATEWAY_MERCHANT_ID));
        Assert.assertFalse(script.contains(JS_PARAM_TAG_MERCHANT_ID));
        Assert.assertFalse(script.contains(JS_PARAM_TAG_MERCHANT_NAME));
        Assert.assertFalse(script.contains(JS_PARAM_TAG_ENVIRONMENT));
        Assert.assertFalse(script.contains(JS_PARAM_TAG_CURRENCY));
        Assert.assertFalse(script.contains(JS_PARAM_TAG_PRICE));
        Assert.assertFalse(script.contains(JS_PARAM_TAG_BTN_TYPE));
        Assert.assertFalse(script.contains(JS_PARAM_TAG_BTN_COLOR));
        Assert.assertFalse(script.contains(JS_PARAM_TAG_CONTAINER));
        Assert.assertFalse(script.contains(JS_PARAM_TAG_CALLBACK));

        InputStream stream = PaymentFormConfigurationServiceImpl.class.getClassLoader().getResourceAsStream("init.js");
        String expectedJs = GooglePayUtils.convertInputStreamToString(stream);

        Assert.assertEquals(expectedJs, script);
    }

    @Test
    public void testGetPaymentFormConfigurationShouldThrowInvalidDataException() throws InvalidDataException {
        PaymentFormConfigurationResponseFailure failPaymentFormConfigurationRequest =PaymentFormConfigurationResponseFailure.PaymentFormConfigurationResponseFailureBuilder
                .aPaymentFormConfigurationResponseFailure()
                .withErrorCode("test InvalidDataException")
                .withFailureCause(FailureCause.INVALID_DATA)
                .withPartnerTransactionId(FAILURE_TRANSACTION_ID)
                .build();

        PaymentFormConfigurationRequest paymentFormConfigurationRequest =createDefaultPaymentFormConfigurationRequest();
        final Map<String, ContractProperty> properties = paymentFormConfigurationRequest.getContractConfiguration().getContractProperties();

        doThrow(new InvalidDataException("test InvalidDataException")).when(service).getValueFromProperties(properties ,PAYMENT_METHOD_TYPE_KEY);
        PaymentFormConfigurationResponseFailure resultPaymentFormConfigurationRequest =(PaymentFormConfigurationResponseFailure) service.getPaymentFormConfiguration(paymentFormConfigurationRequest);

        Assert.assertEquals(failPaymentFormConfigurationRequest.getFailureCause(),resultPaymentFormConfigurationRequest.getFailureCause());
        Assert.assertEquals(failPaymentFormConfigurationRequest.getErrorCode(),resultPaymentFormConfigurationRequest.getErrorCode());
        Assert.assertEquals(failPaymentFormConfigurationRequest.getPartnerTransactionId(),resultPaymentFormConfigurationRequest.getPartnerTransactionId());

    }

    @Test
    public void testGetPaymentFormLogo() throws IOException {
        // given: the logo image read from resources
        InputStream input = PaymentFormConfigurationServiceImpl.class.getClassLoader().getResourceAsStream("google-pay-logo.png");
        BufferedImage image = ImageIO.read(input);

        // when: getPaymentFormLogo is called
        PaymentFormLogoRequest request = PaymentFormLogoRequest.PaymentFormLogoRequestBuilder.aPaymentFormLogoRequest()
                .withLocale(Locale.getDefault())
                .withEnvironment(createDefaultPaylineEnvironment())
                .withContractConfiguration(createContractConfiguration())
                .withPartnerConfiguration(createDefaultPartnerConfiguration())
                .build();
        PaymentFormLogoResponse paymentFormLogoResponse = service.getPaymentFormLogo(request);

        // then: returned elements match the image file data
        Assert.assertTrue(paymentFormLogoResponse instanceof PaymentFormLogoResponseFile);
        PaymentFormLogoResponseFile casted = (PaymentFormLogoResponseFile) paymentFormLogoResponse;
        Assert.assertEquals(image.getHeight(), casted.getHeight());
        Assert.assertEquals(image.getWidth(), casted.getWidth());
        Assert.assertEquals(casted.getTitle(),"Google Pay");
        Assert.assertEquals(casted.getAlt(),"Google Pay logo");
    }

    @Test
    public void testGetLogo() {
        // when: getLogo is called
        PaymentFormLogo paymentFormLogo = service.getLogo("", Locale.getDefault());

        // then: returned elements are not null
        Assert.assertNotNull(paymentFormLogo);
        Assert.assertNotNull(paymentFormLogo.getFile());
        Assert.assertNotNull(paymentFormLogo.getContentType());
    }

    @Test
    public void testGetLogoShouldThrowException() throws IOException {
        Properties props =new Properties();
        doThrow(IOException.class).when(service).getProprities(props);
        Assert.assertThrows(PluginException.class, () -> service.getLogo("", Locale.getDefault()));
    }

    @Test
    public void testGetLogoShouldThrowIOException() throws IOException {
        Properties props = new Properties();
        final String fileName = "fileName";
        doReturn(properties).when(service).getProprities(props);
        doReturn(fileName).when(properties).getProperty(LOGO_FILE_NAME);
        doThrow(IOException.class).when(service).getBufferedImage(fileName);
        Assert.assertThrows(PluginException.class, () -> service.getLogo("", Locale.getDefault()));
    }

    @Test
    public void getBufferedImageOK() throws IOException {
        Assert.assertNotNull(service.getBufferedImage("google-pay-logo.png"));
    }

    @Test
    public void testGetPaymentFormLogoShouldThrowException() throws IOException {
        PaymentFormLogoRequest request = PaymentFormLogoRequest.PaymentFormLogoRequestBuilder.aPaymentFormLogoRequest()
                .withLocale(Locale.getDefault())
                .withEnvironment(createDefaultPaylineEnvironment())
                .withContractConfiguration(createContractConfiguration())
                .withPartnerConfiguration(createDefaultPartnerConfiguration())
                .build();
        Properties props =new Properties();
        doThrow(IOException.class).when(service).getProprities(props);
        Assert.assertThrows(PluginException.class, () -> service.getPaymentFormLogo(request));

    }
    @Test
    public void getPropritiesShouldThrowException() throws IOException {
        Properties props =new Properties();
        doThrow(IOException.class).when(service).getProprities(props);
        Assert.assertThrows(IOException.class, () -> service.getProprities(props));
    }

    @Test
    public void getPropritiesOK() throws IOException {
        Properties props =new Properties();
        Assert.assertEquals(props, service.getProprities(props));
    }

    @Test
    public void testGetAllowedCards() {
        String expectedAllowedCard = "[\"MASTERCARD\", \"AMEX\", \"VISA\"]";
        ContractConfiguration configuration = createContractConfiguration();
        String allowedCard = service.getAllowedCards(configuration.getContractProperties());
        Assert.assertEquals(expectedAllowedCard, allowedCard);
    }

    @Test
    public void testGetAllowedAuthMethodAll() {
        String expectedAllowedCard = "[\"PAN_ONLY\", \"CRYPTOGRAM_3DS\"]";
        ContractConfiguration configuration = createContractConfiguration();
        String allowedAuthMethod = service.getAllowedAuthMethod(configuration.getContractProperties());
        Assert.assertEquals(expectedAllowedCard, allowedAuthMethod);
    }

    @Test
    public void testGetAllowedAuthMethodPANONLY() {
        String expectedAllowedCard = "[\"PAN_ONLY\"]";
        ContractConfiguration configuration = createContractConfiguration();
        configuration.getContractProperties().replace(ALLOWED_AUTH_METHOD_KEY, new ContractProperty(METHOD_PANONLY_KEY));
        String allowedAuthMethod = service.getAllowedAuthMethod(configuration.getContractProperties());
        Assert.assertEquals(expectedAllowedCard, allowedAuthMethod);
    }

    @Test
    public void testGetAllowedAuthMethod3DS() {
        String expectedAllowedCard = "[\"CRYPTOGRAM_3DS\"]";
        ContractConfiguration configuration = createContractConfiguration();
        configuration.getContractProperties().replace(ALLOWED_AUTH_METHOD_KEY, new ContractProperty(METHOD_3DS_KEY));
        String allowedAuthMethod = service.getAllowedAuthMethod(configuration.getContractProperties());
        Assert.assertEquals(expectedAllowedCard, allowedAuthMethod);
    }

    @Test
    public void getAllowedCountry() {
        String expected = "allowedCountryCodes: ['FR'],";
        Assert.assertEquals(expected, service.getAllowedCountry("FR"));
        Assert.assertEquals(expected, service.getAllowedCountry("fr"));
        Assert.assertEquals("", service.getAllowedCountry(""));
        Assert.assertEquals("", service.getAllowedCountry(null));
    }

    @Test
    public void getBoolean() {
        Assert.assertEquals("true", service.getBoolean("YES"));
        Assert.assertEquals("true", service.getBoolean("yes"));
        Assert.assertEquals("false", service.getBoolean(""));
        Assert.assertEquals("false", service.getBoolean("NO"));
        Assert.assertEquals("false", service.getBoolean("no"));
        Assert.assertEquals("false", service.getBoolean(null));
    }
}