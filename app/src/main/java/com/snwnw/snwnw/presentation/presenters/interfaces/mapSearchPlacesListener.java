package com.snwnw.snwnw.presentation.presenters.interfaces;

import com.snwnw.snwnw.domain.models.mapsPlacesLoaderModel;

/**
 * Created by fifi elshafie on 6/30/2018.
 */

public interface mapSearchPlacesListener
{
     public void  onMapPlacesLoaded(mapsPlacesLoaderModel mapsPlacesLoaderModel) ;
     public void onMapPlacesFailed (int code, String Error) ;
}
