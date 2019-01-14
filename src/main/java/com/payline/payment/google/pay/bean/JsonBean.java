package com.payline.payment.google.pay.bean;

import org.json.JSONObject;

class JsonBean {

    String getString(JSONObject jo, String s) {
        return jo.isNull(s) ? null : jo.getString(s);
    }

    int getInt(JSONObject jo, String s) {
        return jo.isNull(s) ? 0 : jo.getInt(s);
    }

    JSONObject getJSONObject(JSONObject jo, String s) {
        return jo.isNull(s) ? new JSONObject() : jo.getJSONObject(s);
    }
}
