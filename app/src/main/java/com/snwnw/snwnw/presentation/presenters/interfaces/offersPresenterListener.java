package com.snwnw.snwnw.presentation.presenters.interfaces;

import com.snwnw.snwnw.domain.models.offerModel;

/**
 * Created by fifi elshafie on 6/30/2018.
 */

public interface offersPresenterListener {
    public void onOffersLoaded (offerModel offerModel) ;
    public void onFailed (int codeError , String error) ;
}
