package com.example.worldapp.Helpers;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.worldapp.Activities.ListAllToursActivity;
import com.example.worldapp.Adapters.MyToursListingsAdapter;
import com.example.worldapp.Core.UserCore;
import com.example.worldapp.Models.GuidedToursModel;
import com.example.worldapp.Models.UserDetailsModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class FirebaseHelper {

    private static FirebaseHelper mFirebaseHelper;
    public static DatabaseReference mToursDatabaseReference;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference mUserDatabase;
    private UserDetailsModel mUser, mAuxUser;
    static ArrayList<String> mExistingBookedDatesTours = new ArrayList<>();

    public FirebaseHelper()
    {
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("users");
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

    public static boolean checkTourDates(String tourID, final String date)
    {
        final boolean[] isDateAvailable = {true};
        mFirebaseHelper.mToursDatabaseReference.child(tourID).child("mBookedDates").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean isDateAvailableAux = true;
                for (DataSnapshot data : dataSnapshot.getChildren())
                {
                    String existingDate = data.toString();
                    if (date.equalsIgnoreCase(existingDate))
                    {
                        isDateAvailableAux =false;
                    }
                }
                isDateAvailable[0] = isDateAvailableAux;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return isDateAvailable[0];
    }

    public static GuidedToursModel getTourById(String tourID)
    {
        final GuidedToursModel[] tour = {new GuidedToursModel()};
        mToursDatabaseReference.child(tourID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GuidedToursModel mTour = dataSnapshot.getValue(GuidedToursModel.class);
                tour[0] = mTour;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return tour[0];
    }

    public static void updateTourBookedDates(String tourID, String bookDate)
    {
        GuidedToursModel tour = getTourById(tourID);
        if (tour.getmBookedDates()!=null)
        {
            mExistingBookedDatesTours = tour.getmBookedDates();
        }
        mExistingBookedDatesTours.add(bookDate);
        HashMap<String, Object> map = new HashMap<>();
        map.put("mBookedDates", mExistingBookedDatesTours);
        mToursDatabaseReference = FirebaseDatabase.getInstance().getReference("Tours").child(tourID);
        mToursDatabaseReference.updateChildren(map);
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
