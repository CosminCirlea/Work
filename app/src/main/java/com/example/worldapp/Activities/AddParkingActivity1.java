package com.example.worldapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.worldapp.BaseClasses.BaseAppCompat;
import com.example.worldapp.Constants.NavigationConstants;
import com.example.worldapp.Core.ParkingCore;
import com.example.worldapp.Core.TourCore;
import com.example.worldapp.Core.UserCore;
import com.example.worldapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class AddParkingActivity1 extends BaseAppCompat implements OnMapReadyCallback {
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey1";
    private MapView mMapView;
    private EditText mSecurityDetails, mRestrictions;
    private LatLng mParkingLocation;
    private LatLng mZoomPoint;
    private DatabaseReference mDatabaseReference;
    private String mParkingID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_add_parking1);
        InitializeViews();
        super.SetToolbarTitle("Add parking");
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);
    }


    @Override
    public void onMapReady(final GoogleMap map) {
        map.moveCamera( CameraUpdateFactory.newLatLngZoom(mZoomPoint , 4.0f) );
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                map.clear();
                MarkerOptions marker = new MarkerOptions().position(
                        new LatLng(point.latitude, point.longitude)).title("Parking location");
                mParkingLocation= point;
                map.addMarker(marker);
            }
        });
    }

    private void GetValues()
    {
        String details = mSecurityDetails.getText().toString();
        String restrictions = mRestrictions.getText().toString();
        mParkingID = UUID.randomUUID().toString();

        ParkingCore.Instance().setmSecurityDetails(details);
        ParkingCore.Instance().setmRestrictions(restrictions);
        ParkingCore.Instance().setmLatitude(mParkingLocation.latitude);
        ParkingCore.Instance().setmLongitude(mParkingLocation.longitude);
        ParkingCore.Instance().setmOwnerID(UserCore.Instance().getUserId());
        ParkingCore.Instance().setmParkingID(mParkingID);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Parkings");
        mDatabaseReference.child(mParkingID).setValue(ParkingCore.Instance());
    }

    private void InitializeViews()
    {
        mMapView = findViewById(R.id.map_add_parking);
        mZoomPoint = new LatLng(47.61328,18.69477);
        mSecurityDetails = findViewById(R.id.et_parking_security);
        mRestrictions = findViewById(R.id.et_parking_restrictions);
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

    public void RegisterParking(View view) {
        GetValues();
        startActivity(new Intent(AddParkingActivity1.this, ActivityHome.class));
        Toast.makeText(this, "Parking added!", Toast.LENGTH_SHORT).show();
    }
}
