package com.snwnw.snwnw.presentation.ui.adapters;

/**
 * Created by fifi elshafie on 6/10/2018.
 */

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.snwnw.snwnw.R;
import com.snwnw.snwnw.domain.models.homeCitiesModel;
import com.snwnw.snwnw.domain.models.homeCountriesModel;

import java.util.ArrayList;
/**
 * Created by fifi elshafie on 6/9/2018.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.snwnw.snwnw.R;
import com.snwnw.snwnw.domain.models.homeCountriesModel;

import java.util.ArrayList;

public class citiesAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflter;
    ArrayList<homeCitiesModel.countryModel> countries;

      ImageView arrow_item  ;
    public citiesAdapter(Context applicationContext, ArrayList<homeCitiesModel.countryModel> countries) {
        this.context = applicationContext;
        this.countries = countries;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return countries.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.spinner_raw, null);
        // ImageView icon = (ImageView) view.findViewById(R.id.imageView);
        TextView names = (TextView) view.findViewById(R.id.catname);
        arrow_item = (ImageView)view.findViewById(R.id.arrow_item) ;
        // icon.setImageResource(flags[i]);
        names.setText(countries.get(i).getName());
        return view;
    }

    public void hideArrow(){
        if (arrow_item!=null) {
            arrow_item.setVisibility(View.GONE);
        }
    }
}
