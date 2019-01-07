package com.payline.payment.google.pay.bean;

import org.json.JSONObject;

public class PaymentData {

    private String apiVersion;
    private String apiVersionMinor;
    private PaymentMethodData paymentMethodData;
    private String email;

    public PaymentData() {
    }

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

    public PaymentData(PaymentData.Builder builder) {
        this.apiVersion = builder.apiVersion;
        this.apiVersionMinor = builder.apiVersionMinor;
        this.paymentMethodData = builder.paymentMethodData;
        this.email = builder.email;
    }

    //******************************************************************************************************************
    //***** BUILDER
    public static final class Builder {
        private String apiVersion;
        private String apiVersionMinor;
        private PaymentMethodData paymentMethodData;
        private String email;


        public PaymentData fromJson(String jsonContent) {
            JSONObject jo = new JSONObject(jsonContent);
            this.apiVersion = String.valueOf( jo.get("apiVersion"));
            this.apiVersionMinor = String.valueOf( jo.get("apiVersionMinor"));
            this.email = jo.getString("email");
            try {
                this.paymentMethodData = new PaymentMethodData.Builder().fromJson(String.valueOf( jo.get("paymentMethodData")));
            } finally {
                return new PaymentData(this);
            }
        }
    }
    //***** BUILDER
    //******************************************************************************************************************


}