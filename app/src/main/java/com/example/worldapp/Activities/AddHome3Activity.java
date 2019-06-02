package com.example.worldapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.worldapp.BaseClasses.BaseAppCompat;
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

    private void InitializeViews()
    {
        mAmenities = findViewById(R.id.et_amenities);
        mPrice = findViewById(R.id.et_price_per_night);
    }

    public void GoToAddHome4(View view) {startActivity(new Intent(this, AddHome1Activity.class));}

}
