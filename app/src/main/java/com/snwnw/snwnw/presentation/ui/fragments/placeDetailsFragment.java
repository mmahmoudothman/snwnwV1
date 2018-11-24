package com.snwnw.snwnw.presentation.ui.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.snwnw.snwnw.R;
import com.snwnw.snwnw.domain.models.place_model;
import com.snwnw.snwnw.domain.models.places_result;
import com.snwnw.snwnw.domain.models.review_item;
import com.snwnw.snwnw.domain.models.service_cat_model;
import com.snwnw.snwnw.presentation.presenters.impl.placesPresenter;
import com.snwnw.snwnw.presentation.presenters.impl.reviewPresenter;
import com.snwnw.snwnw.presentation.presenters.interfaces.placesPrestenerListener;
import com.snwnw.snwnw.presentation.presenters.interfaces.reviewPresenterListener;
import com.snwnw.snwnw.presentation.ui.activities.InternalLoginActivity;
import com.snwnw.snwnw.presentation.ui.adapters.commentsAdapter;
import com.snwnw.snwnw.presentation.utils.Constants;
import com.snwnw.snwnw.presentation.utils.SNWNWPrefs;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by afaf.elshafey on 11/6/2017.
 */

public class placeDetailsFragment extends AppCompatActivity implements placesPrestenerListener,reviewPresenterListener {
    @BindView(R.id.place_name)
    TextView place_name;
    @BindView(R.id.place_distance)
    TextView place_distance;
    @BindView(R.id.address_details)
    TextView address_details;
    @BindView(R.id.address_details_times)
    TextView address_details_times;
    @BindView(R.id.place_phone_details)
    TextView place_phone_details;
    @BindView(R.id.rating_view)
    RelativeLayout rating_view;
    @BindView(R.id.rating_val)
    TextView rating_val ;
    @BindView(R.id.cover)
    ImageView cover_default;
    public static int goProfile =100 ;
    @BindView(R.id.fab)
    FloatingActionButton fab_share;
    @BindView(R.id.fab2)
    FloatingActionButton fab2_add_to_fav;
    @BindView(R.id.add_comment)
    FloatingActionButton add_comment ;
    int index;
     Dialog dialog;
     @BindView(R.id.allcomments)
     RecyclerView allcomments;
     @BindView(R.id.nav_icon)
     ImageView nav_icon ;
  /*  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container , Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.place_details_fragment, container, false);
        ButterKnife.bind(this,view);
        index = getArguments().getInt("placeid", 0);
        Log.i("iamindex",index+"") ;
        getPlaceDetails(index) ;
        getPlaceReviews();

        fab2_add_to_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onAddToFave(index);
            }
        });

        add_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rateDialog();
            }
        });

        return view ;
    }
*/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_details_fragment);
        ButterKnife.bind(this);
        index = getIntent().getIntExtra("placeid", 0);
        Log.i("iamindex",index+"") ;
        getPlaceDetails(index) ;
        getPlaceReviews();

        nav_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        fab2_add_to_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onAddToFave(index);
            }
        });

        add_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rateDialog();
            }
        });

    }

  /*  public static placeDetailsFragment newInstance(int text) {
        placeDetailsFragment f = new placeDetailsFragment();
        Bundle b = new Bundle();
        b.putInt("placeid", text);
        f.setArguments(b);
        return f;
    }*/

    public void getPlaceDetails(int placeid){
        placesPresenter placesPresenter =  new placesPresenter(placeDetailsFragment.this,placeDetailsFragment.this);
        HashMap<String,Object> Params = new HashMap<>();
        Params.put("id",placeid);
        Params.put("lang","ar");

        String AuthorizedToken = "Bearer" + " " + SNWNWPrefs.getDefaults(Constants.Token, placeDetailsFragment.this);
        Log.i("iamthetoken",AuthorizedToken) ;
        Log.i("iamparams",Params+"") ;
        placesPresenter.placeDetails(Params,AuthorizedToken);
    }

    @Override
    public void onPlaceUploadDone(Boolean Result) {


    }

    @Override
    public void onPlaceUploadFailed(int code,String Result) {

    }

    @Override
    public void getAllCategories(ArrayList<service_cat_model> allservices) {

    }

    @Override
    public void getAllSubCategories(ArrayList<service_cat_model> allservices) {

    }

    @Override
    public void onLoadPlacesSearchPlaces(places_result places_result) {

    }

    @Override
    public void onPlaceDetailsLoaded(place_model place_model) {

        updateView(place_model);
    }

    @Override
    public void onUpdateFail(int Code) {

        Intent go = new Intent(placeDetailsFragment.this, InternalLoginActivity.class);
        startActivityForResult(go,goProfile);

    }

    @Override
    public void onFavBtnClicked(String msg) {
        Toast.makeText(placeDetailsFragment.this,msg,Toast.LENGTH_SHORT).show();
    }

    public void updateView(final place_model model) {
        place_name.setText(model.getTitle());
        rating_val.setText(model.getRate());
        if (model.getAddress().length()!=0){
            address_details.setText(model.getAddress());
        }
        else {
            address_details.setText(getResources().getString(R.string.not_found));
        }

        if (model.getWork_hours().length()!=0){
            address_details_times.setText(model.getWork_hours());
        }
        else {
            address_details_times.setText(getResources().getString(R.string.not_found));
        }

        if (model.getPhone().length()!=0){
            place_phone_details.setText(model.getPhone());
        }
        else {

            place_phone_details.setText(getResources().getString(R.string.not_found));

        }

        if (model.getImages()!=null){
        if (model.getImages().size() != 0) {
            Glide.with(placeDetailsFragment.this)
                    .load(model.getImages().get(0))
                    .into(cover_default);


        }
        }

        //todo here to add the action to the share button again

        fab_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share(model.getTitle() +"   "+model.getDescription());
            }
        });
    }
    private void share(String post ){
        List<Intent> targetedShareIntents = new ArrayList<>();
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        // Set title and text to share when the user selects an option.
//                shareIntent.putExtra(Intent.EXTRA_TITLE, title);
        shareIntent.putExtra(Intent.EXTRA_TEXT, post );
//                shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(shareIntent, 0);
        if (!resInfo.isEmpty()) {
            for (ResolveInfo info : resInfo) {
                Intent targetedShare = new Intent(android.content.Intent.ACTION_SEND);
                targetedShare.setType("text/plain"); // put here your mime type
                targetedShare.setPackage(info.activityInfo.packageName.toLowerCase());
                targetedShareIntents.add(targetedShare);
            }
            // Then show the ACTION_PICK_ACTIVITY to let the user select it
            Intent intentPick = new Intent();
            intentPick.setAction(Intent.ACTION_PICK_ACTIVITY);
            // Set the title of the dialog
            intentPick.putExtra(Intent.EXTRA_TITLE, "snwnw app");
            intentPick.putExtra(Intent.EXTRA_INTENT, shareIntent);
            intentPick.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray());
            // Call StartActivityForResult so we can get the app name selected by the user
            startActivityForResult(intentPick, 3);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode ==goProfile){
           // getProfile();
        }
    }

    public void onAddToFave(int itemId) {
        placesPresenter placesPresenter =  new placesPresenter(placeDetailsFragment.this,placeDetailsFragment.this);
        HashMap<String,Object>Params = new HashMap<>();
        Params.put("place_id",itemId);
        String AuthorizedToken = "Bearer" + " " + SNWNWPrefs.getDefaults(Constants.Token,placeDetailsFragment.this);
        Log.i("iamthetoken",AuthorizedToken) ;
        placesPresenter.addPlaceToFav(Params,AuthorizedToken);
    }

    public void rateDialog(){
        // custom dialog
        dialog = new Dialog(placeDetailsFragment.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog.setContentView(R.layout.rate_review_dialog);
        Button btn_send  = (Button)dialog.findViewById(R.id.btn_send);
        final EditText ed_add_comment =(EditText)dialog.findViewById(R.id.ed_add_comment);
        final MaterialRatingBar rating_bar =(MaterialRatingBar)dialog.findViewById(R.id.rating_bar);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("iamrating",rating_bar.getRating()+"") ;
                HashMap<String,Object>Params = new HashMap<>();
                Params.put("rate",rating_bar.getRating());
                Params.put("review",ed_add_comment.getText().toString().trim());
                Params.put("place_id",index);
                Log.i("allparma",Params+"") ;
                reviewPresenter presenter =new reviewPresenter(placeDetailsFragment.this,placeDetailsFragment.this);

                presenter.sendReview(Params);
            }
        });
        // dialog.setTitle("Title...");

        // set the custom dialog components - text, image and button
      /*  TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText("Android custom dialog example!");
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        image.setImageResource(R.drawable.ic_launcher);*/

       /* Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
*/
        dialog.show();
    }

    @Override
    public void onReviewAdded(String msg) {
        Toast.makeText(placeDetailsFragment.this,msg,Toast.LENGTH_SHORT).show();
        if (dialog.isShowing()){
            dialog.dismiss();
        }
    }

    @Override
    public void onReviewFailed(String Errror) {

    }

    @Override
    public void onExpiredToken(int Code) {
        Intent go = new Intent(placeDetailsFragment.this, InternalLoginActivity.class);
        startActivityForResult(go,goProfile);

    }

    @Override
    public void onReviewsLoaded(ArrayList<review_item> allreview) {

        //Set up allcomments;
        commentsAdapter adapter = new commentsAdapter(allreview,placeDetailsFragment.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(placeDetailsFragment.this);
        allcomments.setLayoutManager(mLayoutManager);
        allcomments.setItemAnimator(new DefaultItemAnimator());
        allcomments.setAdapter(adapter);

    }

    //review_result
    public void getPlaceReviews(){
        reviewPresenter presenter = new reviewPresenter(placeDetailsFragment.this,placeDetailsFragment.this) ;
        HashMap<String,Object>Params = new HashMap<>();
        Params.put("id",index);
        Params.put("offset",0);
        Params.put("take",5);
        presenter.getReviews(Params);
    }




}
