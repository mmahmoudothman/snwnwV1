package com.snwnw.snwnw.domain.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by fifi elshafie on 6/9/2018.
 */

public class homeCitiesModel {
    @SerializedName("msg")
    String msg ;
    @SerializedName("cities")
    ArrayList<countryModel>countries ;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<countryModel> getCountries() {
        return countries;
    }

    public void setCountries(ArrayList<countryModel> countries) {
        this.countries = countries;
    }

    public class countryModel{
        @SerializedName("id")
        public  int id ;
        @SerializedName("name")
        public String name;
        @SerializedName("image")
        public String image;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
