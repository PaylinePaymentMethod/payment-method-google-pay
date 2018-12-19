package com.payline.payment.google.pay.bean;

import com.google.gson.annotations.SerializedName;

public class PaymentMethodData {


    private String type;

    @SerializedName("description")
    private String description;

    @SerializedName("info")
    private CardInfo info;

    @SerializedName("tokenizationData")
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

}