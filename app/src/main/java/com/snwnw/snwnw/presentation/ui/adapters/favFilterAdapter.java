package com.snwnw.snwnw.presentation.ui.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.snwnw.snwnw.R;
import com.snwnw.snwnw.domain.models.offerModel;
import com.snwnw.snwnw.domain.models.place_model;
import com.snwnw.snwnw.presentation.presenters.interfaces.ItemListener;
import com.snwnw.snwnw.presentation.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ravi on 16/11/17.
 */

public class favFilterAdapter extends RecyclerView.Adapter<favFilterAdapter.MyViewHolder>
        implements Filterable {
    private Context context;
    private List<place_model> allPlaces;
    private List<place_model> allPlacesFiltered;
    private ItemListener listener;
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

          /*  view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(contactListFiltered.get(getAdapterPosition()));
                }
            });*/
        }
    }
    public favFilterAdapter(ArrayList<place_model> _allPlaces, ItemListener listener, Context context,int ITEMSTATUS) {
        this.context = context;
        this.listener = listener ;
        this.allPlaces = _allPlaces;
        this.allPlacesFiltered = allPlaces ;
    }

   /* public favFilterAdapter(Context context, List<offerModel.offer> contactList, ContactsAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
    }
*/
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final place_model model = allPlacesFiltered.get(position);
        holder.item_name.setText(model.getTitle());
        holder.item_details.setText(model.getDescription());
        holder.rating_val.setText(model.getRate());
        if(ITEMSTATUS == Constants.showitem){
            holder.add_to_fav.setVisibility(View.VISIBLE);
        }
        else if (ITEMSTATUS == Constants.hideitem){
            holder.add_to_fav.setVisibility(View.GONE);
        }

        if (allPlaces.get(position).getIs_favourite()==1){
            holder.add_to_fav.setBackgroundResource(R.drawable.remove_bg);
            holder.add_to_fav.setText(context.getResources().getString(R.string.remove_from_fav));
            holder.add_to_fav.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));

        }
        else {
            holder.add_to_fav.setBackgroundResource(R.drawable.rounded_bg);
            holder.add_to_fav.setText(context.getResources().getString(R.string.add_to_fav));
            holder.add_to_fav.setTextColor(ContextCompat.getColor(context, R.color.white));

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
                .with(context)
                .load(allPlaces.get(position).getLogo())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.loading)
                        .fitCenter())
                .into(holder.item_img);

     /*   Glide.with(context)
                .load(contact.getImage())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.thumbnail);*/
    }

    @Override
    public int getItemCount() {
        Log.i("fffffff",allPlacesFiltered.size()+"") ;
        return allPlacesFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    allPlacesFiltered = allPlaces;
                } else {
                    List<place_model> filteredList = new ArrayList<>();
                    for (place_model row : allPlaces) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase()) || row.getTitle().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    allPlacesFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = allPlacesFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                allPlacesFiltered = (ArrayList<place_model>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ContactsAdapterListener {
        void onContactSelected(offerModel.offer contact);
    }
}
