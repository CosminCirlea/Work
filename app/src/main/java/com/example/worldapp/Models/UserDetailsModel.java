package com.example.worldapp.Models;

import com.example.worldapp.Core.BookingManager;
import com.example.worldapp.Core.UserCore;
import com.example.worldapp.Helpers.FirebaseHelper;

import java.util.ArrayList;

public class UserDetailsModel {
    private String UserId;
    private String Firstname;
    private String Name;
    private String Email;
    private Double AccountBalance;
    private String ImageUri;
    private String PhoneNumber;
    private ArrayList<GuidedToursModel> mFavoriteTours;
    private ArrayList<GuidedToursModel> mListedTours;
    private ArrayList<BookingManager> mBooking;

    public UserDetailsModel() {
    }

    public UserDetailsModel(String id, String firstname, String name, String email, Double moneyAmount, String imageUri, String phoneNumber) {
        UserId = id;
        Firstname = firstname;
        Name = name;
        Email = email;
        AccountBalance = moneyAmount;
        ImageUri = imageUri;
        PhoneNumber = phoneNumber;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
        SyncData();
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

    private void SyncData()
    {
        if(UserCore.Instance().isLoggedIn())
        {
            FirebaseHelper.Instance().SyncUserData(getUserId());
        }
    }

    public ArrayList<BookingManager> getmBooking() {
        return mBooking;
    }

    public void setmBooking(ArrayList<BookingManager> mBooking) {
        this.mBooking = mBooking;
    }
}
