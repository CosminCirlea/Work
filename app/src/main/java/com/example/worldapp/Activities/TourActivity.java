package com.example.worldapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.worldapp.BaseClasses.BaseAppCompat;
import com.example.worldapp.Constants.ConstantValues;
import com.example.worldapp.Constants.NavigationConstants;
import com.example.worldapp.Core.UserCore;
import com.example.worldapp.Helpers.Converters;
import com.example.worldapp.Helpers.FirebaseHelper;
import com.example.worldapp.Models.TourBookingManager;
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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

public class TourActivity extends BaseAppCompat implements OnMapReadyCallback {
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private ImageView mTourImage, mOwnerImage;
    private TextView mTitle, mLocation, mType, mDescription, mParticipants, mDuration, mPrice, mLandmarks, mOwnerName, tvMeetingLocation;
    private RatingBar mRating;
    private MapView mMapView;
    private GuidedToursModel mTour;
    private UserDetailsModel mTourOwner, mAuxUser;
    private String mOwnerId;
    DatabaseReference mDatabaseReference, mUsersDatabase;
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
        mOwnerId = mTour.getmUserId();

        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        mUsersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    mAuxUser = child.getValue(UserDetailsModel.class);
                    if (mAuxUser.getUserId().equals(mOwnerId))
                    {
                        mTourOwner = mAuxUser;
                        String fullName= mTourOwner.getFirstname()+" "+mTourOwner.getName();
                        mOwnerName.setText(fullName);
                        Glide.with(getApplicationContext()).load(mTourOwner.getImageUri()).into(mOwnerImage);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        SetValues();
        Date startDate = Calendar.getInstance().getTime();
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
        LatLng meetingPoint = new LatLng(mTour.getmMeetingPointLatitude(),mTour.getmMeetingPointLongitude());

        Glide.with(getApplicationContext()).load(mTour.getmTourImageUrl()).into(mTourImage);
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
        mOwnerId = mTour.getmUserId();
        mMeetingPoint = meetingPoint;
        tvMeetingLocation.setText(mTour.getmMeetingLocation());

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
        tvMeetingLocation = findViewById(R.id.tv_tour_meeting_location);
    }

    @Override
    public void onMapReady(final GoogleMap map) {
        if (mMeetingPoint !=null)
        {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(mMeetingPoint, 13));
            map.addMarker(new MarkerOptions().position(mMeetingPoint).title("Meeting point"));
        }
        else
        {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-33.88,151.21), 15));
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

    public void OnBook(View view) {
        if (UserCore.Instance().isLoggedIn())
        {
            Toast.makeText(this, "You may buy!", Toast.LENGTH_SHORT).show();
            double mPrice = mTour.getmTourPrice();
            double mFee = mPrice * ConstantValues.BOOKING_APP_FEE;
            double mTotalPrice = mPrice + mFee;
            Date endDate = new GregorianCalendar(2019, Calendar.MAY, 23).getTime();
            String date = Converters.Instance().DateToString(endDate);

            UUID newBookingManager = UUID.randomUUID();
            TourBookingManager mManager = new TourBookingManager();
            mManager.setmBookingId(newBookingManager.toString());
            mManager.setmOwnerId(mTour.getmUserId());
            mManager.setmBuyerId(UserCore.Instance().User.getUserId());
            mManager.setmPrice(mPrice);
            mManager.setmFee(mFee);
            mManager.setmTotalPrice(mTotalPrice);
            mManager.setmBookingDates(date);
            mManager.setmStatus(ConstantValues.BOOKING_PENDING);
            mDatabaseReference = FirebaseDatabase.getInstance().getReference("BookingManager");
            mDatabaseReference.child(newBookingManager.toString()).setValue(mManager);
        }
        else
        {
            Intent myIntent = new Intent(TourActivity.this, ActivityLogin.class);
            myIntent.putExtra("tourID", mTour.getmTourId());
            startActivity(myIntent);
            Toast.makeText(this, "You must be logged in in order to book!", Toast.LENGTH_SHORT).show();
        }
    }

    public void selectedDates()
    {
        Date today = new Date();
        Date tomorrow = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DATE, 1);
        today = c.getTime();
        c.add(Calendar.DATE,1);
        tomorrow = c.getTime();
        String[] aux =
                {
                        today.toString(),
                        tomorrow.toString()
                };

    }

    public void GoToOwner(View view) {

    }
}
