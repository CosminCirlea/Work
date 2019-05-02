package com.example.worldapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.worldapp.R;
import com.google.android.gms.maps.MapView;

public class AddTour4Activity extends AppCompatActivity {
    private TextView mTitle, mLocation, mType, mDescription, mParticipants, mDuration, mPrice, mLandmarks, mOwnerName;
    private MapView mMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tour4);
        InitializeViews();
    }

    public void RegisterTour(View view) {
        Intent mIntent = new Intent(this, ActivityHome.class);
        startActivity(mIntent);
        Toast.makeText(this, "Tour added succesfully!", Toast.LENGTH_SHORT).show();
    }

    private void InitializeViews()
    {
        mMapView = findViewById(R.id.map_tour);

    }
}
