package com.snwnw.snwnw.presentation.presenters.impl;

/**
 * Created by elmalah on 3/5/2017.
 */
/*
public class HomeCategoriesPresenter implements MainPresenter, ResponseCallback.MYCallback {
    MainPresenter.PresenterCallBack presenterCallBack;
    ResponseCallback responseCallback;

    public HomeCategoriesPresenter(Retrofit retrofit, String lang, PresenterCallBack presenterCallBack) {
        this.presenterCallBack = presenterCallBack;
        this.responseCallback = new HomeCategoriesInteractor(retrofit,lang, this, presenterCallBack);
    }

    @Override
    public void success(Object data) {
        presenterCallBack.updateView(data);
    }

    @Override
    public void error(Throwable t) {
        presenterCallBack.showConnectionError(t);
    }

    /*
    this is the LifeCyclePresenter Methods
     */
/*    @Override
    public void onStart() {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        responseCallback.unsubscribe();
    }*/

