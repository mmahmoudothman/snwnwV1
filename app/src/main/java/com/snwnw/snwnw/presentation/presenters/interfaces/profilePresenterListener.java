package com.snwnw.snwnw.presentation.presenters.interfaces;

import com.snwnw.snwnw.domain.models.profileResult;

/**
 * Created by fifi elshafie on 2/6/2018.
 */

public interface profilePresenterListener {

    public void userData (profileResult result);
    public void expiredToken (int Code) ;
    public void onUpdateDone (String msg) ;
    public void onUpdateFail(int code);
}
