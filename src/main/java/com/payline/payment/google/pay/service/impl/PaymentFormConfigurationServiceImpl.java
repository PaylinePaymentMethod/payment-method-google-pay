package com.payline.payment.google.pay.service.impl;

import com.payline.payment.google.pay.exception.PluginException;
import com.payline.payment.google.pay.utils.GooglePayUtils;
import com.payline.payment.google.pay.utils.InvalidDataException;
import com.payline.payment.google.pay.utils.i18n.I18nService;
import com.payline.pmapi.bean.common.FailureCause;
import com.payline.pmapi.bean.payment.ContractProperty;
import com.payline.pmapi.bean.paymentform.bean.PaymentFormLogo;
import com.payline.pmapi.bean.paymentform.bean.form.PartnerWidgetForm;
import com.payline.pmapi.bean.paymentform.bean.form.partnerwidget.PartnerWidgetContainerTargetDivId;
import com.payline.pmapi.bean.paymentform.bean.form.partnerwidget.PartnerWidgetOnPayCallBack;
import com.payline.pmapi.bean.paymentform.bean.form.partnerwidget.PartnerWidgetScriptImport;
import com.payline.pmapi.bean.paymentform.request.PaymentFormConfigurationRequest;
import com.payline.pmapi.bean.paymentform.request.PaymentFormLogoRequest;
import com.payline.pmapi.bean.paymentform.response.configuration.PaymentFormConfigurationResponse;
import com.payline.pmapi.bean.paymentform.response.configuration.impl.PaymentFormConfigurationResponseFailure;
import com.payline.pmapi.bean.paymentform.response.configuration.impl.PaymentFormConfigurationResponseSpecific;
import com.payline.pmapi.bean.paymentform.response.logo.PaymentFormLogoResponse;
import com.payline.pmapi.bean.paymentform.response.logo.impl.PaymentFormLogoResponseFile;
import com.payline.pmapi.logger.LogManager;
import com.payline.pmapi.service.PaymentFormConfigurationService;
import org.apache.logging.log4j.Logger;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;


import static com.payline.payment.google.pay.utils.GooglePayConstants.*;
import static com.payline.payment.google.pay.utils.constants.LogoConstants.*;

public class PaymentFormConfigurationServiceImpl implements PaymentFormConfigurationService {

    private static final Logger LOGGER = LogManager.getLogger(PaymentFormConfigurationServiceImpl.class);

