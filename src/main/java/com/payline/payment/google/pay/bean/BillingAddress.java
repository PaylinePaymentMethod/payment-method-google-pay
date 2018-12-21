package com.payline.payment.google.pay.bean;

import com.google.gson.annotations.SerializedName;

public class BillingAddress {

    @SerializedName("phoneNumber")
    private String phoneNumber;

    @SerializedName("countryCode")
    private String countryCode;

    @SerializedName("postalCode")
    private String postalCode;

    @SerializedName("name")
    private String name;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getName() {
        return name;
    }
}
