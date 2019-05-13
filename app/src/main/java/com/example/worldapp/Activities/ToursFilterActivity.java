package com.example.worldapp.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
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
    private ArrayList<String> fieldValues;

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

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetValues();
                Intent myIntent = new Intent(ToursFilterActivity.this, ListAllToursActivity.class);
                myIntent.putStringArrayListExtra("filters", fieldValues);
                startActivity(myIntent);
            }
        });
    }

    private void GetValues()
    {
        fieldValues = new ArrayList<>();
        String country = GetEditTextValue(mCountry);
        fieldValues.add(country);
        String region = GetEditTextValue(mRegion);
        fieldValues.add(region);
        String city = GetEditTextValue(mCity);
        fieldValues.add(city);
        String price = GetEditTextValue(mPrice);
        fieldValues.add(price);
        String type = mTypeSpinner.getSelectedItem().toString();
        fieldValues.add(type);
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
        List<String> types = Arrays.asList("Walking", "Bike", "Car", "Boat","Other");
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
}
