package com.payline.payment.google.pay.bean;

import org.json.JSONObject;

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
    public static final class Builder {
        private String phoneNumber;
        private String countryCode;
        private String postalCode;
        private String name;

        public BillingAddress fromJson(String jsonContent) {
            JSONObject jo = new JSONObject(jsonContent);

            this.phoneNumber = jo.getString("phoneNumber");
            this.countryCode = jo.getString("countryCode");
            this.postalCode = jo.getString("postalCode");
            this.name = jo.getString("name");

            return new BillingAddress(this);
        }
    }
    //***** BUILDER
    //******************************************************************************************************************

}
