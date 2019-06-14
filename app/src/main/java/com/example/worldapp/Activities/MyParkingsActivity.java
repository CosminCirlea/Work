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

import com.example.worldapp.Adapters.MyParkingsAdapter;
import com.example.worldapp.BaseClasses.BaseAppCompat;
import com.example.worldapp.Core.UserCore;
import com.example.worldapp.Helpers.FirebaseHelper;
import com.example.worldapp.Models.ParkingModel;
import com.example.worldapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyParkingsActivity extends BaseAppCompat {
    private DatabaseReference mParkingsDatabase;
    private RecyclerView recyclerView;
    private ArrayList<ParkingModel> mParkingList;
    private MyParkingsAdapter myParkingsAdapter;
    private String userID;
    private FloatingActionButton mAddParkingFAB;
    private TextView mNoParkingsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_my_parkings);
        InitializeViews();
        super.SetToolbarTitle("My parkings");

        mAddParkingFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyParkingsActivity.this, AddParkingActivity2.class));
            }
        });

        userID = UserCore.Instance().User.getUserId();
        mParkingList = new ArrayList<>();

        mParkingsDatabase = FirebaseHelper.mParkingsDatabaseReference;
        mParkingsDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ParkingModel mHome = dataSnapshot1.getValue(ParkingModel.class);
                    if (mHome != null) {
                        if (mHome.getmOwnerID().contains(userID)) {
                            mParkingList.add(mHome);
                        }
                    }
                }

                myParkingsAdapter = new MyParkingsAdapter(MyParkingsActivity.this, mParkingList);
                recyclerView.setAdapter(myParkingsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MyParkingsActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        if (mParkingList.size()==0)
        {
           // mParkingList = UserCore.Instance().getmListedParkings();
            //myParkingsAdapter = new MyParkingsAdapter(MyParkingsActivity.this, mParkingList);
            //recyclerView.setAdapter(myParkingsAdapter);
            mNoParkingsTv.setText("You currently have no listed parkings!");
        }
        else
        {
            mNoParkingsTv.setText("");
        }
    }

    private void InitializeViews(){
        recyclerView = findViewById(R.id.rv_my_listed_parkings);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyParkingsActivity.this));
        mAddParkingFAB = findViewById(R.id.fab_add_parking);
        mNoParkingsTv = findViewById(R.id.tv_my_listed_parkings_no_parkings_text);
    }
}
