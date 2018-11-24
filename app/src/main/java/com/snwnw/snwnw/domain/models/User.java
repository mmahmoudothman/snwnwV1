package com.snwnw.snwnw.domain.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by fifi elshafie on 3/16/2018.
 */

public class User {
    @SerializedName("id")
    int id;
    @SerializedName("username")
    String username;
    @SerializedName("image")
    String image;
    @SerializedName("first_name")
    String first_name;
    @SerializedName("last_name")
    String last_name ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
}
