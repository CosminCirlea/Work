package com.example.worldapp.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.worldapp.BaseClasses.BaseAppCompat;
import com.example.worldapp.Constants.ConstantValues;
import com.example.worldapp.Constants.NavigationConstants;
import com.example.worldapp.Core.TourCore;
import com.example.worldapp.Core.UserCore;
import com.example.worldapp.Models.BookingManager;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class TourActivity extends BaseAppCompat implements OnMapReadyCallback {
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private ImageView mTourImage, mOwnerImage;
    private TextView mTitle, mLocation, mType, mDescription, mParticipants, mDuration, mPrice, mLandmarks, mOwnerName,
            tvMeetingLocation, mScheduleTv;
    private RatingBar mRating;
    private MapView mMapView;
    private GuidedToursModel mTour;
    private UserDetailsModel mTourOwner, mAuxUser;
    private String mOwnerId;
    private static String mChosenDate;
    DatabaseReference mDatabaseReference, mUsersDatabase, mBookingDatabase, mToursDatabase;
    private LatLng mMeetingPoint;
    ArrayList<String> mExistingBookingManagers = new ArrayList<>();
    ArrayList<String> mExistingBookedDatesTours = new ArrayList<>();
    BookingManager mManager = new BookingManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_tour);
        InitializeViews();
        super.SetToolbarTitle("Tour");
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
        mChosenDate = TourCore.Instance().getmBookedDates();

        mBookingDatabase = FirebaseDatabase.getInstance().getReference().child("BookingManager");
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
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }
        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);

        mMapView.onSaveInstanceState(mapViewBundle);
    }

    private void SetValues() {
        LatLng meetingPoint = new LatLng(mTour.getmMeetingPointLatitude(),mTour.getmMeetingPointLongitude());
        //double ratingValue = mTour.getmToursGrades() /mTour.getmToursRating();
        //String rating = String.valueOf(ratingValue);

        Glide.with(getApplicationContext()).load(mTour.getmTourImageUrl()).into(mTourImage);
        mTitle.setText(mTour.getmTourTitle());
        mDescription.setText(mTour.getmTourDescription());
        String location = mTour.getmTourCountry() + "," + mTour.getmTourRegion() + "," + mTour.getmTourCity();
        mLocation.setText(location);
        mType.setText(mTour.getmTourType());
        mDescription.setText(mTour.getmTourDescription());
        mDuration.setText(mTour.getmTourDuration());
        mPrice.setText(mTour.getmTourPrice() + " EUR");
        mLandmarks.setText(mTour.getmTourLandmarks());
        mParticipants.setText(mTour.getmTourMaxParticipants() + "");
        mRating.setRating(5f);
        mOwnerId = mTour.getmUserId();
        mMeetingPoint = meetingPoint;
        tvMeetingLocation.setText(mTour.getmMeetingLocation());
        mScheduleTv.setText(mTour.getmSchedule());
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
        mScheduleTv = findViewById(R.id.tv_tour_schedule);
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
            String aux;
            aux =TourCore.Instance().getmBookedDates();
            if (aux.isEmpty())
            {
                Toast.makeText(TourActivity.this,"Please select a date!", Toast.LENGTH_SHORT).show();
                ToursFilterActivity.DatePickerFragment newFragment = new ToursFilterActivity.DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
            else{
                doTheBooking();
                Intent myIntent = new Intent(TourActivity.this, PaymentActivity.class);
                String[] mPurchaseValues =
                        {
                                Double.toString(mManager.getmPrice()),
                                Double.toString(mManager.getmFee()),
                                Double.toString(mManager.getmTotalPrice())

                        };
                myIntent.putExtra("paymentDetails", mPurchaseValues);
                startActivity(myIntent);
            }
        }
        else
        {
            Intent myIntent = new Intent(TourActivity.this, ActivityLogin.class);
            myIntent.putExtra("tourID", mTour.getmTourId());
            startActivity(myIntent);
            Toast.makeText(this, "You must be logged in in order to book!", Toast.LENGTH_SHORT).show();
        }
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

    public void updateTourBookedDates(GuidedToursModel tour, String bookedDate)
    {
        if (tour.getmBookedDates()!=null)
        {
            mExistingBookedDatesTours = tour.getmBookedDates();
        }
        mExistingBookedDatesTours.add(bookedDate);
        HashMap<String, Object> map = new HashMap<>();
        map.put("mBookedDates", mExistingBookedDatesTours);
        mToursDatabase = FirebaseDatabase.getInstance().getReference("Tours").child(tour.getmTourId());
        mToursDatabase.updateChildren(map);
    }

    public void doTheBooking()
    {
        double mPrice = mTour.getmTourPrice();
        double mFee = mPrice * ConstantValues.BOOKING_APP_FEE;
        double mTotalPrice = mPrice + mFee;
        String date = TourCore.Instance().getmBookedDates();
        String schedule = mTour.getmSchedule();

        UUID newBookingManager = UUID.randomUUID();
        mManager.setmBookingId(newBookingManager.toString());
        mManager.setmOwnerId(mTour.getmUserId());
        mManager.setmBuyerId(UserCore.Instance().User.getUserId());
        mManager.setmPrice(mPrice);
        mManager.setmFee(mFee);
        mManager.setmTotalPrice(mTotalPrice);
        mManager.setmBookingDates(date);
        mManager.setmStatus(ConstantValues.BOOKING_PENDING);
        mManager.setmAnnouncementTitle(mTour.getmTourTitle());
        mManager.setmBuyerName(UserCore.Instance().User.getName());
        mManager.setmOwnerName(mTourOwner.getName());
        mManager.setmAnnouncementId((mTour.getmTourId()));
        mManager.setmOwnerPhone(mTourOwner.getPhoneNumber());
        mManager.setmBuyerPhone(UserCore.Instance().User.getPhoneNumber());
        mManager.setmSchedule(schedule);
        mManager.setmManagerType(ConstantValues.BOOKING_TYPE_TOUR);
        mBookingDatabase.child(newBookingManager.toString()).setValue(mManager);
        updateBookingManager(mManager, mTourOwner);
        updateBookingManager(mManager, UserCore.Instance().getmUser());
    }

    public void SelectDates(View view) {
        TourActivity.DatePickerFragment newFragment = new TourActivity.DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void GoToOwner(View view) {

    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
            dialog.getDatePicker().setMinDate(c.getTimeInMillis());
            return dialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month - 1, day, 0, 0);
            Date date = calendar.getTime();
            SimpleDateFormat formatTime = new SimpleDateFormat(ConstantValues.DATE_FORMAT);
            mChosenDate = formatTime.format(date);
            TourCore.Instance().setmBookedDates(mChosenDate);
            mChosenDate = TourCore.Instance().getmBookedDates();

        }
    }
}
