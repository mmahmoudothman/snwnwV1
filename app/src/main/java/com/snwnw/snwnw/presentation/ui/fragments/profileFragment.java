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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.snwnw.snwnw.R;
import com.snwnw.snwnw.domain.models.profileResult;
import com.snwnw.snwnw.domain.models.profileSetupModel;
import com.snwnw.snwnw.domain.models.register_result_model;
import com.snwnw.snwnw.presentation.presenters.impl.RegisterPresenter;
import com.snwnw.snwnw.presentation.presenters.impl.profilePresenter;
import com.snwnw.snwnw.presentation.presenters.interfaces.profilePresenterListener;
import com.snwnw.snwnw.presentation.ui.activities.InternalLoginActivity;
import com.snwnw.snwnw.presentation.ui.activities.registerActivity;
import com.snwnw.snwnw.presentation.ui.activities.testAcitivty;
import com.snwnw.snwnw.presentation.ui.activities.updatePasswordActivity;
import com.snwnw.snwnw.presentation.ui.adapters.recyclerAdapter;
import com.snwnw.snwnw.presentation.utils.Constants;
import com.snwnw.snwnw.presentation.utils.SNWNWPrefs;
import com.snwnw.snwnw.presentation.utils.Utility;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

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

public class profileFragment extends Fragment implements profilePresenterListener  {
  /*  @BindView(R.id.profile_view)
    RecyclerView profile_view;
    @BindView(R.id.id_edit)
    ImageView id_edit;
    @BindView(R.id.save)
    Button save;
    View view ;*/
    public static int goProfile =100 ;
    @BindView(R.id.save)
    Button save;
    @BindView(R.id.id_edit)
    ImageView id_edit;
    @BindView(R.id.ed_name)
    EditText ed_name;
    @BindView(R.id.ed_email)
    EditText ed_email;
    @BindView(R.id.ed_phone)
    EditText ed_phone;
    @BindView(R.id.ed_address)
    EditText ed_address;
    @BindView(R.id.ed_country)
    EditText ed_country;
    @BindView(R.id.ed_job)
    EditText ed_job;
    @BindView(R.id.ed_gender)
    EditText ed_gender;
    @BindView(R.id.ed_pass)
    TextView ed_pass;
    @BindView(R.id.img_layout)
    FrameLayout img_layout;
    public static final int data_Permission = 100;
    public static int clickCode =200 ;
    String encoded_imge;
    Bitmap bitmap;
    @BindView(R.id.img_profile)
    CircleImageView img_profile ;
    @Override
    public void onResume() {
        //getProfile();
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container , Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_layout, container, false);
        ButterKnife.bind(this,view);

        ((testAcitivty)getActivity()).setHeader(getResources().getString(R.string.ic_profile));

        HashMap<String,Object>Params = new HashMap<>();
        Params.put("user_id",SNWNWPrefs.get_int_value(Constants.UserId,getActivity()));
        String AuthorizedToken = "Bearer"+" "+SNWNWPrefs.getDefaults(Constants.Token,getActivity());
        profilePresenter profilePresenter =new profilePresenter(profileFragment.this,getActivity());
        profilePresenter.profileData(Params,AuthorizedToken);

        img_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickImageDialog.build(new PickSetup())
                        .setOnPickResult(new IPickResult() {
                            @Override
                            public void onPickResult(PickResult r) {
                                //TODO: do what you have to...
                                encode_to_base(r.getPath(), img_profile);
                            }
                        }).show(getFragmentManager());

//                checkPermissions();
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


        ed_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoUpdate = new Intent(getActivity(),updatePasswordActivity.class);
                startActivity(gotoUpdate);
            }
        });
        //Params,AuthorizedToken
        return view ;
    }

    public static profileFragment newInstance(String text) {
        profileFragment f = new profileFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }

    public void getProfile() {
     /*   HashMap<String, Object> Params = new HashMap<>();
        Params.put("user_id", userID);*/
        HashMap<String,Object>Params = new HashMap<>();
        //todo here to resolve the issue of the userid from facebook and google
        Log.d("google", "this is user id from the profile"+
                String.valueOf(SNWNWPrefs.get_int_value(Constants.UserId,getActivity())));


        Params.put("user_id",SNWNWPrefs.get_int_value(Constants.UserId,getActivity()));
        String AuthorizedToken = "Bearer"+" "+SNWNWPrefs.getDefaults(Constants.Token,getActivity());
        profilePresenter profilePresenter =new profilePresenter(profileFragment.this,getActivity());
        profilePresenter.profileData(Params,AuthorizedToken);

    }
    @Override
    public void userData(profileResult result) {
        ed_name.setText(result.getUser().getFirst_name());
        ed_email.setText(result.getUser().getEmail());
        ed_phone.setText(result.getUser().getPhone());
        ed_address.setText(result.getUser().getAddress());
        ed_country.setText(result.getUser().getHome_country());
        ed_job.setText(result.getUser().getJob());
        ed_gender.setText(result.getUser().getGender());
        ed_pass.setText(getResources().getString(R.string.change_pass));



        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.snwnw_logo)
                .error(R.drawable.snwnw_logo);

        Log.i("iamtheeimag",result.getUser().getImage());
        Glide.with(this).load(result.getUser().getImage()).apply(options).into(img_profile);


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

    private void enableEdit() {
        ed_name.setEnabled(true);
        ed_email.setEnabled(true);
        ed_phone.setEnabled(true);
        ed_address.setEnabled(true);
        ed_country.setEnabled(true);
        ed_job.setEnabled(true);
        ed_gender.setEnabled(true);
       /* EditText et;
        for (int i = 0; i < profile_view.getAdapter().getItemCount(); i++) {
            et = (EditText) profile_view.getChildAt(i).findViewById(R.id.userval);
            et.setEnabled(true);
        }*/

        save.setVisibility(View.VISIBLE);
    }
