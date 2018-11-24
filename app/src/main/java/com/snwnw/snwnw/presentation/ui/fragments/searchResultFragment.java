package com.snwnw.snwnw.presentation.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.snwnw.snwnw.R;
import com.snwnw.snwnw.domain.models.place_model;
import com.snwnw.snwnw.domain.models.places_result;
import com.snwnw.snwnw.domain.models.service_cat_model;
import com.snwnw.snwnw.presentation.presenters.impl.placesPresenter;
import com.snwnw.snwnw.presentation.presenters.interfaces.ItemListener;
import com.snwnw.snwnw.presentation.presenters.interfaces.placesPrestenerListener;
import com.snwnw.snwnw.presentation.ui.activities.testAcitivty;
import com.snwnw.snwnw.presentation.ui.adapters.favFilterAdapter;
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

public class searchResultFragment extends Fragment implements placesPrestenerListener ,ItemListener {
    @BindView(R.id.search_list)
    RecyclerView search_list;
    @BindView(R.id.noitems)
    TextView noitems;
    @BindView(R.id.header_search_txt)
    EditText header_search_txt;
    favFilterAdapter adapter;
    ArrayList<place_model> Places ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container , Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this,view);
        ((testAcitivty)getActivity()).setHeader(getResources().getString(R.string.side_fav));

       // getMyFavList();

        Places =(ArrayList<place_model>) getArguments().getSerializable("data") ;


        updateView(Places);

        header_search_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                try {
                    adapter.getFilter().filter(charSequence.toString());
                }
                catch (Exception e){

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        return view ;
    }

    public static searchResultFragment newInstance(ArrayList<place_model>allplaces) {
        searchResultFragment f = new searchResultFragment();
        Bundle b = new Bundle();
        b.putSerializable("data", allplaces);
        f.setArguments(b);
        return f;
    }
  /*  public void getMyFavList(){
        placesPresenter placesPresenter =  new placesPresenter(searchResultFragment.this,getActivity());
        HashMap<String,Object> Params = new HashMap<>();
        Params.put("take",50);
        Params.put("offset",0);
        Params.put("lang","ar");
        String AuthorizedToken = "Bearer" + " " + SNWNWPrefs.getDefaults(Constants.Token, getActivity());
        Log.i("iamthetoken",AuthorizedToken) ;
        placesPresenter.myFavList(Params,AuthorizedToken);
    }*/

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

        if (places_result.getAllplaces().size()!=0) {
            noitems.setVisibility(View.GONE);
            search_list.setVisibility(View.VISIBLE);
            updateView(places_result.getAllplaces());
        }
        else {
            noitems.setVisibility(View.VISIBLE);
            search_list.setVisibility(View.GONE);

        }
    }

    @Override
    public void onPlaceDetailsLoaded(place_model place_model) {

    }

    @Override
    public void onUpdateFail(int Code) {

    }

    @Override
    public void onFavBtnClicked(String msg) {
       // getMyFavList();
      //  updateView(Places);
    }
    public void updateView (ArrayList<place_model>allPlaces) {

        adapter = new favFilterAdapter(allPlaces,searchResultFragment.this,getActivity(),Constants.showitem);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        search_list.setLayoutManager(mLayoutManager);
        search_list.setItemAnimator(new DefaultItemAnimator());
        search_list.setAdapter(adapter);

    }

    @Override
    public void onAddToFave(int itemId) {
        placesPresenter placesPresenter =  new placesPresenter(searchResultFragment.this,getActivity());
        HashMap<String,Object>Params = new HashMap<>();
        Params.put("place_id",itemId);
        String AuthorizedToken = "Bearer" + " " + SNWNWPrefs.getDefaults(Constants.Token, getActivity());
        Log.i("iamthetoken",AuthorizedToken) ;
        placesPresenter.addPlaceToFav(Params,AuthorizedToken);
        if (Places.get(itemId).getIs_favourite() == 0){
            Places.get(itemId).setIs_favourite(1) ;
        }
        else {
            Places.get(itemId).setIs_favourite(0);
        }

        updateView(Places);
    }

    @Override
    public void onAllRowClicked(place_model model) {

        Intent go = new Intent(getActivity(),placeDetailsFragment.class) ;
        go.putExtra("placeid",model.getId());
        startActivity(go);
        // ((testAcitivty)getActivity()).replace_fragment(placeDetailsFragment.newInstance(model.getId()));
    }
}
