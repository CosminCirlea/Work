package com.example.worldapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.worldapp.BaseClasses.BaseAppCompat;
import com.example.worldapp.R;

public class AddParkingActivity3 extends BaseAppCompat {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_parking3);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        super.SetToolbarTitle("Add parking");
    }
}
