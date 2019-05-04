package com.example.worldapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.worldapp.BaseClasses.BaseAppCompat;
import com.example.worldapp.R;
import com.example.worldapp.Core.TourCore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTour3Activity extends BaseAppCompat {

    private EditText mCountryEditText, mRegionEditText, mCityEditText, mTypeEditText;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
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
        mUser = mAuth.getCurrentUser();
        mUserId = mUser.getUid();
    }

    public void AddNewTourPart3(String country, String region, String city, String type)
    {
        TourCore.Instance().setmTourCountry(country);
        TourCore.Instance().setmTourRegion(region);
        TourCore.Instance().setmTourCity(city);
        TourCore.Instance().setmTourType(type);
    }

    public void RegisterTour(View view) {
        AddNewTourPart3(mCountryEditText.getText().toString(), mRegionEditText.getText().toString(),
                mCityEditText.getText().toString(),mTypeEditText.getText().toString());
        Intent mIntent = new Intent(this, AddTour4Activity.class);
        startActivity(mIntent);
    }

    public void InitializeViews()
    {
        mCountryEditText=findViewById(R.id.et_tour_country);
        mRegionEditText = findViewById(R.id.et_tour_region);
        mCityEditText =findViewById(R.id.et_tour_city);
        mTypeEditText=findViewById(R.id.et_tour_type);
    }
}