    @Override
    public PaymentFormConfigurationResponse getPaymentFormConfiguration(PaymentFormConfigurationRequest paymentFormConfigurationRequest) {

        try {
            URL googlePayScriptUrl = this.getGooglePayScriptUrl();
            String scriptInitPaymentContent = this.getInitPaymentJavaScript(paymentFormConfigurationRequest);

            // Define the JS script to load
            PartnerWidgetScriptImport partnerWidgetScriptImport = PartnerWidgetScriptImport
                    .WidgetPartnerScriptImportBuilder
                    .aWidgetPartnerScriptImport()
                    .withUrl(googlePayScriptUrl)
                    .build();

            // Define the Payline callback
            PartnerWidgetOnPayCallBack partnerWidgetOnPayCallBack = PartnerWidgetOnPayCallBack
                    .WidgetContainerOnPayCallBackBuilder
                    .aWidgetContainerOnPayCallBack()
                    .withName(JS_PARAM_VALUE_CALLBACK)
                    .build();

            // Define the GooglePay button target div id
            PartnerWidgetContainerTargetDivId partnerWidgetContainerTargetDivId = PartnerWidgetContainerTargetDivId
                    .WidgetPartnerContainerTargetDivIdBuilder
                    .aWidgetPartnerContainerTargetDivId()
                    .withId(JS_PARAM_VALUE_CONTAINER)
                    .build();

            PartnerWidgetForm partnerWidgetForm = PartnerWidgetForm
                    .WidgetPartnerFormBuilder
                    .aWidgetPartnerForm()
                    // Le script JS de pilotage de paiement a pre-charger sous forme de String
                    .withLoadingScriptBeforeImport(scriptInitPaymentContent)
                    // Le script JS GooglePay a charger depuis une URL
                    .withScriptImport(partnerWidgetScriptImport)
                    // Apres chargement du script JS GooglePay, la methode a appeler sur le script de pilotage pre-charge
                    .withLoadingScriptAfterImport(JS_START_METHOD_NAME)
                    // L'id de div dans laquelle sera charge et affiche le bouton GooglePay
                    .withContainer(partnerWidgetContainerTargetDivId)
                    // La callback de retour Payline pour proceder a la payment request qui devra suivre
                    .withOnPay(partnerWidgetOnPayCallBack)
                    .withDescription(I18nService.getInstance().getMessage(BUTTON_DESCRIPTION, paymentFormConfigurationRequest.getLocale()))
                    .build();

            return PaymentFormConfigurationResponseSpecific
                    .PaymentFormConfigurationResponseSpecificBuilder
                    .aPaymentFormConfigurationResponseSpecific()
                    .withPaymentForm(partnerWidgetForm)
                    .build();
        } catch (IOException e) {
            LOGGER.error("An error occured when trying to load the js script", e);

            return PaymentFormConfigurationResponseFailure.PaymentFormConfigurationResponseFailureBuilder
                    .aPaymentFormConfigurationResponseFailure()
                    .withPartnerTransactionId(FAILURE_TRANSACTION_ID)
                    .withErrorCode("Unable to read js file")
                    .withFailureCause(FailureCause.INTERNAL_ERROR)
                    .build();
        } catch (InvalidDataException e) {
            return PaymentFormConfigurationResponseFailure.PaymentFormConfigurationResponseFailureBuilder
                    .aPaymentFormConfigurationResponseFailure()
                    .withPartnerTransactionId(FAILURE_TRANSACTION_ID)
                    .withErrorCode(e.getMessage())
                    .withFailureCause(e.getFailureCause())
                    .build();
        }

    }

    /**
     * @return
     */
    private URL getGooglePayScriptUrl() {

        URL url = null;

        try {

            url = new URL(JS_URL_GOOGLE_PAY);

        } catch (MalformedURLException e) {
            LOGGER.error(e.getMessage());
        }

        return url;

    }

    @Override
    public PaymentFormLogoResponse getPaymentFormLogo(PaymentFormLogoRequest paymentFormLogoRequest) {
        Properties props = new Properties();
        try {
            props = getProprities(props);
            return PaymentFormLogoResponseFile.PaymentFormLogoResponseFileBuilder.aPaymentFormLogoResponseFile()
                    .withHeight(Integer.valueOf(props.getProperty(LOGO_HEIGHT)))
                    .withWidth(Integer.valueOf(props.getProperty(LOGO_WIDTH)))
                    .withTitle(I18nService.getInstance().getMessage(props.getProperty(LOGO_TITLE), paymentFormLogoRequest.getLocale()))
                    .withAlt(I18nService.getInstance().getMessage(props.getProperty(LOGO_ALT), paymentFormLogoRequest.getLocale()))
                    .build();
        } catch (IOException e) {
            LOGGER.error("An error occurred reading the file logo.properties", e);
            throw new PluginException("Failed to reading file logo.properties: ", e);

        }
    }

    protected Properties getProprities(Properties props) throws IOException {
        props.load(ConfigurationServiceImpl.class.getClassLoader().getResourceAsStream(LOGO_PROPERTIES));
        return props;
    }

    @Override
    public PaymentFormLogo getLogo(String s, Locale locale) {
        Properties props = new Properties();
        try {
             props =getProprities(props);
        } catch (IOException e) {
            LOGGER.error("An error occurred reading the file logo.properties", e);
            throw new PluginException("An error occurred reading the file logo.properties: ", e);

        }
        final String fileName = props.getProperty(LOGO_FILE_NAME);
        try {
            // Read logo file
            final BufferedImage logo = getBufferedImage(fileName);

            // Recover byte array from image
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(logo, props.getProperty(LOGO_FORMAT), baos);

            return PaymentFormLogo.PaymentFormLogoBuilder.aPaymentFormLogo()
                    .withFile(baos.toByteArray())
                    .withContentType(props.getProperty(LOGO_CONTENT_TYPE))
                    .build();
        } catch (IOException e) {
            LOGGER.error("Unable to load the logo", e);
            throw new PluginException("Unable to load the logo ", e);
        }
    }

