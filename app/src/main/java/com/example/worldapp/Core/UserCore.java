package com.example.worldapp.Core;

import com.example.worldapp.Models.GuidedToursModel;
import com.example.worldapp.Models.UserDetailsModel;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class UserCore {
    private static UserCore mUserCore;
    private FirebaseUser FirebaseUser;
    public UserDetailsModel User;
    private String UserId;
    private String Firstname;
    private String Name;
    private String Email;
    private Double AccountBalance;
    private String ImageUri;
    private String PhoneNumber;
    private boolean IsLoggedIn;
    private ArrayList<GuidedToursModel> mFavoriteTours;
    private ArrayList<GuidedToursModel> mListedTours;

    public static UserCore Instance()
    {
        if (mUserCore == null)
        {
            mUserCore = new UserCore();
        }
        return mUserCore;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Double getAccountBalance() {
        return AccountBalance;
    }

    public void setAccountBalance(Double accountBalance) {
        AccountBalance = accountBalance;
    }

    public String getImageUri() {
        return ImageUri;
    }

    public void setImageUri(String imageUri) {
        ImageUri = imageUri;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public ArrayList<GuidedToursModel> getmFavoriteTours() {
        return mFavoriteTours;
    }

    public void setmFavoriteTours(ArrayList<GuidedToursModel> mFavoriteTours) {
        this.mFavoriteTours = mFavoriteTours;
    }

    public ArrayList<GuidedToursModel> getmListedTours() {
        return mListedTours;
    }

    public void setmListedTours(ArrayList<GuidedToursModel> mListedTours) {
        this.mListedTours = mListedTours;
    }

    public FirebaseUser getmFirebaseUser() {
        return FirebaseUser;
    }

    public UserDetailsModel getmUser() {
        return User;
    }

    public void setmUser(UserDetailsModel mUser) {
        this.User = mUser;
    }

    public boolean isLoggedIn() {
        return IsLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        IsLoggedIn = loggedIn;
    }

    public void setmFirebaseUser(FirebaseUser mUser) {
        this.FirebaseUser = FirebaseUser;
    }

}
