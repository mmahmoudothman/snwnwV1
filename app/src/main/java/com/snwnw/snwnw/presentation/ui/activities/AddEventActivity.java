package com.snwnw.snwnw.presentation.ui.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.snwnw.snwnw.R;
import com.snwnw.snwnw.presentation.ui.fragments.addFragment;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddEventActivity extends AppCompatActivity implements IPickResult {
    //init
    @BindView(R.id.eventnameET)
    EditText eventnameET;
    @BindView(R.id.selectedImage)
    ImageView selectedImage;
    @BindView(R.id.selectImageTV)
    TextView selectImageTV;
    @BindView(R.id.eventplaceET)
    EditText eventplaceET;
    @BindView(R.id.eventDescET)
    EditText eventDescET;

    Bitmap image;


    //for the location from google map
    int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        ButterKnife.bind(this);

    }


    //to select the image;
    @OnClick(R.id.selectImageTV)
    void imageselect() {
        PickImageDialog.build(new PickSetup()).show(AddEventActivity.this);
    }


    //select the place google maps
    @OnClick(R.id.eventplaceET)
    void selectplace() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);

        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }


    double lat, lang;


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName());
                LatLng latLng = place.getLatLng();
                Log.d("google", latLng.toString());
                eventplaceET.setText(place.getAddress());

                lang = latLng.longitude;
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();


            }
        } else if (requestCode == CAMERA_REQUEST) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            selectedImage.setImageBitmap(photo);
        } else {
            Bitmap bm = null;
            if (data != null) {
                try {
                    bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bm==null)
                return;
            selectedImage.setImageBitmap(bm);
        }
    }


    private static final int GALLARY_INTENT = 2;
    private static final int CAMERA_REQUEST = 3;


    @Override
    public void onPickResult(PickResult pickResult) {

        if (pickResult.getError() == null) {
            //If you want the Uri.
            //Mandatory to refresh image from Uri.
            //getImageView().setImageURI(null);

            //Setting the real returned image.
            //getImageView().setImageURI(r.getUri());

            //If you want the Bitmap.
            selectedImage.setImageBitmap(pickResult.getBitmap());
            image = pickResult.getBitmap();
//            take_photo_profile.setVisibility(View.GONE);


            //Image path
            //r.getPath();
        } else {
            //Handle possible errors
            Toast.makeText(this, pickResult.getError().getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
