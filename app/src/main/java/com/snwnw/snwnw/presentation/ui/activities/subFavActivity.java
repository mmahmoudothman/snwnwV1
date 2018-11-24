package com.snwnw.snwnw.presentation.ui.activities;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.snwnw.snwnw.R;
import com.snwnw.snwnw.domain.models.favInterestModel;
import com.snwnw.snwnw.domain.models.place_model;
import com.snwnw.snwnw.domain.models.places_result;
import com.snwnw.snwnw.domain.models.service_cat_model;
import com.snwnw.snwnw.presentation.presenters.impl.categoriesPresenter;
import com.snwnw.snwnw.presentation.presenters.impl.myFavsPresenter;
import com.snwnw.snwnw.presentation.presenters.interfaces.categoryPresenterListener;
import com.snwnw.snwnw.presentation.presenters.interfaces.favPresenterListener;
import com.snwnw.snwnw.presentation.presenters.interfaces.placesPrestenerListener;
import com.snwnw.snwnw.presentation.ui.adapters.favAdapter;
import com.snwnw.snwnw.presentation.ui.adapters.subFavAdapter;
import com.snwnw.snwnw.presentation.utils.Constants;
import com.snwnw.snwnw.presentation.utils.SNWNWPrefs;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fifi elshafie on 6/24/2018.
 */

public class subFavActivity extends AppCompatActivity implements placesPrestenerListener , categoryPresenterListener
, favPresenterListener {
    @BindView(R.id.nav_icon)
    ImageView nav_icon ;
    int  CatID ;
    @BindView(R.id.myFavRecycler)
    RecyclerView myFavRecycler;
    ArrayList<service_cat_model> Services;
    ArrayList<Integer>myNewFav = new ArrayList<>();
    @BindView(R.id.save_btn)
    Button save_btn ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_fav);
        ButterKnife.bind(this);
        CatID = getIntent().getIntExtra("catid",0);
        Log.i("iamcatid",CatID+"") ;
        nav_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getCategories(CatID);
        getMyFav();

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i =0 ;i<Services.size() ; i++) {

                    if (Services.get(i).getFav() != null) {
                        if (Services.get(i).getFav() == true) {
                            myNewFav.add(Services.get(i).getId());
                        }
                    }
                }

                addNewFav(myNewFav);
            }
        });
    }

    public void getCategories(int id ){
        categoriesPresenter presenter = new categoriesPresenter(subFavActivity.this,subFavActivity.this);
        HashMap<String,Object> params = new HashMap<>();
        params.put("lang","ar");
        params.put("id",id);
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

      //  setUpFavList(allservices);
        this.Services = allservices;
        getMyFav();
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
    public void setUpFavList(ArrayList<service_cat_model>allcats,ArrayList<Integer>allFavs){
        //ArrayList<service_cat_model> allcategories ,categoryPresenterListener listener)
        //  ArrayList<service_cat_model> allcategories = new ArrayList<>() ;
        myFavRecycler.setLayoutManager(new LinearLayoutManager(this));
        subFavAdapter adapter = new subFavAdapter(allcats,allFavs,subFavActivity.this);
        //  adapter.setClickListener(this);
        myFavRecycler.setAdapter(adapter);
    }

    @Override
    public void onCategoryClicked(service_cat_model model) {

    }

    @Override
    public void onSwitchClicked(service_cat_model model, Boolean status,int pos) {
        Services.get(pos).setFav(status);
    }

    public void getMyFav(){

        HashMap<String , Object>Params = new HashMap<>();
        Params.put("take",50) ;
        Params.put("offset",0) ;
        myFavsPresenter presenter = new myFavsPresenter(subFavActivity.this,subFavActivity.this);
        String Token = "Bearer"+" "+ SNWNWPrefs.getDefaults(Constants.Token,subFavActivity.this);
     //  String Token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkZXZpY2VfaWQiOiIxMjU1NTUzZWZmNDV2dnZ2NiIsInN1YiI6NjgsImlzcyI6Imh0dHA6Ly9zbmF3bmF3YXBwLmNvbS9hcGkvbG9naW4iLCJpYXQiOjE1MzAxMTEzNjEsImV4cCI6MTcyODk3NTM2MSwibmJmIjoxNTMwMTExMzYxLCJqdGkiOiJCWHRjbnpMNjJnOFNkeEs0In0.A9Hoiy2gS_39SKT7dCQe6oei8hS7Mogbv_IvcI0dFa4";

        presenter.loadMyFav(Params,Token);

    }

    public void addNewFav(ArrayList<Integer>itemList){
        HashMap<String , Object>Params = new HashMap<>();
        Params.put("interests",itemList) ;
        myFavsPresenter presenter = new myFavsPresenter(subFavActivity.this,subFavActivity.this);
        String Token = "Bearer"+" "+ SNWNWPrefs.getDefaults(Constants.Token,subFavActivity.this);
        //  String Token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkZXZpY2VfaWQiOiIxMjU1NTUzZWZmNDV2dnZ2NiIsInN1YiI6NjgsImlzcyI6Imh0dHA6Ly9zbmF3bmF3YXBwLmNvbS9hcGkvbG9naW4iLCJpYXQiOjE1MzAxMTEzNjEsImV4cCI6MTcyODk3NTM2MSwibmJmIjoxNTMwMTExMzYxLCJqdGkiOiJCWHRjbnpMNjJnOFNkeEs0In0.A9Hoiy2gS_39SKT7dCQe6oei8hS7Mogbv_IvcI0dFa4";
        presenter.addToMyFav(Params,Token);

    }

    @Override
    public void onFavsLoadSuccess(favInterestModel model) {

        setUpFavList(Services,model.getInterests());

    }


    @Override
    public void onFavsLoadedFailed(int code, String error) {
        Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onAddedDone(String msg) {
        getMyFav();
    }

    @Override
    public void onAddedFailed(String msg) {

        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
}
