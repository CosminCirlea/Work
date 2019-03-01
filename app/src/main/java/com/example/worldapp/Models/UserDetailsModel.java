package com.example.worldapp.Models;

public class UserDetailsModel {
    private String UserId;
    private String Firstname;
    private String Name;
    private String Email;
    private Double AccountBalance;
    private String ImageUri;
    private String PhoneNumber;

    public UserDetailsModel() {
    }

    public UserDetailsModel(String id, String firstname, String name, String email, Double moneyAmount, String imageUri, String phoneNumber) {
        UserId = id;
        Firstname = firstname;
        Name = name;
        Email = email;
        AccountBalance = moneyAmount;
        ImageUri = imageUri;
        PhoneNumber = phoneNumber;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Double getAccountBalance() {
        return AccountBalance;
    }

    public void setAccountBalance(Double accountBalance) {
        AccountBalance = accountBalance;
    }

    public String getImageUri() {
        return ImageUri;
    }

    public void setImageUri(String imageUri) {
        ImageUri = imageUri;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
