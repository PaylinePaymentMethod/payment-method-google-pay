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

    public String getToken() {
        return token;
    }

}