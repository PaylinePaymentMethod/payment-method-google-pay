package com.payline.payment.google.pay.utils;

public class GooglePayConstants {

    public static final String PRIVATE_KEY = "MIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQgSaq46Z66YlV9Gp/B0WfRB7b4deHKeSE/kSDPI7+5Dw2hRANCAAQD5EWZSKBoQTlspL9hpHFfBvGUhSubJC/dF0uTPKTTwuo2fb+t/kUY2ZJJyuJWI4b9qqLBVxmye359mZAfQNCY";

    public static final String PAYMENT_METHOD_NAME = "paymentMethod.name";

    public static final String RELEASE_DATE_FORMAT = "dd/MM/yyyy";
    public static final String RELEASE_DATE = "release.date";
    public static final String RELEASE_VERSION = "release.version";
    public static final String RELEASE_PROPERTIES = "release.properties";

    public static final String RESOURCE_BUNDLE_BASE_NAME = "messages";

    public static final String I18N_SERVICE_DEFAULT_LOCALE = "en";

    public static final String JS_PARAM_TAG__TYPE = "${type}";
    public static final String JS_PARAM_TAG__GATEWAY = "${gateway}";
    public static final String JS_PARAM_TAG__GATEWAY_MERCHAND_ID = "${gatewayMerchantId}";
    public static final String JS_PARAM_TAG__CONTAINER = "${container}";
    public static final String JS_PARAM_TAG__CALLBACK = "${callback}";

    public static final String JS_PARAM_VALUE__TYPE = "PAYMENT_GATEWAY";
    public static final String JS_PARAM_VALUE__GATEWAY_NAME = "monext";
    public static final String JS_PARAM_VALUE__CONTAINER = "paylineGooglePayBtnDivContainer";
    public static final String JS_PARAM_VALUE__CALLBACK = "paylineProcessPaymentCallback";

    public static final String JS_URL_GOOGLE_PAY = "https://pay.google.com/gp/p/js/pay.js";
    public static final String JS_RES_INIT_PAYMENT = "init_payment.js";

    public static final String JS_START_METHOD_NAME = "onGooglePayLoaded()";

    public static final String PAYMENT_REQUEST_PAYMENT_DATA_KEY = "data";

    public static final String NETWORK_CB_LABEL = "network.credit.card.label";
    public static final String NETWORK_CB_DESCRIPTION = "network.credit.card.description";
    public static final String NETWORK_VISA_LABEL = "network.visa.label";
    public static final String NETWORK_VISA_DESCRIPTION = "network.visa.description";
    public static final String NETWORK_MASTERCARD_LABEL = "network.mastercard.label";
    public static final String NETWORK_MASTERCARD_DESCRIPTION = "network.mastercard.description";

}