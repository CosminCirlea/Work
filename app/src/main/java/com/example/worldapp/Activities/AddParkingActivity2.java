package com.example.worldapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.worldapp.BaseClasses.BaseAppCompat;
import com.example.worldapp.Constants.NavigationConstants;
import com.example.worldapp.Core.ParkingCore;
import com.example.worldapp.R;

public class AddParkingActivity2 extends BaseAppCompat {
    private NumberPicker mParkingSpotsPicker;
    private RadioGroup mRadioGroup;
    private double mParkingPrice;
    private EditText mPriceEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_parking2);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        super.SetToolbarTitle("Add parking");
        InitializeViews();
        InitializeValues();
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.radio_private:
                if (checked) {
                    ParkingCore.Instance().setmType(NavigationConstants.PRIVATE_PARKING);
                    mParkingSpotsPicker.setMinValue(1);
                    mParkingSpotsPicker.setMaxValue(NavigationConstants.PRIVATE_PARKING_MAX_SPOTS);
                }
                break;
            case R.id.radio_lot:
                if (checked) {
                    ParkingCore.Instance().setmType(NavigationConstants.PARKING_LOT);
                    mParkingSpotsPicker.setMinValue(1);
                    mParkingSpotsPicker.setMaxValue(NavigationConstants.PARKING_LOT_MAX_SPOTS);
                }
                break;
        }
    }

    private void InitializeValues()
    {
        mParkingSpotsPicker.setMinValue(1);
        mParkingSpotsPicker.setMaxValue(NavigationConstants.PRIVATE_PARKING_MAX_SPOTS);
        mRadioGroup.check(R.id.radio_private);
    }

    private void InitializeViews()
    {
        mParkingSpotsPicker = findViewById(R.id.number_picker_parking_spots);
        mRadioGroup = findViewById(R.id.radio_container);
        mPriceEt = findViewById(R.id.et_parking_price);
    }

    public void GoToParking1(View view) {
        startActivity(new Intent(AddParkingActivity2.this, AddParkingActivity1.class));
    }

    public void onRadioPriceButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.radio_day_price:
                if (checked) {
                    mPriceEt.setText("");
                    mParkingPrice = Integer.parseInt(mPriceEt.getText().toString());
                }
                break;
            case R.id.radio_hour_price:
                if (checked) {
                    mPriceEt.setText("");
                    mParkingPrice = Integer.parseInt(mPriceEt.getText().toString());
                }
                break;
        }
    }
}
