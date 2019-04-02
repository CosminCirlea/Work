package com.example.worldapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.worldapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddTour3Activity extends AppCompatActivity {

    private EditText mCountryEditText, mRegionEditText, mCityEditText, mTypeEditText;
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
        mDatabaseReference = mFirebaseDatabase.getReference("Tours").child(mUser.getUid()).child(mTourId);
    }

    public void AddNewTourPart3(String country, String region, String city, String type)
    {
        HashMap<String, Object> map = new HashMap<>();
        map.put("TourCountry", country);
        map.put("TourRegion", region);
        map.put("TourCity", city);
        map.put("TourType", type);
        mDatabaseReference.updateChildren(map);
    }

    public void RegisterTour(View view) {
        AddNewTourPart3(mCountryEditText.getText().toString(), mRegionEditText.getText().toString(),
                mCityEditText.getText().toString(),mTypeEditText.getText().toString());
        Intent mIntent = new Intent(this, ActivityHome.class);
        startActivity(mIntent);
        Toast.makeText(this, "Tour added succesfully!", Toast.LENGTH_SHORT).show();
    }

    public void InitializeViews()
    {
        mCountryEditText=findViewById(R.id.et_tour_country);
        mRegionEditText = findViewById(R.id.et_tour_region);
        mCityEditText =findViewById(R.id.et_tour_city);
        mTypeEditText=findViewById(R.id.et_tour_type);
    }
}