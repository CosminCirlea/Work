package com.example.worldapp.Helpers;


import com.example.worldapp.Core.UserCore;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TourHelper {
    private static TourHelper mTourHelper;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;

    public static TourHelper Instance()
    {
        if (mTourHelper == null)
        {
            mTourHelper = new TourHelper();
        }
        return mTourHelper;
    }

    private void LoadAllTours()
    {
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference("tb_userData");
        mDatabaseReference.child(userId).setValue(UserCore.Instance().User);
    }
}
