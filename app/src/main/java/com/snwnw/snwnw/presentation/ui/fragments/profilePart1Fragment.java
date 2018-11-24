package com.snwnw.snwnw.presentation.ui.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.snwnw.snwnw.EventBus.LogoutModel;
import com.snwnw.snwnw.R;
import com.snwnw.snwnw.domain.models.profileResult;
import com.snwnw.snwnw.domain.models.profileSetupModel;
import com.snwnw.snwnw.domain.models.register_result_model;
import com.snwnw.snwnw.presentation.presenters.impl.RegisterPresenter;
import com.snwnw.snwnw.presentation.presenters.impl.profilePresenter;
import com.snwnw.snwnw.presentation.presenters.interfaces.profilePresenterListener;
import com.snwnw.snwnw.presentation.ui.activities.InternalLoginActivity;
import com.snwnw.snwnw.presentation.ui.activities.LoginActivity;
import com.snwnw.snwnw.presentation.ui.activities.editPasswordActivity;
import com.snwnw.snwnw.presentation.ui.activities.myFavActivity;
import com.snwnw.snwnw.presentation.ui.activities.registerActivity;
import com.snwnw.snwnw.presentation.ui.activities.testAcitivty;
import com.snwnw.snwnw.presentation.ui.activities.updatePasswordActivity;
import com.snwnw.snwnw.presentation.ui.adapters.recyclerAdapter;
import com.snwnw.snwnw.presentation.ui.profilePackage.myDataActivity;
import com.snwnw.snwnw.presentation.utils.Constants;
import com.snwnw.snwnw.presentation.utils.SNWNWPrefs;
import com.snwnw.snwnw.presentation.utils.Utility;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by afaf.elshafey on 11/6/2017.
 */

public class profilePart1Fragment extends Fragment implements profilePresenterListener
,GoogleApiClient.OnConnectionFailedListener {
    /*  @BindView(R.id.profile_view)
      RecyclerView profile_view;
      @BindView(R.id.id_edit)
      ImageView id_edit;
      @BindView(R.id.save)
      Button save;
      View view ;*/

    @BindView(R.id.img_profile)
    ImageView img_profile ;

    @BindView(R.id.profile2)
    RelativeLayout myFavLayout;

    @BindView(R.id.profile3)
    RelativeLayout myAccountLayout ;

    @BindView(R.id.profile4)
    RelativeLayout logout;

    @BindView(R.id.name_layout)
    RelativeLayout name_layout;
    public static int goProfile =100 ;

    @BindView(R.id.data_lbl)
    TextView data_lbl;

    @Override
    public void onResume() {
        //getProfile();
        super.onResume();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container , Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_part1_fragment, container, false);
        ButterKnife.bind(this,view);


        ((testAcitivty)getActivity()).setHeader(getResources().getString(R.string.ic_profile));

        HashMap<String,Object>Params = new HashMap<>();
        //todo here to resolve the issue of the userid from facebook and google
        Log.d("google", "this is user id from the profile"+
                String.valueOf(SNWNWPrefs.get_int_value(Constants.UserId,getActivity())));


        Params.put("user_id",SNWNWPrefs.get_int_value(Constants.UserId,getActivity()));
        String AuthorizedToken = "Bearer"+" "+SNWNWPrefs.getDefaults(Constants.Token,getActivity());
        profilePresenter profilePresenter =new profilePresenter(profilePart1Fragment.this,getActivity());
        profilePresenter.profileData(Params,AuthorizedToken);

      /*  img_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermissions();
            }
        });

        id_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enableEdit();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableEdit();
                //  Toast.makeText(getActivity(),"iamnowdiable",Toast.LENGTH_SHORT).show();


            }
        });
*/

    /*    ed_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoUpdate = new Intent(getActivity(),updatePasswordActivity.class);
                startActivity(gotoUpdate);
            }
        });*/
        //Params,AuthorizedToken

        name_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goDetails = new Intent(getActivity(),myDataActivity.class);
                getActivity().startActivity(goDetails);
            }
        });

        myFavLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goDetails = new Intent(getActivity(),myFavActivity.class);
                getActivity().startActivity(goDetails);
            }
        });

        myAccountLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goDetails = new Intent(getActivity(),editPasswordActivity.class);
                getActivity().startActivity(goDetails);

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                EventBus.getDefault().post(new LogoutModel(true));



