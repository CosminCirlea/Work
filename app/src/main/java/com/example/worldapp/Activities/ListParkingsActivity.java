package com.example.worldapp.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.worldapp.Adapters.MyParkingsAdapter;
import com.example.worldapp.BaseClasses.BaseAppCompat;
import com.example.worldapp.Core.UserCore;
import com.example.worldapp.Helpers.FirebaseHelper;
import com.example.worldapp.Models.ParkingModel;
import com.example.worldapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListParkingsActivity extends BaseAppCompat {
    DatabaseReference mParkingsReference;
    RecyclerView recyclerView;
    ArrayList<ParkingModel> mParkingList;
    MyParkingsAdapter mParkingAdapter;
    private Button mFilterButton;
    private String[] mFilterValues;
    private ArrayList<String> mFiltersList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_list_parkings);
        super.SetToolbarTitle("Parkings");
        InitializeViews();
        recyclerView.setLayoutManager(new LinearLayoutManager(ListParkingsActivity.this));

        mParkingList = new ArrayList<>();
        mParkingsReference = FirebaseHelper.mParkingsDatabaseReference;
        mParkingsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ParkingModel mParking = dataSnapshot1.getValue(ParkingModel.class);
                    if (IsMatchingFilter(mParking, mFilterValues)) {
                        mParkingList.add(mParking);
                    }
                }
                mParkingAdapter = new MyParkingsAdapter(ListParkingsActivity.this, mParkingList);
                recyclerView.setAdapter(mParkingAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ListParkingsActivity.this, "Opsss.... Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

        mFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ListParkingsActivity.this, ParkingsFilterActivity.class);
                myIntent.putStringArrayListExtra("alreadyFiltered", mFiltersList);
                startActivity(myIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SetFilters();
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(ListParkingsActivity.this, ActivityHome.class));
    }

    private boolean IsMatchingFilter(ParkingModel parking, String[] filter)
    {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            if (parking.getmOwnerID().contains(UserCore.Instance().User.getUserId()))
            {
                return false;
            }
        }
        if (filter!=null) {
            if (!parking.getmCountry().toLowerCase().contains(filter[0].toLowerCase())) {
                return false;
            }
            if (!parking.getmCity().toLowerCase().contains(filter[1].toLowerCase())) {
                return false;
            }
            int price;
            try {
                price = Integer.parseInt(filter[2]);
            } catch (Exception e) {
                price = Integer.MAX_VALUE;
            }
            if (parking.getmPricePerDay() > price) {
                return false;
            }

           /* if (parking.getmBookedDays()!=null) {
                ArrayList<String>bookedDates = parking.getmBookedDays();
                return !bookedDates.contains(filter[5]);
            }*/
        }
        return true;
    }

    private void SetFilters()
    {
        Intent myIntent = getIntent();
        mFiltersList = myIntent.getStringArrayListExtra("filters");
        if (mFiltersList!=null)
        {
            mFilterValues = new String[mFiltersList.size()];
            mFilterValues = mFiltersList.toArray(mFilterValues);
        }
    }

    private void InitializeViews() {
        recyclerView = findViewById(R.id.rv_listed_parkings);
        mFilterButton = findViewById(R.id.btn_toolbar_filter);
    }
}