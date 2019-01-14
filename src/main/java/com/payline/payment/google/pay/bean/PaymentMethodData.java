package com.payline.payment.google.pay.bean;

import org.json.JSONObject;

import static com.payline.payment.google.pay.utils.GooglePayConstants.*;

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
    public static final class Builder extends JsonBean {
        private String type;
        private String description;
        private CardInfo info;
        private PaymentMethodTokenizationData tokenizationData;

        public PaymentMethodData fromJson(JSONObject jo) {

            this.type = getString(jo,BEAN_TYPE);
            this.description = getString(jo,BEAN_DESCRIPTION);
            this.info = new CardInfo.Builder().fromJson(getJSONObject(jo,BEAN_INFO));
            this.tokenizationData = new PaymentMethodTokenizationData.Builder().fromJson(getJSONObject(jo,BEAN_TOKENIZATION_DATA));

            return new PaymentMethodData(this);
        }
    }
    //***** BUILDER
    //******************************************************************************************************************

}