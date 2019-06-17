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
        try {
            mUser = mAuth.getCurrentUser();
            if (mUser != null) {
                ifLoggedIn(mUser);
            } else {
                Intent intent = new Intent(SplashActivity.this, ActivityHome.class);
                startActivity(intent);
                finish();
            }
        } catch (Exception e) {
        }
    }

    private void ifLoggedIn(final FirebaseUser user) {

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDetailsModel myUser = dataSnapshot.getValue(UserDetailsModel.class);
                UserCore.Instance().setmUser(myUser);
                UserCore.Instance().setmFirebaseUser(user);
                UserCore.Instance().setUserId(user.getUid());
                UserCore.Instance().setName(user.getDisplayName());
                UserCore.Instance().setLoggedIn(true);
                Intent intent = new Intent(SplashActivity.this, ActivityHome.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }
}