    protected BufferedImage getBufferedImage(String fileName) throws IOException {
        final InputStream input = PaymentFormConfigurationService.class.getClassLoader().getResourceAsStream(fileName);
        return ImageIO.read(input);
    }

    /**
     * @return
     */
    private String getInitPaymentJavaScript(PaymentFormConfigurationRequest paymentFormConfigurationRequest) throws IOException, InvalidDataException {

        // get info to put in .js
        if (paymentFormConfigurationRequest.getContractConfiguration() == null){
            throw new InvalidDataException("ContractConfiguration object can't be null");
        }
        final Map<String, ContractProperty> properties = paymentFormConfigurationRequest.getContractConfiguration().getContractProperties();

        final String paymentMethodType = getValueFromProperties(properties, PAYMENT_METHOD_TYPE_KEY);
        final String emailRequired = getValueFromProperties(properties, EMAIL_REQUIRED_KEY);
        final String shippingAddressRequired = getValueFromProperties(properties, SHIPPING_ADDRESS_REQUIRED_KEY);
        final String allowedCountry = getValueFromProperties(properties, ALLOWED_COUNTRY_KEY);
        final String shippingPhoneRequired = getValueFromProperties(properties, SHIPPING_PHONE_REQUIRED_KEY);
        final String billingAddressRequired = getValueFromProperties(properties, BILLING_ADDRESS_REQUIRED_KEY);
        final String billingAddressFormat = getValueFromProperties(properties, BILLING_ADDRESS_FORMAT_KEY);
        final String billingPhoneRequired = getValueFromProperties(properties, BILLING_PHONE_REQUIRED_KEY);
        final String merchantId = getValueFromProperties(properties, MERCHANT_ID_KEY);
        final String merchantName = getValueFromProperties(properties, MERCHANT_NAME_KEY);
        final String environment = paymentFormConfigurationRequest.getEnvironment().isSandbox() ? TEST : PRODUCTION;
        final String currency = paymentFormConfigurationRequest.getAmount().getCurrency().getCurrencyCode();
        final String price = GooglePayUtils.createStringAmount(paymentFormConfigurationRequest.getAmount().getAmountInSmallestUnit());
        // the buttonType variable represents the size of the googlePay button
        final String buttonType = getValueFromProperties(properties, BUTTON_SIZE_KEY);
        final String buttonColor = getValueFromProperties(properties, BUTTON_COLOR_KEY);
        // verify fields
        if (!GooglePayUtils.isEmpty(allowedCountry) && !GooglePayUtils.isISO3166(allowedCountry)) {
            throw new InvalidDataException("allowed Country must be ISO3166 alpha2");
        }

        // get the .js file
        InputStream stream = PaymentFormConfigurationServiceImpl.class.getClassLoader().getResourceAsStream(JS_RES_INIT_PAYMENT);
        String rawScriptInitPaymentContent = GooglePayUtils.convertInputStreamToString(stream);

        return rawScriptInitPaymentContent
                .replace(JS_PARAM_TAG_PAYMENTMETHOD_TYPE, paymentMethodType)
                .replace(JS_PARAM_TAG_EMAIL_REQUIRED, getBoolean(emailRequired))
                .replace(JS_PARAM_TAG_SHIPPING_ADDRESS_REQUIRED, getBoolean(shippingAddressRequired))
                .replace(JS_PARAM_TAG_ALLOWED_COUNTRYCODE, getAllowedCountry(allowedCountry))
                .replace(JS_PARAM_TAG_SHIPPING_PHONE, getBoolean(shippingPhoneRequired))
                .replace(JS_PARAM_TAG_BILLING_ADDRESS_REQUIRED, getBoolean(billingAddressRequired))
                .replace(JS_PARAM_TAG_BILLING_ADDRESS_FORMAT, billingAddressFormat)
                .replace(JS_PARAM_TAG_BILLING_PHONE, getBoolean(billingPhoneRequired))

                .replace(JS_PARAM_TAG_ALLOWED_CARD_NETWORKS, getAllowedCards(properties))
                .replace(JS_PARAM_TAG_ALLOWED_AUTH_METHODS, getAllowedAuthMethod(properties))
                .replace(JS_PARAM_TAG_TYPE, JS_PARAM_VALUE_TYPE)
                .replace(JS_PARAM_TAG_GATEWAY, JS_PARAM_VALUE_GATEWAY_NAME)
                .replace(JS_PARAM_TAG_GATEWAY_MERCHANT_ID, merchantId)

                .replace(JS_PARAM_TAG_MERCHANT_ID, merchantId)
                .replace(JS_PARAM_TAG_MERCHANT_NAME, merchantName)
                .replace(JS_PARAM_TAG_ENVIRONMENT, environment)
                .replace(JS_PARAM_TAG_CURRENCY, currency)
                .replace(JS_PARAM_TAG_PRICE, price)

                .replace(JS_PARAM_TAG_BTN_TYPE, buttonType.toLowerCase())
                .replace(JS_PARAM_TAG_BTN_COLOR, buttonColor.toLowerCase())

                .replace(JS_PARAM_TAG_CONTAINER, JS_PARAM_VALUE_CONTAINER)
                .replace(JS_PARAM_TAG_CALLBACK, JS_PARAM_VALUE_CALLBACK);
    }

