package com.snwnw.snwnw.presentation.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.snwnw.snwnw.domain.models.expand_place_model;
import com.snwnw.snwnw.domain.models.places_model;
import com.snwnw.snwnw.presentation.presenters.interfaces.PlacesListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by navneet on 23/7/16.
 */
public class GetNearbyPlacesData extends AsyncTask<Object, String, String> {

    String googlePlacesData;
    //GoogleMap mMap;
    String url;
    Context _context;
    PlacesListener _listener;
    String Type ;

    public GetNearbyPlacesData(final Context context,String Type,PlacesListener listener) {
        this._context = context;
        this._listener = listener;
        this.Type = Type ;
    }

    @Override
    protected void onPreExecute() {
      /*  dialog = new ACProgressCustom.Builder(_context).speed(3)
                .useImages(R.drawable.g, R.drawable.n, R.drawable.g, R.drawable.n).build();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();*/
    }

    @Override
    protected String doInBackground(Object... params) {
        try {
            Log.d("GetNearbyPlacesData", "doInBackground entered");
            //  mMap = (GoogleMap) params[0];
            url = (String) params[0];
            DownloadUrl downloadUrl = new DownloadUrl();
            googlePlacesData = downloadUrl.readUrl(url);
            Log.i("iamuserplaces",googlePlacesData+"");
            Log.d("GooglePlacesReadTask", "doInBackground Exit");
        } catch (Exception e) {
            Log.d("GooglePlacesReadTask", e.toString());
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String result) {
      // dialog.dismiss();
     //   List<HashMap<String, String>> nearbyPlacesList = new ArrayList<>();
        DataParser dataParser = new DataParser();
        List<HashMap<String, String>> nearbyPlacesList = dataParser.parse(result);
        Log.d("GooglePlacesReadTask", "onPostExecute Entered" + nearbyPlacesList.size());

        ShowNearbyPlaces(nearbyPlacesList);
        Log.d("GooglePlacesReadTask", "onPostExecute Exit");
    }

    private void ShowNearbyPlaces(List<HashMap<String, String>> nearbyPlacesList) {
        ArrayList<places_model> AllPlaces = new ArrayList<>();
        ArrayList<expand_place_model> AllPlacesList = new ArrayList<>();
        for (int i = 0; i < nearbyPlacesList.size(); i++) {
            Log.d("onPostExecute", "Entered into showing locations");
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> googlePlace = nearbyPlacesList.get(i);
            double lat = Double.parseDouble(googlePlace.get("lat"));
            double lng = Double.parseDouble(googlePlace.get("lng"));
            String placeName = googlePlace.get("place_name");
            String vicinity = googlePlace.get("vicinity");

            /*if (error_message.length()!=0){

                Toast.makeText(_context,error_message,Toast.LENGTH_SHORT).show();
            }*/

            Log.i("iamplacenem",placeName);
            places_model model = new places_model(lat, lng, placeName, vicinity);
            AllPlaces.add(model);
        }
        expand_place_model model = new expand_place_model(Type,AllPlaces);
        _listener.onPlacesLoaded(Type,model);

        Log.i("iamallplaces", AllPlaces.size() + "");
    }
}
