package com.payline.payment.google.pay.utils;

public class GooglePayConstants {
    public static final String PRIVATE_KEY_PATH = "partner.google.pay.access.private.key";

    public static final String RESOURCE_BUNDLE_BASE_NAME = "messages";

    public static final String I18N_SERVICE_DEFAULT_LOCALE = "en";

    // fields used in Beans
    public static final String BEAN_PHONE_NUMBER = "phoneNumber";
    public static final String BEAN_COUNTRY_CODE = "countryCode";
    public static final String BEAN_POSTAL_CODE = "postalCode";
    public static final String BEAN_NAME = "name";
    public static final String BEAN_CARD_NETWORK = "cardNetwork";
    public static final String BEAN_CARD_DETAILS = "cardDetails";
    public static final String BEAN_BILLING_ADDRESS = "billingAddress";
    public static final String BEAN_MESSAGE_EXPIRATION = "messageExpiration";
    public static final String BEAN_MESSAGE_ID = "messageId";
    public static final String BEAN_PAYMENT_METHOD = "paymentMethod";
    public static final String BEAN_PAYMENT_METHOD_DETAILS = "paymentMethodDetails";
    public static final String BEAN_AUTH_METHOD = "authMethod";
    public static final String BEAN_PAN = "pan";
    public static final String BEAN_EXPIRATION_MONTH = "expirationMonth";
    public static final String BEAN_EXPIRATION_YEAR = "expirationYear";
    public static final String BEAN_CRYPTOGRAM = "cryptogram";
    public static final String BEAN_ECI_INDICATOR = "eciIndicator";
    public static final String BEAN_API_VERSION = "apiVersion";
    public static final String BEAN_API_VERSION_MINOR = "apiVersionMinor";
    public static final String BEAN_EMAIL = "email";
    public static final String BEAN_PAYMENT_METHOD_DATA = "paymentMethodData";
    public static final String BEAN_TYPE = "type";
    public static final String BEAN_DESCRIPTION = "description";
    public static final String BEAN_INFO = "info";
    public static final String BEAN_TOKENIZATION_DATA = "tokenizationData";
    public static final String BEAN_TOKEN = "token";

    // data used is js file
    public static final String JS_PARAM_TAG_PAYMENTMETHOD_TYPE = "${paymentMethodType}";
    public static final String JS_PARAM_TAG_EMAIL_REQUIRED = "${emailRequired}";
    public static final String JS_PARAM_TAG_SHIPPING_ADDRESS_REQUIRED = "${shippingAddressRequired}";
    public static final String JS_PARAM_TAG_ALLOWED_COUNTRYCODE = "${allowedCountryCode}";
    public static final String JS_PARAM_TAG_SHIPPING_PHONE = "${shippingPhoneNumberRequired}";
    public static final String JS_PARAM_TAG_BILLING_ADDRESS_REQUIRED = "${billingAddressRequired}";
    public static final String JS_PARAM_TAG_BILLING_ADDRESS_FORMAT = "${billingAddressFormat}";
    public static final String JS_PARAM_TAG_BILLING_PHONE = "${billingPhoneNumberRequired}";
    public static final String JS_PARAM_TAG_ALLOWED_CARD_NETWORKS = "${allowedCardNetworks}";
    public static final String JS_PARAM_TAG_ALLOWED_AUTH_METHODS = "${allowedCardAuthMethods}";
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
    public static final String JS_PARAM_VALUE_PANONLY = "PAN_ONLY";
    public static final String JS_PARAM_VALUE_3DS = "CRYPTOGRAM_3DS";
    public static final String JS_PARAM_VALUE_TYPE = "PAYMENT_GATEWAY";
    public static final String JS_PARAM_VALUE_GATEWAY_NAME = "monext";
    public static final String JS_PARAM_VALUE_CONTAINER = "paylineGooglePayBtnDivContainer";
    public static final String JS_PARAM_VALUE_CALLBACK = "paylineProcessPaymentCallback";

    public static final String JS_URL_GOOGLE_PAY = "https://pay.google.com/gp/p/js/pay.js";
    public static final String JS_RES_INIT_PAYMENT = "init_payment.js";

    public static final String JS_START_METHOD_NAME = "onGooglePayLoaded()";
    public static final String ACTIVATE_NETWORK_REGEX = "activateNetwork";
    public static final String PAYMENT_REQUEST_PAYMENT_DATA_KEY = "data";
    public static final String BUTTON_DESCRIPTION = "payment.form.config.description";
    public static final String FAILURE_TRANSACTION_ID = "NO TRANSACTION YET";

    // data used in ConfigurationService
    public static final String MERCHANT_NAME_KEY = "merchantName";
    public static final String MERCHANT_NAME_LABEL = "merchantName.label";
    public static final String MERCHANT_NAME_DESCRIPTION = "merchantName.description";
    public static final String MERCHANT_ID_KEY = "merchantId";
    public static final String MERCHANT_ID_LABEL = "merchantId.label";
    public static final String MERCHANT_ID_DESCRIPTION = "merchantId.description";

    public static final String BUTTON_COLOR_KEY = "buttonColor";
    public static final String BUTTON_COLOR_LABEL = "button.color.label";
    public static final String BUTTON_COLOR_DESCRIPTION = "button.color.description";
    public static final String COLOR_DEFAULT_KEY = "DEFAULT";
    public static final String COLOR_DEFAULT_VAL = "color.default";
    public static final String COLOR_BLACK_KEY = "BLACK";
    public static final String COLOR_BLACK_VAL = "color.black";
    public static final String COLOR_WHITE_KEY = "WHITE";
    public static final String COLOR_WHITE_VAL = "color.white";

