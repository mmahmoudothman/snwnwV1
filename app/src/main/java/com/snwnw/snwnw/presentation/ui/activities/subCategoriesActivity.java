package com.snwnw.snwnw.presentation.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.snwnw.snwnw.R;
import com.snwnw.snwnw.domain.models.place_model;
import com.snwnw.snwnw.domain.models.places_result;
import com.snwnw.snwnw.domain.models.service_cat_model;
import com.snwnw.snwnw.presentation.presenters.impl.categoriesPresenter;
import com.snwnw.snwnw.presentation.presenters.interfaces.categoryPresenterListener;
import com.snwnw.snwnw.presentation.presenters.interfaces.placesPrestenerListener;
import com.snwnw.snwnw.presentation.ui.adapters.MyRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fifi elshafie on 2/23/2018.
 */

public class subCategoriesActivity extends AppCompatActivity implements placesPrestenerListener,categoryPresenterListener {
    int CatID ;
    @BindView(R.id.filterlist)
    RecyclerView filterlist ;
    MyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);
        CatID = getIntent().getIntExtra("catid",0);
        Log.i("iamcat",CatID+"") ;
        getCategories(CatID);

    }
    public void getCategories(int id){
        categoriesPresenter presenter = new categoriesPresenter(subCategoriesActivity.this,subCategoriesActivity.this);
        HashMap<String,Object> params = new HashMap<>();
        params.put("lang","ar");
        params.put("id",id);
        params.put("take","100");
        params.put("offset",0);
        presenter.getCategories(params);
    }

    @Override
    public void onCategoryClicked(service_cat_model model) {

    }

    @Override
    public void onSwitchClicked(service_cat_model model, Boolean status,int pos) {

    }

    @Override
    public void onPlaceUploadDone(Boolean Result) {

    }

    @Override
    public void onPlaceUploadFailed(int code ,String Result) {

    }

    @Override
    public void getAllCategories(ArrayList<service_cat_model> allservices) {

        updateView(allservices);
    }

    @Override
    public void getAllSubCategories(ArrayList<service_cat_model> allservices) {

    }

    @Override
    public void onLoadPlacesSearchPlaces(places_result places_result) {


    }

    @Override
    public void onPlaceDetailsLoaded(place_model place_model) {

    }

    @Override
    public void onUpdateFail(int Code) {

    }

    @Override
    public void onFavBtnClicked(String msg) {

    }

    public void updateView(ArrayList<service_cat_model>allserivces){
        int numberOfColumns = 6;
        filterlist.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new MyRecyclerViewAdapter(allserivces ,subCategoriesActivity.this);
        //adapter.setClickListener(this);
        filterlist.setAdapter(adapter);

    }
}
