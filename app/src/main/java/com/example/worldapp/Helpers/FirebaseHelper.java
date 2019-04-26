package com.example.worldapp.Helpers;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.worldapp.Activities.ListAllToursActivity;
import com.example.worldapp.Adapters.MyToursListingsAdapter;
import com.example.worldapp.Core.UserCore;
import com.example.worldapp.Models.GuidedToursModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseHelper {

    private static FirebaseHelper mFirebaseHelper;
    private DatabaseReference mToursDatabaseReference;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;

    public FirebaseHelper()
    {
        mToursDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Tours");
    }

    public static FirebaseHelper Instance()
    {
        if(mFirebaseHelper == null)
        {
            mFirebaseHelper = new FirebaseHelper();
        }
        return mFirebaseHelper;
    }

    //todo not working properly
    public ArrayList<GuidedToursModel> GetAllTours(final ArrayList<GuidedToursModel> tours)
    {
        mToursDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    GuidedToursModel mGuidedTour = dataSnapshot1.getValue(GuidedToursModel.class);
                    tours.add(mGuidedTour);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return tours;
    }

    public void GetMyTours(final String userId)
    {
        mToursDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<GuidedToursModel> tours = new ArrayList<>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    GuidedToursModel mGuidedTour = dataSnapshot1.getValue(GuidedToursModel.class);
                    if (mGuidedTour.getmUserId().contains(userId))
                    {
                        tours.add(mGuidedTour);
                    }
                }
                UserCore.Instance().setmListedTours(tours);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    public void SyncUserData(String userId)
    {
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference("users");
        mDatabaseReference.child(userId).setValue(UserCore.Instance().User);
    }

    public void SyncUserData()
    {
        if(UserCore.Instance().isLoggedIn())
        {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabaseReference = mDatabase.getReference("users");
            mDatabaseReference.child(UserCore.Instance().User.getUserId()).setValue(UserCore.Instance().User);
        }
    }
}
