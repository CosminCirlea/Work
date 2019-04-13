package com.example.worldapp.Models;

public class GuidedToursModel {
    private String mTourTitle;
    private String TourDescription;
    private String TourLandmarks;
    private String TourImageUrl;
    private String TourCountry;
    private String TourRegion;
    private String TourCity;
    private String TourType;
    private double TourPrice;
    private String TourDuration;
    private int TourMaxParticipants;
    private String UserId, TourId;

    public GuidedToursModel()
    {
    }

    public GuidedToursModel(String mTourTitle, String tourDescription, String tourLandmarks, String tourImageUrl, String tourCountry,
                            String tourRegion, String tourCity, String tourType, double tourPrice, String tourDuration, int tourMaxParticipants, String userId, String tourId) {
        this.mTourTitle = mTourTitle;
        TourDescription = tourDescription;
        TourLandmarks = tourLandmarks;
        TourImageUrl = tourImageUrl;
        TourCountry = tourCountry;
        TourRegion = tourRegion;
        TourCity = tourCity;
        TourType = tourType;
        TourPrice = tourPrice;
        TourDuration = tourDuration;
        TourMaxParticipants = tourMaxParticipants;
        UserId = userId;
        TourId = tourId;
    }

    public GuidedToursModel(String mTourTitle, String mTourDescription, String mTourLandmarks, String mTourImage, double mTourPrice, String mTourDuration , int mTourMaxParticipants) {
        this.mTourTitle = mTourTitle;
        this.TourDescription = mTourDescription;
        this.TourLandmarks = mTourLandmarks;
        this.TourImageUrl = mTourImage;
        this.TourPrice = mTourPrice;
        this.TourDuration = mTourDuration;
        this.TourMaxParticipants = mTourMaxParticipants;
    }

    public String getmTourTitle() {
        return mTourTitle;
    }

    public void setmTourTitle(String mTourTitle) {
        this.mTourTitle = mTourTitle;
    }

    public String getmTourDescription() {
        return TourDescription;
    }

    public void setmTourDescription(String mTourDescription) {
        this.TourDescription = mTourDescription;
    }

    public String getmTourLandmarks() {
        return TourLandmarks;
    }

    public void setmTourLandmarks(String mTourLandmarks) {
        this.TourLandmarks = mTourLandmarks;
    }

    public String getmTourImageUrl() {
        return TourImageUrl;
    }

    public void setmTourImageUrl(String mTourImage) {
        this.TourImageUrl = mTourImage;
    }

    public double getmTourPrice() {
        return TourPrice;
    }

    public void setmTourPrice(double mTourPrice) {
        this.TourPrice = mTourPrice;
    }

    public int getmTourMaxParticipants() {
        return TourMaxParticipants;
    }

    public void setmTourMaxParticipants(int mTourMaxParticipants) {
        this.TourMaxParticipants = mTourMaxParticipants;
    }

    public String getmTourDuration() {
        return TourDuration;
    }

    public void setmTourDuration(String mTourDuration) {
        this.TourDuration = mTourDuration;
    }

    public String getmTourId() {
        return TourId;
    }

    public void setmTourId(String mTourId) {
        this.TourId = mTourId;
    }

    public String getmUserId() {
        return UserId;
    }

    public void setmUserId(String mUserId) {
        this.UserId = mUserId;
    }

    public String getmTourCountry() {
        return TourCountry;
    }

    public void setmTourCountry(String mTourCountry) {
        this.TourCountry = mTourCountry;
    }

    public String getmTourRegion() {
        return TourRegion;
    }

    public void setmTourRegion(String mTourRegion) {
        this.TourRegion = mTourRegion;
    }

    public String getmTourCity() {
        return TourCity;
    }

    public void setmTourCity(String mTourCity) {
        this.TourCity = mTourCity;
    }

    public String getmTourType() {
        return TourType;
    }

    public void setmTourType(String mTourType) {
        this.TourType = mTourType;
    }

}
