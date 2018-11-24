package com.snwnw.snwnw.presentation.ui.adapters;

/**
 * Created by fifi elshafie on 2/6/2018.
 */

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.snwnw.snwnw.R;
import com.snwnw.snwnw.domain.models.place_model;
import com.snwnw.snwnw.domain.models.profileResult;
import com.snwnw.snwnw.domain.models.profileSetupModel;
import com.snwnw.snwnw.domain.models.review_item;
import com.snwnw.snwnw.presentation.presenters.interfaces.ItemListener;
import com.snwnw.snwnw.presentation.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class commentsAdapter extends RecyclerView.Adapter<commentsAdapter.MyViewHolder> {

    private ArrayList<review_item> allcomments;
    Context _context ;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView item_img ;
        public TextView item_name;
        public TextView item_details;
        public MyViewHolder(View view) {
            super(view);
            item_img =(ImageView)view.findViewById(R.id.item_img);
            item_name =(TextView)view.findViewById(R.id.address);
            item_details=(TextView)view.findViewById(R.id.details);
        }
    }


    public commentsAdapter(ArrayList<review_item> _allcomments,Context context) {
        this.allcomments = _allcomments;
        _context = context ;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        review_item review_item = allcomments.get(position);

        holder.item_name.setText(review_item.getUser().getFirst_name());
        holder.item_details.setText(review_item.getReview());
        Glide
                .with(_context)
                .load(allcomments.get(position).getUser().getImage())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.loading)
                        .fitCenter())
                .into(holder.item_img);

     /*   holder.userKey.setText(user.getUserKey());
        holder.userVal.setText(user.getUserVal());
       /* holder.title.setText(movie.getTitle());
        holder.genre.setText(movie.getGenre());
        holder.year.setText(movie.getYear());*/
    }

    @Override
    public int getItemCount() {
        return allcomments.size();
    }
}