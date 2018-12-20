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

    public String getApiVersionMinor() {
        return apiVersionMinor;
    }

    public PaymentMethodData getPaymentMethodData() {
        return paymentMethodData;
    }

    public String getEmail() {
        return email;
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