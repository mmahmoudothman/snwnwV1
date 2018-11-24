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
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.snwnw.snwnw.R;
import com.snwnw.snwnw.domain.models.offerModel;
import com.snwnw.snwnw.domain.models.place_model;
import com.snwnw.snwnw.domain.models.profileResult;
import com.snwnw.snwnw.domain.models.profileSetupModel;
import com.snwnw.snwnw.presentation.presenters.interfaces.ItemListener;
import com.snwnw.snwnw.presentation.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class itemAdapter extends RecyclerView.Adapter<itemAdapter.MyViewHolder> {

    private ArrayList<place_model> allPlaces;
    ItemListener listener ;
    Context _context ;
    public int ITEMSTATUS ;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView item_img ;
        public TextView item_name;
        public TextView item_details;
        public TextView rating_val;
        public Button add_to_fav;
        public RelativeLayout allview;
        public MyViewHolder(View view) {
            super(view);
            item_img =(ImageView)view.findViewById(R.id.item_img);
            item_name =(TextView)view.findViewById(R.id.item_name);
            item_details=(TextView)view.findViewById(R.id.item_details);
            rating_val = (TextView)view.findViewById(R.id.rating_val);
            add_to_fav = (Button)view.findViewById(R.id.add_to_fav);
            allview = (RelativeLayout)view.findViewById(R.id.allview);
        }
    }


    public itemAdapter(ArrayList<place_model> _allPlaces, ItemListener listener, Context context,int ITEMSTATUS) {
        this.allPlaces = _allPlaces;
        this.listener = listener ;
        this.ITEMSTATUS = ITEMSTATUS;
        _context = context ;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        place_model place_model = allPlaces.get(position);
        holder.item_name.setText(place_model.getTitle());
        holder.item_details.setText(place_model.getDescription());
        holder.rating_val.setText(place_model.getRate());
        if(ITEMSTATUS == Constants.showitem){
            holder.add_to_fav.setVisibility(View.VISIBLE);
        }
        else if (ITEMSTATUS == Constants.hideitem){
            holder.add_to_fav.setVisibility(View.GONE);
        }

        if (allPlaces.get(position).getIs_favourite()==1){
            holder.add_to_fav.setBackgroundResource(R.drawable.remove_bg);
            holder.add_to_fav.setText(_context.getResources().getString(R.string.remove_from_fav));
            holder.add_to_fav.setTextColor(ContextCompat.getColor(_context, R.color.colorAccent));

        }
        else {
            holder.add_to_fav.setBackgroundResource(R.drawable.rounded_bg);
            holder.add_to_fav.setText(_context.getResources().getString(R.string.add_to_fav));
            holder.add_to_fav.setTextColor(ContextCompat.getColor(_context, R.color.white));

        }

        holder.add_to_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listener.onAddToFave(allPlaces.get(position).getId());
            }
        });
        holder.allview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listener.onAllRowClicked(allPlaces.get(position));
            }
        });


        Glide
                .with(_context)
                .load(allPlaces.get(position).getLogo())
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
        return allPlaces.size();
    }


    public interface ContactsAdapterListener {
        void onContactSelected(offerModel.offer contact);
    }
}