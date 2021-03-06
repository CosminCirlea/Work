package com.example.worldapp.Models;

import java.util.ArrayList;

public class HomeDetailsModel {
    private String AnnouncementTitle;
    private String City;
    private String Region="";
    private String Country;
    private String Guests;
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
    private ArrayList<String> mBookedDates;
    private ArrayList<String> mImagesUrls;
    private Double mLocationLongitude;
    private Double mLocationLatitude;
    private int mBookedNumber =0;
    private int mGrades =0;
    private double mRating = 0;

    public HomeDetailsModel() {
    }


    public ArrayList<String> getmImagesUrls() {
        return mImagesUrls;
    }

    public void setmImagesUrls(ArrayList<String> mImagesUrls) {
        this.mImagesUrls = mImagesUrls;
    }

    public Double getmLocationLongitude() {
        return mLocationLongitude;
    }

    public void setmLocationLongitude(Double mLocationLongitude) {
        this.mLocationLongitude = mLocationLongitude;
    }

    public Double getmLocationLatitude() {
        return mLocationLatitude;
    }

    public void setmLocationLatitude(Double mLocationLatitude) {
        this.mLocationLatitude = mLocationLatitude;
    }

    public String getGuests() {
        return Guests;
    }

    public void setGuests(String guests) {
        Guests = guests;
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

    public String getAnnouncementTitle() {
        return AnnouncementTitle;
    }

    public void setAnnouncementTitle(String announcementTitle) {
        AnnouncementTitle = announcementTitle;
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

    public int getRoomsToUse() {
        return RoomsToUse;
    }

    public void setRoomsToUse(int roomsToUse) {
        this.RoomsToUse = roomsToUse;
    }

    public double getBedsToUse() {
        return BedsToUse;
    }

    public void setBedsToUse(double bedsToUse) {
        this.BedsToUse = bedsToUse;
    }

    public ArrayList<String> getmBookedDates() {
        return mBookedDates;
    }

    public void setmBookedDates(ArrayList<String> mBookedDates) {
        this.mBookedDates = mBookedDates;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String zipCode) {
        ZipCode = zipCode;
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

}
