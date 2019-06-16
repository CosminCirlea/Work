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
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class FirebaseHelper {

    private static FirebaseHelper mFirebaseHelper;
    public static DatabaseReference mToursDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Tours");
    public static DatabaseReference mBookingManagerDatabase = FirebaseDatabase.getInstance().getReference().child("BookingManager");
    public static DatabaseReference mAccommodationDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Accommodation");
    public static DatabaseReference mParkingsDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Parkings");
    public static DatabaseReference mUserDatabase = FirebaseDatabase.getInstance().getReference().child("users");
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;
    private UserDetailsModel mUser, mAuxUser;
    public static StorageReference mAccommodationStorageReference = FirebaseStorage.getInstance().getReference("AccommodationPictures");
    static ArrayList<String> mExistingBookedDatesTours = new ArrayList<>();
    public static GuidedToursModel mCurrentTour ;
    public static DatabaseReference mGeneralReference;

    public FirebaseHelper()
    {
    }

    public static FirebaseHelper Instance()
    {
        if(mFirebaseHelper == null)
        {
            mFirebaseHelper = new FirebaseHelper();
        }
        return mFirebaseHelper;
    }

    public static void getTourById(final String mTourID, final String bookDate)
    {
        mToursDatabaseReference.child(mTourID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GuidedToursModel mGuidedTour = dataSnapshot.getValue(GuidedToursModel.class);
                {
                    mCurrentTour = mGuidedTour;
                    updateTourBookedDates("Tours",mTourID, bookDate);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public static void getAccommodationById(final String mItemId, final String bookDate)
    {
        mToursDatabaseReference.child(mItemId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GuidedToursModel mGuidedTour = dataSnapshot.getValue(GuidedToursModel.class);
                {
                    mCurrentTour = mGuidedTour;
                    updateTourBookedDates("Accommodation",mItemId, bookDate);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public static void updateTourBookedDates(String database, String itemID, String bookDate)
    {
        if (mCurrentTour.getmBookedDates()!=null)
        {
            mExistingBookedDatesTours = mCurrentTour.getmBookedDates();
        }
        mExistingBookedDatesTours.add(bookDate);
        HashMap<String, Object> map = new HashMap<>();
        map.put("mBookedDates", mExistingBookedDatesTours);
        mGeneralReference = FirebaseDatabase.getInstance().getReference(database).child(itemID);
        mGeneralReference.updateChildren(map);
    }

    public static void incrementValueInTour(String toUpdateId, String valueToUpdate, final int incrementingValue)
    {
        mToursDatabaseReference.child(toUpdateId).child(valueToUpdate).runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                if (mutableData == null){
                    mutableData.setValue(0);
                }
                Integer currentValue = mutableData.getValue(Integer.class);
                if (currentValue == null) {
                    mutableData.setValue(1);
                } else {
                    mutableData.setValue(currentValue + incrementingValue);
                }

                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean committed, DataSnapshot dataSnapshot) {
                System.out.println("Transaction completed");
            }
        });
    }
}
