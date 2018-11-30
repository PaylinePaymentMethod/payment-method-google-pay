package com.payline.payment.google.pay.bean;

import com.google.gson.annotations.SerializedName;

public class CardInfo {

    @SerializedName("cardNetwork")
    private String cardNetwork;

    @SerializedName("cardDetails")
    private String cardDetails;

    public CardInfo() { }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();

        result.append("cardNetwork : " + cardNetwork + "\n");
        result.append("cardDetails : " + cardDetails + "\n");

        return result.toString();
    }

}