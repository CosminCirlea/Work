package com.example.worldapp;

public class TourCore {
    private static TourCore mTourCore;
    private String TourTitle;
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


    public static TourCore Instance()
    {
        if (mTourCore == null)
        {
            mTourCore = new TourCore();
        }
        return mTourCore;
    }

    public String getTourTitle() {
        return TourTitle;
    }

    public void setTourTitle(String tourTitle) {
        TourTitle = tourTitle;
    }

    public String getTourDescription() {
        return TourDescription;
    }

    public void setTourDescription(String tourDescription) {
        TourDescription = tourDescription;
    }

    public String getTourLandmarks() {
        return TourLandmarks;
    }

    public void setTourLandmarks(String tourLandmarks) {
        TourLandmarks = tourLandmarks;
    }

    public String getTourImageUrl() {
        return TourImageUrl;
    }

    public void setTourImageUrl(String tourImageUrl) {
        TourImageUrl = tourImageUrl;
    }

    public String getTourCountry() {
        return TourCountry;
    }

    public void setTourCountry(String tourCountry) {
        TourCountry = tourCountry;
    }

    public String getTourRegion() {
        return TourRegion;
    }

    public void setTourRegion(String tourRegion) {
        TourRegion = tourRegion;
    }

    public String getTourCity() {
        return TourCity;
    }

    public void setTourCity(String tourCity) {
        TourCity = tourCity;
    }

    public String getTourType() {
        return TourType;
    }

    public void setTourType(String tourType) {
        TourType = tourType;
    }

    public double getTourPrice() {
        return TourPrice;
    }

    public void setTourPrice(double tourPrice) {
        TourPrice = tourPrice;
    }

    public String getTourDuration() {
        return TourDuration;
    }

    public void setTourDuration(String tourDuration) {
        TourDuration = tourDuration;
    }

    public int getTourMaxParticipants() {
        return TourMaxParticipants;
    }

    public void setTourMaxParticipants(int tourMaxParticipants) {
        TourMaxParticipants = tourMaxParticipants;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getTourId() {
        return TourId;
    }

    public void setTourId(String tourId) {
        TourId = tourId;
    }

}
