package com.example.worldapp.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.worldapp.BaseClasses.BaseAppCompat;
import com.example.worldapp.Constants.ConstantValues;
import com.example.worldapp.Core.TourCore;
import com.example.worldapp.R;
import com.squareup.timessquare.CalendarPickerView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class ToursFilterActivity extends BaseAppCompat {
    private TextInputEditText mCountry, mRegion, mCity, mPrice;
    private Spinner mTypeSpinner;
    private static TextView mTourDate;
    private FloatingActionButton mSearchButton;
    private ArrayList<String> mFieldValues;
    private String[] mFilterValues;
    public static String mChosenDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_tours_filter);
        super.SetToolbarTitle("Filter tours");
        InitializeViews();
        SetSpinner();
        SetFilters();

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetValues();
                Intent myIntent = new Intent(ToursFilterActivity.this, ListAllToursActivity.class);
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

    private void SetFiltersValues() {
        mFilterValues = mFieldValues.toArray(mFilterValues);
        SetEditTextValue(mCountry, mFilterValues[0]);
        SetEditTextValue(mRegion, mFilterValues[1]);
        SetEditTextValue(mCity, mFilterValues[2]);
        SetEditTextValue(mPrice, mFilterValues[3]);
        mTourDate.setText(TourCore.Instance().getmBookedDates());
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
        String type = mTypeSpinner.getSelectedItem().toString();
        mFieldValues.add(type);
        mFieldValues.add(mChosenDate);
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

    private void SetSpinner() {
        List<String> types = Arrays.asList("All", "Walking", "Bike", "Car", "Boat", "Other");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.template_spinner_layout, types);
        mTypeSpinner.setAdapter(adapter);
    }

    private void InitializeViews() {
        mCountry = findViewById(R.id.et_filter_country);
        mRegion = findViewById(R.id.et_filter_region);
        mCity = findViewById(R.id.et_filter_city);
        mPrice = findViewById(R.id.et_filter_price);
        mSearchButton = findViewById(R.id.btn_filter_save);
        mTypeSpinner = findViewById(R.id.spinner_filter_tour_type);
        mTourDate = findViewById(R.id.tv_filter_set_date);
    }

    public void SelectDates(View view) {
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
            mChosenDate = formatTime.format(date);
            mTourDate.setText(mChosenDate);
            TourCore.Instance().setmBookedDates(mChosenDate);
        }
    }
}
