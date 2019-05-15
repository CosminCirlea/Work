package com.example.worldapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.worldapp.BaseClasses.BaseAppCompat;
import com.example.worldapp.R;

public class ListParkingsActivity extends BaseAppCompat {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_list_parkings);
        super.SetToolbarTitle("Parkings");
    }
}
