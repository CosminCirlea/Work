package com.example.worldapp.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.worldapp.BaseClasses.BaseAppCompat;
import com.example.worldapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ToursFilterActivity extends BaseAppCompat {
    private TextInputEditText mCountry, mRegion, mCity, mPrice;
    private Spinner mTypeSpinner;
    private FloatingActionButton mSearchButton;
    private ArrayList<String> mFieldValues;
    private String[] mFilterValues;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_tours_filter);
        InitializeViews();
        SetSpinner();
        SetFilters();

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetValues();
                Intent myIntent = new Intent(ToursFilterActivity.this, ListAllToursActivity.class);
                myIntent.putStringArrayListExtra("filters", mFieldValues);
                startActivity(myIntent);
            }
        });
    }

    private void SetFilters()
    {
        Intent myIntent = getIntent();
        mFieldValues = myIntent.getStringArrayListExtra("alreadyFiltered");
        if (mFieldValues != null) {
            mFilterValues = new String[mFieldValues.size()];
            mFilterValues = mFieldValues.toArray(mFilterValues);
            SetFiltersValues();
        }
    }

    private void SetFiltersValues()
    {
        mFilterValues = mFieldValues.toArray(mFilterValues);
        SetEditTextValue(mCountry, mFilterValues[0]);
        SetEditTextValue(mRegion, mFilterValues[1]);
        SetEditTextValue(mCity, mFilterValues[2]);
        SetEditTextValue(mPrice, mFilterValues[3]);
    }

    private void GetValues()
    {
        mFieldValues = new ArrayList<>();
        String country = GetEditTextValue(mCountry);
        mFieldValues.add(country);
        String region = GetEditTextValue(mRegion);
        mFieldValues.add(region);
        String city = GetEditTextValue(mCity);
        mFieldValues.add(city);
        String price = GetEditTextValue(mPrice);
        mFieldValues.add(price);
        String type = mTypeSpinner.getSelectedItem().toString();
        mFieldValues.add(type);
    }

    private void SetEditTextValue(TextInputEditText destination, String value)
    {
        destination.setText(value);
    }

    private String GetEditTextValue(TextInputEditText value)
    {
        if (value.getText().toString() != null) {
            return value.getText().toString();
        }
        return "";
    }

    private void SetSpinner()
    {
        List<String> types = Arrays.asList("All","Walking", "Bike", "Car", "Boat","Other");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.template_spinner_layout, types);
        mTypeSpinner.setAdapter(adapter);
    }

    private void InitializeViews()
    {
        mCountry = findViewById(R.id.et_filter_country);
        mRegion = findViewById(R.id.et_filter_region);
        mCity = findViewById(R.id.et_filter_city);
        mPrice = findViewById(R.id.et_filter_price);
        mSearchButton = findViewById(R.id.btn_filter_save);
        mTypeSpinner = findViewById(R.id.spinner_filter_tour_type);
    }

    public void SelectDates(View view) {

    }
}
