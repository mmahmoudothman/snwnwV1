package com.snwnw.snwnw.presentation.ui.fragments;

/**
 * Created by fifi elshafie on 5/30/2018.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.snwnw.snwnw.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.snwnw.snwnw.R;
import com.snwnw.snwnw.domain.models.offerModel;
import com.snwnw.snwnw.domain.models.service_cat_model;
import com.snwnw.snwnw.presentation.presenters.impl.offersPresenter;
import com.snwnw.snwnw.presentation.presenters.interfaces.offersPresenterListener;
import com.snwnw.snwnw.presentation.ui.adapters.Contact;
import com.snwnw.snwnw.presentation.ui.adapters.MyDividerItemDecoration;
import com.snwnw.snwnw.presentation.ui.adapters.MyRecyclerViewAdapter;
import com.snwnw.snwnw.presentation.ui.adapters.filterbaseAdapter;
import com.snwnw.snwnw.presentation.ui.adapters.offersAdapter;
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

public class offersFragment extends Fragment implements filterbaseAdapter.ContactsAdapterListener ,
        offersPresenterListener {
    @BindView(R.id.offersList)
    RecyclerView offersList;
   // offersAdapter adapter ;
    private List<offerModel.offer> contactList;
    private filterbaseAdapter mAdapter;
    @BindView(R.id.header_search_txt)
    EditText header_search_txt ;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container , Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.offers_fragment, container, false);
        ButterKnife.bind(this,view) ;
      //  updateView();
        Log.i("iaminoffers","offers") ;

        contactList = new ArrayList<>();
        mAdapter = new filterbaseAdapter(getActivity(), contactList, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        offersList.setLayoutManager(mLayoutManager);
        offersList.setItemAnimator(new DefaultItemAnimator());
        offersList.addItemDecoration(new MyDividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL, 36));
        offersList.setAdapter(mAdapter);

      //  fetchContacts();

        loadOffers();

        //getActivity().getMenuInflater().inflate(R.menu.menu_item, menu);

       // MenuItem item = menu.findItem(R.id.action_search);
      //  header_search_txt.setMenuItem(item);

      /*  header_search_txt.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                Log.i("iam2",query.toString()) ;
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                Log.i("iam2",newText) ;
                mAdapter.getFilter().filter(newText);

                return false;
            }
        });
*/


      header_search_txt.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

          }

          @Override
          public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

              mAdapter.getFilter().filter(charSequence.toString());


          }

          @Override
          public void afterTextChanged(Editable editable) {

          }
      });

     /*   header_search_txt.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });*/



        return view ;
    }

    public static offersFragment newInstance(String text) {
        offersFragment f = new offersFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }




    public void updateView() {
      /*  adapter = new offersAdapter(getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        offersList.setLayoutManager(mLayoutManager);
        offersList.setItemAnimator(new DefaultItemAnimator());
        offersList.setAdapter(adapter);
*/
    }


    private void fetchContacts(ArrayList<offerModel.offer>Offers) {

        ArrayList<Contact> items = new ArrayList<>();
        items.add(new Contact("fifi","01099559262")) ;
        items.add(new Contact("walid","01099559262")) ;
        items.add(new Contact("ghada","01099559262")) ;
        items.add(new Contact("eman","01099559262")) ;
        items.add(new Contact("mama","01099559262")) ;
        items.add(new Contact("baba","01099559262")) ;
// adding contacts to contacts list
        contactList.clear();
        contactList.addAll(Offers);

        // refreshing recycler view
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void onContactSelected(offerModel.offer contact) {

    }

    public void loadOffers() {
        offersPresenter presenter  =  new offersPresenter(offersFragment.this, getActivity() ) ;
        HashMap<String,Object>Params = new HashMap<>() ;
        Params.put("offset",0) ;
        Params.put("take",20) ;
        Params.put("lang","ar") ;
        presenter.getOffers(Params, SNWNWPrefs.getDefaults(Constants.Token,getActivity()));
    }


    @Override
    public void onOffersLoaded(offerModel offerModel) {

        Log.i("iamsixw",offerModel.getAllOffers().size()+"") ;
        fetchContacts(offerModel.getAllOffers());
    }

    @Override
    public void onFailed(int codeError, String error) {
        Log.i("iamsixw",error+"") ;

        Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();
    }
}
