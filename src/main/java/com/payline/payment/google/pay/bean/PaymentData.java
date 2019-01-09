package com.payline.payment.google.pay.bean;

import org.json.JSONObject;

import static com.payline.payment.google.pay.utils.GooglePayConstants.*;

public class PaymentData {

    private int apiVersion;
    private int apiVersionMinor;
    private PaymentMethodData paymentMethodData;
    private String email;

    public PaymentData() {
    }

    public int getApiVersion() {
        return apiVersion;
    }

    public int getApiVersionMinor() {
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
    public static final class Builder extends JsonBean {
        private int apiVersion;
        private int apiVersionMinor;
        private PaymentMethodData paymentMethodData;
        private String email;


        public PaymentData fromJson(String jsonContent) {
            JSONObject jo = new JSONObject(jsonContent);
            this.apiVersion = getInt(jo, BEAN_API_VERSION);
            this.apiVersionMinor = getInt(jo, BEAN_API_VERSION_MINOR);
            this.email = getString(jo, BEAN_EMAIL);
            this.paymentMethodData = new PaymentMethodData.Builder().fromJson(getJSONObject(jo, BEAN_PAYMENT_METHOD_DATA));

            return new PaymentData(this);

        }
    }
    //***** BUILDER
    //******************************************************************************************************************


}