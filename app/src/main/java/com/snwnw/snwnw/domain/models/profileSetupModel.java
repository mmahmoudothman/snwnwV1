package com.snwnw.snwnw.domain.models;

/**
 * Created by fifi elshafie on 2/6/2018.
 */

public class  profileSetupModel{
    String userKey ;
    String userVal ;

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getUserVal() {
        return userVal;
    }

    public void setUserVal(String userVal) {
        this.userVal = userVal;
    }

    public profileSetupModel(String userKey, String userVal) {
        this.userKey = userKey;
        this.userVal = userVal;
    }
}