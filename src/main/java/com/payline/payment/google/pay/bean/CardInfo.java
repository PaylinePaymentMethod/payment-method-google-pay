package com.payline.payment.google.pay.bean;

import com.google.gson.annotations.SerializedName;

public class CardInfo {

    @SerializedName("cardNetwork")
    private String cardNetwork;

    @SerializedName("cardDetails")
    private String cardDetails;

    @SerializedName("billingAddress")
    private BillingAddress billingAddress;

    public CardInfo() { }

    public String getCardNetwork() {
        return cardNetwork;
    }

    public String getCardDetails() {
        return cardDetails;
    }

    public BillingAddress getBillingAddress() {
        return billingAddress;
    }

}