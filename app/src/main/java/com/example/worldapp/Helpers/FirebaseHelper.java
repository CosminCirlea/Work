package com.example.worldapp.Helpers;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.worldapp.Activities.ListAllToursActivity;
import com.example.worldapp.Adapters.MyToursListingsAdapter;
import com.example.worldapp.Core.UserCore;
import com.example.worldapp.Models.GuidedToursModel;
import com.example.worldapp.Models.HomeDetailsModel;
import com.example.worldapp.Models.ParkingModel;
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
    public static DatabaseReference mOfflineTestingDatabase = FirebaseDatabase.getInstance().getReference().child("OfflineTesting");
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;
    private UserDetailsModel mUser, mAuxUser;
    public static StorageReference mAccommodationStorageReference = FirebaseStorage.getInstance().getReference("AccommodationPictures");
    static ArrayList<String> mExistingBookedDatesTours = new ArrayList<>();
    static ArrayList<String> mExistingBookedDatesAccommodation = new ArrayList<>();
    static ArrayList<String> mExistingBookedDatesParkings = new ArrayList<>();
    public static GuidedToursModel mCurrentTour ;
    public static HomeDetailsModel mCurrentAccommodation ;
    public static ParkingModel mCurrentParking;
    public static DatabaseReference mGeneralReference;
    public static DatabaseReference mStatisticsReference = FirebaseDatabase.getInstance().getReference().child("Statistics");


    public FirebaseHelper()
    {
    }

    public static FirebaseHelper Instance()
    {
        if(mFirebaseHelper == null)
        {
            mFirebaseHelper = new FirebaseHelper();
        }
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
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

    public static void getAccommodationById(final String mItemId, final ArrayList<String> bookDates)
    {
        mAccommodationDatabaseReference.child(mItemId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HomeDetailsModel mAccommodation = dataSnapshot.getValue(HomeDetailsModel.class);
                {
                    mCurrentAccommodation = mAccommodation;
                    updateAccommodationBookedDates("Accommodation",mItemId, bookDates);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public static void getParkingByID(final String mItemId, final ArrayList<String> bookDates)
    {
        mParkingsDatabaseReference.child(mItemId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ParkingModel mAccommodation = dataSnapshot.getValue(ParkingModel.class);
                {
                    mCurrentParking = mAccommodation;
                    updateParkingBookedDates("Parkings",mItemId, bookDates);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public static void updateTourBookedDates(String database, String itemID, String bookDate)
    {
        if (mCurrentTour.getmBookedDatesList()!=null)
        {
            mExistingBookedDatesTours = mCurrentTour.getmBookedDatesList();
        }
        mExistingBookedDatesTours.add(bookDate);
        HashMap<String, Object> map = new HashMap<>();
        map.put("mBookedDates", mExistingBookedDatesTours);
        mGeneralReference = FirebaseDatabase.getInstance().getReference(database).child(itemID);
        mGeneralReference.updateChildren(map);

    }

    public static void updateAccommodationBookedDates(String database, String itemID, ArrayList<String> bookDates)
    {
        if (mCurrentAccommodation.getmBookedDates()!=null)
        {
            mExistingBookedDatesAccommodation = mCurrentAccommodation.getmBookedDates();
        }
        mExistingBookedDatesAccommodation.addAll(bookDates);
        HashMap<String, Object> map = new HashMap<>();
        map.put("mBookedDates", mExistingBookedDatesAccommodation);
        mGeneralReference = FirebaseDatabase.getInstance().getReference(database).child(itemID);
        mGeneralReference.updateChildren(map);
    }

    public static void updateParkingBookedDates(String database, String itemID, ArrayList<String> bookDates)
    {
        if (mCurrentParking.getmBookedDates()!=null)
        {
            mExistingBookedDatesAccommodation = mCurrentParking.getmBookedDates();
        }
        mExistingBookedDatesParkings.addAll(bookDates);
        HashMap<String, Object> map = new HashMap<>();
        map.put("mBookedDates", mExistingBookedDatesParkings);
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

    public static void incrementValueInAccommodation(String toUpdateId, String valueToUpdate, final int incrementingValue)
    {
        mAccommodationDatabaseReference.child(toUpdateId).child(valueToUpdate).runTransaction(new Transaction.Handler() {
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

    public static void incrementValueInParkings(String toUpdateId, String valueToUpdate, final int incrementingValue)
    {
        mAccommodationDatabaseReference.child(toUpdateId).child(valueToUpdate).runTransaction(new Transaction.Handler() {
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

    public static void incrementStatistics(String category, String country, final int incrementingValue)
    {
        mStatisticsReference.child(category).child(country).runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
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
