package com.payline.payment.google.pay.bean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class PaymentData {

    @SerializedName("apiVersion")
    private String apiVersion;

    @SerializedName("apiVersionMinor")
    private String apiVersionMinor;

    @SerializedName("paymentMethodData")
    private PaymentMethodData paymentMethodData;

    @SerializedName("email")
    private String email;

    public PaymentData() { }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getApiVersionMinor() {
        return apiVersionMinor;
    }

    public void setApiVersionMinor(String apiVersionMinor) {
        this.apiVersionMinor = apiVersionMinor;
    }

    public PaymentMethodData getPaymentMethodData() {
        return paymentMethodData;
    }

    public void setPaymentMethodData(PaymentMethodData paymentMethodData) {
        this.paymentMethodData = paymentMethodData;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();

        result.append("***** PaymentData info\n");

        result.append("apiVersion : " + apiVersion + "\n");
        result.append("apiVersionMinor : " + apiVersionMinor + "\n");
        result.append("email : " + email + "\n");

        result.append(paymentMethodData.toString());

        return result.toString();
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    //******************************************************************************************************************
    //***** BUILDER
    public static final class Builder {
        public PaymentData fromJson(String jsonContent ) {
            Gson gson = new Gson();
            return gson.fromJson( jsonContent, PaymentData.class );
        }
    }
    //***** BUILDER
    //******************************************************************************************************************



}