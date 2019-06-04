package com.example.worldapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.worldapp.BaseClasses.BaseAppCompat;
import com.example.worldapp.Constants.NavigationConstants;
import com.example.worldapp.Core.TourCore;
import com.example.worldapp.Core.UserCore;
import com.example.worldapp.Models.GuidedToursModel;
import com.example.worldapp.Models.HomeDetailsModel;
import com.example.worldapp.Models.TourBookingManager;
import com.example.worldapp.Models.UserDetailsModel;
import com.example.worldapp.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

public class AccommodationActivity extends BaseAppCompat implements OnMapReadyCallback {
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private HomeDetailsModel mHome;
    private String mOwnerId;
    private MapView mMapView;
    private DatabaseReference mDatabaseReference;
    ArrayList<String> mExistingBookingManagers = new ArrayList<>();
    ArrayList<String> mExistingBookedDatesTours = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accommodation);
        InitializeViews();

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);

        Gson gson = new Gson();
        String aux = getIntent().getStringExtra(NavigationConstants.ACCOMMODATION_MODEL_KEY);
        mHome = gson.fromJson(aux, HomeDetailsModel.class);
        mOwnerId = mHome.getUserId();
    }

    private void InitializeViews() {
        mMapView = findViewById(R.id.map_accommodation);
    }

    public void OnBook(View view) {
        if (UserCore.Instance().isLoggedIn())
        {
            String aux;
            aux = TourCore.Instance().getmBookedDates();
           /* if (aux.isEmpty())
            {
                Toast.makeText(AccommodationActivity.this,"Please select a date!", Toast.LENGTH_SHORT).show();
                ToursFilterActivity.DatePickerFragment newFragment = new ToursFilterActivity.DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }*/
            //doTheBooking();
        }
        else
        {
            Intent myIntent = new Intent(AccommodationActivity.this, ActivityLogin.class);
            myIntent.putExtra("tourID", mHome.getHomeId());
            startActivity(myIntent);
            Toast.makeText(this, "You must be logged in in order to book!", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateBookingManager(TourBookingManager mManager, UserDetailsModel mUser)
    {
        if (mUser.getmBooking()!=null) {
            mExistingBookingManagers = mUser.getmBooking();
        }
        mExistingBookingManagers.add(mManager.getmBookingId());
        HashMap<String, Object> map = new HashMap<>();
        map.put("mBookingManager", mExistingBookingManagers);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("users").child(mUser.getUserId());
        mDatabaseReference.updateChildren(map);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }
        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapView.onStop();
    }
    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}
