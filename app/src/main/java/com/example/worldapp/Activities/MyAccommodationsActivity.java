package com.example.worldapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.worldapp.Adapters.MyHomesListingsAdapter;
import com.example.worldapp.Adapters.MyToursListingsAdapter;
import com.example.worldapp.BaseClasses.BaseAppCompat;
import com.example.worldapp.Core.UserCore;
import com.example.worldapp.Models.HomeDetailsModel;
import com.example.worldapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyAccommodationsActivity extends BaseAppCompat {
    private DatabaseReference mToursDatabaseReference;
    private RecyclerView recyclerView;
    private ArrayList<HomeDetailsModel> mHomeList;
    private MyHomesListingsAdapter mHomesAdapter;
    private String userID;
    private FloatingActionButton mAddAccommodationFAB;
    private TextView mNoToursTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_my_accommodations);
        InitializeViews();
        super.SetToolbarTitle("My homes");

        mAddAccommodationFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyAccommodationsActivity.this, AddHome2Activity.class));
            }
        });

        userID = UserCore.Instance().User.getUserId();
        mHomeList = new ArrayList<>();
        mToursDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Accommodation");
        mToursDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    HomeDetailsModel mHome = dataSnapshot1.getValue(HomeDetailsModel.class);
                    if (mHome != null) {
                        if (mHome.getUserId().contains(userID)) {
                            mHomeList.add(mHome);
                        }
                    }
                }

                mHomesAdapter = new MyHomesListingsAdapter(MyAccommodationsActivity.this, mHomeList);
                recyclerView.setAdapter(mHomesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MyAccommodationsActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });
        if (UserCore.Instance().getmListedHomes() != null)
        {
            mHomeList = UserCore.Instance().getmListedHomes();
            mHomesAdapter = new MyHomesListingsAdapter(MyAccommodationsActivity.this, mHomeList);
            recyclerView.setAdapter(mHomesAdapter);
            mNoToursTv.setVisibility(View.VISIBLE);
        }
        else
        {
            mNoToursTv.setVisibility(View.INVISIBLE);
        }
    }

    private void InitializeViews(){
        recyclerView = findViewById(R.id.rv_my_listed_homes);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyAccommodationsActivity.this));
        mAddAccommodationFAB = findViewById(R.id.fab_add_accommodations);
        mNoToursTv = findViewById(R.id.tv_my_listed_tours_no_tour_text);
    }
}

