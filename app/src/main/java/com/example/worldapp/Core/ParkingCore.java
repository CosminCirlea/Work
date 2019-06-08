package com.example.worldapp.Core;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

public class ParkingCore {
    private static ParkingCore mParkingCore;
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
    private ArrayList<String> mBookedDays;
    private String mParkingID;
    private String mOwnerID;
    private Double mLongitude;
    private Double mLatitude;
    private String mDate;
    private String mStartDate;
    private String mEndDate;
    private int mNumberOfDays;

    public static ParkingCore Instance()
    {
        if (mParkingCore == null)
        {
            mParkingCore = new ParkingCore();
        }
        return mParkingCore;
    }

    public List<Date> getBookedDates(Date startDate, Date endDate)
    {
        List<Date> allDates = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);

        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(endDate);

        while (calendar.before(endCalendar)) {
            Date result = calendar.getTime();
            allDates.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        mNumberOfDays = daysBetween(startDate,endDate)+1;
        return allDates;
    }

    public int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    public String getmStartDate() {
        return mStartDate;
    }

    public void setmStartDate(String mStartDate) {
        this.mStartDate = mStartDate;
    }

    public String getmEndDate() {
        return mEndDate;
    }

    public void setmEndDate(String mEndDate) {
        this.mEndDate = mEndDate;
    }

    public double getmPricePerDay() {
        return mPricePerDay;
    }

    public int getmNumberOfDays() {
        return mNumberOfDays;
    }

    public void setmNumberOfDays(int mNumberOfDays) {
        this.mNumberOfDays = mNumberOfDays;
    }

    public void setmPricePerDay(double mPricePerDay) {
        this.mPricePerDay = mPricePerDay;
    }

    public ArrayList<String> getmBookedDays() {
        return mBookedDays;
    }

    public void setmBookedDays(ArrayList<String>  mBookedDays) {
        this.mBookedDays = mBookedDays;
    }

    public String getmParkingID() {
        return mParkingID;
    }

    public void setmParkingID(String mParkingID) {
        this.mParkingID = mParkingID;
    }

    public String getmCountry() {
        return mCountry;
    }

    public void setmCountry(String mCountry) {
        this.mCountry = mCountry;
    }

    public String getmBookedDate() {
        return mStartDate;
    }

    public void setmBookedDate(String mBookedDate) {
        this.mStartDate = mBookedDate;
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

    public String getmOwnerID() {
        return mOwnerID;
    }

    public void setmOwnerID(String mOwnerID) {
        this.mOwnerID = mOwnerID;
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

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
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

    /*public double getmPricePerHour() {
        return mPricePerHour;
    }

    public void setmPricePerHour(double mPricePerHour) {
        this.mPricePerHour = mPricePerHour;
    }*/

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
