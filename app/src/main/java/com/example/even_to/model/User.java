package com.example.even_to.model;

public class User {

    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE_NO = "phone";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_ABOUT = "about";
    private static final String KEY_OCCUPATION = "occupation";
    private static final String KEY_PROFILE_PIC = "photo";

    String name, email, userId, phoneNumber, about,occupation,location;

    public User(String name, String email, String userId, String phoneNumber, String about, String occupation, String location) {
        this.name = name;
        this.email = email;
        this.about = about;
        this.phoneNumber = phoneNumber;
        this.occupation = occupation;
        this.location  =location;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
