package com.example.worldapp.Models;

import java.util.ArrayList;

public class BookingManager {
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
    private String mBuyerPhone;
    private String mOwnerPhone;
    private String mAnnouncementTitle;
    private String mAnnouncementId;
    private String mSchedule;
    private ArrayList<String> mSelectedDates;
    private String mStartDate;
    private String mEndDate;
    private int mManagerType;

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

    public String getmStartDate() {
        return mStartDate;
    }

    public String getmSchedule() {
        return mSchedule;
    }

    public void setmSchedule(String mSchedule) {
        this.mSchedule = mSchedule;
    }

    public void setmStartDate(String mStartDate) {
        this.mStartDate = mStartDate;
    }

    public int getmManagerType() {
        return mManagerType;
    }

    public void setmManagerType(int mManagerType) {
        this.mManagerType = mManagerType;
    }

    public String getmEndDate() {
        return mEndDate;
    }

    public void setmEndDate(String mEndDate) {
        this.mEndDate = mEndDate;
    }

    public void setmFee(double mFee) {
        this.mFee = mFee;
    }

    public ArrayList<String> getmSelectedDates() {
        return mSelectedDates;
    }

    public void setmSelectedDates(ArrayList<String> mSelectedDates) {
        this.mSelectedDates = mSelectedDates;
    }

    public double getmTotalPrice() {
        return mTotalPrice;
    }

    public String getmBuyerPhone() {
        return mBuyerPhone;
    }

    public void setmBuyerPhone(String mBuyerPhone) {
        this.mBuyerPhone = mBuyerPhone;
    }

    public String getmOwnerPhone() {
        return mOwnerPhone;
    }

    public void setmOwnerPhone(String mOwnerPhone) {
        this.mOwnerPhone = mOwnerPhone;
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

    public String getmAnnouncementId() {
        return mAnnouncementId;
    }

    public void setmAnnouncementId(String mAnnouncementId) {
        this.mAnnouncementId = mAnnouncementId;
    }
}
