package com.snwnw.snwnw.presentation.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snwnw.snwnw.R;
import com.snwnw.snwnw.domain.models.place_model;
import com.snwnw.snwnw.domain.models.places_result;
import com.snwnw.snwnw.domain.models.service_cat_model;
import com.snwnw.snwnw.presentation.presenters.impl.placesPresenter;
import com.snwnw.snwnw.presentation.presenters.interfaces.ItemListener;
import com.snwnw.snwnw.presentation.presenters.interfaces.placesPrestenerListener;
import com.snwnw.snwnw.presentation.ui.activities.testAcitivty;
import com.snwnw.snwnw.presentation.ui.adapters.itemAdapter;
import com.snwnw.snwnw.presentation.utils.Constants;
import com.snwnw.snwnw.presentation.utils.SNWNWPrefs;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by afaf.elshafey on 11/6/2017.
 */

public class uploadedPlacesFragment extends Fragment implements placesPrestenerListener ,ItemListener {
    @BindView(R.id.search_list)
    RecyclerView search_list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container , Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this,view);
        ((testAcitivty)getActivity()).setHeader(getResources().getString(R.string.side_my_places));

        getUploadedPlaces();
        return view ;
    }

    public static uploadedPlacesFragment newInstance(String text) {
        uploadedPlacesFragment f = new uploadedPlacesFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }
    public void getUploadedPlaces(){
            placesPresenter placesPresenter =  new placesPresenter(uploadedPlacesFragment.this,getActivity());
            HashMap<String,Object> Params = new HashMap<>();
            Params.put("take",50);
            Params.put("offset",0);
            Params.put("lang","ar");
            String AuthorizedToken = "Bearer" + " " + SNWNWPrefs.getDefaults(Constants.Token, getActivity());
            Log.i("iamthetoken",AuthorizedToken) ;
            placesPresenter.myUploadedPlaces(Params,AuthorizedToken);

    }

    @Override
    public void onPlaceUploadDone(Boolean Result) {

    }

    @Override
    public void onPlaceUploadFailed(int code,String Result) {

    }

    @Override
    public void getAllCategories(ArrayList<service_cat_model> allservices) {

    }

    @Override
    public void getAllSubCategories(ArrayList<service_cat_model> allservices) {

    }

    @Override
    public void onLoadPlacesSearchPlaces(places_result places_result) {
        updateView(places_result.getAllplaces());
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
    public void updateView (ArrayList<place_model>allPlaces) {

        itemAdapter adapter = new itemAdapter(allPlaces,uploadedPlacesFragment.this,getActivity(),Constants.hideitem);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        search_list.setLayoutManager(mLayoutManager);
        search_list.setItemAnimator(new DefaultItemAnimator());
        search_list.setAdapter(adapter);

    }

    @Override
    public void onAddToFave(int itemId) {

    }

    @Override
    public void onAllRowClicked(place_model model) {
      //  ((testAcitivty)getActivity()).replace_fragment(placeDetailsFragment.newInstance(model.getId()));
        Intent go = new Intent(getActivity(),placeDetailsFragment.class) ;
        go.putExtra("placeid",model.getId());
        startActivity(go);
    }
}
