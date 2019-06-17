package com.example.worldapp.Activities;

import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.worldapp.BaseClasses.BaseAppCompat;
import com.example.worldapp.Core.UserCore;
import com.example.worldapp.Fragments.FragmentProfileLoggedIn;
import com.example.worldapp.Helpers.FirebaseHelper;
import com.example.worldapp.Models.UserDetailsModel;
import com.example.worldapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.timessquare.CalendarCellDecorator;
import com.squareup.timessquare.CalendarPickerView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import javax.xml.datatype.Duration;

public class EditProfileActivity extends BaseAppCompat {

    private TextView mEmailTV;
    private EditText mFirstnameET, mNameET, mPhoneNumberET;
    private ImageView mProfileIV;
    private Button mSaveButton;
    private UserDetailsModel mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_edit_profile);
        InitializeViews();
        super.SetToolbarTitle(UserCore.Instance().User.getFirstname());
        setValues();

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseHelper.mUserDatabase.child(mUser.getUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String firstname = mFirstnameET.getText().toString();
                        String name = mNameET.getText().toString();
                        String phoneNumber = mPhoneNumberET.getText().toString();

                        HashMap<String, Object> map = new HashMap<>();
                        map.put("firstname", firstname);
                        map.put("name", name);
                        map.put("phoneNumber", phoneNumber);
                        FirebaseHelper.mUserDatabase.child(mUser.getUserId()).updateChildren(map);
                        startActivity(new Intent(EditProfileActivity.this, ActivityHome.class));
                        Toast.makeText(EditProfileActivity.this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void InitializeViews()
    {
        mEmailTV = findViewById(R.id.tv_profile_email);
        mProfileIV = findViewById(R.id.iv_profile);
        mFirstnameET = findViewById(R.id.et_profile_first_name);
        mNameET = findViewById(R.id.et_profile_name);
        mPhoneNumberET = findViewById(R.id.et_profile_phone_number);
        mSaveButton = findViewById(R.id.btn_profile_save);
    }

    private void setValues()
    {
        mUser = UserCore.Instance().User;
        mEmailTV.setText(mUser.getEmail());
        mNameET.setText(mUser.getName());
        mFirstnameET.setText(mUser.getFirstname());
        mPhoneNumberET.setText(mUser.getPhoneNumber());
        Glide.with(mProfileIV.getContext()).load(mUser.getImageUri())
                .into(mProfileIV);
    }
}
