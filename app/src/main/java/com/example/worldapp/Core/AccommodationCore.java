package com.example.worldapp.Core;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class AccommodationCore {
    private static AccommodationCore mAccommodationCore;
    private String AnnouncementTitle;
    private String City;
    private String Guests;
    private String Region="";
    private String Country;
    private String ListingType;
    private String OwnerType;
    private double PricePerNight;
    private String AddressLine;
    private String ZipCode;
    private int RoomsToUse;
    private double BedsToUse;
    private int BathroomsToUse;
    private String Amenities;
    private String HomeId;
    private String UserId;
    private String[] mBookedDays;
    private ArrayList<String> mImagesUrls;
    private Double mLocationLongitude;
    private Double mLocationLatitude;
    private int mBookedNumber =0;
    private int mGrades =0;
    private double mRating = 0;
    private String mStartDate;
    private String mEndDate;
    private int mNumberOfNights;
    private String mSchedule;

    public static AccommodationCore Instance()
    {
        if (mAccommodationCore == null)
        {
            mAccommodationCore = new AccommodationCore();
        }
        return mAccommodationCore;
    }

    public String getAnnouncementTitle() {
        return AnnouncementTitle;
    }

    public ArrayList<String> getmImagesUrls() {
        return mImagesUrls;
    }

    public void setmImagesUrls(ArrayList<String> mImagesUrls) {
        this.mImagesUrls = mImagesUrls;
    }

    public void setAnnouncementTitle(String announcementTitle) {
        AnnouncementTitle = announcementTitle;
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
        mNumberOfNights = daysBetween(startDate,endDate) + 1;
        return allDates;
    }

    public int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    public String getGuests() {
        return Guests;
    }

    public void setGuests(String guests) {
        Guests = guests;
    }

    public Double getmLocationLongitude() {
        return mLocationLongitude;
    }

    public void setmLocationLongitude(Double mLocationLongitude) {
        this.mLocationLongitude = mLocationLongitude;
    }

    public String getmSchedule() {
        return mSchedule;
    }

    public void setmSchedule(String mSchedule) {
        this.mSchedule = mSchedule;
    }

    public Double getmLocationLatitude() {
        return mLocationLatitude;
    }

    public void setmLocationLatitude(Double mLocationLatitude) {
        this.mLocationLatitude = mLocationLatitude;
    }

    public int getmBookedNumber() {
        return mBookedNumber;
    }

    public void setmBookedNumber(int mBookedNumber) {
        this.mBookedNumber = mBookedNumber;
    }

    public int getmGrades() {
        return mGrades;
    }

    public void setmGrades(int mGrades) {
        this.mGrades = mGrades;
    }

    public double getmRating() {
        return mRating;
    }

    public void setmRating(double mRating) {
        this.mRating = mRating;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getRegion() {
        return Region;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getListingType() {
        return ListingType;
    }

    public void setListingType(String listingType) {
        ListingType = listingType;
    }

    public String getOwnerType() {
        return OwnerType;
    }

    public void setOwnerType(String ownerType) {
        OwnerType = ownerType;
    }

    public double getPricePerNight() {
        return PricePerNight;
    }

    public void setPricePerNight(double pricePerNight) {
        PricePerNight = pricePerNight;
    }

    public String getAddressLine() {
        return AddressLine;
    }

    public void setAddressLine(String addressLine) {
        AddressLine = addressLine;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String zipCode) {
        ZipCode = zipCode;
    }

    public int getRoomsToUse() {
        return RoomsToUse;
    }

    public void setRoomsToUse(int roomsToUse) {
        RoomsToUse = roomsToUse;
    }

    public double getBedsToUse() {
        return BedsToUse;
    }

    public void setBedsToUse(double bedsToUse) {
        BedsToUse = bedsToUse;
    }

    public int getBathroomsToUse() {
        return BathroomsToUse;
    }

    public void setBathroomsToUse(int bathroomsToUse) {
        BathroomsToUse = bathroomsToUse;
    }

    public String getAmenities() {
        return Amenities;
    }

    public void setAmenities(String amenities) {
        Amenities = amenities;
    }

    public String getHomeId() {
        return HomeId;
    }

    public void setHomeId(String homeId) {
        HomeId = homeId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String[] getmBookedDays() {
        return mBookedDays;
    }

    public void setmBookedDays(String[] mBookedDays) {
        this.mBookedDays = mBookedDays;
    }
}
