package com.example.worldapp.Core;

import com.google.android.gms.maps.model.LatLng;

public class TourCore {
    private static TourCore mTourCore;
    private String mTourTitle;
    private String mTourDescription;
    private String mTourLandmarks;
    private String mTourImageUrl;
    private String mTourCountry;
    private String mTourRegion;
    private String mTourCity;
    private String mTourType;
    private double mTourPrice;
    private String mTourDuration;
    private int mTourMaxParticipants;
    private String mUserId, mTourId;
    private double mRating;
    private Double mMeetingPointLongitude;
    private Double mMeetingPointLatitude;
    private String mMeetingLocation;
    private int mmToursBookedNumber;

    public static TourCore Instance()
    {
        if (mTourCore == null)
        {
            mTourCore = new TourCore();
        }
        return mTourCore;
    }

    public int getMmToursBookedNumber() {
        return mmToursBookedNumber;
    }

    public void setMmToursBookedNumber(int mmToursBookedNumber) {
        this.mmToursBookedNumber = mmToursBookedNumber;
    }

    public double getmRating() {
        return mRating;
    }

    public void setmRating(double mRating) {
        this.mRating = mRating;
    }
    public String getmTourTitle() {
        return mTourTitle;
    }

    public void setmTourTitle(String mTourTitle) {
        this.mTourTitle = mTourTitle;
    }

    public String getmTourDescription() {
        return mTourDescription;
    }

    public void setmTourDescription(String mTourDescription) {
        this.mTourDescription = mTourDescription;
    }

    public String getmTourLandmarks() {
        return mTourLandmarks;
    }

    public void setmTourLandmarks(String mTourLandmarks) {
        this.mTourLandmarks = mTourLandmarks;
    }

    public String getmTourImageUrl() {
        return mTourImageUrl;
    }

    public void setmTourImageUrl(String mTourImageUrl) {
        this.mTourImageUrl = mTourImageUrl;
    }

    public String getmTourCountry() {
        return mTourCountry;
    }

    public void setmTourCountry(String mTourCountry) {
        this.mTourCountry = mTourCountry;
    }

    public String getmTourRegion() {
        return mTourRegion;
    }

    public void setmTourRegion(String mTourRegion) {
        this.mTourRegion = mTourRegion;
    }

    public String getmTourCity() {
        return mTourCity;
    }

    public void setmTourCity(String mTourCity) {
        this.mTourCity = mTourCity;
    }

    public String getmTourType() {
        return mTourType;
    }

    public void setmTourType(String mTourType) {
        this.mTourType = mTourType;
    }

    public double getmTourPrice() {
        return mTourPrice;
    }

    public void setmTourPrice(double mTourPrice) {
        this.mTourPrice = mTourPrice;
    }

    public String getmTourDuration() {
        return mTourDuration;
    }

    public void setmTourDuration(String mTourDuration) {
        this.mTourDuration = mTourDuration;
    }

    public int getmTourMaxParticipants() {
        return mTourMaxParticipants;
    }

    public void setmTourMaxParticipants(int mTourMaxParticipants) {
        this.mTourMaxParticipants = mTourMaxParticipants;
    }

    public String getmUserId() {
        return mUserId;
    }

    public void setmUserId(String mUserId) {
        this.mUserId = mUserId;
    }

    public String getmTourId() {
        return mTourId;
    }

    public void setmTourId(String mTourId) {
        this.mTourId = mTourId;
    }


    public Double getmMeetingPointLongitude() {
        return mMeetingPointLongitude;
    }

    public void setmMeetingPointLongitude(Double mMeetingPointLongitude) {
        this.mMeetingPointLongitude = mMeetingPointLongitude;
    }

    public Double getmMeetingPointLatitude() {
        return mMeetingPointLatitude;
    }

    public void setmMeetingPointLatitude(Double mMeetingPointLatitude) {
        this.mMeetingPointLatitude = mMeetingPointLatitude;
    }


    public String getmMeetingLocation() {
        return mMeetingLocation;
    }

    public void setmMeetingLocation(String mMeetingLocation) {
        this.mMeetingLocation = mMeetingLocation;
    }
}
