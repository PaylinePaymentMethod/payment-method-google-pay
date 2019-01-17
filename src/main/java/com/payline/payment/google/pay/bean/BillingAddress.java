package com.payline.payment.google.pay.bean;

import org.json.JSONObject;

import static com.payline.payment.google.pay.utils.GooglePayConstants.*;

public class BillingAddress {

    private String phoneNumber;
    private String countryCode;
    private String postalCode;
    private String name;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getName() {
        return name;
    }

    public BillingAddress(BillingAddress.Builder builder) {
        this.phoneNumber = builder.phoneNumber;
        this.countryCode = builder.countryCode;
        this.postalCode = builder.postalCode;
        this.name = builder.name;
    }

    //******************************************************************************************************************
    //***** BUILDER
    public static final class Builder extends JsonBean {
        private String phoneNumber;
        private String countryCode;
        private String postalCode;
        private String name;

        public BillingAddress fromJson(JSONObject jo) {

            this.phoneNumber = getString(jo, BEAN_PHONE_NUMBER);
            this.countryCode = getString(jo, BEAN_COUNTRY_CODE);
            this.postalCode = getString(jo, BEAN_POSTAL_CODE);
            this.name = getString(jo, BEAN_NAME);

            return new BillingAddress(this);
        }
    }
    //***** BUILDER
    //******************************************************************************************************************

}