//                SNWNWPrefs.setDefaults(Constants.Token,"",getContext());
//                mFirebaseAuth.signOut();
//                //facebook sign out
//                if (LoginManager.getInstance() != null)
//                    LoginManager.getInstance().logOut();
//                //google log out
//                // Google sign out
//                if (Auth.CREDENTIALS_API != null)
//                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
//                            new ResultCallback<Status>() {
//                                @Override
//                                public void onResult(@NonNull Status status) {
////                                updateUI(null);
//                                }
//                            });
//
//                Intent goDetails = new Intent(getActivity(),LoginActivity.class);
//                getActivity().startActivity(goDetails);
//                SNWNWPrefs.setDefaults(Constants.Token, "", getActivity());
//                getActivity().finish();



            }
        });
        return view ;
    }

    public static profilePart1Fragment newInstance(String text) {
        profilePart1Fragment f = new profilePart1Fragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }

    public void getProfile() {
     /*   HashMap<String, Object> Params = new HashMap<>();
        Params.put("user_id", userID);*/
        HashMap<String,Object>Params = new HashMap<>();


        Params.put("user_id",SNWNWPrefs.get_int_value(Constants.UserId,getActivity()));
        String AuthorizedToken = "Bearer"+" "+SNWNWPrefs.getDefaults(Constants.Token,getActivity());
        profilePresenter profilePresenter =new profilePresenter(profilePart1Fragment.this,getActivity());
        profilePresenter.profileData(Params,AuthorizedToken);

    }
    @Override
    public void userData(profileResult result) {


        data_lbl.setText(result.getUser().getFirst_name());
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.snwnw_logo)
                .error(R.drawable.snwnw_logo);

        Log.i("iamtheeimag",result.getUser().getImage());
        Glide.with(this).load(result.getUser().getImage()).apply(options).into(img_profile);


     /*   ed_name.setText(result.getUser().getFirst_name());
        ed_email.setText(result.getUser().getEmail());
        ed_phone.setText(result.getUser().getPhone());
        ed_address.setText(result.getUser().getAddress());
        ed_country.setText(result.getUser().getHome_country());
        ed_job.setText(result.getUser().getJob());
        ed_gender.setText(result.getUser().getGender());
        ed_pass.setText(getResources().getString(R.string.change_pass));

*/

    /*    RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);

        Log.i("iamtheeimag",result.getUser().getImage());
      //  Glide.with(this).load(result.getUser().getImage()).apply(options).into(img_profile);

*/
      /*  ArrayList<profileSetupModel>profileList = new ArrayList<>();
        profileList.add(new profileSetupModel(getResources().getString(R.string.full_name),result.getUser().getFirst_name()));
        profileList.add(new profileSetupModel(getResources().getString(R.string.email_name),result.getUser().getEmail()));
        profileList.add(new profileSetupModel(getResources().getString(R.string.phone_name),result.getUser().getPhone()));
        profileList.add(new profileSetupModel(getResources().getString(R.string.address_name),result.getUser().getAddress()));
        profileList.add(new profileSetupModel(getResources().getString(R.string.job_name),result.getUser().getJob()));
        profileList.add(new profileSetupModel(getResources().getString(R.string.religon),result.getUser().getRelgion()));
        profileList.add(new profileSetupModel(getResources().getString(R.string.gender),result.getUser().getGender()));
        profileList.add(new profileSetupModel(getResources().getString(R.string.user_password),getResources().getString(R.string.change_pass)));*/

        //   profileList.add(new profileSetupModel(getResources().getString(R.string.full_name),result.getUser().getFirst_name()));
        //  setUpView(profileList);
    }

    @Override
    public void expiredToken(int Code) {
        Intent go = new Intent(getActivity(), InternalLoginActivity.class);
        startActivityForResult(go,goProfile);
    }

    @Override
    public void onUpdateDone(String msg) {
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateFail(int code) {
        Toast.makeText(getActivity(),code+"",Toast.LENGTH_SHORT).show();
    }

    public void setUpView(ArrayList<profileSetupModel>usetDataList){

     /*   recyclerAdapter adapter = new recyclerAdapter(usetDataList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        profile_view.setLayoutManager(mLayoutManager);
        profile_view.setItemAnimator(new DefaultItemAnimator());
        profile_view.setAdapter(adapter) ;*/
    }

   /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(getActivity(),"iaminactivity",Toast.LENGTH_SHORT).show();
    }*/

/*  public void enableEdit(){
      for (int i = 0; i < profile_view.getAdapter().getItemCount(); i++) {
           view = profile_view.findViewHolderForAdapterPosition(i).itemView;
          EditText et = (EditText)view.findViewById(R.id.userval);
          et.setEnabled(true);
      }

      save.setVisibility(View.VISIBLE);
  }*/

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d("google", "onConnectionFailed:" + connectionResult);
        Toast.makeText(getContext(), "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }


}
