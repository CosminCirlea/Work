package com.example.worldapp.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.worldapp.BaseClasses.BaseAppCompat;
import com.example.worldapp.Core.AccommodationCore;
import com.example.worldapp.Core.TourCore;
import com.example.worldapp.R;

import java.util.ArrayList;

public class AccommodationFilterActivity extends BaseAppCompat {
    private TextInputEditText mCountry, mRegion, mCity, mPrice;
    private static TextView mStartDate, mEndDate;
    private FloatingActionButton mSearchButton;
    private ArrayList<String> mFieldValues;
    private String[] mFilterValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accommodation_filter);
        super.SetToolbarTitle("Acommodation filter");
        InitializeViews();
        SetFilters();

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetValues();
                Intent myIntent = new Intent(AccommodationFilterActivity.this, ListAllAccommodationActivity.class);
                myIntent.putStringArrayListExtra("filters", mFieldValues);
                startActivity(myIntent);
            }
        });
    }
    private void SetFilters() {
        Intent myIntent = getIntent();
        mFieldValues = myIntent.getStringArrayListExtra("alreadyFiltered");
        if (mFieldValues != null) {
            mFilterValues = new String[mFieldValues.size()];
            mFilterValues = mFieldValues.toArray(mFilterValues);
            SetFiltersValues();
        }
    }

    private void SetFiltersValues() {
        mFilterValues = mFieldValues.toArray(mFilterValues);
        SetEditTextValue(mCountry, mFilterValues[0]);
        SetEditTextValue(mRegion, mFilterValues[1]);
        SetEditTextValue(mCity, mFilterValues[2]);
        SetEditTextValue(mPrice, mFilterValues[3]);
        mStartDate.setText(AccommodationCore.Instance().getmStartDate());
        mEndDate.setText(AccommodationCore.Instance().getmEndDate());
    }

    private void GetValues() {
        mFieldValues = new ArrayList<>();
        String country = GetEditTextValue(mCountry);
        mFieldValues.add(country);
        String region = GetEditTextValue(mRegion);
        mFieldValues.add(region);
        String city = GetEditTextValue(mCity);
        mFieldValues.add(city);
        String price = GetEditTextValue(mPrice);
        mFieldValues.add(price);
    }

    private void SetEditTextValue(TextInputEditText destination, String value) {
        destination.setText(value);
    }

    private String GetEditTextValue(TextInputEditText value) {
        if (value.getText().toString() != null) {
            return value.getText().toString();
        }
        return "";
    }
    private void InitializeViews() {
        mCountry = findViewById(R.id.et_filter_country);
        mRegion = findViewById(R.id.et_filter_region);
        mCity = findViewById(R.id.et_filter_city);
        mPrice = findViewById(R.id.et_filter_price);
        mSearchButton = findViewById(R.id.btn_filter_save);
        mStartDate = findViewById(R.id.tv_filter_set_start_date);
        mEndDate = findViewById(R.id.tv_filter_set_end_date);
    }


    public void SelectStartDate(View view) {
    }

    public void SelectEndDate(View view) {
    }
}
