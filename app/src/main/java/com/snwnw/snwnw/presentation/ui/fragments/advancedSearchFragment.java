package com.snwnw.snwnw.presentation.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.snwnw.snwnw.R;
import com.snwnw.snwnw.domain.models.homeCitiesModel;
import com.snwnw.snwnw.domain.models.homeCountriesModel;
import com.snwnw.snwnw.domain.models.place_model;
import com.snwnw.snwnw.domain.models.places_result;
import com.snwnw.snwnw.domain.models.service_cat_model;
import com.snwnw.snwnw.domain.models.user_model;
import com.snwnw.snwnw.presentation.presenters.impl.RegisterPresenter;
import com.snwnw.snwnw.presentation.presenters.impl.categoriesPresenter;
import com.snwnw.snwnw.presentation.presenters.impl.searchPresenter;
import com.snwnw.snwnw.presentation.presenters.interfaces.LoginPresenterListener;
import com.snwnw.snwnw.presentation.presenters.interfaces.placesPrestenerListener;
import com.snwnw.snwnw.presentation.presenters.interfaces.searchPresenterListener;
import com.snwnw.snwnw.presentation.ui.activities.testAcitivty;
import com.snwnw.snwnw.presentation.ui.adapters.citiesAdapter;
import com.snwnw.snwnw.presentation.ui.adapters.spinnerArrayAdapter;
import com.snwnw.snwnw.presentation.utils.Constants;
import com.snwnw.snwnw.presentation.utils.SNWNWPrefs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by afaf.elshafey on 11/6/2017.
 */

