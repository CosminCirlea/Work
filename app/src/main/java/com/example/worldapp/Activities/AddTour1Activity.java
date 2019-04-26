package com.example.worldapp.Activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.worldapp.BaseClasses.BaseAppCompat;
import com.example.worldapp.R;
import com.example.worldapp.Core.TourCore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class AddTour1Activity extends BaseAppCompat {

    private TextInputEditText mTourTitleEt, mTourLandmarksEt, mTourParticipantsEt, mTourPriceEt, mTourDurationEt;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabaseReference;
    private String userID, tourId;
    private String mTitle, mLandmarks, mDuration;
    private int mParticipants;
    private double mPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_add_tour1);
        InitializeViews();
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
        mUser = mAuth.getCurrentUser();
        userID = mUser.getUid();
        tourId = UUID.randomUUID().toString();
    }

    public void AddNewTourPart1(String title, String landmarks, int participants, double price, String duration)
    {
        TourCore.Instance().setmTourTitle(title);
        TourCore.Instance().setmTourLandmarks(landmarks);
        TourCore.Instance().setmTourMaxParticipants(participants);
        TourCore.Instance().setmTourPrice(price);
        TourCore.Instance().setmTourDuration(duration);
        TourCore.Instance().setmUserId(mUser.getUid());
        TourCore.Instance().setmTourId(tourId);
        TourCore.Instance().setmTourImageUrl("");
    }

    public void GoToAddTour2(View view) {
        Intent myIntent = new Intent(this, AddTour2Activity.class);
        myIntent.putExtra("tourId", tourId);
        if (GetValues()) {
            AddNewTourPart1(mTitle, mLandmarks, mParticipants, mPrice, mDuration);
            startActivity(myIntent);
        }
    }

    public void InitializeViews()
    {
        mTourTitleEt = findViewById(R.id.et_tour_title);
        mTourLandmarksEt =findViewById(R.id.et_tour_landmarks);
        mTourDurationEt =findViewById(R.id.et_tour_duration);
        mTourParticipantsEt = findViewById(R.id.et_tour_participants);
        mTourPriceEt = findViewById(R.id.et_tour_price);
    }

    private boolean GetValues()
    {
        mTitle = mTourTitleEt.getText().toString();
        mLandmarks = mTourLandmarksEt.getText().toString();
        mDuration= mTourDurationEt.getText().toString();
        mParticipants = Integer.parseInt(mTourParticipantsEt.getText().toString());
        mPrice = Double.parseDouble(mTourPriceEt.getText().toString());

        if (mTitle ==null || mLandmarks==null || mDuration==null || mParticipants==0 || mPrice==0)
        {
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(AddTour1Activity.this, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show();
                }
            });
            return false;
        }
        return true;
    }
}
