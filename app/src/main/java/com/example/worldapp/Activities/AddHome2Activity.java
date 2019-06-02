package com.example.worldapp.Activities;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.worldapp.BaseClasses.BaseAppCompat;
import com.example.worldapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddHome2Activity extends BaseAppCompat {

    private Spinner listingsSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_home2);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        InitializeViews();
        InitializeSpinners();
    }

    private void InitializeGuestsSpinner()
    {
        ArrayList<Integer> rooms = new ArrayList<>();
        for (int i=1; i<=60; i++)
        {
            rooms.add(i);
        }
        Spinner roomsSpinner = findViewById(R.id.spinner_select_number_of_guests);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, R.layout.template_spinner_layout, rooms);
        roomsSpinner.setAdapter(adapter);
    }

    private void InitializeRooomsSpinner()
    {
        ArrayList<Integer> rooms = new ArrayList<>();
        for (int i=1; i<=30; i++)
        {
            rooms.add(i);
        }
        Spinner roomsSpinner = findViewById(R.id.spinner_select_number_of_rooms);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, R.layout.template_spinner_layout, rooms);
        roomsSpinner.setAdapter(adapter);
    }

    private void InitializeBedsSpinner()
    {
        ArrayList<Integer> rooms = new ArrayList<>();
        for (int i=1; i<=60; i++)
        {
            rooms.add(i);
        }
        Spinner roomsSpinner = findViewById(R.id.spinner_select_number_of_beds);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, R.layout.template_spinner_layout, rooms);
        roomsSpinner.setAdapter(adapter);
    }

    private void InitializeBathroomSpinner()
    {
        ArrayList<Integer> rooms = new ArrayList<>();
        for (int i=1; i<=20; i++)
        {
            rooms.add(i);
        }
        TextView textView = findViewById(R.id.default_spinner_tv);
        Spinner roomsSpinner = findViewById(R.id.spinner_select_bathrooms_number);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, R.layout.template_spinner_layout, rooms);
        roomsSpinner.setAdapter(adapter);
       // textView.setBackgroundColor(Color.parseColor("#D7DE9B"));
    }

    private void InitializeListingTypeSpinner() {
        String[] listingType = {"Private room","Shared room","Entire home"};
        List<String> listings = Arrays.asList(listingType);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.template_spinner_layout, listings);
        listingsSpinner.setAdapter(adapter);
    }

    private void InitializeOwnerTypeSpinner() {
        String[] listingType = {"Private home","Hotel","Mansion", "Inn","Hostel"};
        List<String> listings = Arrays.asList(listingType);
        Spinner roomsSpinner = findViewById(R.id.spinner_select_owner_type);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.template_spinner_layout, listings);
        roomsSpinner.setAdapter(adapter);
    }

    private void InitializeSpinners()
    {
        InitializeGuestsSpinner();
        InitializeRooomsSpinner();
        InitializeBedsSpinner();
        InitializeBathroomSpinner();
        InitializeOwnerTypeSpinner();
        InitializeListingTypeSpinner();
    }

    private void InitializeViews()
    {
        listingsSpinner = findViewById(R.id.spinner_select_listing_type);
    }

    public void GoToAddHome3(View view) {
        startActivity(new Intent(this, AddHome3Activity.class));
    }

}
