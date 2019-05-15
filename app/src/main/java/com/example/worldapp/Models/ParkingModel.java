package com.example.worldapp.Models;

import java.util.UUID;

public class ParkingModel {
    private String mTitle;
    private String mDescription;
    private String mSecurityDetails;
    private String mRestrictions;
    private int mType;
    private int mSpotsNumber;
    private int mPricePerDay;
    private double mPricePerHour;
    private String[] mBookedDays;
    private UUID mParkingID;
    private UUID mOwnerID;
    private Double mLongitude;
    private Double mLatitude;

    public ParkingModel(String mTitle, String mDescription, String mSecurityDetails, String mRestrictions, int mType, int mSpotsNumber, int mPricePerDay, double mPricePerHour, String[] mBookedDays, UUID mParkingID, UUID mOwnerID, Double mLongitude, Double mLatitude) {
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mSecurityDetails = mSecurityDetails;
        this.mRestrictions = mRestrictions;
        this.mType = mType;
        this.mSpotsNumber = mSpotsNumber;
        this.mPricePerDay = mPricePerDay;
        this.mPricePerHour = mPricePerHour;
        this.mBookedDays = mBookedDays;
        this.mParkingID = mParkingID;
        this.mOwnerID = mOwnerID;
        this.mLongitude = mLongitude;
        this.mLatitude = mLatitude;
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

    public int getmPricePerDay() {
        return mPricePerDay;
    }

    public void setmPricePerDay(int mPricePerDay) {
        this.mPricePerDay = mPricePerDay;
    }

    public double getmPricePerHour() {
        return mPricePerHour;
    }

    public void setmPricePerHour(double mPricePerHour) {
        this.mPricePerHour = mPricePerHour;
    }

    public String[] getmBookedDays() {
        return mBookedDays;
    }

    public void setmBookedDays(String[] mBookedDays) {
        this.mBookedDays = mBookedDays;
    }

    public UUID getmParkingID() {
        return mParkingID;
    }

    public void setmParkingID(UUID mParkingID) {
        this.mParkingID = mParkingID;
    }

    public UUID getmOwnerID() {
        return mOwnerID;
    }

    public void setmOwnerID(UUID mOwnerID) {
        this.mOwnerID = mOwnerID;
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
