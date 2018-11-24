package com.snwnw.snwnw.presentation.ui.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.all.All;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.snwnw.snwnw.R;
import com.snwnw.snwnw.domain.models.homeCitiesModel;
import com.snwnw.snwnw.domain.models.homeCountriesModel;
import com.snwnw.snwnw.domain.models.place_model;
import com.snwnw.snwnw.domain.models.places_result;
import com.snwnw.snwnw.domain.models.service_cat_model;
import com.snwnw.snwnw.domain.models.user_model;
import com.snwnw.snwnw.presentation.presenters.impl.RegisterPresenter;
import com.snwnw.snwnw.presentation.presenters.impl.categoriesPresenter;
import com.snwnw.snwnw.presentation.presenters.impl.placesPresenter;
import com.snwnw.snwnw.presentation.presenters.interfaces.LoginPresenterListener;
import com.snwnw.snwnw.presentation.presenters.interfaces.placesPrestenerListener;
import com.snwnw.snwnw.presentation.ui.activities.InternalLoginActivity;
import com.snwnw.snwnw.presentation.ui.activities.registerActivity;
import com.snwnw.snwnw.presentation.ui.activities.testAcitivty;
import com.snwnw.snwnw.presentation.ui.adapters.citiesAdapter;
import com.snwnw.snwnw.presentation.ui.adapters.spinnerArrayAdapter;
import com.snwnw.snwnw.presentation.utils.Constants;
import com.snwnw.snwnw.presentation.utils.SNWNWPrefs;
import com.snwnw.snwnw.presentation.utils.Utility;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class addFragment extends AppCompatActivity implements placesPrestenerListener, View.OnClickListener, LoginPresenterListener, IPickResult {
    @BindView(R.id.add_place)
    Button add_place;
    @BindView(R.id.place_name)
    EditText place_name;
    // @BindView(R.id.placeonmap)
    // TextView placeonmap;
    @BindView(R.id.type_spinner)
    Spinner type_spinner;
    @BindView(R.id.card1)
    CardView card1;
    @BindView(R.id.card2)
    CardView card2;
    @BindView(R.id.card3)
    CardView card3;
    String encoded_imge;
    Bitmap bitmap;
    @BindView(R.id.img1)
    ImageView img1;
    @BindView(R.id.img2)
    ImageView img2;
    @BindView(R.id.img3)
    ImageView img3;


    @BindView(R.id.img4)
    ImageView img4;
    @BindView(R.id.img5)
    ImageView img5;
    @BindView(R.id.card4)
    CardView card4;
    @BindView(R.id.card5)
    CardView card5;


    //todo here  sub category
    ArrayList<service_cat_model> sub_types;
    @BindView(R.id.sub_type)
    Spinner sub_type;
    int Selected_sub_type =0 ;
    int selected_type = 0 ;


    double Latitue, Longtiude;
    public static int goProfile = 100;

    String Address;
    ArrayList<service_cat_model> all_types;
    public static int PLACE_PICKER_REQUEST = 1001;
    public static final int data_Permission = 100;
    // public static final int SELECT_FILE = 100;
    public static int clickCode = 0;
    ArrayList<String> Imags = new ArrayList<>();
    @BindView(R.id.city_spinner)
    Spinner city_spinner;
    ArrayList<homeCitiesModel.countryModel> cities;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add);
        ButterKnife.bind(this);
        getAllCategories();
        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);

        card4.setOnClickListener(this);
        card5.setOnClickListener(this);


        getCities();

        add_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String, Object> Params = new HashMap<>();
                Params.put("name", place_name.getText().toString().trim());
                Params.put("category_id", all_types.get(type_spinner.getSelectedItemPosition()).getId());
                Params.put("latitude", Latitue);
                Params.put("longitude", Longtiude);
                Params.put("address", Address);
                Log.i("iamparams", Params + "");


                Params.put("images", Imags);

                String AuthorizedToken = "Bearer" + " " + SNWNWPrefs.getDefaults(Constants.Token, addFragment.this);
                placesPresenter placesPresenter = new placesPresenter(addFragment.this, addFragment.this);
                placesPresenter.addPlace(Params, AuthorizedToken);
            }

        });

     /*   placeonmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayPlacePicker();
            }
        });*/


    }


    @Override
    public void onPlaceUploadDone(Boolean Result) {
        Log.i("iamuploadrd", "uploaded");
        //replaceME();
    }

    @Override
    public void onPlaceUploadFailed(int code, String Result) {
        if (code == Constants.ExpiredToken) {
            Intent go = new Intent(addFragment.this, InternalLoginActivity.class);
            startActivityForResult(go, goProfile);
        }
    }

    @Override
    public void getAllCategories(ArrayList<service_cat_model> allservices) {

        all_types = allservices;
        setUpSpinner(all_types);
        getSubCategories(allservices.get(0).getId());
    }

    @Override
    public void getAllSubCategories(ArrayList<service_cat_model> allservices) {
        sub_types = allservices;
        setUpSubSpinner(sub_types);


    }

    @Override
    public void onLoadPlacesSearchPlaces(places_result places_result) {

    }

    @Override
    public void onPlaceDetailsLoaded(place_model place_model) {

    }

    @Override
    public void onUpdateFail(int Code) {

    }

    @Override
    public void onFavBtnClicked(String msg) {

    }


    private void displayPlacePicker() {

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(addFragment.this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            Log.d("PlacesAPI Demo", "GooglePlayServicesRepairableException thrown");
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.d("PlacesAPI Demo", "GooglePlayServicesNotAvailableException thrown");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == goProfile) {
                Log.i("iamcomfrompr", "iamcomfrompr");
            } else if (requestCode == PLACE_PICKER_REQUEST) {
                displayPlace(PlacePicker.getPlace(data, addFragment.this));
            }
//            else {
//                //onSelectFromGalleryResult(data);
//                if (null != data) {
//                    Uri selectedImage = data.getData();
//                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
//
//                    Cursor cursor = getApplicationContext().getContentResolver().query(selectedImage,
//                            filePathColumn, null, null, null);
//                    cursor.moveToFirst();
//                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                    String picturePath = cursor.getString(columnIndex);
//                    if (clickCode == 1) {
//                        encode_to_base(picturePath, img1);
//                    } else if (clickCode == 2) {
//                        encode_to_base(picturePath, img2);
//                    } else if (clickCode == 3) {
//                        encode_to_base(picturePath, img3);
//                    }
//                }
//
//                Toast.makeText(getApplicationContext(), "iamselected", Toast.LENGTH_SHORT).show();
//            }

        }

