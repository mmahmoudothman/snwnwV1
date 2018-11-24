package com.snwnw.snwnw.domain.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by fifi elshafie on 3/10/2018.
 */

public class FAQ_result_model {
    @SerializedName("msg")
    String msg;
    @SerializedName("faqs")
    ArrayList<faq_model>allFQS ;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<faq_model> getAllFQS() {
        return allFQS;
    }

    public void setAllFQS(ArrayList<faq_model> allFQS) {
        this.allFQS = allFQS;
    }
}
