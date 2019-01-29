package com.payline.payment.google.pay.bean;

import org.json.JSONObject;

import static com.payline.payment.google.pay.utils.GooglePayConstants.BEAN_TOKEN;
import static com.payline.payment.google.pay.utils.GooglePayConstants.BEAN_TYPE;

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
    public static final class Builder extends JsonBean {
        private String type;
        private String token;

        public PaymentMethodTokenizationData fromJson(JSONObject jo) {
            this.type = getString(jo, BEAN_TYPE);
            this.token = getString(jo, BEAN_TOKEN);

            return new PaymentMethodTokenizationData(this);
        }
    }
    //***** BUILDER
    //******************************************************************************************************************

}