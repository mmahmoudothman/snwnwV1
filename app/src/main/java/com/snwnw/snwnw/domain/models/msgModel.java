package com.snwnw.snwnw.domain.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by fifi elshafie on 7/1/2018.
 */

public class msgModel {
    @SerializedName("msg")
    String msg ;
    @SerializedName("not_found_data")
    ArrayList<Integer>  not_found_data;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<Integer> getNot_found_data() {
        return not_found_data;
    }

    public void setNot_found_data(ArrayList<Integer> not_found_data) {
        this.not_found_data = not_found_data;
    }
}
