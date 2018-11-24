package com.snwnw.snwnw.presentation.presenters.interfaces;

/**
 * Created by fifi elshafie on 3/9/2018.
 */

public interface codePresenterListener {
    public  void onTokenSuccess(String result);
    public  void  onTokenFailed(int code,String error);
    public  void  onCodeResent(String result);
}



