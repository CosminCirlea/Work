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
    private String[] mBookedDays;
    private String[] mImagesUrls;
    private Double mLocationLongitude;
    private Double mLocationLatitude;
    private int mBookedNumber =0;
    private int mGrades =0;
    private double mRating = 0;

    public HomeDetailsModel(String announcementTitle, String city, String region, String country, String listingType, String ownerType, double pricePerNight, int RoomsToUse, double BedsToUse) {
        AnnouncementTitle = announcementTitle;
        City = city;
        Region = region;
        Country = country;
        PricePerNight = pricePerNight;
        this.RoomsToUse = RoomsToUse;
        this.BedsToUse = BedsToUse;
        ListingType = listingType;
        OwnerType = ownerType;
    }

    private static int lastContactId = 0;

    public static ArrayList<HomeDetailsModel> createHomeList(int numHomes) {
        ArrayList<HomeDetailsModel> homes = new ArrayList<HomeDetailsModel>();
        for (int i = 0; i <= numHomes; i++) {
            homes.add(new HomeDetailsModel("Very cool private room, vinteage, in the city center", "Venice", "Veneto","Italy",
                    "Private room", "Private house", 40, ++lastContactId, 2));
        }
        return homes;
    }

    public String[] getmImagesUrls() {
        return mImagesUrls;
    }

    public void setmImagesUrls(String[] mImagesUrls) {
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

    public static int getLastContactId() {
        return lastContactId;
    }

    public static void setLastContactId(int lastContactId) {
        HomeDetailsModel.lastContactId = lastContactId;
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

    public String[] getmBookedDays() {
        return mBookedDays;
    }

    public void setmBookedDays(String[] mBookedDays) {
        this.mBookedDays = mBookedDays;
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
