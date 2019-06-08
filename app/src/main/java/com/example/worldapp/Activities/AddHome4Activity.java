package com.example.worldapp.Activities;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.worldapp.BaseClasses.BaseAppCompat;
import com.example.worldapp.Core.AccommodationCore;
import com.example.worldapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

public class AddHome4Activity extends BaseAppCompat implements OnMapReadyCallback {
    private static final String MAPVIEW_BUNDLE_KEY1 = "MapViewBundleKey";
    private MapView mMapView;
    private LatLng mLocation;
    private EditText mAddressET, mZipCodeET, mRegionET;
    private LatLng mZoomPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_add_home4);
        super.SetToolbarTitle("Add location");

        InitializeViews();

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY1);
        }

        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);
    }

    public void onMapReady(final GoogleMap map) {
        map.moveCamera( CameraUpdateFactory.newLatLngZoom(mZoomPoint , 4.0f) );
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {

                map.clear();
                MarkerOptions marker = new MarkerOptions().position(
                        new LatLng(point.latitude, point.longitude)).title("Accommodation location");
                mLocation= point;
                map.addMarker(marker);
                LocationInitializer();
            }
        });
    }

    private void LocationInitializer()
    {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.ENGLISH);
        try{
            addresses = geocoder.getFromLocation(mLocation.latitude, mLocation.longitude, 1);
            String address = addresses.get(0).getAddressLine(0);
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();

            AccommodationCore.Instance().setAddressLine(address);
            AccommodationCore.Instance().setCountry(country);
            AccommodationCore.Instance().setRegion(state);
            AccommodationCore.Instance().setCity(city);
            AccommodationCore.Instance().setZipCode(postalCode);
            AccommodationCore.Instance().setmLocationLongitude(mLocation.longitude);
            AccommodationCore.Instance().setmLocationLatitude(mLocation.latitude);
            mAddressET.setText(address);
            mZipCodeET.setText(postalCode);
            mRegionET.setText(state);
        }
        catch (Exception e)
        {
            e.toString();
        }
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

    private void GetValues()
    {
        AccommodationCore.Instance().setAddressLine(mAddressET.getText().toString());
        AccommodationCore.Instance().setZipCode(mZipCodeET.getText().toString());
        AccommodationCore.Instance().setRegion(mRegionET.getText().toString());
    }

    private void InitializeViews()
    {
        mAddressET = findViewById(R.id.et_address_line);
        mZipCodeET = findViewById(R.id.et_zip_code);
        mRegionET = findViewById(R.id.et_region);
        mMapView = findViewById(R.id.map_add_accommodation);
        mZoomPoint = new LatLng(47.61328,18.69477);
    }

    public void GoToAddHome3(View view) {
        GetValues();
        startActivity(new Intent(this, AddHome3Activity.class));
    }
}
