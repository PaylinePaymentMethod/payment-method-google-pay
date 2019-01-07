package com.payline.payment.google.pay.bean;

import org.json.JSONObject;

public class DecryptedPaymentMethodDetails {

    private String authMethod;
    private String pan;
    private int expirationMonth;
    private int expirationYear;
    private String cryptogram;
    private String eciIndicator;

    public DecryptedPaymentMethodDetails() { }

    public String getAuthMethod() {
        return authMethod;
    }

    public String getPan() {
        return pan;
    }

    public int getExpirationMonth() {
        return expirationMonth;
    }

    public int getExpirationYear() {
        return expirationYear;
    }

    public String getCryptogram() {
        return cryptogram;
    }

    public String getEciIndicator() {
        return eciIndicator;
    }

    public DecryptedPaymentMethodDetails(DecryptedPaymentMethodDetails.Builder builder) {
        this.authMethod = builder.authMethod;
        this.pan = builder.pan;
        this.expirationMonth = builder.expirationMonth;
        this.expirationYear = builder.expirationYear;
        this.cryptogram = builder.cryptogram;
        this.eciIndicator = builder.eciIndicator;
    }

    //******************************************************************************************************************
    //***** BUILDER
    public static final class Builder {
        private String authMethod;
        private String pan;
        private int expirationMonth;
        private int expirationYear;
        private String cryptogram;
        private String eciIndicator;


        public DecryptedPaymentMethodDetails fromJson(String jsonContent) {
            JSONObject jo = new JSONObject(jsonContent);

            try{
                this.authMethod = jo.getString("authMethod");
            } catch (Exception e){

            }
            this.pan = jo.getString("pan");
            this.expirationMonth = jo.getInt("expirationMonth");
            this.expirationYear = jo.getInt("expirationYear");
            try{
                this.cryptogram = jo.getString("cryptogram");
            } catch (Exception e){

            }

            try {
                this.eciIndicator = jo.getString("eciIndicator");
            }catch (Exception e ){

            }

            return new DecryptedPaymentMethodDetails(this);

        }
    }
    //***** BUILDER
    //******************************************************************************************************************

}