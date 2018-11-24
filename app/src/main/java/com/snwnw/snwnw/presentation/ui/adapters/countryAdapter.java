package com.snwnw.snwnw.presentation.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.snwnw.snwnw.R;
import com.snwnw.snwnw.domain.models.homeCountriesModel;
import com.snwnw.snwnw.domain.models.service_cat_model;

import java.util.ArrayList;

public class countryAdapter extends ArrayAdapter<String> {
    // View lookup cache
    ArrayList<homeCountriesModel.countryModel>_counteries ;
    Context con ;
    private static class ViewHolder {
        TextView name;
        //   TextView home;
    }

    public countryAdapter(Context context, ArrayList<homeCountriesModel.countryModel> countries) {
        super(context, R.layout.spinner_raw);
        this._counteries = countries ;
        this.con = context ;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

      //  LayoutInflater inflater = con.getLayoutInflater();
        LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View row = inflater.inflate(R.layout.spinner_raw, parent, false);
        TextView catname = (TextView) row.findViewById(R.id.catname);
        catname.setText(_counteries.get(position).getName());
        return row;
    }


    public View getDropDownView(int position, View convertView, ViewGroup parent) {

      //  LayoutInflater inflater = getLayoutInflater();
        LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.spinner_raw, parent,
                false);
        TextView catname = (TextView) row.findViewById(R.id.catname);
        catname.setText(_counteries.get(position).getName());
        return row;
    }
}
