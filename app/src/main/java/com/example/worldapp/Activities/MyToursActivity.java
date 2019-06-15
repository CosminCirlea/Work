package com.example.worldapp.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.worldapp.Adapters.MyToursListingsAdapter;
import com.example.worldapp.BaseClasses.BaseAppCompat;
import com.example.worldapp.Core.UserCore;
import com.example.worldapp.Models.GuidedToursModel;
import com.example.worldapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class MyToursActivity extends BaseAppCompat {
    private DatabaseReference mToursDatabaseReference;
    private RecyclerView recyclerView;
    private ArrayList<GuidedToursModel> mTourList;
    private MyToursListingsAdapter mTourAdapter;
    private String userID;
    private FloatingActionButton mAddTourFAB;
    private TextView mNoToursTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_my_tours);
        InitializeViews();
        super.SetToolbarTitle("My tours");

        mAddTourFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyToursActivity.this, AddTour1Activity.class));
            }
        });

        userID = UserCore.Instance().User.getUserId();
        mTourList = new ArrayList<>();
        mToursDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Tours");
        mToursDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    GuidedToursModel mGuidedTour = dataSnapshot1.getValue(GuidedToursModel.class);
                    if (mGuidedTour != null) {
                        if (mGuidedTour.getmUserId().contains(userID)) {
                            mTourList.add(mGuidedTour);
                        }
                    }
                }

                mTourAdapter = new MyToursListingsAdapter(MyToursActivity.this, mTourList);
                recyclerView.setAdapter(mTourAdapter);
                checkForItems();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MyToursActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void checkForItems()
    {
        if (mTourList.size()==0)
        {
            //mTourList = UserCore.Instance().getmListedTours();
            //mTourAdapter = new MyToursListingsAdapter(MyToursActivity.this, mTourList);
            //recyclerView.setAdapter(mTourAdapter);
            mNoToursTv.setText("You currently have no listed tours!");
        }
        else
        {
            mNoToursTv.setText("");
        }
    }

    private void InitializeViews(){
        recyclerView = findViewById(R.id.rv_my_listed_tours);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyToursActivity.this));
        mAddTourFAB = findViewById(R.id.fab_add_tour);
        mNoToursTv = findViewById(R.id.tv_my_listed_tours_no_tour_text);
    }
}