    String getValueFromProperties(Map<String, ContractProperty> properties, String key) throws InvalidDataException {
        if (properties == null) {
            throw new InvalidDataException("property Map can't be null");
        }
        if (properties.get(key) == null || properties.get(key).getValue() == null) {
            throw new InvalidDataException("property " + key + "must be present");
        }
        return properties.get(key).getValue();
    }

    String getAllowedCards(Map<String, ContractProperty> contractPropertyMap) {
        List<String> allowedCard = new ArrayList<>();

        // check every contract properties
        for (Map.Entry<String, ContractProperty> entry : contractPropertyMap.entrySet()) {
            // check if key contains "activateNetwork" and the value is "TRUE"
            String key = entry.getKey();
            if (key.contains(ACTIVATE_NETWORK_REGEX) && YES_KEY.equals(contractPropertyMap.get(key).getValue())) {
                allowedCard.add("\"" + key.split(ACTIVATE_NETWORK_REGEX)[1] + "\"");
            }
        }

        String s = "[" + String.join(", ", allowedCard) + "]";
        LOGGER.info("Allowed card list : {}", s);
        return s;
    }

    String getAllowedAuthMethod(Map<String, ContractProperty> contractPropertyMap) {
        StringBuilder sb = new StringBuilder("[");

        String allowedAuthMethod = contractPropertyMap.get(ALLOWED_AUTH_METHOD_KEY).getValue();
        switch (allowedAuthMethod) {
            case METHOD_PANONLY_KEY:
                LOGGER.info("Payment initiated with allowed method : PAN only");
                sb.append("\"").append(JS_PARAM_VALUE_PANONLY).append("\"");
                break;
            case METHOD_3DS_KEY:
                LOGGER.info("Payment initiated with allowed method : 3D secure");
                sb.append("\"").append(JS_PARAM_VALUE_3DS).append("\"");
                break;
            case METHOD_BOTH_KEY:
                LOGGER.info("Payment initiated with allowed method : PAN only and 3D secure");
                sb.append("\"").append(JS_PARAM_VALUE_PANONLY).append("\"").append(", ")
                        .append("\"").append(JS_PARAM_VALUE_3DS).append("\"");
                break;
            default:
                LOGGER.warn("Payment initiated without allowed method");
                break;
        }
        sb.append("]");
        return sb.toString();
    }

    String getAllowedCountry(String country) {
        if (GooglePayUtils.isEmpty(country)) {
            return "";
        } else {
            return "allowedCountryCodes: ['" + country.toUpperCase() + "'],";
        }
    }

    String getBoolean(String yesOrNo) {
        if (YES_KEY.equalsIgnoreCase(yesOrNo)) {
            return "true";
        } else {
            return "false";
        }
    }
}