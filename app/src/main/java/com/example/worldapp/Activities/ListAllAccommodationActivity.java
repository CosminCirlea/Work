package com.example.worldapp.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.worldapp.Adapters.MyHomesListingsAdapter;
import com.example.worldapp.BaseClasses.BaseAppCompat;
import com.example.worldapp.Core.UserCore;
import com.example.worldapp.Helpers.FirebaseHelper;
import com.example.worldapp.Interfaces.ClickListener;
import com.example.worldapp.Models.HomeDetailsModel;
import com.example.worldapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListAllAccommodationActivity extends BaseAppCompat {
    DatabaseReference mAccommodationDatabase;
    RecyclerView recyclerView;
    ArrayList<HomeDetailsModel> mAccommodationList;
    MyHomesListingsAdapter mHomeAdapter;
    private Button mFilterButton;
    private String[] mFilterValues;
    private ArrayList<String> mFiltersList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all_accommodation);
        super.SetToolbarTitle("Accommodation");
        InitializeViews();
        recyclerView = findViewById(R.id.rv_listed_accommodation);
        recyclerView.setLayoutManager( new LinearLayoutManager(ListAllAccommodationActivity.this));

        mAccommodationList = new ArrayList<>();
        mAccommodationDatabase = FirebaseHelper.mAccommodationDatabaseReference;

        interfaceTest(new ClickListener() {
            @Override
            public void onClickedAction(boolean test) {

            }
        });

        mFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ListAllAccommodationActivity.this, AccommodationFilterActivity.class);
                myIntent.putStringArrayListExtra("alreadyFiltered", mFiltersList);
                startActivity(myIntent);
            }
        });
    }

    private void interfaceTest(final ClickListener clickListener)
    {
        mAccommodationDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    HomeDetailsModel mHome = dataSnapshot1.getValue(HomeDetailsModel.class);
                    if (IsMatchingFilter(mHome, mFilterValues)) {
                        mAccommodationList.add(mHome);
                        clickListener.onClickedAction(true);
                    }
                }
                mHomeAdapter = new MyHomesListingsAdapter(ListAllAccommodationActivity.this, mAccommodationList);
                recyclerView.setAdapter(mHomeAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ListAllAccommodationActivity.this, "Opsss.... Something went wrong", Toast.LENGTH_SHORT).show();
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
        finish();
        startActivity(new Intent(ListAllAccommodationActivity.this, ActivityHome.class));
    }

    private boolean IsMatchingFilter(HomeDetailsModel house, String[] filter)
    {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            if (house.getUserId().contains(UserCore.Instance().User.getUserId()))
            {
                return false;
            }
        }
        if (filter!=null) {
            if (!house.getCountry().toLowerCase().contains(filter[0].toLowerCase())) {
                return false;
            }
            if (!house.getRegion().toLowerCase().contains(filter[1].toLowerCase())) {
                return false;
            }
            if (!house.getCity().toLowerCase().contains(filter[2].toLowerCase())) {
                return false;
            }

            int price;
            try {
                price = Integer.parseInt(filter[3]);
            } catch (Exception e) {
                price = Integer.MAX_VALUE;
            }
            if (house.getPricePerNight() > price) {
                return false;
            }

           /* if (house.getmBookedDates()!=null) {
                ArrayList<String>bookedDates = house.getmBookedDates();
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

    private void InitializeViews()
    {
        mFilterButton = findViewById(R.id.btn_toolbar_filter);
    }
}

