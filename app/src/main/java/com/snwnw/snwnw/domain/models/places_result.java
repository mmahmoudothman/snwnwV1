package com.snwnw.snwnw.domain.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by fifi elshafie on 2/24/2018.
 */

public class places_result implements Serializable {
    @SerializedName("msg")
    String msg;
    @SerializedName("places")
    ArrayList<place_model>allplaces ;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<place_model> getAllplaces() {
        return allplaces;
    }

    public void setAllplaces(ArrayList<place_model> allplaces) {
        this.allplaces = allplaces;
    }
}
