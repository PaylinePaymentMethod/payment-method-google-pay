package com.payline.payment.google.pay.bean;

import org.json.JSONObject;

public class PaymentMethodData {

    private String type;
    private String description;
    private CardInfo info;
    private PaymentMethodTokenizationData tokenizationData;

    public PaymentMethodData() {
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public CardInfo getInfo() {
        return info;
    }

    public PaymentMethodTokenizationData getTokenizationData() {
        return tokenizationData;
    }

    public PaymentMethodData(PaymentMethodData.Builder builder) {
        this.type = builder.type;
        this.description = builder.description;
        this.info = builder.info;
        this.tokenizationData = builder.tokenizationData;
    }

    //******************************************************************************************************************
    //***** BUILDER
    public static final class Builder {
        private String type;
        private String description;
        private CardInfo info;
        private PaymentMethodTokenizationData tokenizationData;

        public PaymentMethodData fromJson(String jsonContent) {
            JSONObject jo = new JSONObject(jsonContent);

            this.type = jo.getString("type");
            this.description = jo.getString("description");
            this.info = new CardInfo.Builder().fromJson(String.valueOf(jo.get("info")));

            try {
                this.tokenizationData = new PaymentMethodTokenizationData.Builder().fromJson(String.valueOf(jo.get("tokenizationData")));
            } finally {
                return new PaymentMethodData(this);
            }

        }
    }
    //***** BUILDER
    //******************************************************************************************************************

}