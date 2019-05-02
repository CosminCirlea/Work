package com.example.worldapp.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.worldapp.BaseClasses.BaseAppCompat;
import com.example.worldapp.Constants.NavigationConstants;
import com.example.worldapp.Core.UserCore;
import com.example.worldapp.Models.GuidedToursModel;
import com.example.worldapp.Models.UserDetailsModel;
import com.example.worldapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class TourActivity extends BaseAppCompat implements OnMapReadyCallback {
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private ImageView mTourImage, mOwnerImage;
    private TextView mTitle, mLocation, mType, mDescription, mParticipants, mDuration, mPrice, mLandmarks, mOwnerName;
    private RatingBar mRating;
    private MapView mMapView;
    private GuidedToursModel mTour;
    private String mUserID;
    private DatabaseReference mDatabaseReference;
    private LatLng mMeetingPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_tour);
        InitializeViews();

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);

        Gson gson = new Gson();
        String aux = getIntent().getStringExtra(NavigationConstants.TOUR_MODEL_KEY);
        mTour = gson.fromJson(aux, GuidedToursModel.class);
        SetValues();
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

    private void SetValues() {
        Glide.with(mTourImage.getContext()).load(mTour.getmTourImageUrl()).into(mTourImage);
        mTitle.setText(mTour.getmTourTitle());
        mDescription.setText(mTour.getmTourDescription());
        String location = mTour.getmTourCountry() + "," + mTour.getmTourRegion() + "," + mTour.getmTourCity();
        mLocation.setText(location);
        mType.setText(mTour.getmTourType());
        mDescription.setText(mTour.getmTourDescription());
        mDuration.setText(mTour.getmTourDuration());
        mPrice.setText(mTour.getmTourPrice() + " $");
        mLandmarks.setText(mTour.getmTourLandmarks());
        mParticipants.setText(mTour.getmTourMaxParticipants() + "");
        mRating.setRating(3.4f);
        mUserID = mTour.getmUserId();
        mMeetingPoint = mTour.getmMeetingPoint();
        GetUserDetails();
    }

    private void GetUserDetails() {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("users");
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    UserDetailsModel myUser = dataSnapshot1.getValue(UserDetailsModel.class);
                    if (myUser.getUserId().contains(mUserID)) {
                        String fullname = myUser.getFirstname() + " " + myUser.getName();
                        Glide.with(mOwnerImage.getContext()).load(myUser.getImageUri()).apply(new RequestOptions().placeholder(R.drawable.photo_placeholder).centerCrop()).into(mOwnerImage);
                        mOwnerName.setText(fullname);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    private void InitializeViews() {
        mTourImage = findViewById(R.id.iv_tour_photo);
        mTitle = findViewById(R.id.tv_tour_title);
        mLocation = findViewById(R.id.tv_tour_location);
        mType = findViewById(R.id.tv_tour_type_details);
        mDescription = findViewById(R.id.tv_tour_description);
        mParticipants = findViewById(R.id.tv_tour_details_participants);
        mDuration = findViewById(R.id.tv_tour_details_duration);
        mPrice = findViewById(R.id.tv_tour_details_price);
        mLandmarks = findViewById(R.id.tv_tour_landmarks_details);
        mRating = findViewById(R.id.rb_tour_rating_details);
        mMapView = findViewById(R.id.map_tour);
        mOwnerImage = findViewById(R.id.iv_tour_owner_image);
        mOwnerName = findViewById(R.id.tv_tour_owner_name);
    }

    @Override
    public void onMapReady(final GoogleMap map) {
        map.addMarker(new MarkerOptions().position(new LatLng(mMeetingPoint.latitude, mMeetingPoint.longitude)).title("Meeting point"));
        //getMyLocation();
    }

    private Location getMyLocation() {
        // Get location from GPS if it's available
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        // Location wasn't found, check the next most accurate place for the current location
        if (myLocation == null) {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            // Finds a provider that matches the criteria
            String provider = lm.getBestProvider(criteria, true);
            // Use the provider to get the last known location
            myLocation = lm.getLastKnownLocation(provider);
        }

        return myLocation;
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

    public void OnBook(View view) {
        if (UserCore.Instance().isLoggedIn())
        {
            Toast.makeText(this, "You may buy!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent myIntent = new Intent(TourActivity.this, ActivityLogin.class);
            myIntent.putExtra("tourID", mTour.getmTourId());
            startActivity(myIntent);
            Toast.makeText(this, "You must be logged in in order to book!", Toast.LENGTH_SHORT).show();
        }
    }

    public void GoToOwner(View view) {

    }
}
