package com.example.worldapp.Models;

import java.util.ArrayList;
import java.util.UUID;

public class ParkingModel {
    private String mTitle;
    private String mCountry;
    private String mCity;
    private String mAddress;
    private String mDescription;
    private String mSecurityDetails;
    private String mRestrictions;
    private String mType;
    private int mSpotsNumber;
    private double mPricePerDay;
    private ArrayList<String> mBookedDates;
    private String mParkingID;
    private String mOwnerID;
    private Double mLongitude;
    private Double mLatitude;
    private String mBookedDate;

    public ParkingModel() {
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

    public String getmBookedDate() {
        return mBookedDate;
    }

    public void setmBookedDate(String mBookedDate) {
        this.mBookedDate = mBookedDate;
    }

    public String getmRestrictions() {
        return mRestrictions;
    }

    public void setmRestrictions(String mRestrictions) {
        this.mRestrictions = mRestrictions;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public int getmSpotsNumber() {
        return mSpotsNumber;
    }

    public void setmSpotsNumber(int mSpotsNumber) {
        this.mSpotsNumber = mSpotsNumber;
    }

    public double getmPricePerDay() {
        return mPricePerDay;
    }

    public ArrayList<String> getmBookedDates() {
        return mBookedDates;
    }

    public void setmBookedDates(ArrayList<String> mBookedDates) {
        this.mBookedDates = mBookedDates;
    }

    public void setmPricePerDay(int mPricePerDay) {
        this.mPricePerDay = mPricePerDay;
    }

    /*public double getmPricePerHour() {
        return mPricePerHour;
    }*/

    public String getmCountry() {
        return mCountry;
    }

    public void setmCountry(String mCountry) {
        this.mCountry = mCountry;
    }

    public String getmCity() {
        return mCity;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }


    public ArrayList<String> getmBookedDays() {
        return mBookedDates;
    }

    public void setmBookedDays(ArrayList<String> mBookedDays) {
        this.mBookedDates = mBookedDays;
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
