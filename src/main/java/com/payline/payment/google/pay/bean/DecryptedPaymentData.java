package com.payline.payment.google.pay.bean;

import org.json.JSONObject;

public class DecryptedPaymentData {

    private String messageExpiration;
    private String messageId;
    private String paymentMethod;
    private DecryptedPaymentMethodDetails paymentMethodDetails;

    public DecryptedPaymentData() {
    }

    public String getMessageExpiration() {
        return messageExpiration;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public DecryptedPaymentMethodDetails getPaymentMethodDetails() {
        return paymentMethodDetails;
    }

    public DecryptedPaymentData(DecryptedPaymentData.Builder builder) {
        this.messageExpiration = builder.messageExpiration;
        this.messageId = builder.messageId;
        this.paymentMethod = builder.paymentMethod;
        this.paymentMethodDetails = builder.paymentMethodDetails;
    }

    //******************************************************************************************************************
    //***** BUILDER
    public static final class Builder {

        private String messageExpiration;
        private String messageId;
        private String paymentMethod;
        private DecryptedPaymentMethodDetails paymentMethodDetails;

        public DecryptedPaymentData fromJson(String jsonContent) {
            JSONObject jo = new JSONObject(jsonContent);

            this.messageExpiration = jo.getString("messageExpiration");
            this.messageId = jo.getString("messageId");
            this.paymentMethod = jo.getString("paymentMethod");

            try {
                this.paymentMethodDetails =  new DecryptedPaymentMethodDetails.Builder().fromJson(String.valueOf(jo.get("paymentMethodDetails")));

            }finally {
                return new DecryptedPaymentData(this);
            }
        }
    }
    //***** BUILDER
    //******************************************************************************************************************

}