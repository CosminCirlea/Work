package com.example.worldapp.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.worldapp.Adapters.TourBookingsAdapterTrips;
import com.example.worldapp.Constants.ConstantValues;
import com.example.worldapp.Core.UserCore;
import com.example.worldapp.Helpers.FirebaseHelper;
import com.example.worldapp.Models.TourBookingManager;
import com.example.worldapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentTrips extends Fragment {
    RecyclerView recyclerView, incomingRecyclerView;
    ArrayList<TourBookingManager> mBookingList, mIncomingList;
    DatabaseReference mBookingManagerDatabase;
    TourBookingsAdapterTrips mTourAdapter , mIncomingAdapter;
    private String mUserId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_trips, container, false);

        recyclerView = rootView.findViewById(R.id.rv_notifications);
        incomingRecyclerView = rootView.findViewById(R.id.rv_notifications_incoming);
        recyclerView.setLayoutManager( new LinearLayoutManager(getActivity()));
        incomingRecyclerView.setLayoutManager( new LinearLayoutManager(getActivity()));

        mUserId = UserCore.Instance().User.getUserId();
        mBookingList = new ArrayList<>();
        mIncomingList = new ArrayList<>();

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.show();

        mBookingManagerDatabase = FirebaseHelper.mBookingManagerDatabase;
        mBookingManagerDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    TourBookingManager mManager = dataSnapshot1.getValue(TourBookingManager.class);
                    if (mManager.getmBuyerId().equals(mUserId))
                    {
                        mBookingList.add(mManager);
                    }
                    else if (mManager.getmOwnerId().equals(mUserId) && mManager.getmStatus()== ConstantValues.BOOKING_ACCEPTED)
                    {
                        mManager.setmStatus(ConstantValues.BOOKING_INCOMING);
                        mIncomingList.add(mManager);
                    }
                }
                mTourAdapter = new TourBookingsAdapterTrips(getActivity(),mBookingList);
                recyclerView.setAdapter(mTourAdapter);
                mIncomingAdapter = new TourBookingsAdapterTrips(getActivity(),mIncomingList);
                incomingRecyclerView.setAdapter(mIncomingAdapter);
                pd.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;

    }
}

