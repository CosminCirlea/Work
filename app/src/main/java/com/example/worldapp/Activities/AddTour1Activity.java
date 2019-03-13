package com.example.worldapp.Activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.worldapp.Models.GuidedToursModel;
import com.example.worldapp.Models.UserDetailsModel;
import com.example.worldapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

import static java.util.UUID.randomUUID;

public class AddTour1Activity extends AppCompatActivity {

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
        GuidedToursModel tour = new GuidedToursModel();
        tour.setmTourTitle(title+"ceva");
        tour.setmTourLandmarks(landmarks+"altceva");
        tour.setmTourMaxParticipants(2);
        tour.setmTourPrice(234.2);
        tour.setmTourDuration("3");
        tour.setmUserId(mUser.getUid());
        tour.setmTourId(tourId);
        tour.setmTourImageUrl("");

        mDatabaseReference.child("Tours").child(userID).child(tourId.toString()).setValue(tour);
    }

    public void GoToAddTour2(View view) {
        Intent myIntent = new Intent(this, AddTour2Activity.class);
        myIntent.putExtra("tourId", tourId);
        //GetValues();
        AddNewTourPart1(mTitle, mLandmarks, mParticipants, mPrice, mDuration);
        startActivity(myIntent);
    }

    public void InitializeViews()
    {
        mTourTitleEt = findViewById(R.id.et_tour_title);
        mTourLandmarksEt =findViewById(R.id.et_tour_landmarks);
        mTourDurationEt =findViewById(R.id.et_tour_duration);
        mTourParticipantsEt = findViewById(R.id.et_tour_participants);
        mTourPriceEt = findViewById(R.id.et_tour_price);
    }

    private void GetValues()
    {
        mTitle = mTourTitleEt.getText().toString();
        mLandmarks = mTourLandmarksEt.getText().toString();
        mDuration= mTourDurationEt.getText().toString();
        mParticipants = Integer.parseInt(mTourParticipantsEt.getText().toString());
        mPrice = Double.parseDouble(mTourPriceEt.getText().toString());
    }
}
