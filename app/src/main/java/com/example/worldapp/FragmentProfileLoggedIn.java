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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FragmentProfileLoggedIn extends Fragment {
    private static final String TAG = "AccountFragment";
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    public Button BtnSignOut;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_logged_in, container, false);
        BtnSignOut = view.findViewById(R.id.btn_sign_out);

        setupFirebaseListener();
        BtnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Log.d(TAG, "onClick: attempting to sign out the user.");
                FirebaseAuth.getInstance().signOut();
            }

        });
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