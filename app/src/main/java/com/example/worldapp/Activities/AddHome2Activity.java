package com.example.worldapp.Activities;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.worldapp.BaseClasses.BaseAppCompat;
import com.example.worldapp.Core.AccommodationCore;
import com.example.worldapp.Core.TourCore;
import com.example.worldapp.Core.UserCore;
import com.example.worldapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class AddHome2Activity extends BaseAppCompat {

    private EditText mTitle;
    private Spinner listingsSpinner, roomsSpinner,guestsSpinner, bedsSpinner, bathroomsSpinner,ownerTypeSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_home2);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        super.SetToolbarTitle("Add accommodation");

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
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, R.layout.template_spinner_layout, rooms);
        guestsSpinner.setAdapter(adapter);
    }

    private void InitializeRooomsSpinner()
    {
        ArrayList<Integer> rooms = new ArrayList<>();
        for (int i=1; i<=30; i++)
        {
            rooms.add(i);
        }
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
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, R.layout.template_spinner_layout, rooms);
        bedsSpinner.setAdapter(adapter);
    }

    private void InitializeBathroomSpinner()
    {
        ArrayList<Integer> rooms = new ArrayList<>();
        for (int i=1; i<=20; i++)
        {
            rooms.add(i);
        }
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, R.layout.template_spinner_layout, rooms);
        bathroomsSpinner.setAdapter(adapter);
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
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.template_spinner_layout, listings);
        ownerTypeSpinner.setAdapter(adapter);
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

    private void GetValues()
    {
        String title = mTitle.getText().toString();
        int rooms = Integer.getInteger(roomsSpinner.getSelectedItem().toString());
        int bathrooms = Integer.getInteger(bathroomsSpinner.getSelectedItem().toString());
        double beds = Double.valueOf(bedsSpinner.getSelectedItem().toString());
        String homeID = UUID.randomUUID().toString();

        AccommodationCore.Instance().setAnnouncementTitle(title);
        AccommodationCore.Instance().setRoomsToUse(rooms);
        AccommodationCore.Instance().setBathroomsToUse(bathrooms);
        AccommodationCore.Instance().setBedsToUse(beds);
        AccommodationCore.Instance().setListingType(listingsSpinner.getSelectedItem().toString());
        AccommodationCore.Instance().setHomeId(homeID);
        AccommodationCore.Instance().setUserId(UserCore.Instance().User.getUserId());
    }

    private void InitializeViews()
    {
        mTitle = findViewById(R.id.et_announcement_title);
        listingsSpinner = findViewById(R.id.spinner_select_listing_type);
        guestsSpinner = findViewById(R.id.spinner_select_number_of_guests);
        ownerTypeSpinner = findViewById(R.id.spinner_select_owner_type);
        bathroomsSpinner = findViewById(R.id.spinner_select_bathrooms_number);
        bedsSpinner = findViewById(R.id.spinner_select_number_of_beds);
        roomsSpinner = findViewById(R.id.spinner_select_number_of_rooms);
    }

    public void GoToAddHome4(View view) {
        startActivity(new Intent(this, AddHome4Activity.class));
    }

}
