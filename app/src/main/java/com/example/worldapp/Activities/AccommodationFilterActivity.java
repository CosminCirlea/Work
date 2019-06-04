package com.example.worldapp.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.worldapp.BaseClasses.BaseAppCompat;
import com.example.worldapp.Constants.ConstantValues;
import com.example.worldapp.Core.AccommodationCore;
import com.example.worldapp.Core.TourCore;
import com.example.worldapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AccommodationFilterActivity extends BaseAppCompat {
    private TextInputEditText mCountry, mRegion, mCity, mPrice;
    private static TextView mStartDateTv, mEndDateTv, mNightsTv;
    private FloatingActionButton mSearchButton;
    private ArrayList<String> mFieldValues;
    private String[] mFilterValues;
    private static String mStartDate;
    private static String mEndDate;
    private static int mDate;
    private static Date mStartDay, mEndDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accommodation_filter);
        super.SetToolbarTitle("Acommodation filter");
        InitializeViews();
        SetFilters();
        InitializeDates();

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetValues();
                Intent myIntent = new Intent(AccommodationFilterActivity.this, ListAllAccommodationActivity.class);
                myIntent.putStringArrayListExtra("filters", mFieldValues);
                startActivity(myIntent);
            }
        });
    }

    private void SetFilters() {
        Intent myIntent = getIntent();
        mFieldValues = myIntent.getStringArrayListExtra("alreadyFiltered");
        if (mFieldValues != null) {
            mFilterValues = new String[mFieldValues.size()];
            mFilterValues = mFieldValues.toArray(mFilterValues);
            SetFiltersValues();
        }
    }

    private void InitializeDates()
    {
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        mStartDay = today;
        calendar.setTime(today);
        calendar.add(Calendar.DATE,1);
        mEndDay = calendar.getTime();
    }

    private void SetFiltersValues() {
        mFilterValues = mFieldValues.toArray(mFilterValues);
        SetEditTextValue(mCountry, mFilterValues[0]);
        SetEditTextValue(mRegion, mFilterValues[1]);
        SetEditTextValue(mCity, mFilterValues[2]);
        SetEditTextValue(mPrice, mFilterValues[3]);
        mStartDateTv.setText(AccommodationCore.Instance().getmStartDate());
        mEndDateTv.setText(AccommodationCore.Instance().getmEndDate());
    }

    private void GetValues() {
        mFieldValues = new ArrayList<>();
        String country = GetEditTextValue(mCountry);
        mFieldValues.add(country);
        String region = GetEditTextValue(mRegion);
        mFieldValues.add(region);
        String city = GetEditTextValue(mCity);
        mFieldValues.add(city);
        String price = GetEditTextValue(mPrice);
        mFieldValues.add(price);
    }

    private void SetEditTextValue(TextInputEditText destination, String value) {
        destination.setText(value);
    }

    private String GetEditTextValue(TextInputEditText value) {
        if (value.getText().toString() != null) {
            return value.getText().toString();
        }
        return "";
    }
    private void InitializeViews() {
        mCountry = findViewById(R.id.et_filter_country);
        mRegion = findViewById(R.id.et_filter_region);
        mCity = findViewById(R.id.et_filter_city);
        mPrice = findViewById(R.id.et_filter_price);
        mSearchButton = findViewById(R.id.btn_filter_save);
        mNightsTv = findViewById(R.id.tv_accommodation_filter_nights);
        mStartDateTv = findViewById(R.id.tv_filter_set_start_date);
        mEndDateTv = findViewById(R.id.tv_filter_set_end_date);
    }


    public void SelectStartDate(View view) {
        mDate =0;
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void SelectEndDate(View view) {
        mDate = 1;
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
            dialog.getDatePicker().setMinDate(c.getTimeInMillis());
            return dialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day, 0, 0);
            Date date = calendar.getTime();
            SimpleDateFormat formatTime = new SimpleDateFormat(ConstantValues.DATE_FORMAT);
            if (mDate == 0 )
            {
                mStartDay = date;
                if (mStartDay.after(mEndDay))
                {
                    Calendar aux = Calendar.getInstance();
                    aux.setTime(mStartDay);
                    aux.add(Calendar.DATE,1);
                    mEndDay = aux.getTime();
                    mEndDate = formatTime.format(mEndDay);
                    mEndDateTv.setText(mEndDate);
                    AccommodationCore.Instance().setmEndDate(mEndDate);
                }
                mStartDate = formatTime.format(date);
                mStartDateTv.setText(mStartDate);
                AccommodationCore.Instance().setmStartDate(mStartDate);
            }
            else if (mDate == 1 )
            {
                mEndDay = date;
                if (mStartDay.after(date) || mStartDay.equals(mEndDay))
                {
                    Toast.makeText(getActivity(), "End day must be after the start day!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mEndDate = formatTime.format(date);
                    mEndDateTv.setText(mEndDate);
                    AccommodationCore.Instance().setmStartDate(mEndDate);
                }
            }
        }
    }//date picker

}