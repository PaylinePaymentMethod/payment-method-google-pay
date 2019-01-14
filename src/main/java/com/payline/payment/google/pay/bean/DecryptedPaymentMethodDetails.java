package com.payline.payment.google.pay.bean;

import org.json.JSONObject;

import static com.payline.payment.google.pay.utils.GooglePayConstants.*;

public class DecryptedPaymentMethodDetails {

    private String authMethod;
    private String pan;
    private int expirationMonth;
    private int expirationYear;
    private String cryptogram;
    private String eciIndicator;

    public DecryptedPaymentMethodDetails() {
    }

    public String getAuthMethod() {
        return authMethod;
    }

    public String getPan() {
        return pan;
    }

    public int getExpirationMonth() {
        return expirationMonth;
    }

    public int getExpirationYear() {
        return expirationYear;
    }

    public String getCryptogram() {
        return cryptogram;
    }

    public String getEciIndicator() {
        return eciIndicator;
    }

    public DecryptedPaymentMethodDetails(DecryptedPaymentMethodDetails.Builder builder) {
        this.authMethod = builder.authMethod;
        this.pan = builder.pan;
        this.expirationMonth = builder.expirationMonth;
        this.expirationYear = builder.expirationYear;
        this.cryptogram = builder.cryptogram;
        this.eciIndicator = builder.eciIndicator;
    }

    //******************************************************************************************************************
    //***** BUILDER
    public static final class Builder extends JsonBean {
        private String authMethod;
        private String pan;
        private int expirationMonth;
        private int expirationYear;
        private String cryptogram;
        private String eciIndicator;

        public DecryptedPaymentMethodDetails fromJson(JSONObject jo) {

            this.authMethod = getString(jo, BEAN_AUTH_METHOD);
            this.pan = getString(jo, BEAN_PAN);
            this.expirationMonth = getInt(jo, BEAN_EXPIRATION_MONTH);
            this.expirationYear = getInt(jo, BEAN_EXPIRATION_YEAR);
            this.cryptogram = getString(jo, BEAN_CRYPTOGRAM);
            this.eciIndicator = getString(jo, BEAN_ECI_INDICATOR);

            return new DecryptedPaymentMethodDetails(this);
        }
    }
    //***** BUILDER
    //******************************************************************************************************************

}