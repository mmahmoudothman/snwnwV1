package com.snwnw.snwnw.domain.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by fifi elshafie on 6/30/2018.
 */

public class mapsPlacesLoaderModel
{
    @SerializedName("msg")
    String msg ;
    @SerializedName("places")
    ArrayList<placeModel>places ;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<placeModel> getPlaces() {
        return places;
    }

    public void setPlaces(ArrayList<placeModel> places) {
        this.places = places;
    }

    public class placeModel {
        @SerializedName("id")
        public int id ;
        @SerializedName("longitude")
        public double longitude;
        @SerializedName("latitude")
        public double latitude ;
        @SerializedName("distance")
        public double distance ;
        @SerializedName("title")
        public String title ;
        @SerializedName("description")
        public String description ;

        @SerializedName("logo")
        public String logo ;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }
    }
}

