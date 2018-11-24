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

public class CustomArrayAdapter extends ArrayAdapter<homeCountriesModel.countryModel> {
    // View lookup cache
    ArrayList<homeCountriesModel.countryModel>Countries ;
    private static class ViewHolder {
        TextView name;
        //   TextView home;
    }

    public CustomArrayAdapter(Context context, ArrayList<homeCountriesModel.countryModel> users) {
        super(context, R.layout.spinner_raw, users);
        this.Countries = users ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data menu_item for this position
        String user = Countries.get(position).getName();
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder = null; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.spinner_raw, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.catname);
            viewHolder.name.setText(user);
            // viewHolder.home = (TextView) convertView.findViewById(R.id.tvHome);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        }
        /*else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }*/
        // Populate the data from the data object via the viewHolder object
        // into the template view.
       // viewHolder.name.setText(user);
        //  viewHolder.home.setText(user.hometown);
        // Return the completed view to render on screen
        return convertView;
    }
}
