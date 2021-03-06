package com.example.worldapp.Core;

import com.example.worldapp.Helpers.FirebaseHelper;
import com.example.worldapp.Helpers.TourHelper;
import com.example.worldapp.Models.GuidedToursModel;
import com.example.worldapp.Models.HomeDetailsModel;
import com.example.worldapp.Models.ParkingModel;
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
    private ArrayList<HomeDetailsModel> mListedHomes;
    private ArrayList<BookingManager> mBooking;
    private ArrayList<ParkingModel> mListedParkings;

    public static UserCore Instance()
    {
        if (mUserCore == null)
        {
            mUserCore = new UserCore();
        }
        return mUserCore;
    }

    public UserCore()
    {
        User = new UserDetailsModel();
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

    public ArrayList<HomeDetailsModel> getmListedHomes() {
        return mListedHomes;
    }

    public void setmListedHomes(ArrayList<HomeDetailsModel> mListedHomes) {
        this.mListedHomes = mListedHomes;
    }

    public ArrayList<ParkingModel> getmListedParkings() {
        return mListedParkings;
    }

    public void setmListedParkings(ArrayList<ParkingModel> mListedParkings) {
        this.mListedParkings = mListedParkings;
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
        //FirebaseHelper.Instance().SyncUserData(User.getUserId());
    }

    public boolean isLoggedIn() {
        return IsLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        IsLoggedIn = loggedIn;
    }

    public void setmFirebaseUser(FirebaseUser mUser) {
        this.FirebaseUser = mUser;
    }

    public ArrayList<BookingManager> getmBooking() {
        return mBooking;
    }

    public void setmBooking(ArrayList<BookingManager> mBooking) {
        this.mBooking = mBooking;
    }
}