public class advancedSearchFragment extends Fragment implements LoginPresenterListener,
        placesPrestenerListener, searchPresenterListener {

    ArrayList<homeCitiesModel.countryModel> cities;
    ArrayList<service_cat_model> all_types;
    ArrayList<service_cat_model> sub_types;

    @BindView(R.id.city_spinner)
    Spinner city_spinner;
    @BindView(R.id.type_spinner)
    Spinner type_spinner;
    @BindView(R.id.sub_type)
    Spinner sub_type;
    @BindView(R.id.search_place_btn)
    Button search_place_btn;
    @BindView(R.id.header_search_txt)
    EditText header_search_txt ;
    int selected_city = 0 ;
    int selected_type = 0 ;
    int Selected_sub_type =0 ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_advanced_search, container, false);
        ButterKnife.bind(this, view);
        getCities();
        getAllCategories();

        search_place_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Integer> list = new ArrayList<>();
                list.add(all_types.get(selected_type).getId());
                list.add(sub_types.get(Selected_sub_type).getId());

                HashMap<String, Object> Params = new HashMap<>();
                Params.put("take", 30);
                Params.put("offset", 0);
                Params.put("lang", "ar");
                Params.put("name",header_search_txt.getText().toString().trim());
                Params.put("city_id", cities.get(selected_city).getId());
                Params.put("category_id", list);
                searchNow(Params);
                // ((testAcitivty)getActivity()).replace_fragment(searchResultFragment.newInstance(""));
            }
        });

        return view;
    }

    public static advancedSearchFragment newInstance(String text) {
        advancedSearchFragment f = new advancedSearchFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }

    public void getCities() {
        HashMap<String, Object> Params = new HashMap<>();
        // Params.put("offset", 0);
        // Params.put("take", 10);
        Params.put("lang", "ar");
        RegisterPresenter presenter = new RegisterPresenter(advancedSearchFragment.this, getActivity());
        presenter.loadCities(Params);

    }

    @Override
    public void onRegisterSuccess(String Token) {

    }

    @Override
    public void onRegistrFailed(String Error) {

    }

    @Override
    public void onLoginSuccess(user_model user_model) {

    }

    @Override
    public void onLoginFailed(String Error) {

    }

    @Override
    public void onForgetPassSuccess(String Msg) {

    }

    @Override
    public void onForgetPassFail(String Msg) {

    }

    @Override
    public void onHomeCountriesLoadedSuccess(homeCountriesModel model) {

    }

    @Override
    public void onHomeCountriesLoadedFailed(String error) {

    }

    @Override
    public void onHomeCitiesLoadedSuccess(homeCitiesModel model) {

        cities = model.getCountries();
        Log.i("iamciires", model.getCountries().size() + "");
        loadHomeCitiesSpinner(model.getCountries());
    }

    @Override
    public void onHomeCitiesLoadedFailed(String error) {

    }

    public void loadHomeCitiesSpinner(ArrayList<homeCitiesModel.countryModel> Countries) {
        final citiesAdapter customAdapter = new citiesAdapter(getActivity(), Countries);
        city_spinner.setAdapter(customAdapter);

        city_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                selected_city = i ;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    public void getAllCategories() {

        categoriesPresenter presenter = new categoriesPresenter(advancedSearchFragment.this, getActivity());
        HashMap<String, Object> Params = new HashMap<>();
        Params.put("lang", "ar");
        Params.put("id", 0);
        Params.put("take", 10);
        Params.put("offset", 0);
        Params.put("offset", 0);
        presenter.getCategories(Params);
    }

    public void getSubCategories(int ID) {

        categoriesPresenter presenter = new categoriesPresenter(advancedSearchFragment.this, getActivity());
        HashMap<String, Object> Params = new HashMap<>();
        Params.put("lang", "ar");
        Params.put("id", ID);
        Params.put("take", 10);
        Params.put("offset", 0);
        Params.put("offset", 0);
        Log.i("paraaams", Params + "");
        presenter.getSubCategories(Params);
    }


    @Override
    public void onPlaceUploadDone(Boolean Result) {

    }

    @Override
    public void onPlaceUploadFailed(int code, String result) {

    }

    @Override
    public void getAllCategories(ArrayList<service_cat_model> allservices) {
        all_types = allservices;
        setUpSpinner(all_types);
        getSubCategories(allservices.get(0).getId());
    }

    @Override
    public void getAllSubCategories(ArrayList<service_cat_model> allservices) {

        sub_types = allservices;
        setUpSubSpinner(sub_types);

    }

    @Override
    public void onLoadPlacesSearchPlaces(places_result places_result) {

        Log.i("iamsearch", places_result.getAllplaces().size() + "");
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", places_result.getAllplaces());

        ((testAcitivty) getActivity()).replace_fragment(searchResultFragment.newInstance(places_result.getAllplaces()));

        // intent.putExtras(bundle);

    }

    @Override
    public void onLoadFailed(int Code, String msg) {
        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.search_empty) + "", Toast.LENGTH_SHORT).show();

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

    public void setUpSpinner(ArrayList<service_cat_model> AllServices) {
        // Creating adapter for spinner
        //ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, AllServices);
        ArrayList<String> SpinnerData = new ArrayList<>();

        for (int i = 0; i < AllServices.size(); i++) {

            SpinnerData.add(AllServices.get(i).getName());
        }
        spinnerArrayAdapter adapter = new spinnerArrayAdapter(getActivity(), SpinnerData);
        //  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type_spinner.setAdapter(adapter);
        type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                selected_type = position ;
                if (position != 0) {
                    getSubCategories(all_types.get(position).getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


    }

    public void setUpSubSpinner(ArrayList<service_cat_model> AllServices) {
        // Creating adapter for spinner
        //ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, AllServices);
        ArrayList<String> SpinnerData = new ArrayList<>();

        for (int i = 0; i < AllServices.size(); i++) {

            SpinnerData.add(AllServices.get(i).getName());
        }
        spinnerArrayAdapter adapter = new spinnerArrayAdapter(getActivity(), SpinnerData);
        // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sub_type.setAdapter(adapter);
        sub_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                Selected_sub_type = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


    }

    public void searchNow(HashMap<String, Object> Params) {
    /*    List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(3);
*/

        searchPresenter presenter = new searchPresenter(advancedSearchFragment.this, getActivity());
       /* HashMap<String ,Object>Params = new HashMap<>() ;
        Params.put("take",10);
        Params.put("offset",0);
        Params.put("city_id",2);
        Params.put("name","e");
        Params.put("lang","ar") ;
        Params.put("category_id", list);*/

        String Token = "Bearer" + " " + SNWNWPrefs.getDefaults(Constants.Token, getActivity());
        presenter.searchNow(Params, Token);
    }

}
