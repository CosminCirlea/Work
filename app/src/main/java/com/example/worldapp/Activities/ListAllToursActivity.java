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
import android.widget.TextView;
import android.widget.Toast;

import com.example.worldapp.Adapters.MyToursListingsAdapter;
import com.example.worldapp.BaseClasses.BaseAppCompat;
import com.example.worldapp.Core.UserCore;
import com.example.worldapp.Helpers.FirebaseHelper;
import com.example.worldapp.Models.GuidedToursModel;
import com.example.worldapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListAllToursActivity extends BaseAppCompat implements SearchView.OnQueryTextListener {
    DatabaseReference mToursDatabaseReference;
    RecyclerView recyclerView;
    ArrayList<GuidedToursModel> mTourList;
    MyToursListingsAdapter mTourAdapter;
    private Button mFilterButton;
    private String[] mFilterValues;
    private ArrayList<String> mFiltersList;
    private TextView mNoToursFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_list_all_tours);
        InitializeViews();
        super.SetToolbarTitle("Tours");

        recyclerView = findViewById(R.id.rv_listed_tours);
        recyclerView.setLayoutManager( new LinearLayoutManager(ListAllToursActivity.this));

        SearchView mSearchView = findViewById(R.id.sv_filter_tours);
        mSearchView.setOnQueryTextListener(this);

        mTourList = new ArrayList<>();
        mToursDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Tours");
        mToursDatabaseReference.orderByChild("mTourPrice").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    GuidedToursModel mGuidedTour = dataSnapshot1.getValue(GuidedToursModel.class);
                    if (IsMatchingFilter(mGuidedTour, mFilterValues)) {
                        mTourList.add(mGuidedTour);
                    }
                }
                mTourAdapter = new MyToursListingsAdapter(ListAllToursActivity.this,mTourList);
                recyclerView.setAdapter(mTourAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ListAllToursActivity.this, "Opsss.... Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

        if (mTourList.size()==0)
        {
            mNoToursFound.setText("No tours found!");
        }
        else
        {
            mNoToursFound.setText("");
        }

        mFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ListAllToursActivity.this, ToursFilterActivity.class);
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
        startActivity(new Intent(ListAllToursActivity.this, ActivityHome.class));
    }

    private boolean IsMatchingFilter(GuidedToursModel tour, String[] filter)
    {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            if (tour.getmUserId().contains(UserCore.Instance().User.getUserId())) {
                return false;
            }
        }
        if (filter!=null) {
            if (!tour.getmTourCountry().toLowerCase().contains(filter[0].toLowerCase())) {
                return false;
            }
            if (!tour.getmTourRegion().toLowerCase().contains(filter[1].toLowerCase())) {
                return false;
            }
            if (!tour.getmTourCity().toLowerCase().contains(filter[2].toLowerCase())) {
                return false;
            }
            if (!tour.getmTourType().toLowerCase().contains(filter[4].toLowerCase()) && !filter[4].toLowerCase().contains("all")) {
                return false;
            }
            int price;
            try {
                price = Integer.parseInt(filter[3]);
            } catch (Exception e) {
                price = Integer.MAX_VALUE;
            }
            if (tour.getmTourPrice() > price) {
                return false;
            }

            if (tour.getmBookedDatesList()!=null) {
                ArrayList<String>bookedDates = tour.getmBookedDatesList();
                return !bookedDates.contains(filter[5]);
            }
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
        //mTourAdapter.updateList(newList);
        return true;
    }

    private void InitializeViews()
    {
        mNoToursFound = findViewById(R.id.tv_no_tours_found);
        mFilterButton = findViewById(R.id.btn_toolbar_filter);
    }
}
