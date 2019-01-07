package com.payline.payment.google.pay.bean;

import org.json.JSONObject;

public class CardInfo {

    private String cardNetwork;
    private String cardDetails;
    private BillingAddress billingAddress;

    public String getCardNetwork() {
        return cardNetwork;
    }

    public String getCardDetails() {
        return cardDetails;
    }

    public BillingAddress getBillingAddress() {
        return billingAddress;
    }

    public CardInfo(CardInfo.Builder builder) {
        this.cardNetwork = builder.cardNetwork;
        this.cardDetails = builder.cardDetails;
        this.billingAddress = builder.billingAddress;
    }

    //******************************************************************************************************************
    //***** BUILDER
    public static final class Builder {
        private String cardNetwork;
        private String cardDetails;
        private BillingAddress billingAddress;

        public CardInfo fromJson(String jsonContent) {
            JSONObject jo = new JSONObject(jsonContent);

            this.cardNetwork = jo.getString("cardNetwork");
            this.cardDetails = jo.getString("cardDetails");
            try {
                this.billingAddress = new BillingAddress.Builder().fromJson(String.valueOf(jo.get("billingAddress")));
            }finally {
                return new CardInfo(this);
            }

        }
    }
    //***** BUILDER
    //******************************************************************************************************************

}