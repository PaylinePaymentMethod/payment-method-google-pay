package com.payline.payment.google.pay.bean;

import com.google.gson.annotations.SerializedName;

public class DecryptedPaymentMethodDetails {

    @SerializedName("authMethod")
    private String authMethod;

    @SerializedName("pan")
    private String pan;

    @SerializedName("expirationMonth")
    private int expirationMonth;

    @SerializedName("expirationYear")
    private int expirationYear;

    @SerializedName("cryptogram")
    private String cryptogram;

    @SerializedName("eciIndicator")
    private String eciIndicator;

    public DecryptedPaymentMethodDetails() { }

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

}