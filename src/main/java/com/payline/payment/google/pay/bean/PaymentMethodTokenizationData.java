package com.payline.payment.google.pay.bean;

import com.google.gson.annotations.SerializedName;

public class PaymentMethodTokenizationData {

    @SerializedName("type")
    private String type;

    @SerializedName("token")
    private String token;

    public PaymentMethodTokenizationData() { }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();

        result.append("type : " + type + "\n");
        result.append("token : " + token + "\n");

        return result.toString();
    }

}