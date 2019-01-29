package com.payline.payment.google.pay.utils;

import com.payline.pmapi.bean.common.Amount;
import com.payline.pmapi.bean.common.Buyer;
import com.payline.pmapi.bean.configuration.PartnerConfiguration;
import com.payline.pmapi.bean.payment.*;
import com.payline.pmapi.bean.payment.request.PaymentRequest;
import com.payline.pmapi.bean.paymentform.request.PaymentFormConfigurationRequest;

import java.math.BigInteger;
import java.util.*;

import static com.payline.payment.google.pay.utils.GooglePayConstants.*;

public class Utils {
    private static final Locale FRENCH = Locale.FRENCH;
    private static final String EUR = "EUR";
    public static final String SUCCESS_URL = "https://succesurl.com/";
    public static final String FAILURE_URL = "http://cancelurl.com/";
    public static final String NOTIFICATION_URL = "http://notificationurl.com/";
    public static final String AUTH_URL = "http://authenticationurl.com/";

    public static final String MERCHANT_NAME_VAL = "monext";
    public static final String MERCHANT_ID_VAL = "gatewayMerchantId";
    public static final String TEST_PRIVATE_KEY = "MIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQgSaq46Z66YlV9Gp/B0WfRB7b4deHKeSE/kSDPI7+5Dw2hRANCAAQD5EWZSKBoQTlspL9hpHFfBvGUhSubJC/dF0uTPKTTwuo2fb+t/kUY2ZJJyuJWI4b9qqLBVxmye359mZAfQNCY";

    public static PaymentRequest.Builder createCompletePaymentBuilder() {
        final Amount amount = createAmount(EUR);
        final ContractConfiguration contractConfiguration = createContractConfiguration();
        final Environment paylineEnvironment = new Environment(NOTIFICATION_URL, SUCCESS_URL, FAILURE_URL, true);
        final String transactionID = createTransactionId();
        final Order order = createOrder(transactionID);
        final String softDescriptor = "softDescriptor";
        final Locale locale = Locale.FRANCE;
        final Buyer buyer = createDefaultBuyer();

        Map<String, String> configMap = new HashMap();
        Map<String, String> configSensitiveMap = new HashMap();
        configSensitiveMap.put(PRIVATE_KEY_PATH, TEST_PRIVATE_KEY);
        final PartnerConfiguration configuration = new PartnerConfiguration(configMap, configSensitiveMap);

        return PaymentRequest.builder()
                .withAmount(amount)
                .withBrowser(new Browser("", Locale.FRANCE))
                .withContractConfiguration(contractConfiguration)
                .withEnvironment(paylineEnvironment)
                .withOrder(order)
                .withLocale(locale)
                .withTransactionId(transactionID)
                .withSoftDescriptor(softDescriptor)
                .withBuyer(buyer)
                .withPartnerConfiguration(configuration);
    }

    private static String createTransactionId() {
        return "transactionID" + Calendar.getInstance().getTimeInMillis();
    }

    private static Map<Buyer.AddressType, Buyer.Address> createAddresses(Buyer.Address address) {
        Map<Buyer.AddressType, Buyer.Address> addresses = new HashMap<>();
        addresses.put(Buyer.AddressType.DELIVERY, address);
        addresses.put(Buyer.AddressType.BILLING, address);

        return addresses;
    }

    private static Map<Buyer.AddressType, Buyer.Address> createDefaultAddresses() {
        Buyer.Address address = createDefaultAddress();
        return createAddresses(address);
    }

    private static Amount createAmount(String currency) {
        return new Amount(BigInteger.TEN, Currency.getInstance(currency));
    }

    private static Order createOrder(String transactionID) {
        return Order.OrderBuilder.anOrder().withReference(transactionID).build();
    }

    private static Buyer.FullName createFullName() {
        return new Buyer.FullName("foo", "bar", "UNKNOWN");
    }

    private static Map<Buyer.PhoneNumberType, String> createDefaultPhoneNumbers() {
        Map<Buyer.PhoneNumberType, String> phoneNumbers = new HashMap<>();
        phoneNumbers.put(Buyer.PhoneNumberType.BILLING, "0606060606");

        return phoneNumbers;
    }

    public static ContractConfiguration createContractConfiguration() {
        final ContractConfiguration contractConfiguration = new ContractConfiguration("", new HashMap<>());
        contractConfiguration.getContractProperties().put(MERCHANT_NAME_KEY, new ContractProperty(MERCHANT_NAME_VAL));
        contractConfiguration.getContractProperties().put(MERCHANT_ID_KEY, new ContractProperty(MERCHANT_ID_VAL));
        contractConfiguration.getContractProperties().put(BUTTON_COLOR_KEY, new ContractProperty(COLOR_DEFAULT_KEY));
        contractConfiguration.getContractProperties().put(BUTTON_SIZE_KEY, new ContractProperty(SIZE_LONG_KEY));

        contractConfiguration.getContractProperties().put(ACTIVATE_NETWORK_VISA_KEY, new ContractProperty(YES_KEY));
        contractConfiguration.getContractProperties().put(ACTIVATE_NETWORK_MASTERCARD_KEY, new ContractProperty(YES_KEY));
        contractConfiguration.getContractProperties().put(ACTIVATE_NETWORK_AMEX_KEY, new ContractProperty(YES_KEY));

        contractConfiguration.getContractProperties().put(ALLOWED_AUTH_METHOD_KEY, new ContractProperty(METHOD_BOTH_KEY));

        return contractConfiguration;
    }

    private static Buyer.Address createAddress(String street, String city, String zip) {
        return Buyer.Address.AddressBuilder.anAddress()
                .withStreet1(street)
                .withCity(city)
                .withZipCode(zip)
                .withCountry("country")
                .build();
    }

    private static Buyer.Address createDefaultAddress() {
        return createAddress("a street", "a city", "a zip");
    }

    private static Buyer createBuyer(Map<Buyer.PhoneNumberType, String> phoneNumbers, Map<Buyer.AddressType, Buyer.Address> addresses, Buyer.FullName fullName) {
        return Buyer.BuyerBuilder.aBuyer()
                .withCustomerIdentifier("customerId")
                .withEmail("foo@bar.baz")
                .withPhoneNumbers(phoneNumbers)
                .withAddresses(addresses)
                .withFullName(fullName)
                .build();
    }

    private static Buyer createDefaultBuyer() {
        return createBuyer(createDefaultPhoneNumbers(), createDefaultAddresses(), createFullName());
    }

    public static Environment createDefaultPaylineEnvironment() {
        return new Environment("http://notificationURL.com", "http://redirectionURL.com", "http://redirectionCancelURL.com", true);
    }

    public static PartnerConfiguration createDefaultPartnerConfiguration() {
        Map<String, String> partnerConfigMap = new HashMap<>();
        return new PartnerConfiguration(partnerConfigMap, new HashMap<>());
    }

    public static PaymentFormConfigurationRequest createDefaultPaymentFormConfigurationRequest() {
        return PaymentFormConfigurationRequest.PaymentFormConfigurationRequestBuilder.aPaymentFormConfigurationRequest()
                .withLocale(Locale.FRANCE)
                .withBuyer(createDefaultBuyer())
                .withAmount(new Amount(BigInteger.valueOf(100), Currency.getInstance(EUR)))
                .withContractConfiguration(createContractConfiguration())
                .withOrder(createOrder("007"))
                .withEnvironment(createDefaultPaylineEnvironment())
                .withPartnerConfiguration(createDefaultPartnerConfiguration())
                .build();
    }
}
