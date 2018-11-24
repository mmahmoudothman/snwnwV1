package com.snwnw.snwnw.domain.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by fifi elshafie on 1/16/2018.
 */

public class register_result_model {
    @SerializedName("msg")
    String msg;
    @SerializedName("user")
    user_model user;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public user_model getUser() {
        return user;
    }

    public void setUser(user_model user) {
        this.user = user;
    }
}
