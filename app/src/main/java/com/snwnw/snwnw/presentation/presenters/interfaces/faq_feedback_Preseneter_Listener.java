package com.snwnw.snwnw.presentation.presenters.interfaces;

import com.snwnw.snwnw.domain.models.FAQ_result_model;

import java.util.ArrayList;

/**
 * Created by fifi elshafie on 3/10/2018.
 */

public interface faq_feedback_Preseneter_Listener {

    public  void onTokenSuccess(String resultcode);
    public void  onTokenFailed (int code ,String Result);
    public void onFAQSLoaded (FAQ_result_model model);
    public void onFAQSFailed (int code,String result);
}
