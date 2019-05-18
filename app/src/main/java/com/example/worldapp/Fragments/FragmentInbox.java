package com.example.worldapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.worldapp.Activities.ListAllToursActivity;
import com.example.worldapp.Adapters.MyToursListingsAdapter;
import com.example.worldapp.Adapters.TourBookingsAdapter;
import com.example.worldapp.Core.UserCore;
import com.example.worldapp.Models.GuidedToursModel;
import com.example.worldapp.Models.TourBookingManager;
import com.example.worldapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentInbox extends Fragment {
    RecyclerView recyclerView;
    ArrayList<TourBookingManager> mBookingList;
    DatabaseReference mToursDatabaseReference;
    TourBookingsAdapter mTourAdapter;
    private String mUserId;
    private TourBookingManager mBookingManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_inbox, container, false);

        recyclerView = rootView.findViewById(R.id.rv_notifications);
        recyclerView.setLayoutManager( new LinearLayoutManager(getActivity()));

        mUserId = UserCore.Instance().getUserId();
        mBookingList = new ArrayList<>();

        mToursDatabaseReference = FirebaseDatabase.getInstance().getReference().child("BookingManager");
        mToursDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    TourBookingManager mGuidedTour = dataSnapshot1.getValue(TourBookingManager.class);
                    if (mGuidedTour.getmOwnerId().equals(mUserId))
                    {
                        mBookingList.add(mGuidedTour);
                    }
                }
                mTourAdapter = new TourBookingsAdapter(getActivity(),mBookingList);
                recyclerView.setAdapter(mTourAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;

    }
}

