package com.example.worldapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentProfile  extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    public void SomeFunction(){

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
