package com.example.worldapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.worldapp.BaseClasses.BaseAppCompat;
import com.example.worldapp.Core.TourCore;
import com.example.worldapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTour4Activity extends BaseAppCompat implements OnMapReadyCallback {
    private static final String MAPVIEW_BUNDLE_KEY1 = "MapViewBundleKey";
    private EditText mMeetingLocation;
    private MapView mMapView;
    private LatLng mMeetingPoint;
    private DatabaseReference mDatabaseReference;
    private String mTourID;
    private LatLng mZoomPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_add_tour4);
        InitializeViews();

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY1);
        }

        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);

        mTourID = TourCore.Instance().getmTourId();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Tours");
    }

    public void RegisterTour(View view) {
        String location = mMeetingLocation.getText().toString();
        TourCore.Instance().setmMeetingPointLatitude(mMeetingPoint.latitude);
        TourCore.Instance().setmMeetingPointLongitude(mMeetingPoint.longitude);
        TourCore.Instance().setmMeetingLocation(location);
        mDatabaseReference.child(mTourID).setValue(TourCore.Instance());
        Intent mIntent = new Intent(this, ActivityHome.class);
        startActivity(mIntent);
        Toast.makeText(this, "Tour added succesfully!", Toast.LENGTH_SHORT).show();
    }

    private void InitializeViews()
    {
        mMeetingLocation = findViewById(R.id.et_add_tour_location);
        mMapView = findViewById(R.id.map_add_tour);
        mZoomPoint = new LatLng(47.61328,18.69477);
    }

    @Override
    public void onMapReady(final GoogleMap map) {
        map.moveCamera( CameraUpdateFactory.newLatLngZoom(mZoomPoint , 4.0f) );
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {

                map.clear();
                MarkerOptions marker = new MarkerOptions().position(
                        new LatLng(point.latitude, point.longitude)).title("Meeting point");
                mMeetingPoint= point;
                map.addMarker(marker);
            }
        });
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
        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY1);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY1, mapViewBundle);
        }
        mMapView.onSaveInstanceState(mapViewBundle);
    }
}
