package com.example.worldapp.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.worldapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class AddHome1Activity extends AppCompatActivity {

    private Layout templateSpinner;
    private TextView mSpinnerTv;
    Spinner citizenshipSpinner;
    private TextInputEditText mAnnouncementTitleEt, mRegionEt, mCityEt, mAddressLineEt, mZipCodeEt;
    private Button mNextButton;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_add_home1);
        InitializeViews();
        SetWhitelabelColors();
        InitializeCountrySpinner();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
            }
        });
    }



    private void InitializeCountrySpinner()
    {
        Locale[] locale = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<>();
        String country;
        for( Locale loc : locale ){
            country = loc.getDisplayCountry();
            if( country.length() > 0 && !countries.contains(country) ){
                countries.add( country );
            }
        }
        Collections.sort(countries, String.CASE_INSENSITIVE_ORDER);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.template_spinner_layout, countries);
        citizenshipSpinner.setAdapter(adapter);
    }

    private void InitializeViews() {
        mAnnouncementTitleEt =findViewById(R.id.et_announcement_title);
        mRegionEt =findViewById(R.id.et_region);
        mCityEt = findViewById(R.id.et_city);
        mAddressLineEt=findViewById(R.id.et_address_line);
        mZipCodeEt =findViewById(R.id.et_zip_code);
        mNextButton = findViewById(R.id.btn_add_new_home);
        toolbar = findViewById(R.id.toolbar);
        citizenshipSpinner = findViewById(R.id.spinner_select_country);
        citizenshipSpinner.setBackgroundColor(Color.parseColor("#D7DE9B"));
    }

    private void SetWhitelabelColors()
    {
        int hintColor = Color.parseColor("#D0D0D0");
        mAnnouncementTitleEt.setHintTextColor(hintColor);
        mRegionEt.setHintTextColor(hintColor);
        mCityEt.setHintTextColor(hintColor);
    }

    public void GoToAddHome2(View view) {
        startActivity(new Intent(this, AddHome2Activity.class));
    }
}
