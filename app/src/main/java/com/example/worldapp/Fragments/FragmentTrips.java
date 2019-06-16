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
import android.widget.TextView;
import android.widget.Toast;

import com.example.worldapp.Adapters.TripsBookingAdapter;
import com.example.worldapp.Constants.ConstantValues;
import com.example.worldapp.Core.UserCore;
import com.example.worldapp.Helpers.FirebaseHelper;
import com.example.worldapp.Models.BookingManager;
import com.example.worldapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentTrips extends Fragment {
    RecyclerView recyclerView, incomingRecyclerView;
    ArrayList<BookingManager> mBookingList, mIncomingList;
    DatabaseReference mBookingManagerDatabase;
    TripsBookingAdapter mTourAdapter , mIncomingAdapter;
    private String mUserId;
    private TextView mNoBookings, mNoGuests;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_trips, container, false);
        mNoBookings = rootView.findViewById(R.id.tv_no_bookings_done);
        mNoGuests = rootView.findViewById(R.id.tv_no_incoming_bookings);
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
                    BookingManager mManager = dataSnapshot1.getValue(BookingManager.class);
                    if (mManager.getmBuyerId().equals(mUserId))
                    {
                        mBookingList.add(mManager);
                    }
                    else if (mManager.getmOwnerId().equals(mUserId) && mManager.getmStatus()== ConstantValues.BOOKING_ACCEPTED)
                    {
                        mManager.setmStatus(ConstantValues.BOOKING_INCOMING);
                        mIncomingList.add(mManager);
                        mManager.setmTotalPrice(mManager.getmPrice());
                    }
                }
                mTourAdapter = new TripsBookingAdapter(getActivity(),mBookingList,2);
                recyclerView.setAdapter(mTourAdapter);
                mIncomingAdapter = new TripsBookingAdapter(getActivity(),mIncomingList,1);
                incomingRecyclerView.setAdapter(mIncomingAdapter);
                pd.dismiss();
                checkIfHasItems();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;

    }

    private void checkIfHasItems()
    {
        if (mBookingList.size()==0)
        {
            mNoBookings.setText("No bookings done!");
        }
        else
        {
            mNoBookings.setText("");
        }

        if (mIncomingList.size()==0)
        {
            mNoGuests.setText("No incoming guests!");
        }
        else
        {
            mNoGuests.setText("");
        }
    }

}

