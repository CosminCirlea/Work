package com.example.worldapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

import com.example.worldapp.BaseClasses.BaseAppCompat;
import com.example.worldapp.R;

public class AddParkingActivity1 extends BaseAppCompat {
    private NumberPicker mSpotsPicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_parking1);

    }

    public void onRadioButtonClicked(View view) {
    }
}