package com.snwnw.snwnw.presentation.ui.adapters;

/**
 * Created by fifi elshafie on 2/6/2018.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.snwnw.snwnw.R;
import com.snwnw.snwnw.domain.models.service_cat_model;
import com.snwnw.snwnw.presentation.presenters.interfaces.categoryPresenterListener;

import java.util.ArrayList;

public class offersAdapter extends RecyclerView.Adapter<offersAdapter.MyViewHolder> {

    private ArrayList<service_cat_model> allcategories;
    categoryPresenterListener listener ;
    Context context ;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView info_text;
        public LinearLayout allview;

        public MyViewHolder(View view) {
            super(view);
            info_text = (TextView) view.findViewById(R.id.info_text);
            allview = (LinearLayout)view.findViewById(R.id.allview);
        }
    }


    public offersAdapter(Context context) {
        this.context = context ;
       // this.allcategories = allcategories;
       // this.listener = listener ;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offer_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
//        service_cat_model user = allcategories.get(position);
   //     holder.info_text.setText(user.getName());
    //    holder.allview.setOnClickListener(new View.OnClickListener() {
          /*  @Override
            public void onClick(View view) {

                listener.onCategoryClicked(allcategories.get(position));
            }
        });*/
       /* holder.title.setText(movie.getTitle());
        holder.genre.setText(movie.getGenre());
        holder.year.setText(movie.getYear());*/
    }

    @Override
    public int getItemCount() {
      //  return allcategories.size();
   return 5;
    }
}