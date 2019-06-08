package com.example.worldapp.Core;

import java.util.ArrayList;

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
    private int mToursBookedNumber =0;
    private int mToursGrades =0;
    private double mToursRating = 0;
    private static String mBookedDate;
    private String mSchedule;

    public static TourCore Instance()
    {
        if (mTourCore == null)
        {
            mTourCore = new TourCore();
            mBookedDate = "";
        }
        return mTourCore;
    }

    public int getmToursBookedNumber() {
        return mToursBookedNumber;
    }

    public void setmToursBookedNumber(int mToursBookedNumber) {
        this.mToursBookedNumber = mToursBookedNumber;
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

    public int getmToursGrades() {
        return mToursGrades;
    }

    public void setmToursGrades(int mToursGrades) {
        this.mToursGrades = mToursGrades;
    }

    public double getmToursRating() {
        return mToursRating;
    }

    public String getmSchedule() {
        return mSchedule;
    }

    public void setmSchedule(String mSchedule) {
        this.mSchedule = mSchedule;
    }

    public void setmToursRating(double mToursRating) {
        this.mToursRating = mToursRating;
    }

    public String getmTourLandmarks() {
        return mTourLandmarks;
    }

    public void setmTourLandmarks(String mTourLandmarks) {
        this.mTourLandmarks = mTourLandmarks;
    }

    public String getmBookedDates() {
        return mBookedDate;
    }

    public void setmBookedDates(String mBookedDates) {
        this.mBookedDate = mBookedDates;
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
