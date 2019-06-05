package com.example.worldapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.worldapp.BaseClasses.BaseAppCompat;
import com.example.worldapp.Constants.NavigationConstants;
import com.example.worldapp.Core.TourCore;
import com.example.worldapp.Core.UserCore;
import com.example.worldapp.Helpers.FirebaseHelper;
import com.example.worldapp.Helpers.GlideMediaHelper;
import com.example.worldapp.Models.HomeDetailsModel;
import com.example.worldapp.Models.TourBookingManager;
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
import com.veinhorn.scrollgalleryview.MediaInfo;
import com.veinhorn.scrollgalleryview.ScrollGalleryView;
import com.veinhorn.scrollgalleryview.builder.GallerySettings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AccommodationActivity extends BaseAppCompat implements OnMapReadyCallback {
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private TextView mTitleTv, mLocationTv, mVenueTypeTv, mPriceTv , mGuestTv , mRoomsTv, mBedsTv , mBathsTv ,
            mAmenitiesTv , mExactLocationTv , mOwnerNameTv;
    private HomeDetailsModel mHome;
    private String mOwnerId;
    private MapView mMapView;
    private DatabaseReference mDatabaseReference;
    private LatLng mMeetingPoint;
    private ImageView mOwnerImage;
    private DatabaseReference mBookingDatabase, mUsersDatabase;
    ArrayList<String> mExistingBookingManagers = new ArrayList<>();
    ArrayList<String> mExistingBookedDatesTours = new ArrayList<>();
    private UserDetailsModel mTourOwner, mAuxUser;
    private ScrollGalleryView galleryView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accommodation);
        InitializeViews();
        super.SetToolbarTitle("Accommodation");
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

        mBookingDatabase = FirebaseHelper.mBookingManagerDatabase;
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        mUsersDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    mAuxUser = child.getValue(UserDetailsModel.class);
                    String userId = mAuxUser.getUserId();
                    if (userId.equalsIgnoreCase(mOwnerId))
                    {
                        mTourOwner = mAuxUser;
                        String fullName= mTourOwner.getFirstname()+" "+mTourOwner.getName();
                        mOwnerNameTv.setText(fullName);
                        Glide.with(getApplicationContext()).load(mTourOwner.getImageUri()).into(mOwnerImage);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        SetValues();
        setGallery();
    }

    private void setGallery()
    {
        GlideMediaHelper mediaHelper = new GlideMediaHelper();
        List<MediaInfo> list = mediaHelper.images(mHome.getmImagesUrls());

        galleryView = ScrollGalleryView
                .from((ScrollGalleryView) findViewById(R.id.scroll_gallery_view))
                .settings(
                        GallerySettings
                                .from(getSupportFragmentManager())
                                .thumbnailSize(100)
                                .enableZoom(true)
                                .build()
                )
                .onPageChangeListener(new CustomOnPageListener())
                .add(list)
                .build();
    }


    private void InitializeViews() {
        mMapView = findViewById(R.id.map_accommodation);
        mTitleTv = findViewById(R.id.tv_announcement_title);
        mLocationTv = findViewById(R.id.tv_location);
        mVenueTypeTv = findViewById(R.id.tv_venue_type);
        mPriceTv = findViewById(R.id.tv_price_per_night);
        mGuestTv = findViewById(R.id.tv_accommodation_guests);
        mRoomsTv = findViewById(R.id.tv_accommodation_rooms);
        mBedsTv = findViewById(R.id.tv_accommodation_beds);
        mBathsTv = findViewById(R.id.tv_tour_details_baths);
        mAmenitiesTv = findViewById(R.id.tv_accommodation_amenities);
        mExactLocationTv = findViewById(R.id.tv_accommodation_address);
        mOwnerImage = findViewById(R.id.iv_accommodation_owner_image);
        mOwnerNameTv = findViewById(R.id.tv_accommodation_owner_name);
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

    private void SetValues() {
        LatLng meetingPoint = new LatLng(mHome.getmLocationLatitude(),mHome.getmLocationLongitude());
        String mLocation = mHome.getCity()+", "+ mHome.getRegion()+", "+ mHome.getCountry();
        String mVenue = mHome.getListingType() +" - "+mHome.getOwnerType();

        mTitleTv.setText(mHome.getAnnouncementTitle());
        mLocationTv.setText(mLocation);
        mVenueTypeTv.setText(mVenue);
        mPriceTv.setText(Double.toString(mHome.getPricePerNight())+" EUR /night");
        mGuestTv.setText(mHome.getGuests());
        mBedsTv.setText(Double.toString(mHome.getBedsToUse()));
        mBathsTv.setText(Integer.toString(mHome.getBathroomsToUse()));
        mRoomsTv.setText(Integer.toString(mHome.getRoomsToUse()));
        mMeetingPoint = meetingPoint;
        mAmenitiesTv.setText(mHome.getAmenities());
        mExactLocationTv.setText(mHome.getAddressLine());
    }

    @Override
    public void onMapReady(GoogleMap map) {
        if (mMeetingPoint !=null)
        {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(mMeetingPoint, 13));
            map.addMarker(new MarkerOptions().position(mMeetingPoint).title("Accommodation"));
        }
        else
        {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-33.88,151.21), 15));
        }
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

    private class CustomOnPageListener extends ViewPager.SimpleOnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
        }
    }
}