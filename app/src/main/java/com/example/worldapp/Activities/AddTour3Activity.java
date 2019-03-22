package com.example.worldapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.worldapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTour3Activity extends AppCompatActivity {

    private EditText mCountry, mRegion, mCity, mType;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabaseReference, mDatabaseRef;
    private String mTourId, mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_add_tour3);
        Intent myIntent = getIntent();
        mTourId = myIntent.getStringExtra("tourId");
        InitializeViews();

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUser = mAuth.getCurrentUser();
        mUserId = mUser.getUid();
        mDatabaseReference = mFirebaseDatabase.getReference("Tours").child(mUser.getUid());
    }

    public void RegisterTour(View view) {
    }

    public void InitializeViews()
    {
        mCountry=findViewById(R.id.et_tour_country);
        mRegion = findViewById(R.id.et_tour_region);
        mCity =findViewById(R.id.et_tour_city);
        mType=findViewById(R.id.et_tour_type);
    }
}
