package com.snwnw.snwnw.domain.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by fifi elshafie on 2/24/2018.
 */

public class place_details_model {
    @SerializedName("msg")
    String msg;
    @SerializedName("place")
    place_model place_model ;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public com.snwnw.snwnw.domain.models.place_model getPlace_model() {
        return place_model;
    }

    public void setPlace_model(com.snwnw.snwnw.domain.models.place_model place_model) {
        this.place_model = place_model;
    }
}
