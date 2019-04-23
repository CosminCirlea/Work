package com.example.worldapp.Helpers;

import com.example.worldapp.Core.UserCore;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHelper {

    private static FirebaseHelper mFirebaseHelper;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;

    public static FirebaseHelper Instance()
    {
        if(mFirebaseHelper == null)
        {
            mFirebaseHelper = new FirebaseHelper();
        }
        return mFirebaseHelper;
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
