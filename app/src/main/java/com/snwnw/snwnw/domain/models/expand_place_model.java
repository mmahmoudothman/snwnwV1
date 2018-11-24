package com.snwnw.snwnw.domain.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by afaf.elshafey on 1/16/2018.
 */

public class expand_place_model {

    @SerializedName("serviceName")
    String ServiceName ;
    @SerializedName("stores")
    ArrayList<places_model>AllPlaces ;

    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String serviceName) {
        ServiceName = serviceName;
    }

    public ArrayList<places_model> getAllPlaces() {
        return AllPlaces;
    }

    public void setAllPlaces(ArrayList<places_model> allPlaces) {
        AllPlaces = allPlaces;
    }

    public expand_place_model(String serviceName, ArrayList<places_model> allPlaces) {
        ServiceName = serviceName;
        AllPlaces = allPlaces;
    }
}
