package com.example.worldapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.worldapp.Models.UserDetailsModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentProfileLoggedIn extends Fragment {
    private static final String TAG = "AccountFragment";
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    public  Button BtnSignOut, BtnEditProfile, BtnDeleteAccount;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference myRef;
    private String userID;
    private TextView TvFirstName, TvName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_logged_in, container, false);
        TvFirstName = view.findViewById(R.id.tv_profile_first_name);
        TvName = view.findViewById(R.id.tv_profile_family_name);
        BtnSignOut = view.findViewById(R.id.btn_sign_out);
        BtnEditProfile = view.findViewById(R.id.btn_edit_profile);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        mUser = mAuth.getCurrentUser();
        userID = mUser.getUid();
        setupFirebaseListener();

        BtnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Log.d(TAG, "onClick: attempting to sign out the user.");
                FirebaseAuth.getInstance().signOut();
            }

        });
        if (mUser!=null) {
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    showData(dataSnapshot);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        return view;
    }

    private void setupFirebaseListener(){
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    //Log.d(TAG, "onAuthStateChanged: signed_in: " + user.getUid());
                }else{
                    Toast.makeText(getActivity(), "Signed out", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), ActivityLogin.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        };
    }

    private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren()) {
            UserDetailsModel uInfo = new UserDetailsModel();
            uInfo.setName(ds.child(userID).getValue(UserDetailsModel.class).getName());
            uInfo.setEmail(ds.child(userID).getValue(UserDetailsModel.class).getEmail());
            uInfo.setFirstname(ds.child(userID).getValue(UserDetailsModel.class).getFirstname());

            TvName.setText(uInfo.getName());
            TvFirstName.setText(uInfo.getFirstname());
        }
    }

    @Override
    public void onStart()
    {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop()
    {
        super.onStop();
        if (mAuthStateListener != null)
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthStateListener);

    }

    public void SomeFunction() {

        //TODO on profile registration
                                /*String uid = user.getUid();
                                mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                                HashMap<String, String> UserMap = new HashMap<>();
                                UserMap.put("Name", Name);
                                UserMap.put("Firstname", Firstname);
                                UserMap.put("Email", Email);
                                UserMap.put("Id", "2");

                                mDatabase.setValue(UserMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Intent myIntent = new Intent(ActivityRegister.this, ActivityLogin.class);
                                        startActivity(myIntent);
                                    }
                                });*/
    }
}