package com.snwnw.snwnw.domain.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by fifi elshafie on 3/9/2018.
 */

public class code_result {
    @SerializedName("msg")
    String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
