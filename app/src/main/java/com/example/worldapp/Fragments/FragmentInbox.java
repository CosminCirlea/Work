package com.example.worldapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.worldapp.Adapters.InboxBookingsAdapter;
import com.example.worldapp.Constants.ConstantValues;
import com.example.worldapp.Core.UserCore;
import com.example.worldapp.Models.BookingManager;
import com.example.worldapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentInbox extends Fragment {
    RecyclerView recyclerView;
    ArrayList<BookingManager> mBookingList;
    DatabaseReference mToursDatabaseReference;
    InboxBookingsAdapter mTourAdapter;
    private String mUserId;
    private TextView mNoPendingBookings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_inbox, container, false);
        mNoPendingBookings =rootView.findViewById(R.id.tv_no_pending_bookings_text);
        recyclerView = rootView.findViewById(R.id.rv_notifications);
        recyclerView.setLayoutManager( new LinearLayoutManager(getActivity()));

        mUserId = UserCore.Instance().User.getUserId();
        mBookingList = new ArrayList<>();

        mToursDatabaseReference = FirebaseDatabase.getInstance().getReference().child("BookingManager");
        mToursDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mBookingList.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    BookingManager mBookedManager = dataSnapshot1.getValue(BookingManager.class);
                    if (mBookedManager.getmOwnerId().equals(mUserId) && mBookedManager.getmStatus()== ConstantValues.BOOKING_PENDING)
                    {
                        mBookingList.add(mBookedManager);
                    }
                }
                if (mBookingList != null) {
                    mTourAdapter = new InboxBookingsAdapter(getActivity(), mBookingList);
                    recyclerView.setAdapter(mTourAdapter);
                    checkForItems();
                }}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });


        return rootView;
    }

    private void checkForItems()
    {
        if (mBookingList.size()==0)
        {
            mNoPendingBookings.setText("No pending bookings at the moment");
            mNoPendingBookings.setVisibility(View.VISIBLE);
        }
        else
        {
            mNoPendingBookings.setText("");
            mNoPendingBookings.setVisibility(View.INVISIBLE);
        }
    }
}

