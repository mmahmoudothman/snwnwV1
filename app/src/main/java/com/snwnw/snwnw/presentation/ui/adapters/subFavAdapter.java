package com.snwnw.snwnw.presentation.ui.adapters;

/**
 * Created by fifi elshafie on 6/24/2018.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.snwnw.snwnw.R;
import com.snwnw.snwnw.domain.models.service_cat_model;
import com.snwnw.snwnw.presentation.presenters.interfaces.categoryPresenterListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by fifi elshafie on 6/23/2018.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.snwnw.snwnw.R;
import com.snwnw.snwnw.domain.models.service_cat_model;
import com.snwnw.snwnw.presentation.presenters.interfaces.categoryPresenterListener;

import java.util.ArrayList;
/**
 * Created by fifi elshafie on 2/6/2018.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.snwnw.snwnw.R;
import com.snwnw.snwnw.domain.models.profileResult;
import com.snwnw.snwnw.domain.models.profileSetupModel;
import com.snwnw.snwnw.domain.models.service_cat_model;
import com.snwnw.snwnw.presentation.presenters.interfaces.categoryPresenterListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class subFavAdapter extends RecyclerView.Adapter<subFavAdapter.MyViewHolder> {

    private ArrayList<service_cat_model> allcategories;
    categoryPresenterListener listener ;
    ArrayList<Integer>AllFav ;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ///  public TextView info_text;
        //  public LinearLayout allview;
        public ImageView itemImg;
        public TextView itemTxt ;
        public RelativeLayout allview ;
        public Switch item_switch ;

        public MyViewHolder(View view) {
            super(view);
            itemImg = (ImageView)view.findViewById(R.id.img) ;
            itemTxt = (TextView) view.findViewById(R.id.itemTxt) ;
            allview = (RelativeLayout)view.findViewById(R.id.allview) ;
            item_switch = (Switch)view.findViewById(R.id.item_switch);
            //  info_text = (TextView) view.findViewById(R.id.info_text);
            // allview = (LinearLayout)view.findViewById(R.id.allview);
        }
    }


    public subFavAdapter(ArrayList<service_cat_model> allcategories , ArrayList<Integer>_AllFav ,categoryPresenterListener listener) {
        this.allcategories = allcategories;
        this.AllFav = _AllFav ;
        this.listener = listener ;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sub_fav_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        service_cat_model user = allcategories.get(position);
        holder.itemTxt.setText(user.getName());
        if (user.getImage().length()!= 0){
            Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(holder.itemImg);

        }
        //  holder.info_text.setText(user.getName());
        holder.allview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listener.onCategoryClicked(allcategories.get(position));
            }
        });

        holder.item_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                listener.onSwitchClicked(allcategories.get(position),b,position);
            }
        });

        if (AllFav.contains(allcategories.get(position).getId())){

            holder.item_switch.setChecked(true);
        }
        else {
            holder.item_switch.setChecked(false);
        }
       /* holder.title.setText(movie.getTitle());
        holder.genre.setText(movie.getGenre());
        holder.year.setText(movie.getYear());*/
    }

    @Override
    public int getItemCount() {
        return  allcategories.size() ;
        //  return allcategories.size();
    }
}