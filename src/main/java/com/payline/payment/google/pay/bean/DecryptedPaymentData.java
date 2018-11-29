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

 public void setMessageExpiration(String messageExpiration) {
  this.messageExpiration = messageExpiration;
 }

 public String getMessageId() {
  return messageId;
 }

 public void setMessageId(String messageId) {
  this.messageId = messageId;
 }

 public String getPaymentMethod() {
  return paymentMethod;
 }

 public void setPaymentMethod(String paymentMethod) {
  this.paymentMethod = paymentMethod;
 }

 public DecryptedPaymentMethodDetails getPaymentMethodDetails() {
  return paymentMethodDetails;
 }

 public void setPaymentMethodDetails(DecryptedPaymentMethodDetails paymentMethodDetails) {
  this.paymentMethodDetails = paymentMethodDetails;
 }

 @Override
 public String toString() {
  final StringBuilder result = new StringBuilder();

  result.append("***** DecryptedPaymentData info\n");

  result.append("messageExpiration : " + messageExpiration + "\n");
  result.append("messageId : " + messageId + "\n");
  result.append("paymentMethod : " + paymentMethod + "\n");

  result.append(paymentMethodDetails.toString());

  return result.toString();
 }

 public String toJson() {
  return new Gson().toJson(this);
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