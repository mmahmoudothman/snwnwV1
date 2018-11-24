package com.snwnw.snwnw.presentation.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snwnw.snwnw.R;
import com.snwnw.snwnw.domain.models.place_model;
import com.snwnw.snwnw.domain.models.places_result;
import com.snwnw.snwnw.domain.models.service_cat_model;
import com.snwnw.snwnw.presentation.presenters.impl.categoriesPresenter;
import com.snwnw.snwnw.presentation.presenters.interfaces.categoryPresenterListener;
import com.snwnw.snwnw.presentation.presenters.interfaces.placesPrestenerListener;
import com.snwnw.snwnw.presentation.ui.activities.filterActivity;
import com.snwnw.snwnw.presentation.ui.activities.testAcitivty;
import com.snwnw.snwnw.presentation.ui.adapters.MyRecyclerViewAdapter;
import com.snwnw.snwnw.presentation.utils.EqualSpacingItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by afaf.elshafey on 11/6/2017.
 */

public class categoriesFragment extends Fragment implements placesPrestenerListener, categoryPresenterListener {
    @BindView(R.id.filterlist)
    RecyclerView filterlist;
    MyRecyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_filter, container, false);
        ButterKnife.bind(this, view);
        ((testAcitivty)getActivity()).setHeader(getResources().getString(R.string.ic_search));

        getCategories();
        return view;
    }

    public static categoriesFragment newInstance(String text) {
        categoriesFragment f = new categoriesFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }

    public void getCategories() {
        categoriesPresenter presenter = new categoriesPresenter(categoriesFragment.this, getActivity());
        HashMap<String, Object> params = new HashMap<>();
        params.put("lang", "ar");
        params.put("id", 0);
        params.put("take", "50");
        params.put("offset", 0);
        presenter.getCategories(params);
    }

    @Override
    public void onCategoryClicked(service_cat_model model) {

        replaceME();
    }

    @Override
    public void onSwitchClicked(service_cat_model model, Boolean status,int pos) {

    }

    @Override
    public void onPlaceUploadDone(Boolean Result) {

    }

    @Override
    public void onPlaceUploadFailed(int code,String Result) {

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

    public void updateView(ArrayList<service_cat_model> allserivces) {
        int numberOfColumns = 4;
        filterlist.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
        //filterlist.addItemDecoration(new EqualSpacingItemDecoration(16, EqualSpacingItemDecoration.HORIZONTAL)); // 16px. In practice, you'll want to use getDimensionPixelSize

        adapter = new MyRecyclerViewAdapter(allserivces, categoriesFragment.this);
        //adapter.setClickListener(this);
        filterlist.setAdapter(adapter);
    }
    public void replaceME() {
        // Create fragment and give it an argument specifying the article it should show
        ((testAcitivty)getActivity()).replace_fragment(searchFragment.newInstance(""));
    }
}
