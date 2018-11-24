package com.snwnw.snwnw.presentation.ui.adapters;

/**
 * Created by fifi elshafie on 2/6/2018.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.snwnw.snwnw.R;
import com.snwnw.snwnw.domain.models.profileResult;
import com.snwnw.snwnw.domain.models.profileSetupModel;

import java.util.List;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {

    private List<profileSetupModel> userData;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView userKey;
        public EditText userVal ;
        public MyViewHolder(View view) {
            super(view);
            userKey = (TextView) view.findViewById(R.id.data_lbl);
            userVal = (EditText) view.findViewById(R.id.userval);
        }
    }


    public recyclerAdapter(List<profileSetupModel> userData) {
        this.userData = userData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profile_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        profileSetupModel user = userData.get(position);
        holder.userKey.setText(user.getUserKey());
        holder.userVal.setText(user.getUserVal());
       /* holder.title.setText(movie.getTitle());
        holder.genre.setText(movie.getGenre());
        holder.year.setText(movie.getYear());*/
    }

    @Override
    public int getItemCount() {
        return userData.size();
    }
}