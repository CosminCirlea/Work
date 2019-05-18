package com.example.worldapp.Models;

import com.google.firebase.auth.FirebaseUser;

import java.util.UUID;

public class TourBookingManager {
    private String mBookingId;
    private String mBuyerId;
    private String mOwnerId;
    private String mBookingDates;
    private double mPrice;
    private double mFee;
    private double mTotalPrice;
    private int mStatus;
    private String mBuyerName;
    private String mOwnerName;
    private String mAnnouncementTitle;

    public String getmBookingId() {
        return mBookingId;
    }

    public void setmBookingId(String mBookingId) {
        this.mBookingId = mBookingId;
    }

    public String getmBuyerId() {
        return mBuyerId;
    }

    public void setmBuyerId(String mBuyerId) {
        this.mBuyerId = mBuyerId;
    }

    public String getmOwnerId() {
        return mOwnerId;
    }

    public void setmOwnerId(String mOwnerId) {
        this.mOwnerId = mOwnerId;
    }

    public String getmBookingDates() {
        return mBookingDates;
    }

    public void setmBookingDates(String mBookingDates) {
        this.mBookingDates = mBookingDates;
    }

    public double getmPrice() {
        return mPrice;
    }

    public void setmPrice(double mPrice) {
        this.mPrice = mPrice;
    }

    public double getmFee() {
        return mFee;
    }

    public void setmFee(double mFee) {
        this.mFee = mFee;
    }

    public double getmTotalPrice() {
        return mTotalPrice;
    }

    public void setmTotalPrice(double mTotalPrice) {
        this.mTotalPrice = mTotalPrice;
    }

    public int getmStatus() {
        return mStatus;
    }

    public void setmStatus(int mStatus) {
        this.mStatus = mStatus;
    }

    public String getmBuyerName() {
        return mBuyerName;
    }

    public void setmBuyerName(String mBuyerName) {
        this.mBuyerName = mBuyerName;
    }

    public String getmOwnerName() {
        return mOwnerName;
    }

    public void setmOwnerName(String mOwnerName) {
        this.mOwnerName = mOwnerName;
    }

    public String getmAnnouncementTitle() {
        return mAnnouncementTitle;
    }

    public void setmAnnouncementTitle(String mAnnouncementTitle) {
        this.mAnnouncementTitle = mAnnouncementTitle;
    }
}
