package com.example.worldapp.Activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.example.worldapp.Adapters.MyToursListingsAdapter;
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

public class MyToursActivity extends AppCompatActivity {
    DatabaseReference mToursDatabaseReference;
    RecyclerView recyclerView;
    private FirebaseDatabase mFirebaseDatabase;
    ArrayList<GuidedToursModel> mTourList;
    MyToursListingsAdapter mTourAdapter;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_my_tours);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUser = mAuth.getCurrentUser();
        userID = mUser.getUid();

        recyclerView = findViewById(R.id.rv_my_listed_tours);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyToursActivity.this));

        mTourList = new ArrayList<>();
        mToursDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Tours");
        mToursDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    GuidedToursModel mGuidedTour = dataSnapshot1.getValue(GuidedToursModel.class);
                    mTourList.add(mGuidedTour);
                }

                mTourAdapter = new MyToursListingsAdapter(MyToursActivity.this, mTourList);
                recyclerView.setAdapter(mTourAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MyToursActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

