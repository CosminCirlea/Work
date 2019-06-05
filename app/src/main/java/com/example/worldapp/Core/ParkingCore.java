package com.example.worldapp.Core;

import java.util.UUID;

public class ParkingCore {
    private static ParkingCore mParkingCore;
    private String mTitle;
    private String mDescription;
    private String mSecurityDetails;
    private String mRestrictions;
    private int mType;
    private int mSpotsNumber;
    private double mPricePerDay;
    private double mPricePerHour;
    private String[] mBookedDays;
    private String mParkingID;
    private String mOwnerID;
    private Double mLongitude;
    private Double mLatitude;
    private String mDate;

    public static ParkingCore Instance()
    {
        if (mParkingCore == null)
        {
            mParkingCore = new ParkingCore();
        }
        return mParkingCore;
    }


    public double getmPricePerDay() {
        return mPricePerDay;
    }

    public void setmPricePerDay(double mPricePerDay) {
        this.mPricePerDay = mPricePerDay;
    }

    public String[] getmBookedDays() {
        return mBookedDays;
    }

    public void setmBookedDays(String[] mBookedDays) {
        this.mBookedDays = mBookedDays;
    }

    public String getmParkingID() {
        return mParkingID;
    }

    public void setmParkingID(String mParkingID) {
        this.mParkingID = mParkingID;
    }

    public String getmOwnerID() {
        return mOwnerID;
    }

    public void setmOwnerID(String mOwnerID) {
        this.mOwnerID = mOwnerID;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmSecurityDetails() {
        return mSecurityDetails;
    }

    public void setmSecurityDetails(String mSecurityDetails) {
        this.mSecurityDetails = mSecurityDetails;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmRestrictions() {
        return mRestrictions;
    }

    public void setmRestrictions(String mRestrictions) {
        this.mRestrictions = mRestrictions;
    }

    public int getmType() {
        return mType;
    }

    public void setmType(int mType) {
        this.mType = mType;
    }

    public int getmSpotsNumber() {
        return mSpotsNumber;
    }

    public void setmSpotsNumber(int mSpotsNumber) {
        this.mSpotsNumber = mSpotsNumber;
    }

    public double getmPricePerHour() {
        return mPricePerHour;
    }

    public void setmPricePerHour(double mPricePerHour) {
        this.mPricePerHour = mPricePerHour;
    }

    public Double getmLongitude() {
        return mLongitude;
    }

    public void setmLongitude(Double mLongitude) {
        this.mLongitude = mLongitude;
    }

    public Double getmLatitude() {
        return mLatitude;
    }

    public void setmLatitude(Double mLatitude) {
        this.mLatitude = mLatitude;
    }
}
