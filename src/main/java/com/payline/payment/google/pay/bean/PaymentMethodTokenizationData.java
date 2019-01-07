package com.payline.payment.google.pay.bean;

import org.json.JSONObject;

public class PaymentMethodTokenizationData {

    private String type;
    private String token;

    public PaymentMethodTokenizationData() { }

    public String getType() {
        return type;
    }

    public String getToken() {
        return token;
    }

    public PaymentMethodTokenizationData(PaymentMethodTokenizationData.Builder builder) {
        this.type = builder.type;
        this.token = builder.token;
    }

    //******************************************************************************************************************
    //***** BUILDER
    public static final class Builder {
        private String type;
        private String token;

        public PaymentMethodTokenizationData fromJson(String jsonContent) {
            JSONObject jo = new JSONObject(jsonContent);

            this.type = jo.getString("type");
            this.token = jo.getString("token");

            return new PaymentMethodTokenizationData(this);
        }
    }
    //***** BUILDER
    //******************************************************************************************************************

}