package com.snwnw.snwnw.domain.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by fifi elshafie on 3/16/2018.
 */

public class review_result {
    @SerializedName("msg")
    String msg;
    @SerializedName("reviews")
    ArrayList<review_item> reviews;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<review_item> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<review_item> reviews) {
        this.reviews = reviews;
    }
}
