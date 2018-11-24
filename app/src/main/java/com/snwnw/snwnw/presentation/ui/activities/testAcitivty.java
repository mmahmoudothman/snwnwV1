package com.snwnw.snwnw.presentation.ui.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;
import com.snwnw.snwnw.EventBus.LogoutModel;
import com.snwnw.snwnw.R;
import com.snwnw.snwnw.domain.models.mapsPlacesLoaderModel;
import com.snwnw.snwnw.presentation.presenters.impl.mapSearchPlacesPresenter;
import com.snwnw.snwnw.presentation.presenters.interfaces.mapSearchPlacesListener;
import com.snwnw.snwnw.presentation.ui.fragments.addFragment;
import com.snwnw.snwnw.presentation.ui.fragments.advancedSearchFragment;
import com.snwnw.snwnw.presentation.ui.fragments.favFragment;
import com.snwnw.snwnw.presentation.ui.fragments.offersFragment;
import com.snwnw.snwnw.presentation.ui.fragments.placeDetailsFragment;
import com.snwnw.snwnw.presentation.ui.fragments.profilePart1Fragment;
import com.snwnw.snwnw.presentation.ui.fragments.uploadedPlacesFragment;
import com.snwnw.snwnw.presentation.utils.Constants;
import com.snwnw.snwnw.presentation.utils.CustomTypefaceSpan;
import com.snwnw.snwnw.presentation.utils.LocaleManager;
import com.snwnw.snwnw.presentation.utils.SNWNWPrefs;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class testAcitivty extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, mapSearchPlacesListener {


    Marker marker;
    Marker myMarker;
    private GoogleApiClient googleApiClient;
    private Location mylocation;
    BottomBar bottomBar;
    @BindView(R.id.menu_btn)
    ImageView menu_btn;
    @BindView(R.id.nav_view)
    NavigationView nav_view;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer_layout;
    @BindView(R.id.content_view)
    LinearLayout content_view;
    @BindView(R.id.contentframe)
    FrameLayout contentframe;
    //    @BindView(R.id.mapView)
//    MapView mMapView;
    private GoogleMap _googleMap;
    private static final int LOCATION_PERMISSION_ID = 1001;
    PlaceAutocompleteFragment autocompleteFragment;
    @BindView(R.id.imgMyLocation)
    ImageView imgMyLocation;
    @BindView(R.id.header_txt)
    TextView header_txt;
    @BindView(R.id.add_place_img)
    ImageView add_place_img;

    //todo here to load the markers for the first time only
    int firstLoadMarker;
    int firstLoadMarker1;

    @OnClick(R.id.bannerImage)
    void gotoPlace() {
        Intent go = new Intent(testAcitivty.this, placeDetailsFragment.class);
        go.putExtra("placeid", 89);
        startActivity(go);
    }


    int zoomFirst;

    private final static int REQUEST_CHECK_SETTINGS_GPS = 0x1;
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS = 0x2;
    ArrayList<mapsPlacesLoaderModel.placeModel> Places = new ArrayList<>();


    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    //google sign in log out
    //google sign in
//    private GoogleApiClient mGoogleApiClient;


//    @Override
//    protected void onStart() {
//        super.onStart();
//       /* SmartLocation.with(getApplicationContext()).location()
//                .start(new OnLocationUpdatedListener() {
//                    @Override
//                    public void onLocationUpdated(Location location) {
//
//                        Toast.makeText(getApplicationContext(),location.getLatitude()+"&"+location.getLongitude(),Toast
//                        .LENGTH_SHORT).show();
//                    }
//                });
//*/
//    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_acitivty);
        ButterKnife.bind(this);

        // Initialize Firebase Auth

        mFirebaseAuth = FirebaseAuth.getInstance();
        //google sign in logout


        zoomFirst = 0;
        firstLoadMarker = 0;
        firstLoadMarker1 = 0;


        LocaleManager.setNewLocale(getApplicationContext(), "ar");
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        bottomBar = findViewById(R.id.bottomBar);
        // setHeader(getResources().getString(R.string.ic_home));
        // setUpAutoComplete();
        loadPlaces();

        // setUpGClient();
       /* if (Build.VERSION.SDK_INT >= 23) {

            if (ContextCompat.checkSelfPermission(testAcitivty.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(testAcitivty.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_ID);
                return;
            } else {
                //startLocation();
            }
        }
        else {
           // startLocation();
        }*/

//        mMapView.onCreate(savedInstanceState);
//        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

//        mMapView.getMapAsync(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map__);
        mapFragment.getMapAsync(this);

        overrideFonts(getApplicationContext(), drawer_layout);

        imgMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double curlat = Double.parseDouble(SNWNWPrefs.getDefaults("curlat", getApplicationContext()));
                double curlng = Double.parseDouble(SNWNWPrefs.getDefaults("curlng", getApplicationContext()));
                LatLng myLoc = new LatLng(curlat, curlng);
                zoomToLoc(myLoc, "mylocation");
            }
        });

        add_place_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // replace_first_time(addFragment.newInstance(""));
                Intent go = new Intent(testAcitivty.this, addFragment.class);
                startActivity(go);
            }
        });

        /// setSupportActionBar(toolbar);
        //   getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        setTypeFace();

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_main) {
                    content_view.setVisibility(View.VISIBLE);
                    contentframe.setVisibility(View.GONE);
                } else if (tabId == R.id.tab_fav) {
                    content_view.setVisibility(View.GONE);
                    contentframe.setVisibility(View.VISIBLE);

                    replace_fragment_internal(favFragment.newInstance(""));
                } else if (tabId == R.id.tab_search) {
                    content_view.setVisibility(View.GONE);
                    contentframe.setVisibility(View.VISIBLE);

                    //   replace_fragment_internal(categoriesFragment.newInstance(""));
                    replace_fragment_internal(advancedSearchFragment.newInstance(""));

                } else if (tabId == R.id.tab_offers) {
                    content_view.setVisibility(View.GONE);
                    contentframe.setVisibility(View.VISIBLE);
                    replace_fragment_internal(offersFragment.newInstance(""));
                } else if (tabId == R.id.tab_profile) {
                    content_view.setVisibility(View.GONE);
                    contentframe.setVisibility(View.VISIBLE);

                    replace_fragment_internal(profilePart1Fragment.newInstance(""));
                }

            }
        });

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(int tabId) {
                if (tabId == R.id.tab_search) {
                    content_view.setVisibility(View.GONE);
                    contentframe.setVisibility(View.VISIBLE);

                    replace_fragment_internal(advancedSearchFragment.newInstance(""));
                    //  replace_fragment_internal(categoriesFragment.newInstance(""));
                }
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
       /* ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);*/
      /*  drawer.addDrawerListener(toggle);
        // drawer.setForegroundGravity(Gravity.RIGHT);
        toggle.syncState();*/

        NavigationView navigationView = findViewById(R.id.nav_view);

        Menu m = navigationView.getMenu();
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }


        navigationView.setNavigationItemSelectedListener(this);

        menu_btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RtlHardcoded")
            @Override
            public void onClick(View view) {
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.test_acitivty, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar menu_item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view menu_item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_fav) {
            content_view.setVisibility(View.GONE);
            contentframe.setVisibility(View.VISIBLE);
            replace_fragment_internal(favFragment.newInstance(""));

            replace_fragment(favFragment.newInstance(""));
        } else if (id == R.id.nav_slideshow) {

            // replace_fragment(uploadedPlacesFragment.newInstance(""));
            content_view.setVisibility(View.GONE);
            contentframe.setVisibility(View.VISIBLE);
            replace_fragment_internal(uploadedPlacesFragment.newInstance(""));
        } else if (id == R.id.nav_gallery) {
            // replace_fragment(uploadedPlacesFragment.newInstance(""));
            content_view.setVisibility(View.GONE);
            contentframe.setVisibility(View.VISIBLE);
            replace_fragment_internal(advancedSearchFragment.newInstance(""));
        } else if (id == R.id.nav_feedback) {
            // open activity feedback
            Intent intent = new Intent(testAcitivty.this, feedbackActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_quest) {
            Intent intent = new Intent(testAcitivty.this, FQAActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(testAcitivty.this, settingActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_exit) {
            SNWNWPrefs.setDefaults(Constants.Token, "", getApplicationContext());
            mFirebaseAuth.signOut();
            //facebook sign out
            if (LoginManager.getInstance() != null)
                LoginManager.getInstance().logOut();
            //google log out
            // Google sign out
            if (Auth.CREDENTIALS_API != null)
                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(@NonNull Status status) {
//                                updateUI(null);
                            }
                        });

            finish();
        } else if (id == R.id.nav_camera) {
            Intent go = new Intent(testAcitivty.this, addFragment.class);
            startActivity(go);

        } else if (id == R.id.nav_share) {

            share();
        }

        else if (id == R.id.nav_event) {
            startActivity(new Intent(this, AddEventActivity.class));
        }

     /*   if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
*/
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogoutModel(LogoutModel event) {
        if (event.isLogout()) {
            SNWNWPrefs.setDefaults(Constants.Token, "", getApplicationContext());
            mFirebaseAuth.signOut();
            //facebook sign out
            if (LoginManager.getInstance() != null)
                LoginManager.getInstance().logOut();
            //google log out
            // Google sign out
            if (Auth.CREDENTIALS_API != null)
                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(@NonNull Status status) {
//                                updateUI(null);
                            }
                        });

            finish();
        }
    }


    private void share() {
        List<Intent> targetedShareIntents = new ArrayList<>();
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        // Set title and text to share when the user selects an option.
//                shareIntent.putExtra(Intent.EXTRA_TITLE, title);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.snwnw.snwnw");
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
            intentPick.putExtra(Intent.EXTRA_TITLE, "عائلة السريع");
            intentPick.putExtra(Intent.EXTRA_INTENT, shareIntent);
            intentPick.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray());
            // Call StartActivityForResult so we can get the app name selected by the user
            startActivityForResult(intentPick, 3);
        }
    }


    public void setTypeFace() {
        Typeface font = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/JF_Flat_regular.ttf");
        //  bottomBar.setTabTitleTypeface(font);
    }

    public void replace_fragment(Fragment frag) {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentframe, frag).addToBackStack("");
        fragmentTransaction.commit();
    }

    public void replace_fragment_internal(Fragment frag) {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // ExampleFragments fragment = new ExampleFragments();
        fragmentTransaction.replace(R.id.contentframe, frag).addToBackStack("");
        fragmentTransaction.commit();
    }

    public void replace_first_time(Fragment frag) {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // ExampleFragments fragment = new ExampleFragments();
        fragmentTransaction.replace(R.id.contentframe, frag);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public static void overrideFonts(final Context context, final View v) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/JF_Flat_regular.ttf");

        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideFonts(context, child);
                }
            } else if (v instanceof TextView) {
                ((TextView) v).setTypeface(font);
            }
        } catch (Exception ignored) {
        }
    }

    public void setUpMapView() {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        _googleMap = googleMap;

        setUpGClient();
       /* if (Build.VERSION.SDK_INT >= 23) {

            if (ContextCompat.checkSelfPermission(testAcitivty.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(testAcitivty.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_ID);
                return;
            } else {
               // startLocation();
            }
        }
        else {
          //  startLocation();
        }*/


        _googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker.getTag() != null) {
                    Log.i("iamthetag", marker.getTag() + "");
                    Intent go = new Intent(testAcitivty.this, placeDetailsFragment.class);
                    go.putExtra("placeid", (Integer) marker.getTag());
                    startActivity(go);
                    //  replace_fragment(placeDetailsFragment.newInstance((Integer) marker.getTag()));
                }
                return false;
            }
        });


    }

    public void setUpAutoComplete(String CountryCode) {
        autocompleteFragment =
                (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("places", "Place: " + place.getName());
                zoomToLoc((place.getLatLng()), place.getName().toString());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("places", "An error occurred: " + status);
            }
        });
        setAutocompleteFilter(CountryCode);

    }

    public void setAutocompleteFilter(String CountryCode) {
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setCountry(CountryCode)
                .build();
        autocompleteFragment.setFilter(typeFilter);
    }


  /*  @Override
    public void onLocationUpdated(Location location) {
        LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
        SNWNWPrefs.setDefaults("curlat",String.valueOf(location.getLatitude()),getApplicationContext());
        SNWNWPrefs.setDefaults("curlng",String.valueOf(location.getLongitude()),getApplicationContext());
        if (marker != null) {
            marker.remove();
        }
            marker = _googleMap.addMarker(new MarkerOptions()
                    .position(myLocation).title("myLocation").snippet(""));
            CameraPosition cameraPosition = new CameraPosition.Builder().target(myLocation).zoom(16).build();
            _googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            String CountryCode = getCountryName(getApplicationContext(), location.getLatitude(), location.getLongitude());
            setUpAutoComplete(CountryCode);

    }*/

    public void zoomToLoc(LatLng locate, String title) {

        // LatLng myLocation = new LatLng(location.getLatitude(),location.getLongitude());
        if (myMarker != null) {
            myMarker.remove();
        }
        myMarker = _googleMap.addMarker(new MarkerOptions()
                .position(locate).title(title).snippet(""));

        // _googleMap.addMarker(new MarkerOptions().position(myLocation).title("").snippet(""));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(locate).zoom(12).build();
        _googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }


    //todo here to add the logo on the marker
    private Bitmap getMarkerBitmapFromView(String resId, String logo) {

        assert (getSystemService(Context.LAYOUT_INFLATER_SERVICE)) != null;
        @SuppressLint("InflateParams") View customMarkerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_custom_marker, null);
        TextView markerImageView = customMarkerView.findViewById(R.id.ivHostImage);
        CircleImageView logoImage = customMarkerView.findViewById(R.id.logoImage);
        markerImageView.setText(resId);


        if (logo == null || logo.isEmpty())
            logoImage.setImageDrawable(getResources().getDrawable(R.drawable.logo_blue_preview));

        if (!testAcitivty.this.isDestroyed())
            Glide.with(testAcitivty.this).load(logo).into(logoImage);


        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }


    public static String getCountryName(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        String country_code = "";
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            Address result;
            if (addresses != null && !addresses.isEmpty()) {
                country_code = addresses.get(0).getCountryCode();
                return country_code;
                // return  addresses.get(0).getCountryName();
            }
            return null;
        } catch (IOException ignored) {
            //do something
        }

        return country_code;
    }

    public void hideView() {
        content_view.setVisibility(View.VISIBLE);
        contentframe.setVisibility(View.GONE);
    }

    public void showView() {
        content_view.setVisibility(View.GONE);
        contentframe.setVisibility(View.VISIBLE);
    }

    public void setUpViewPager() {
        //    ViewPager pager = (ViewPager) findViewById(R.id.pager);
        //    pager.setAdapter(new TestAdapter(getSupportFragmentManager()));

// Bind the tabs to the ViewPager
       /* PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(pager);*/

    }

    public void setHeader(String str) {

        header_txt.setText(str);
    }

   /* public void updateMyLocation(Location location){
        LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
        SNWNWPrefs.setDefaults("curlat",String.valueOf(location.getLatitude()),getApplicationContext());
        SNWNWPrefs.setDefaults("curlng",String.valueOf(location.getLongitude()),getApplicationContext());
        if (marker != null) {
            marker.remove();
        }
        marker = _googleMap.addMarker(new MarkerOptions()
                .position(myLocation).title("myLocation").snippet("").icon(BitmapDescriptorFactory.fromResource(R.drawable.if_location_48)));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(myLocation).zoom(16).build();
        _googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        String CountryCode = getCountryName(getApplicationContext(), location.getLatitude(), location.getLongitude());
        setUpAutoComplete(CountryCode);
    }*/

    public void createSearchDialog() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getApplicationContext());

        // set title
        alertDialogBuilder.setTitle("Your Title");

        // set dialog message
        alertDialogBuilder
                .setMessage("Click yes to exit!")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        testAcitivty.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();


    }

    public void createDialog() {
 /*       LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final Dialog dialog = new Dialog(ProfileActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.logout);
        TextView txtv_logout = (TextView)dialog.findViewById(R.id.txtv_logout);
        TextView txtv_canceltoolbar = (TextView)dialog.findViewById(R.id.txtv_canceltoolbar);
        txtv_canceltoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        txtv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                logout();
                dialog.dismiss();
            }
        });

        dialog.show();*/
    }


    private synchronized void setUpGClient() {


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        googleApiClient.connect();
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
        LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
        SNWNWPrefs.setDefaults("curlat", String.valueOf(location.getLatitude()), getApplicationContext());
        SNWNWPrefs.setDefaults("curlng", String.valueOf(location.getLongitude()), getApplicationContext());
        if (myMarker != null) {
            myMarker.remove();
        }

//        myMarker.remove();
        if (firstLoadMarker1 == 0 && firstLoadMarker == 0)
            _googleMap.clear();


        myMarker = _googleMap.addMarker(new MarkerOptions()
                .position(myLocation).title("myLocation").snippet("").icon(BitmapDescriptorFactory.fromResource(R.drawable.if_location_48)));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(myLocation).zoom(16).build();

        //todo make the zoom here again
        if (zoomFirst == 0) {
            _googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            zoomFirst = 1;
        }


        String CountryCode = getCountryName(getApplicationContext(), location.getLatitude(), location.getLongitude());
        setUpAutoComplete(CountryCode);


        //todo here to fix the icons in the markers
//       // loadPlaces() ;

        if (firstLoadMarker == 0 && firstLoadMarker1 == 0) {

            if (Places.size() != 0) {
                loadMarkers(Places);
                firstLoadMarker = 1;
            } else {
                loadPlaces();
                firstLoadMarker1 = 1;
            }


        }

    }


    private void checkPermissions() {
        int permissionLocation = ContextCompat.checkSelfPermission(testAcitivty.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this,
                        listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            }
        } else {
            getMyLocation();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        int permissionLocation = ContextCompat.checkSelfPermission(testAcitivty.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
            getMyLocation();
        }
    }


    private void getMyLocation() {
        if (googleApiClient != null) {
            if (googleApiClient.isConnected()) {
                int permissionLocation = ContextCompat.checkSelfPermission(testAcitivty.this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                    mylocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                    LocationRequest locationRequest = new LocationRequest();
                    locationRequest.setInterval(6 * 1000);
                    locationRequest.setFastestInterval(6 * 1000);
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                            .addLocationRequest(locationRequest);
                    builder.setAlwaysShow(true);
                    LocationServices.FusedLocationApi
                            .requestLocationUpdates(googleApiClient, locationRequest, this);
                    PendingResult<LocationSettingsResult> result =
                            LocationServices.SettingsApi
                                    .checkLocationSettings(googleApiClient, builder.build());
                    result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

                        @Override
                        public void onResult(LocationSettingsResult result) {
                            final Status status = result.getStatus();
                            switch (status.getStatusCode()) {
                                case LocationSettingsStatusCodes.SUCCESS:
                                    // All location settings are satisfied.
                                    // You can initialize location requests here.
                                    int permissionLocation = ContextCompat
                                            .checkSelfPermission(testAcitivty.this,
                                                    Manifest.permission.ACCESS_FINE_LOCATION);
                                    if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                                        mylocation = LocationServices.FusedLocationApi
                                                .getLastLocation(googleApiClient);
                                    }
                                    break;
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    // Location settings are not satisfied.
                                    // But could be fixed by showing the user a dialog.
                                    try {
                                        // Show the dialog by calling startResolutionForResult(),
                                        // and check the result in onActivityResult().
                                        // Ask to turn on GPS automatically
                                        status.startResolutionForResult(testAcitivty.this,
                                                REQUEST_CHECK_SETTINGS_GPS);
                                    } catch (IntentSender.SendIntentException e) {
                                        // Ignore the error.
                                    }
                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    // Location settings are not satisfied.
                                    // However, we have no way
                                    // to fix the
                                    // settings so we won't show the dialog.
                                    // finish();
                                    break;
                            }
                        }
                    });
                }
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS_GPS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        getMyLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        finish();
                        break;
                }
                break;
        }

        if (requestCode == 3) {
            if (data != null && data.getComponent() != null && !TextUtils.isEmpty(data.getComponent().flattenToShortString())) {
                String appName = data.getComponent().flattenToShortString();
                // Now you know the app being picked.
                // data is a copy of your launchIntent with this important extra info added.


                // Start the selected activity

                if (appName.contains("facebook")) {
                    //todo the facebook
                    ShareLinkContent content = new ShareLinkContent.Builder()
                            .setContentUrl(Uri.parse("https://play.google.com/store/apps/details?id=com.snwnw.snwnw"))
                            .setQuote("Snwnw app")
                            .build();

                    ShareDialog.show(testAcitivty.this, content);
                }
                startActivity(data);
            }
        }
    }

    public void loadPlaces() {

        mapSearchPlacesPresenter presenter = new mapSearchPlacesPresenter(testAcitivty.this, testAcitivty.this);
        HashMap<String, Object> Params = new HashMap<>();
        Params.put("latitude", SNWNWPrefs.getDefaults(Constants.curlat, getApplicationContext()));
        Params.put("longitude", SNWNWPrefs.getDefaults(Constants.curlng, getApplicationContext()));
        //  Params.put("latitude","30.3");
        //   Params.put("longitude","20.1");
        Params.put("lang", "ar");

        String Token = "Bearer" + " " + SNWNWPrefs.getDefaults(Constants.Token, getApplicationContext());
        presenter.getPlaces(Params, Token);
    }

    @Override
    public void onMapPlacesLoaded(mapsPlacesLoaderModel mapsPlacesLoaderModel) {

        Places = mapsPlacesLoaderModel.getPlaces();


        loadMarkers(Places);


        Log.i("iamlocation", mapsPlacesLoaderModel.getPlaces().size() + "");
    }

    @Override
    public void onMapPlacesFailed(int code, String Error) {

    }

    public void loadMarkers(ArrayList<mapsPlacesLoaderModel.placeModel> AllPlaces) {
        ArrayList<Marker> AllMarkers = new ArrayList<>();
        for (int i = 0; i < AllPlaces.size(); i++) {

            MarkerOptions m = new MarkerOptions().position(new LatLng(AllPlaces.get(i).getLatitude(), AllPlaces.get(i).getLongitude()))
                    .title(AllPlaces.get(i).getTitle());

            MarkerOptions marker1 = new MarkerOptions()
                    .position(new LatLng(AllPlaces.get(i).getLatitude(), AllPlaces.get(i).getLongitude()))
                    .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(AllPlaces.get(i).getTitle(),
                            AllPlaces.get(i).getLogo())));
//             Log.d("google","this is the logo "+AllPlaces.get(i).getLogo());


            marker = _googleMap.addMarker(marker1);
            marker.setTag(AllPlaces.get(i).getId());
            AllMarkers.add(marker);
  /*           _googleMap.addMarker(new MarkerOptions().position(new LatLng(AllPlaces.get(i).getLatitude(),AllPlaces.get(i).getLongitude()))
                     .title(AllPlaces.get(i).getTitle()));
*/
        }

      /*  LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : AllMarkers) {
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();
        int padding = 0; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        _googleMap.moveCamera(cu);*/

    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/JF_Flat_regular.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        //mNewTitle.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, mNewTitle.length(), 0); Use this if you want to center the items
        mi.setTitle(mNewTitle);
    }


}
