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

    public void setAuthMethod(String authMethod) {
        this.authMethod = authMethod;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public int getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(int expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public int getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(int expirationYear) {
        this.expirationYear = expirationYear;
    }

    public String getCryptogram() {
        return cryptogram;
    }

    public void setCryptogram(String cryptogram) {
        this.cryptogram = cryptogram;
    }

    public String getEciIndicator() {
        return eciIndicator;
    }

    public void setEciIndicator(String eciIndicator) {
        this.eciIndicator = eciIndicator;
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();

        result.append("authMethod : " + authMethod + "\n");
        result.append("pan : " + pan + "\n");
        result.append("expirationMonth : " + expirationMonth + "\n");
        result.append("expirationYear : " + expirationYear + "\n");
        result.append("cryptogram : " + cryptogram + "\n");
        result.append("eciIndicator : " + eciIndicator + "\n");

        return result.toString();
    }

}