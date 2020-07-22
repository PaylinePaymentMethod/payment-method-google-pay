package com.payline.payment.google.pay.bean;

import org.json.JSONObject;

import static com.payline.payment.google.pay.utils.GooglePayConstants.BEAN_BILLING_ADDRESS;
import static com.payline.payment.google.pay.utils.GooglePayConstants.BEAN_CARD_DETAILS;
import static com.payline.payment.google.pay.utils.GooglePayConstants.BEAN_CARD_NETWORK;

public class CardInfo {
    public static final String VISA = "VISA";
    public static final String MASTERCARD = "MASTERCARD";

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
    public static final class Builder extends JsonBean {
        private String cardNetwork;
        private String cardDetails;
        private BillingAddress billingAddress;

        public CardInfo fromJson(JSONObject jo) {

            this.cardNetwork = getString(jo, BEAN_CARD_NETWORK);
            this.cardDetails = getString(jo, BEAN_CARD_DETAILS);
            this.billingAddress = new BillingAddress.Builder().fromJson(getJSONObject(jo, BEAN_BILLING_ADDRESS));

            return new CardInfo(this);
        }
    }
    //***** BUILDER
    //******************************************************************************************************************

}