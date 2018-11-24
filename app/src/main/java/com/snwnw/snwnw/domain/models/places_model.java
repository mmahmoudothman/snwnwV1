package com.snwnw.snwnw.domain.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by afaf.elshafey on 1/16/2018.
 */

public class places_model implements Serializable{
    @SerializedName("storeLatitude")
    double lat ;
    @SerializedName("storeLongitude")
    double lng ;
    @SerializedName("storeName")
    String placeName;
    @SerializedName("storeAddressOnMap")
    String vicinity;

    public places_model(double lat, double lng, String placeName, String vicinity) {
        this.lat = lat;
        this.lng = lng;
        this.placeName = placeName;
        this.vicinity = vicinity;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }
}
