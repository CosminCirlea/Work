package com.example.worldapp.Models;

import java.util.UUID;

public class GuidedToursModel {
    private String mTourTitle;
    private String mTourDescription;
    private String mTourLandmarks;
    private String mTourImageUrl;
    private double mTourPrice;
    private double mTourDuration;
    private int mTourMaxParticipants;
    private UUID mTourId;
    private String mUserId;

    public GuidedToursModel(String mTourTitle, String mTourDescription, String mTourLandmarks, String mTourImage, double mTourPrice, double mTourDuration , int mTourMaxParticipants) {
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

    public double getmTourDuration() {
        return mTourDuration;
    }

    public void setmTourDuration(double mTourDuration) {
        this.mTourDuration = mTourDuration;
    }

    public UUID getmTourId() {
        return mTourId;
    }

    public void setmTourId(UUID mTourId) {
        this.mTourId = mTourId;
    }

    public String getmUserId() {
        return mUserId;
    }

    public void setmUserId(String mUserId) {
        this.mUserId = mUserId;
    }
}
