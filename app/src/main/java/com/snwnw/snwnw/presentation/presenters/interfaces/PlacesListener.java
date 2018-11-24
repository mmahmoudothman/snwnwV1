package com.snwnw.snwnw.presentation.presenters.interfaces;

import com.snwnw.snwnw.domain.models.expand_place_model;

import java.util.ArrayList;

/**
 * Created by afaf.elshafey on 1/16/2018.
 */

public interface PlacesListener  {
    public void onPlacesLoaded(String ServiceName,expand_place_model all_places);
    public void onPlacesFailed(String error) ;
}
