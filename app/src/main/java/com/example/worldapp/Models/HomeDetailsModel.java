package com.example.worldapp.Models;

import java.util.ArrayList;

public class HomeDetailsModel {
    private String AnnouncementTitle;
    private String City;
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


}
