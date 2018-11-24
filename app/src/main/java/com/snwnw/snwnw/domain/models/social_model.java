package com.snwnw.snwnw.domain.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by fifi elshafie on 3/16/2018.
 */

public class social_model {
    @SerializedName("msg")
    String msg;
    @SerializedName("token")
    String token;
    @SerializedName("user")
    user_model user_model;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public com.snwnw.snwnw.domain.models.user_model getUser_model() {
        return user_model;
    }

    public void setUser_model(com.snwnw.snwnw.domain.models.user_model user_model) {
        this.user_model = user_model;
    }
}
