package com.snwnw.snwnw.domain.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by fifi elshafie on 7/1/2018.
 */

public class favInterestModel {
    @SerializedName("msg")
    String msg ;
    @SerializedName("interests")
    ArrayList<Integer>interests;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<Integer> getInterests() {
        return interests;
    }

    public void setInterests(ArrayList<Integer> interests) {
        this.interests = interests;
    }
}
