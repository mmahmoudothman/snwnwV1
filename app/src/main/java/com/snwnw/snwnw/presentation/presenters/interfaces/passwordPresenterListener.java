package com.snwnw.snwnw.presentation.presenters.interfaces;

import com.snwnw.snwnw.domain.models.profileResult;

/**
 * Created by fifi elshafie on 3/13/2018.
 */

public interface passwordPresenterListener {
    public void expiredToken (int Code) ;
    public void onUpdateDone (String msg) ;

}
