package com.example.worldapp.Models;

public class GuidedToursModel {
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

    public GuidedToursModel()
    {
    }

    public GuidedToursModel(String mTourTitle, String tourDescription, String mTourLandmarks, String tourImageUrl, String tourCountry,
                            String tourRegion, String tourCity, String tourType, double tourPrice, String tourDuration, int tourMaxParticipants, String userId, String tourId) {
        this.mTourTitle = mTourTitle;
        mTourDescription = tourDescription;
        this.mTourLandmarks = mTourLandmarks;
        mTourImageUrl = tourImageUrl;
        mTourCountry = tourCountry;
        mTourRegion = tourRegion;
        mTourCity = tourCity;
        mTourType = tourType;
        mTourPrice = tourPrice;
        mTourDuration = tourDuration;
        mTourMaxParticipants = tourMaxParticipants;
        mUserId = userId;
        mTourId = tourId;
    }

    public GuidedToursModel(String mTourTitle, String mTourDescription, String mTourLandmarks, String mTourImage, double mTourPrice, String mTourDuration , int mTourMaxParticipants) {
        this.mTourTitle = mTourTitle;
        this.mTourDescription = mTourDescription;
        this.mTourLandmarks = mTourLandmarks;
        this.mTourImageUrl = mTourImage;
        this.mTourPrice = mTourPrice;
        this.mTourDuration = mTourDuration;
        this.mTourMaxParticipants = mTourMaxParticipants;
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

    public void setmTourImageUrl(String mTourImage) {
        this.mTourImageUrl = mTourImage;
    }

    public double getmTourPrice() {
        return mTourPrice;
    }

    public void setmTourPrice(double mTourPrice) {
        this.mTourPrice = mTourPrice;
    }

    public int getmTourMaxParticipants() {
        return mTourMaxParticipants;
    }

    public void setmTourMaxParticipants(int mTourMaxParticipants) {
        this.mTourMaxParticipants = mTourMaxParticipants;
    }

    public String getmTourDuration() {
        return mTourDuration;
    }

    public void setmTourDuration(String mTourDuration) {
        this.mTourDuration = mTourDuration;
    }

    public String getmTourId() {
        return mTourId;
    }

    public void setmTourId(String mTourId) {
        this.mTourId = mTourId;
    }

    public String getmUserId() {
        return mUserId;
    }

    public void setmUserId(String mUserId) {
        this.mUserId = mUserId;
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

}
