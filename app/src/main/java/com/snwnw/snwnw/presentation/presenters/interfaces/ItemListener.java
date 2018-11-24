package com.snwnw.snwnw.presentation.presenters.interfaces;

import com.snwnw.snwnw.domain.models.place_model;

/**
 * Created by fifi elshafie on 2/24/2018.
 */

public interface ItemListener  {

    public void onAddToFave(int itemId);
    public void onAllRowClicked(place_model model) ;
   // public void refreshView () ;
}
