package com.payline.payment.google.pay.bean;

import org.json.JSONObject;

import static com.payline.payment.google.pay.utils.GooglePayConstants.*;

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
    public static final class Builder extends JsonBean {

        private String messageExpiration;
        private String messageId;
        private String paymentMethod;
        private DecryptedPaymentMethodDetails paymentMethodDetails;

        public DecryptedPaymentData fromJson(String jsonContent) {
            JSONObject jo = new JSONObject(jsonContent);

            this.messageExpiration = getString(jo, BEAN_MESSAGE_EXPIRATION);
            this.messageId = getString(jo, BEAN_MESSAGE_ID);
            this.paymentMethod = getString(jo, BEAN_PAYMENT_METHOD);
            this.paymentMethodDetails = new DecryptedPaymentMethodDetails.Builder().fromJson(getJSONObject(jo,BEAN_PAYMENT_METHOD_DETAILS));

            return new DecryptedPaymentData(this);
        }
    }
    //***** BUILDER
    //******************************************************************************************************************

}