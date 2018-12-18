package com.payline.payment.google.pay.utils;

public class GooglePayConstants {

    public static final String PRIVATE_KEY = "MIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQgSaq46Z66YlV9Gp/B0WfRB7b4deHKeSE/kSDPI7+5Dw2hRANCAAQD5EWZSKBoQTlspL9hpHFfBvGUhSubJC/dF0uTPKTTwuo2fb+t/kUY2ZJJyuJWI4b9qqLBVxmye359mZAfQNCY";


    public static final String RESOURCE_BUNDLE_BASE_NAME = "messages";

    public static final String I18N_SERVICE_DEFAULT_LOCALE = "en";

    // data used is js file
    public static final String JS_PARAM_TAG_TYPE = "${type}";
    public static final String JS_PARAM_TAG_GATEWAY = "${gateway}";
    public static final String JS_PARAM_TAG_GATEWAY_MERCHANT_ID = "${gatewayMerchantId}";
    public static final String JS_PARAM_TAG_CONTAINER = "${container}";
    public static final String JS_PARAM_TAG_CALLBACK = "${callback}";
    public static final String JS_PARAM_TAG_MERCHANT_ID = "${merchantId}";
    public static final String JS_PARAM_TAG_MERCHANT_NAME = "${merchantName}";
    public static final String JS_PARAM_TAG_ENVIRONMENT = "${environement}";
    public static final String JS_PARAM_TAG_CURRENCY = "${currency}";
    public static final String JS_PARAM_TAG_PRICE = "${totalPrice}";
    public static final String JS_PARAM_TAG_BTN_COLOR = "${buttonColor}";
    public static final String JS_PARAM_TAG_BTN_TYPE = "${buttonType}";

    // data used in PaymentFormConfigurationServiceImpl
    public static final String TEST = "TEST";
    public static final String PRODUCTION = "PRODUCTION";
    public static final String JS_PARAM_VALUE_TYPE = "PAYMENT_GATEWAY";
    public static final String JS_PARAM_VALUE_GATEWAY_NAME = "monext";
    public static final String JS_PARAM_VALUE_CONTAINER = "paylineGooglePayBtnDivContainer";
    public static final String JS_PARAM_VALUE_CALLBACK = "paylineProcessPaymentCallback";

    public static final String JS_URL_GOOGLE_PAY = "https://pay.google.com/gp/p/js/pay.js";
    public static final String JS_RES_INIT_PAYMENT = "init_payment.js";

    public static final String JS_START_METHOD_NAME = "onGooglePayLoaded()";

    public static final String PAYMENT_REQUEST_PAYMENT_DATA_KEY = "data";

    // data used in ConfigurationService
    public static final String MERCHANT_NAME_KEY = "merchantName";
    public static final String MERCHANT_NAME_LABEL = "merchantName.label";
    public static final String MERCHANT_NAME_DESCRIPTION = "merchantName.description";
    public static final String MERCHANT_ID_KEY = "merchantId";
    public static final String MERCHANT_ID_LABEL = "merchantId.label";
    public static final String MERCHANT_ID_DESCRIPTION = "merchantId.description";

    public static final String BUTTON_COLOR_KEY = "buttonColor";
    public static final String BUTTON_COLOR_LABEL = "button.color.label";
    public static final String COLOR_DEFAULT_KEY = "DEFAULT";
    public static final String COLOR_DEFAULT_VAL = "color.default";
    public static final String COLOR_BLACK_KEY = "BLACK";
    public static final String COLOR_BLACK_VAL = "color.black";
    public static final String COLOR_WHITE_KEY = "WHITE";
    public static final String COLOR_WHITE_VAL = "color.white";

    public static final String BUTTON_SIZE_KEY = "buttonSize";
    public static final String BUTTON_SIZE_LABEL = "button.size.label";
    public static final String SIZE_SHORT_KEY = "SHORT";
    public static final String SIZE_SHORT_VAL = "size.short.label";
    public static final String SIZE_LONG_KEY = "button.size.label";
    public static final String SIZE_LONG_VAL = "button.size.label";

    public static final String GATEWAY_MERCHANT_ID_KEY = "GatewayMerchantId";
    public static final String GATEWAY_MERCHANT_ID_LABEL = "GatewayMerchantId.label";
    public static final String GATEWAY_MERCHANT_ID_DESCRIPTION = "GatewayMerchantId.descrption";

    public static final String YES_KEY = "YES";
    public static final String NO_KEY = "NO";
    public static final String YES_VAL = "yes";
    public static final String NO_VAL = "no";
    public static final String ACTIVATE_NETWORK_CB_KEY = "activateNeworkCB";
    public static final String ACTIVATE_NETWORK_CB_LABEL = "network.activate.cb";
    public static final String ACTIVATE_NETWORK_VISA_KEY = "activateNeworkVisa";
    public static final String ACTIVATE_NETWORK_VISA_LABEL = "network.activate.visa";
    public static final String ACTIVATE_NETWORK_MASTERCARD_KEY = "activateNeworkMastercard";
    public static final String ACTIVATE_NETWORK_MASTERCARD_LABEL = "network.activate.mastercard";
    public static final String ACTIVATE_NETWORK_AMEX_KEY = "activateNeworkAmex";
    public static final String ACTIVATE_NETWORK_AMEX_LABEL = "network.activate.amex";
    public static final String ACTIVATE_NETWORK_JCB_KEY = "activateNeworkJCBB";
    public static final String ACTIVATE_NETWORK_JCB_LABEL = "network.activate.jcb";
    public static final String ACTIVATE_NETWORK_DISCOVER_KEY = "activateNeworkDiscover";
    public static final String ACTIVATE_NETWORK_DISCOVER_LABEL = "network.activate.discover";


    public static final String NETWORK_CB_LABEL = "network.credit.card.label";
    public static final String NETWORK_CB_DESCRIPTION = "network.credit.card.description";
    public static final String NETWORK_VISA_LABEL = "network.visa.label";
    public static final String NETWORK_VISA_DESCRIPTION = "network.visa.description";
    public static final String NETWORK_MASTERCARD_LABEL = "network.mastercard.label";
    public static final String NETWORK_MASTERCARD_DESCRIPTION = "network.mastercard.description";
    public static final String NETWORK_AMEX_LABEL = "network.amex.label";
    public static final String NETWORK_AMEX_DESCRIPTION = "network.amex.description";
    public static final String NETWORK_JCB_LABEL = "network.jcb.label";
    public static final String NETWORK_JCB_DESCRIPTION = "network.jcb.description";
    public static final String NETWORK_DISCOVER_LABEL = "network.discover.label";
    public static final String NETWORK_DISCOVER_DESCRIPTION = "network.discover.description";

}