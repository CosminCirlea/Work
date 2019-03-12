package com.example.worldapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.worldapp.R;

public class AddTour3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        Intent myIntent = getIntent();
        myIntent.getStringExtra("tourId");
        InitializeViews();
        setContentView(R.layout.activity_add_tour3);
    }

    public void RegisterTour(View view) {
    }

    public void InitializeViews()
    {

    }
}
