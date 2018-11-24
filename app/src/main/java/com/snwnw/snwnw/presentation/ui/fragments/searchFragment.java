package com.snwnw.snwnw.presentation.ui.fragments;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.google.gson.annotations.SerializedName;
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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by afaf.elshafey on 11/6/2017.
 */

public class searchFragment extends Fragment implements placesPrestenerListener ,ItemListener {
    @BindView(R.id.search_list)
    RecyclerView search_list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container , Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this,view);
        searchNow() ;
      //  generateKeyHash();
        return view ;
    }


    public static searchFragment newInstance(String text) {
        searchFragment f = new searchFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }

    public void searchNow(){
        placesPresenter placesPresenter =  new placesPresenter(searchFragment.this,getActivity());
        HashMap<String,Object>Params = new HashMap<>();
        Params.put("take",50);
        Params.put("offset",0);
        Params.put("category_id",0);
        Params.put("name","te");
        Params.put("lang","ar");
        String AuthorizedToken = "Bearer" + " " + SNWNWPrefs.getDefaults(Constants.Token, getActivity());
        Log.i("iamthetoken",AuthorizedToken) ;
        placesPresenter.searchPlaces(Params,AuthorizedToken);
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

        Log.i("iamalldata",places_result.getAllplaces().size()+"");
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
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
        searchNow() ;
    }


    public void updateView (ArrayList<place_model>allPlaces) {

       itemAdapter adapter = new itemAdapter(allPlaces,searchFragment.this,getActivity(),Constants.showitem);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        search_list.setLayoutManager(mLayoutManager);
        search_list.setItemAnimator(new DefaultItemAnimator());
        search_list.setAdapter(adapter);

    }

    @Override
    public void onAddToFave(int itemId) {
        placesPresenter placesPresenter =  new placesPresenter(searchFragment.this,getActivity());
        HashMap<String,Object>Params = new HashMap<>();
        Params.put("place_id",itemId);
        String AuthorizedToken = "Bearer" + " " + SNWNWPrefs.getDefaults(Constants.Token, getActivity());
        Log.i("iamthetoken",AuthorizedToken) ;
        placesPresenter.addPlaceToFav(Params,AuthorizedToken);
    }

    @Override
    public void onAllRowClicked(place_model model) {
        //((testAcitivty)getActivity()).replace_fragment(placeDetailsFragment.newInstance(model.getId()));
    }

    public void generateKeyHash(){
        // Add code to print out the key hash
        try {
            PackageInfo info = getActivity().getPackageManager().getPackageInfo(
                    "com.snwnw.snwnw",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.i("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        }
        catch (PackageManager.NameNotFoundException e) {

            Log.i("iamrheexce",e.getMessage());

        } catch (NoSuchAlgorithmException e) {

        }
    }
}
