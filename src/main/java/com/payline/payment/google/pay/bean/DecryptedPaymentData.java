package com.payline.payment.google.pay.bean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class DecryptedPaymentData {

 @SerializedName("messageExpiration")
 private String messageExpiration;

 @SerializedName("messageId")
 private String messageId;

 @SerializedName("paymentMethod")
 private String paymentMethod;

 @SerializedName("paymentMethodDetails")
 private DecryptedPaymentMethodDetails paymentMethodDetails;

 public DecryptedPaymentData() { }

 public String getMessageExpiration() {
  return messageExpiration;
 }

 public String getMessageId() {
  return messageId;
 }

 public String getPaymentMethod() {
  return paymentMethod;
 }

 public DecryptedPaymentMethodDetails getPaymentMethodDetails() {
  return paymentMethodDetails;
 }

 //******************************************************************************************************************
 //***** BUILDER
 public static final class Builder {
  public DecryptedPaymentData fromJson(String jsonContent ) {
   Gson gson = new Gson();
   return gson.fromJson( jsonContent, DecryptedPaymentData.class );
  }
 }
 //***** BUILDER
 //******************************************************************************************************************

}