/*            else {
               // displayPlace(PlacePicker.getPlace(data, getActivity()));
            }*/


    }

    //}
    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // ivImage.setImageBitmap(bm);
    }

    @Override
    public void onPickResult(PickResult pickResult) {
        if (pickResult.getError() == null) {
            //If you want the Uri.
            //Mandatory to refresh image from Uri.
            //getImageView().setImageURI(null);

            //Setting the real returned image.
            //getImageView().setImageURI(r.getUri());

            //If you want the Bitmap.
//            favorImage.setImageBitmap(pickResult.getBitmap());
//            image=pickResult.getBitmap();
//            take_photo_profile.setVisibility(View.GONE);

            if (clickCode == 1) {
                encode_to_base(pickResult.getPath(), img1);
            } else if (clickCode == 2) {
                encode_to_base(pickResult.getPath(), img2);
            } else if (clickCode == 3) {
                encode_to_base(pickResult.getPath(), img3);
            }else if (clickCode == 4) {
                encode_to_base(pickResult.getPath(), img4);
            } else if (clickCode == 5) {
                encode_to_base(pickResult.getPath(), img5);
            }


            //Image path
            //r.getPath();
        } else {
            //Handle possible errors
            Toast.makeText(this, pickResult.getError().getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    public String encode_to_base(String path, ImageView img) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //String
        encoded_imge = "";
        try {
            bitmap = BitmapFactory.decodeFile(path);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            byte[] byteArrayImage = baos.toByteArray();
            encoded_imge = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);

            Log.i("enccoooded", encoded_imge + "fifi");
            // img.setImageBitmap(getResizedBitmap(bitmap,40));

            //  Glide.with(this).load("http://goo.gl/gEgYUd").into(img);

            Imags.add(encoded_imge);
            Glide.with(this).load(byteArrayImage).into(img);

            //  img.setImageBitmap(bitmap);

        } catch (Exception e) {

            e.printStackTrace();
        }
        return encoded_imge;
    }

    private void displayPlace(Place place) {
        if (place == null)
            return;
        String content = "";
        Latitue = place.getLatLng().latitude;
        Longtiude = place.getLatLng().longitude;
        Address = place.getAddress().toString();
        if (!TextUtils.isEmpty(place.getName())) {
            //   content += "Name: " + place.getName() + "\n";
        }
        if (!TextUtils.isEmpty(place.getAddress())) {
            content += "Address: " + place.getAddress() + "\n";
            // Here we can get location ....
        }
        if (!TextUtils.isEmpty(place.getPhoneNumber())) {
            //  content += "Phone: " + place.getPhoneNumber();
        }

        //placeonmap.setText(content);
    }

    public void getAllCategories() {

        categoriesPresenter presenter = new categoriesPresenter(addFragment.this, addFragment.this);
        HashMap<String, Object> Params = new HashMap<>();
        Params.put("lang", "ar");
        Params.put("id", 0);
        Params.put("take", 10);
        Params.put("offset", 0);
        Params.put("offset", 0);
        presenter.getCategories(Params);
    }

    public void setUpSpinner(ArrayList<service_cat_model> AllServices) {
        // Creating adapter for spinner
        //ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, AllServices);
        ArrayList<String> SpinnerData = new ArrayList<>();

        for (int i = 0; i < AllServices.size(); i++) {

            SpinnerData.add(AllServices.get(i).getName());
        }
        spinnerArrayAdapter adapter = new spinnerArrayAdapter(getApplicationContext(), SpinnerData);
        // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type_spinner.setAdapter(adapter);
        // Drop down layout style - list view with radio button
        //  dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        // type_spinner.setAdapter(dataAdapter);

        type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                selected_type = position ;
                if (position != 0) {
                    getSubCategories(all_types.get(position).getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });



    }

    public void getSubCategories(int ID) {

        categoriesPresenter presenter = new categoriesPresenter(addFragment.this, addFragment.this);
        HashMap<String, Object> Params = new HashMap<>();
        Params.put("lang", "ar");
        Params.put("id", ID);
        Params.put("take", 100);
        Params.put("offset", 0);
        Params.put("offset", 0);
        Log.i("paraaams", Params + "");
        presenter.getSubCategories(Params);
    }

    public void setUpSubSpinner(ArrayList<service_cat_model> AllServices) {
        // Creating adapter for spinner
        //ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, AllServices);
        ArrayList<String> SpinnerData = new ArrayList<>();

        for (int i = 0; i < AllServices.size(); i++) {

            SpinnerData.add(AllServices.get(i).getName());
        }
        spinnerArrayAdapter adapter = new spinnerArrayAdapter(addFragment.this, SpinnerData);
        // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sub_type.setAdapter(adapter);
        sub_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                Selected_sub_type = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {

            case R.id.card1:
                clickCode = 1;
                PickImageDialog.build(new PickSetup()).show(addFragment.this);
                break;
            case R.id.card2:
                clickCode = 2;
                PickImageDialog.build(new PickSetup()).show(addFragment.this);
                break;

            case R.id.card3:
                clickCode = 3;
                PickImageDialog.build(new PickSetup()).show(addFragment.this);
                break;

            case R.id.card4:
                clickCode = 4;
                PickImageDialog.build(new PickSetup()).show(addFragment.this);
                break;

            case R.id.card5:
                clickCode = 5;
                PickImageDialog.build(new PickSetup()).show(addFragment.this);
                break;

        }
    }


    public void checkPermissions() {
        boolean result = Utility.checkPermission(getApplicationContext());
        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // this.requestPermissions();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                this.requestPermissions(
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, data_Permission);
            }
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

   /* public void replaceME() {

         Toast.makeText(getApplicationContext(),getActivity().getResources().getString(R.string.thanks_for_upload),Toast.LENGTH_SHORT).show();
        ((testAcitivty)getApplicationContext()).hideView();
        getApplicationContext().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }*/

    public void getCities() {
        HashMap<String, Object> Params = new HashMap<>();
        // Params.put("offset", 0);
        // Params.put("take", 10);
        Params.put("lang", "ar");
        RegisterPresenter presenter = new RegisterPresenter(addFragment.this, addFragment.this);
        presenter.loadCities(Params);

    }

    @Override
    public void onRegisterSuccess(String Token) {

    }

    @Override
    public void onRegistrFailed(String Error) {

    }

    @Override
    public void onLoginSuccess(user_model user_model) {

    }

    @Override
    public void onLoginFailed(String Error) {

    }

    @Override
    public void onForgetPassSuccess(String Msg) {

    }

    @Override
    public void onForgetPassFail(String Msg) {

    }

    @Override
    public void onHomeCountriesLoadedSuccess(homeCountriesModel model) {

    }

    @Override
    public void onHomeCountriesLoadedFailed(String error) {

    }

    @Override
    public void onHomeCitiesLoadedSuccess(homeCitiesModel model) {
        cities = model.getCountries();
        Log.i("iamciires", model.getCountries().size() + "");
        loadHomeCitiesSpinner(model.getCountries());
    }

    public void loadHomeCitiesSpinner(ArrayList<homeCitiesModel.countryModel> Countries) {
        final citiesAdapter customAdapter = new citiesAdapter(getApplicationContext(), Countries);
        city_spinner.setAdapter(customAdapter);


        city_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onHomeCitiesLoadedFailed(String error) {

    }


    /**
     * add the back button action
     */

    @OnClick(R.id.nav_icon)void back(){
        finish();
    }
}
