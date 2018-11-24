package com.snwnw.snwnw.presentation.presenters.interfaces;

import com.snwnw.snwnw.domain.models.review_item;

import java.util.ArrayList;

/**
 * Created by fifi elshafie on 3/16/2018.
 */

public interface reviewPresenterListener {
    public void onReviewAdded(String msg);
    public void onReviewFailed(String Errror) ;
    public void onExpiredToken (int Code) ;
    public void onReviewsLoaded (ArrayList<review_item>allreview) ;
}