/*  public void enableEdit(){
      for (int i = 0; i < profile_view.getAdapter().getItemCount(); i++) {
           view = profile_view.findViewHolderForAdapterPosition(i).itemView;
          EditText et = (EditText)view.findViewById(R.id.userval);
          et.setEnabled(true);
      }

      save.setVisibility(View.VISIBLE);
  }*/

   public void disableEdit(){

       ed_name.setEnabled(false);
       ed_email.setEnabled(false);
       ed_phone.setEnabled(false);
       ed_address.setEnabled(false);
       ed_country.setEnabled(false);
       ed_job.setEnabled(false);
       ed_gender.setEnabled(false);

        HashMap<String,Object>Params = new HashMap<>();
        Params.put("first_name",ed_name.getText().toString().trim());
        Params.put("last_name","");
        Params.put("email",ed_email.getText().toString().trim());
        Params.put("image",encoded_imge);
        Params.put("job",ed_job.getText().toString().trim());
        Params.put("phone",ed_phone.getText().toString().trim());
       // Params.put("relgion",)
        //Params.put("bio",)
        Params.put("gender",ed_gender.getText().toString());
        Params.put("address",ed_address.getText().toString());
        Params.put("mobile",ed_phone.getText().toString().trim());

        Log.i("iamallparams",Params+"");
       String AuthorizedToken = "Bearer"+" "+SNWNWPrefs.getDefaults(Constants.Token,getActivity());
       Log.i("iamallparams",AuthorizedToken+"");

       profilePresenter profilePresenter =new profilePresenter(profileFragment.this,getActivity());
       profilePresenter.UpdateprofileData(Params,AuthorizedToken);

        save.setVisibility(View.GONE);
    }

    public void  updateData(){
       /* HashMap<String,Object>Params = new HashMap<>();
        Params.put("first_name",)
        Params.put("last_name",)
        Params.put("first_name",)
        Params.put("email",)
        Params.put("image",)
        Params.put("job",)
        Params.put("phone",)
        Params.put("relgion",)
        Params.put("bio",)
        Params.put("gender",)
        Params.put("address",)
        Params.put("mobile",)
        Params.put("username",)
        Params.put("birthdate",)*/
    }

    public void checkPermissions() {
        boolean result = Utility.checkPermission(getActivity());
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // this.requestPermissions();
            this.requestPermissions(
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, data_Permission);
        } else {
            Log.e("DB", "PERMISSION GRANTED");
            galleryIntent(clickCode);
        }
    }
    private void galleryIntent(int req_cdode) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), req_cdode);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case data_Permission:
                galleryIntent(clickCode);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
                //onSelectFromGalleryResult(data);
                if (null != data) {
                    if (requestCode ==goProfile){
                        getProfile();
                    }
//                    else {
//                    Uri selectedImage = data.getData();
//                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                    Cursor cursor = getActivity().getContentResolver().query(selectedImage,
//                            filePathColumn, null, null, null);
//                    cursor.moveToFirst();
//                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                    String picturePath = cursor.getString(columnIndex);
//
//                    encode_to_base(picturePath, img_profile);
//                }
                }
               // Toast.makeText(getActivity(), "iamselected", Toast.LENGTH_SHORT).show();
            }
        }

/*            else {
               // displayPlace(PlacePicker.getPlace(data, getActivity()));
            }*/
public String encode_to_base(String path, ImageView img) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    //String
    encoded_imge = "";
    try {
        bitmap = BitmapFactory.decodeFile(path);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] byteArrayImage = baos.toByteArray();
        encoded_imge = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);

        Log.i("enccoooded",encoded_imge+"fifi");

       // Imags.add(encoded_imge);
        Glide.with(this).load(byteArrayImage).into(img);

        //  img.setImageBitmap(bitmap);

    } catch (Exception e) {

        e.printStackTrace();
    }
    return encoded_imge;
}
}
