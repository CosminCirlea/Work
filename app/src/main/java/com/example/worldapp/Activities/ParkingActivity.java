package com.example.worldapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.worldapp.BaseClasses.BaseAppCompat;
import com.example.worldapp.Constants.ConstantValues;
import com.example.worldapp.Constants.NavigationConstants;
import com.example.worldapp.Core.AccommodationCore;
import com.example.worldapp.Core.UserCore;
import com.example.worldapp.Models.BookingManager;
import com.example.worldapp.Models.HomeDetailsModel;
import com.example.worldapp.Models.ParkingModel;
import com.example.worldapp.Models.UserDetailsModel;
import com.example.worldapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ParkingActivity extends BaseAppCompat  implements OnMapReadyCallback {
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private String mOwnerId;
    private MapView mMapView;
    private ParkingModel mParking;
    private DatabaseReference mDatabaseReference;
    private LatLng mMeetingPoint;
    private DatabaseReference mBookingDatabase;
    ArrayList<String> mExistingBookingManagers = new ArrayList<>();
    ArrayList<String> mExistingBookedDatesTours = new ArrayList<>();
    private UserDetailsModel mHomeOwner;
    private TextView mTitleTv, mLocationTv, mVenueTypeTv, mPriceTv , mDescriptionTv , mParkingTypeTv , mAvailabilityTv , mSecurityDetailsTv ,
            mRestrictionsTv;
    BookingManager mManager = new BookingManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_parking);
        super.SetToolbarTitle("Parking");
        InitializeViews();

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);

        Gson gson = new Gson();
        String aux = getIntent().getStringExtra(NavigationConstants.PARKING_MODEL_KEY);
        mParking = gson.fromJson(aux, ParkingModel.class);
        mOwnerId = mParking.getmOwnerID();

        SetValues();
    }

    public void OnBook(View view) {
        if (UserCore.Instance().isLoggedIn())
        {
            doTheBooking();
            Intent myIntent = new Intent(this, PaymentActivity.class);
            String[] mPurchaseValues =
                    {
                            Double.toString(mManager.getmPrice()),
                            Double.toString(mManager.getmFee()),
                            Double.toString(mManager.getmTotalPrice())

                    };
            myIntent.putExtra("paymentDetails", mPurchaseValues);
            startActivity(myIntent);
        }
        else
        {
            Toast.makeText(this, "You must be logged in in order to book!", Toast.LENGTH_SHORT).show();
        }
    }

    private void InitializeViews() {
        mMapView = findViewById(R.id.map_show_parking);
        mTitleTv = findViewById(R.id.tv_parking_announcement_title);
        mLocationTv = findViewById(R.id.tv_parking_address);
        mDescriptionTv = findViewById(R.id.tv_parking_description);
        mParkingTypeTv = findViewById(R.id.tv_parking_type);
        mPriceTv = findViewById(R.id.tv_parking_price);
        mAvailabilityTv = findViewById(R.id.tv_parking_availability);
        mSecurityDetailsTv = findViewById(R.id.tv_parking_security_details);
        mRestrictionsTv = findViewById(R.id.tv_parking_restrictions_details);
    }

    public void doTheBooking()
    {
        double mPricePerDay = mParking.getmPricePerDay();
        int days = AccommodationCore.Instance().getmNumberOfNights();
        double mPrice = days*mPricePerDay;
        double mFee = days * mPrice * ConstantValues.BOOKING_APP_FEE;
        double mTotalPrice = days * mPrice + mFee;
        String startDate = AccommodationCore.Instance().getmStartDate();
        String endDate = AccommodationCore.Instance().getmEndDate();

        UUID newBookingManager = UUID.randomUUID();
        mManager.setmBookingId(newBookingManager.toString());
        mManager.setmOwnerId(mParking.getmOwnerID());
        mManager.setmBuyerId(UserCore.Instance().User.getUserId());
        mManager.setmAnnouncementId((mParking.getmParkingID()));
        mManager.setmPrice(mPrice);
        mManager.setmFee(mFee);
        mManager.setmTotalPrice(mTotalPrice);
        mManager.setmStartDate(startDate);
        mManager.setmEndDate(endDate);
        mManager.setmStatus(ConstantValues.BOOKING_PENDING);
        mManager.setmAnnouncementTitle(mParking.getmTitle());
        mManager.setmBuyerName(UserCore.Instance().User.getName());
        mManager.setmOwnerName(mHomeOwner.getName());
        mManager.setmOwnerPhone(mHomeOwner.getPhoneNumber());
        mManager.setmBuyerPhone(UserCore.Instance().User.getPhoneNumber());
        mManager.setmManagerType(ConstantValues.BOOKING_TYPE_PARKING);
        mBookingDatabase.child(newBookingManager.toString()).setValue(mManager);
        updateBookingManager(mManager, mHomeOwner);
        updateBookingManager(mManager, UserCore.Instance().getmUser());
    }

    public void updateBookingManager(BookingManager mManager, UserDetailsModel mUser)
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
        LatLng meetingPoint = new LatLng(mParking.getmLatitude(),mParking.getmLongitude());
        String mLocation = mParking.getmAddress();
        String availability = String.valueOf(mParking.getmSpotsNumber());

        mTitleTv.setText(mParking.getmTitle());
        mDescriptionTv.setText(mParking.getmDescription());
        mParkingTypeTv.setText(mParking.getmType());
        mPriceTv.setText(mParking.getmPricePerDay()+" EUR /day");
        mAvailabilityTv.setText(availability);
        mSecurityDetailsTv.setText(mParking.getmSecurityDetails());
        mRestrictionsTv.setText(mParking.getmRestrictions());
        mMeetingPoint = meetingPoint;
        mLocationTv.setText(mLocation);
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

    @Override
    public void onMapReady(GoogleMap map) {
        if (mMeetingPoint !=null)
        {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(mMeetingPoint, 13));
            map.addMarker(new MarkerOptions().position(mMeetingPoint).title("Parking spot"));
        }
        else
        {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-33.88,151.21), 15));
        }
    }
}
