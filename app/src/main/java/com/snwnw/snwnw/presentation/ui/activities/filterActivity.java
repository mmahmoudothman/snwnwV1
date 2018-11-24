package com.snwnw.snwnw.presentation.ui.activities;

import android.content.Intent;
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
 * Created by fifi elshafie on 2/17/2018.
 */

public class filterActivity extends AppCompatActivity implements placesPrestenerListener ,categoryPresenterListener {
    @BindView(R.id.filterlist)
    RecyclerView filterlist ;
    MyRecyclerViewAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this) ;
        getCategories();
    }



    public void getCategories(){
        categoriesPresenter presenter = new categoriesPresenter(filterActivity.this,filterActivity.this);
        HashMap<String,Object>params = new HashMap<>();
        params.put("lang","ar");
        params.put("id",0);
        params.put("take","12");
        params.put("offset",0);
        presenter.getCategories(params);
    }

    @Override
    public void onPlaceUploadDone(Boolean Result) {

    }

    @Override
    public void onPlaceUploadFailed(int code ,String Result) {

    }

    @Override
    public void getAllCategories(ArrayList<service_cat_model> allservices) {
        Log.i("iamsss",allservices.size()+"");
        updateView(allservices) ;
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
        adapter = new MyRecyclerViewAdapter(allserivces ,filterActivity.this);
        //adapter.setClickListener(this);
        filterlist.setAdapter(adapter);

    }

    @Override
    public void onCategoryClicked(service_cat_model model) {
        Intent go = new Intent(filterActivity.this, subCategoriesActivity.class) ;
        go.putExtra("catid",model.getId());
        startActivity(go);

    }

    @Override
    public void onSwitchClicked(service_cat_model model, Boolean status,int pos) {

    }
}
