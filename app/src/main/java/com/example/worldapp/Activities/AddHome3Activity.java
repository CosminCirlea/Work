package com.example.worldapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.worldapp.BaseClasses.BaseAppCompat;
import com.example.worldapp.Core.AccommodationCore;
import com.example.worldapp.R;

public class AddHome3Activity extends BaseAppCompat {
    private EditText mAmenities, mPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_add_home3);
        super.SetToolbarTitle("Add details");
        InitializeViews();
    }

    private void GetValues()
    {
        String price = mPrice.getText().toString();
        String amenities = mAmenities.getText().toString();

        AccommodationCore.Instance().setPricePerNight(Double.parseDouble(price));
        AccommodationCore.Instance().setAmenities(amenities);
    }

    private void InitializeViews()
    {
        mAmenities = findViewById(R.id.et_amenities);
        mPrice = findViewById(R.id.et_price_per_night);
    }

    public void GoToAddHome4(View view) {
        GetValues();
        startActivity(new Intent(this, AddHome1Activity.class));
    }
}
