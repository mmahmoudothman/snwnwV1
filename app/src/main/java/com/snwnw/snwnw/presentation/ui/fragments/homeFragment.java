package com.snwnw.snwnw.presentation.ui.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.seatgeek.placesautocomplete.OnPlaceSelectedListener;
import com.seatgeek.placesautocomplete.PlacesAutocompleteTextView;
import com.snwnw.snwnw.R;
import com.snwnw.snwnw.domain.models.expand_place_model;
import com.snwnw.snwnw.presentation.presenters.interfaces.PlacesListener;
import com.snwnw.snwnw.presentation.ui.activities.filterActivity;
import com.snwnw.snwnw.presentation.ui.activities.testAcitivty;
import com.snwnw.snwnw.presentation.utils.Constants;
import com.snwnw.snwnw.presentation.utils.GetNearbyPlacesData;
import com.snwnw.snwnw.presentation.utils.SNWNWPrefs;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by afaf.elshafey on 11/6/2017.
 */

public class homeFragment extends Fragment implements OnMapReadyCallback, PlacesListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    SupportMapFragment mMapView;
    private GoogleMap _googleMap;
    private int PROXIMITY_RADIUS = 5000; // in meters...
    EditText search_view;
    PlacesAutocompleteTextView places_autocomplete;
    private GoogleApiClient googleApiClient;
    public static int REQUEST_ID_MULTIPLE_PERMISSIONS = 1000;
    private Location mylocation;
    private final static int REQUEST_CHECK_SETTINGS_GPS = 0x1;
    @BindView(R.id.imgMyLocation)
    ImageView imgMyLocation;
    PlaceAutocompleteFragment autocompleteFragment ;
    private static View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      //  View view = inflater.inflate(R.layout.home_fragment, container, false);

        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
           // view = inflater.inflate(R.layout.map, container, false);
             view = inflater.inflate(R.layout.home_fragment, container, false);
            ButterKnife.bind(this,view);
            ((testAcitivty)getActivity()).setHeader(getResources().getString(R.string.ic_home));

            mMapView =(SupportMapFragment) getFragmentManager()
                    .findFragmentById(R.id.map__);
        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }

        setUpGClient();
        setUpAutoComplete();
        //  search_view = (EditText)view.findViewById(R.id.search_view);
        places_autocomplete = (PlacesAutocompleteTextView) view.findViewById(R.id.places_autocomplete);
        setupTypeFace();
        places_autocomplete.setOnPlaceSelectedListener(
                new OnPlaceSelectedListener() {
                    @Override
                    public void onPlaceSelected(@NonNull com.seatgeek.placesautocomplete.model.Place place) {
                        Toast.makeText(getActivity(), place.description, Toast.LENGTH_SHORT).show();
                    }

                }
        );

        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(this);

        imgMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMyLocation();
            }
        });

        return view;
    }

    public static homeFragment newInstance(String text) {
        homeFragment f = new homeFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        _googleMap = googleMap;
        // For showing a move to my location button
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
      //  googleMap.setMyLocationEnabled(true);
        // For dropping a marker at a point on the Map
     //   _googleMap.addMarker(new MarkerOptions().position(curlocation).title("Marker Title").snippet("Marker Description"));
    //    CameraPosition cameraPosition = new CameraPosition.Builder().target(curlocation).zoom(12).build();
    //    _googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    public void getAllService(String Type,double lat, double lng) {
      //  double cur_lat = Double.parseDouble(SNWNWPrefs.getDefaults(constants.cur_lat, getActivity()));
      //  double cur_lng = Double.parseDouble(GNGNPrefs.getDefaults(constants.cur_lng, getActivity()));

      /*  double cur_lat = 31.244288;
        double cur_lng = 29.986816;*/

        String url = getUrl(lat, lng, Type);
        Object[] DataTransfer = new Object[2];
        // DataTransfer[0] = mMap;
        DataTransfer[0] = url;
        Log.d("onClick", url);
        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData(getActivity(), Type,homeFragment.this);
        getNearbyPlacesData.execute(DataTransfer);

    }

    private String getUrl(double latitude, double longitude, String nearbyPlace) {
        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + latitude + "," + longitude);
        googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
        googlePlacesUrl.append("&type=" + nearbyPlace);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + "AIzaSyC97r2CkllgQuM1McJNXN-kh7OZ4DQrenA");
        Log.d("getUrl", googlePlacesUrl.toString());
        return (googlePlacesUrl.toString());
    }

    @Override
    public void onPlacesLoaded(String ServiceName, expand_place_model all_places) {
        for (int i=0 ;i<all_places.getAllPlaces().size() ;i++) {
            LatLng newloc = new LatLng(all_places.getAllPlaces().get(i).getLat(), all_places.getAllPlaces().get(i).getLat());
           _googleMap.addMarker(new MarkerOptions().position(newloc).title(all_places.getAllPlaces().get(i).getPlaceName()).snippet(""));
            CameraPosition cameraPosition = new CameraPosition.Builder().target(newloc).zoom(12).build();
            _googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }

    }

    @Override
    public void onPlacesFailed(String error) {

    }
    public void setupTypeFace(){
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/JF_Flat_regular.ttf");
        places_autocomplete.setTypeface(font);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        checkPermissions();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i("iamyourloca",location.getLatitude()+"&"+location.getLongitude());
        SNWNWPrefs.setDefaults(Constants.curlat,String.valueOf(location.getLatitude()),getActivity());
        SNWNWPrefs.setDefaults(Constants.curlng,String.valueOf(location.getLongitude()),getActivity());

        LatLng curlocation = new LatLng(location.getLatitude(),location.getLongitude());

        Log.i("iamcurrentlocation",curlocation.latitude+"");

        updateMarker(curlocation);
     //   getAllService("restaurant",location.getLatitude(),location.getLongitude());


        //  _googleMap.addMarker(new MarkerOptions().position(curlocation).title("mylocation").snippet(""));
      //  CameraPosition cameraPosition = new CameraPosition.Builder().target(curlocation).zoom(12).build();
      //  _googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


    }
    private void checkPermissions() {
        int permissionLocation = ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(getActivity(),
                        listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            }
        } else {
            getMyLocation();

        }
    }
    private void getMyLocation() {
        if (googleApiClient != null) {
            if (googleApiClient.isConnected()) {
                int permissionLocation = ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                    mylocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                    LocationRequest locationRequest = new LocationRequest();
                   /* locationRequest.setInterval(30000);
                    locationRequest.setFastestInterval(30000);*/
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                            .addLocationRequest(locationRequest);
                    builder.setAlwaysShow(true);
                    LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, (com.google.android.gms.location.LocationListener) this);
                    PendingResult result =
                            LocationServices.SettingsApi
                                    .checkLocationSettings(googleApiClient, builder.build());

                    result.setResultCallback(new ResultCallback() {
                        @Override
                        public void onResult(@NonNull Result result) {

                            final Status status = result.getStatus();
                            switch (status.getStatusCode()) {
                                case LocationSettingsStatusCodes.SUCCESS:

                                    Log.i("iamgpsstaus", "success");
                                    // All location settings are satisfied.
                                    // You can initialize location requests here.
                                    int permissionLocation = ContextCompat
                                            .checkSelfPermission(getActivity(),
                                                    Manifest.permission.ACCESS_FINE_LOCATION);
                                    if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                                        mylocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

                                       drawMarker(mylocation) ;
                                        // if (mylocation != null) {
                                        ///  saveLocation(mylocation.getLatitude(), mylocation.getLongitude());
                                        //  }
                                    }
                                    break;
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                                    Log.i("iamgpsstaus", "resloutiin");
                                    // Location settings are not satisfied.
                                    // But could be fixed by showing the user a dialog.
                                    try {
                                        // Show the dialog by calling startResolutionForResult(),
                                        // and check the result in onActivityResult().
                                        // Ask to turn on GPS automatically
                                        status.startResolutionForResult(getActivity(),
                                                REQUEST_CHECK_SETTINGS_GPS);
                                    } catch (IntentSender.SendIntentException e) {
                                        // Ignore the error.
                                    }
                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    Log.i("iamgpsstaus", "unavailable");
                                    // Location settings are not satisfied. However, we have no way to fix the
                                    // settings so we won't show the dialog.
                                    //finish();
                                    break;
                            }
                        }

                    });


                }
            }
        }
    }
    @Override
    public void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        googleApiClient.disconnect();
        super.onDestroy();
    }

    private synchronized void setUpGClient() {
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(), 0, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        int permissionLocation = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
            getMyLocation();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        googleApiClient.stopAutoManage(getActivity());
        googleApiClient.disconnect();
    }

    private void drawMarker(Location location){
        _googleMap.clear();
        LatLng currentPosition = new LatLng(location.getLatitude(),location.getLongitude());
        _googleMap.addMarker(new MarkerOptions()
                .position(currentPosition)
                .snippet("Lat:" + location.getLatitude() + "Lng:"+ location.getLongitude())
                 .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .title("ME"));
    }

    private void updateMarker(LatLng latLng) {
       // LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18);
        _googleMap.animateCamera(cameraUpdate);
    }

    public void setUpAutoComplete(){
        autocompleteFragment =
                (PlaceAutocompleteFragment)getActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("places", "Place: " + place.getName());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("places", "An error occurred: " + status);
            }
        });

        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setCountry("EG")
                .build();

        autocompleteFragment.setFilter(typeFilter);
    }

//});
}
