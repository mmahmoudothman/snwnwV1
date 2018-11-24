package com.snwnw.snwnw.domain.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by fifi elshafie on 2/10/2018.
 */

public class categoriesResult {
    @SerializedName("msg")
    String msg ;
    @SerializedName("categories")
    ArrayList<service_cat_model>AllServices;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<service_cat_model> getAllServices() {
        return AllServices;
    }

    public void setAllServices(ArrayList<service_cat_model> allServices) {
        AllServices = allServices;
    }
}
