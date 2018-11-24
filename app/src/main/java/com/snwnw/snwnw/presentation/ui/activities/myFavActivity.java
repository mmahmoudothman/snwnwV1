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
import com.snwnw.snwnw.presentation.ui.adapters.favAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fifi elshafie on 6/23/2018.
 */

public class myFavActivity extends AppCompatActivity implements categoryPresenterListener ,placesPrestenerListener {

    @BindView(R.id.myFavRecycler)
    RecyclerView myFavRecycler ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fav);
        ButterKnife.bind(this) ;
        //setUpFavList();
        getCategories();
    }

    public void setUpFavList(ArrayList<service_cat_model>allcats){
        //ArrayList<service_cat_model> allcategories ,categoryPresenterListener listener)
      //  ArrayList<service_cat_model> allcategories = new ArrayList<>() ;
        int numberOfColumns = 3;
        myFavRecycler.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        favAdapter  adapter = new favAdapter(allcats,myFavActivity.this);
      //  adapter.setClickListener(this);
        myFavRecycler.setAdapter(adapter);
    }

    @Override
    public void onCategoryClicked(service_cat_model model) {

        Log.i("click","clicked") ;
        Intent i = new Intent(myFavActivity.this,subFavActivity.class) ;
        i.putExtra("catid",model.getId()) ;
        startActivity(i);
    }

    @Override
    public void onSwitchClicked(service_cat_model model, Boolean status,int pos) {

    }

    public void getCategories(){
        categoriesPresenter presenter = new categoriesPresenter(myFavActivity.this,myFavActivity.this);
        HashMap<String,Object> params = new HashMap<>();
        params.put("lang","ar");
        params.put("id",0);
        params.put("take","20");
        params.put("offset",0);
        presenter.getCategories(params);
    }

    @Override
    public void onPlaceUploadDone(Boolean Result) {

    }

    @Override
    public void onPlaceUploadFailed(int code, String result) {

    }

    @Override
    public void getAllCategories(ArrayList<service_cat_model> allservices) {
        Log.i("iamsss",allservices.size()+"");
        setUpFavList(allservices) ;
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
}
