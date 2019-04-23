package com.example.worldapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.worldapp.Core.UserCore;
import com.example.worldapp.Models.GuidedToursModel;
import com.example.worldapp.Models.UserDetailsModel;
import com.example.worldapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class SplashActivity extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private List<GuidedToursModel> mListedTours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        try{
            mUser = mAuth.getCurrentUser();
            if (mUser != null)
            {
                ifLoggedIn(mUser);
            }
            else
            {
                Intent intent = new Intent(SplashActivity.this, ActivityHome.class);
                startActivity(intent);
                finish();
            }
        }
        catch (Exception e)
        {}
    }

    private void ifLoggedIn(final FirebaseUser user) {
        //final FirebaseUser user = mAuth.getCurrentUser();
        UserCore.Instance().setmFirebaseUser(user);
        UserCore.Instance().User.setUserId(user.getUid());

        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference("users");
        DatabaseReference userData = mDatabaseReference.child(user.getUid());
        userData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!UserCore.Instance().isLoggedIn())
                {
                    UserDetailsModel userData = dataSnapshot.getValue(UserDetailsModel.class);
                    if(userData.getUserId().contains(user.getUid()))
                    {
                        UserCore.Instance().setLoggedIn(true);
                        UserCore.Instance().setmUser(userData);

                        Intent intent = new Intent(SplashActivity.this, ActivityHome.class);
                        startActivity(intent);
                        finish();
                        return;
                    }
                }
                else
                {
                    Intent intent = new Intent(SplashActivity.this, ActivityHome.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }
}