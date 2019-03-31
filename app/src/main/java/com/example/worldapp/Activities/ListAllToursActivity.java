package com.example.worldapp.Activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.worldapp.Adapters.MyToursListingsAdapter;
import com.example.worldapp.Models.GuidedToursModel;
import com.example.worldapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListAllToursActivity extends AppCompatActivity {
    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<GuidedToursModel> mTourList;
    ArrayAdapter<GuidedToursModel> mArrayAdapter;
    MyToursListingsAdapter mTourAdapter;
    GuidedToursModel mGuidedTour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_list_all_tours);
        recyclerView = findViewById(R.id.rv_listed_tours);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));

        mGuidedTour = new GuidedToursModel();
        mTourList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference().child("Tours");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Map<String, Object> td = (HashMap<String,Object>) dataSnapshot.getValue();
                ArrayList<Object> values = new ArrayList<>(td.values());
                ArrayList<GuidedToursModel> castedList = (ArrayList<GuidedToursModel>) (ArrayList) values;

                /*for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    mGuidedTour = dataSnapshot1.getValue(GuidedToursModel.class);
                    mTourList.add(mGuidedTour);
                }*/
                mTourAdapter = new MyToursListingsAdapter(castedList);
                recyclerView.setAdapter(mTourAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ListAllToursActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