    public static final String BUTTON_SIZE_KEY = "buttonSize";
    public static final String BUTTON_SIZE_LABEL = "button.size.label";
    public static final String BUTTON_SIZE_DESCRIPTION = "button.size.description";
    public static final String SIZE_SHORT_KEY = "SHORT";
    public static final String SIZE_SHORT_VAL = "size.short.label";
    public static final String SIZE_LONG_KEY = "LONG";
    public static final String SIZE_LONG_VAL = "size.long.label";

    public static final String YES_KEY = "YES";
    public static final String NO_KEY = "NO";
    public static final String YES_VAL = "activate.yes";
    public static final String NO_VAL = "activate.no";
    public static final String MIN = "MIN";
    public static final String FULL = "FULL";
    public static final String METHOD_PANONLY_KEY = "METHOD_PANONLY";
    public static final String METHOD_3DS_KEY = "METHOD_3DS";
    public static final String METHOD_BOTH_KEY = "METHOD_BOTH";
    public static final String METHOD_PANONLY_VAL = "method.panOnly";
    public static final String METHOD_3DS_VAL = "method.3ds";
    public static final String METHOD_BOTH_VAL = "method.both";
    public static final String ALLOWED_AUTH_METHOD_KEY = "allowedAuthMethod";
    public static final String ALLOWED_AUTH_METHOD_LABEL = "allowed.auth.method.label";
    public static final String ALLOWED_AUTH_METHOD_DESCRIPTION = "allowed.auth.method.description";
    public static final String ACTIVATE_NETWORK_VISA_KEY = "activateNetworkVISA";
    public static final String ACTIVATE_NETWORK_VISA_LABEL = "network.activate.visa.label";
    public static final String ACTIVATE_NETWORK_VISA_DESCRIPTION = "network.activate.visa.description";
    public static final String ACTIVATE_NETWORK_MASTERCARD_KEY = "activateNetworkMASTERCARD";
    public static final String ACTIVATE_NETWORK_MASTERCARD_LABEL = "network.activate.mastercard.label";
    public static final String ACTIVATE_NETWORK_MASTERCARD_DESCRIPTION = "network.activate.mastercard.description";
    public static final String ACTIVATE_NETWORK_AMEX_KEY = "activateNetworkAMEX";
    public static final String ACTIVATE_NETWORK_AMEX_LABEL = "network.activate.amex.label";
    public static final String ACTIVATE_NETWORK_AMEX_DESCRIPTION = "network.activate.amex.description";
    public static final String ACTIVATE_NETWORK_JCB_KEY = "activateNetworkJCB";
    public static final String ACTIVATE_NETWORK_JCB_LABEL = "network.activate.jcb.label";
    public static final String ACTIVATE_NETWORK_JCB_DESCRIPTION = "network.activate.jcb.description";
    public static final String ACTIVATE_NETWORK_DISCOVER_KEY = "activateNetworkDISCOVER";
    public static final String ACTIVATE_NETWORK_DISCOVER_LABEL = "network.activate.discover.label";
    public static final String ACTIVATE_NETWORK_DISCOVER_DESCRIPTION = "network.activate.discover.description";

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

    public static final String CARD_KEY = "CARD";
    public static final String CARD_VAL = "CARD";
    public static final String PAYMENT_METHOD_TYPE_KEY = "paymentMethodType";
    public static final String PAYMENT_METHOD_TYPE_LABEL = "paymentMethod.type.label";
    public static final String PAYMENT_METHOD_TYPE_DESCRIPTION = "paymentMethod.type.description";
    public static final String EMAIL_REQUIRED_KEY = "emailRequired";
    public static final String EMAIL_REQUIRED_LABEL = "email.required.label";
    public static final String EMAIL_REQUIRED_DESCRIPTION = "email.required.description";
    public static final String SHIPPING_ADDRESS_REQUIRED_KEY = "shippingAddressRequired";
    public static final String SHIPPING_ADDRESS_REQUIRED_LABEL = "shippingAddressRequired.label";
    public static final String SHIPPING_ADDRESS_REQUIRED_DESCRIPTION = "shippingAddressRequired.description";
    public static final String ALLOWED_COUNTRY_KEY = "allowedCountry";
    public static final String ALLOWED_COUNTRY_LABEL = "allowedCountry.label";
    public static final String ALLOWED_COUNTRY_DESCRIPTION = "allowedCountry.description";
    public static final String SHIPPING_PHONE_REQUIRED_KEY = "shippingPhoneRequired";
    public static final String SHIPPING_PHONE_REQUIRED_LABEL = "shippingPhoneRequired.label";
    public static final String SHIPPING_PHONE_REQUIRED_DESCRIPTION = "shippingPhoneRequired.description";
    public static final String BILLING_ADDRESS_REQUIRED_KEY = "billingAddressRequired";
    public static final String BILLING_ADDRESS_REQUIRED_LABEL = "billingAddressRequired.label";
    public static final String BILLING_ADDRESS_REQUIRED_DESCRIPTION = "billingAddressRequired.description";
    public static final String BILLING_ADDRESS_FORMAT_KEY = "billingAddressFormat";
    public static final String BILLING_ADDRESS_FORMAT_LABEL = "billingAddressFormat.label";
    public static final String BILLING_ADDRESS_FORMAT_DESCRIPTION = "billingAddressFormat.description";
    public static final String BILLING_PHONE_REQUIRED_KEY = "billingPhoneRequired";
    public static final String BILLING_PHONE_REQUIRED_LABEL = "billingPhoneRequired.label";
    public static final String BILLING_PHONE_REQUIRED_DESCRIPTION = "billingPhoneRequired.description";

    public static final String ERROR_NOT_ISO3166 = "contract.errors.countryNotISO";

    // Data used in PaymentService
    public static final String PAYMENTDATA_TOKENDATA = "PAYMENTDATA_TOKENDATA";
}