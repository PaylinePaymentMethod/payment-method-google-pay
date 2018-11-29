package com.payline.payment.google.pay.bean;

import com.google.gson.annotations.SerializedName;

public class PaymentMethodData {

    @SerializedName("type")
    private String type;

    @SerializedName("description")
    private String description;

    @SerializedName("info")
    private CardInfo info;

    @SerializedName("tokenizationData")
    private PaymentMethodTokenizationData tokenizationData;

    public PaymentMethodData() { }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CardInfo getInfo() {
        return info;
    }

    public void setInfo(CardInfo info) {
        this.info = info;
    }

    public PaymentMethodTokenizationData getTokenizationData() {
        return tokenizationData;
    }

    public void setTokenizationData(PaymentMethodTokenizationData tokenizationData) {
        this.tokenizationData = tokenizationData;
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();

        result.append("type : " + type + "\n");
        result.append("description : " + description + "\n");

        result.append(info.toString());
        result.append(tokenizationData.toString());

        return result.toString();
    }

}