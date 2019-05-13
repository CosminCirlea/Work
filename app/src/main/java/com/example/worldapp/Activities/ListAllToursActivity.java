package com.example.worldapp.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.worldapp.Adapters.MyToursListingsAdapter;
import com.example.worldapp.BaseClasses.BaseAppCompat;
import com.example.worldapp.Helpers.FirebaseHelper;
import com.example.worldapp.Models.GuidedToursModel;
import com.example.worldapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListAllToursActivity extends BaseAppCompat implements SearchView.OnQueryTextListener {
    DatabaseReference mToursDatabaseReference;
    RecyclerView recyclerView;
    ArrayList<GuidedToursModel> mTourList;
    MyToursListingsAdapter mTourAdapter;
    private Button mFilterButton;
    private ArrayList<String> mFilterValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_list_all_tours);
        InitializeViews();
        SetFilters();

        recyclerView = findViewById(R.id.rv_listed_tours);
        recyclerView.setLayoutManager( new LinearLayoutManager(ListAllToursActivity.this));

        SearchView mSearchView = findViewById(R.id.sv_filter_tours);
        mSearchView.setOnQueryTextListener(this);

        mTourList = new ArrayList<>();
        mToursDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Tours");
        mToursDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    GuidedToursModel mGuidedTour = dataSnapshot1.getValue(GuidedToursModel.class);
                    mTourList.add(mGuidedTour);
                }
                mTourAdapter = new MyToursListingsAdapter(ListAllToursActivity.this,mTourList);
                recyclerView.setAdapter(mTourAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ListAllToursActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        mFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListAllToursActivity.this, ToursFilterActivity.class));
            }
        });
    }

    private void SetFilters()
    {
        Intent myIntent = getIntent();
        mFilterValues = myIntent.getStringArrayListExtra("filters");
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String userInput  = newText.toLowerCase();
        List<GuidedToursModel> newList = new ArrayList<>();
        for (GuidedToursModel city : mTourList)
        {
            if (city.getmTourCity().toLowerCase().contains(userInput))
            {
                newList.add(city);
            }
        }
        mTourAdapter.updateList(newList);
        return true;
    }

    private void InitializeViews()
    {
        mFilterButton = findViewById(R.id.btn_toolbar_filter);
    }
}
