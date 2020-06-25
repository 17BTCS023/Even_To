package com.example.even_to.model;

import com.example.even_to.R;

public class Order {

    public Order(){

    }

    public static final String KEY_NAME = "name";
    public static final String KEY_SERVICE_TYPE = "type";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_EXPERIENCE = "experience";
    public static final String KEY_PHONE_NO = "phoneNumber";
    public static final String KEY_LOGO = "imageLogo";
    public static final String KEY_WEBSITE_URL = "link";
    public static final String KEY_NUMBER_RATINGS = "numRatings";
    public static final String KEY_AVG_RATING = "avgRating";
    public static final String KEY_CITY = "city";
    public static final String KEY_USER_ID = "userId";
    public static final String KEY_SERVICE_DOCUMENT_ID = "serviceDocumentId";
    public static final String KEY_SERVICE_PROVIDER_ID = "serviceProviderId";
    public static final String KEY_CAPACITY = "capacity";

    private String name;
    private String type;
    private String category;

    private String description;

    private String experience;
    private String imageLogo;
    private String link;
    private String city;
    private String capacity;
    private double avgRating;
    public String documentId, userId, serviceProviderUserId;
    private String phoneNumber;
    int numRatings;
    public  Order(String userId, String documentId, String category,
                  String imageLogo , double avgRating, String capacity, String city,
                  String description, String phoneNumber, String link, String id, String name, String experience,
                  int numRatings, String type){
        this.userId = userId;
        this.documentId = documentId;
        this.category = category;
        this.imageLogo = imageLogo;
        this.avgRating = avgRating;
        this.capacity = capacity;
        this.city = city;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.link = link;
        this.serviceProviderUserId = id;
        this.name = name;
        this.experience = experience;
        this.numRatings = numRatings;
        this.type = type;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public void setImageLogo(String imageLogo) {
        this.imageLogo = imageLogo;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setServiceProviderUserId(String serviceProviderUserId) {
        this.serviceProviderUserId = serviceProviderUserId;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setNumRatings(int numRatings) {
        this.numRatings = numRatings;
    }


    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getExperience() {
        return experience;
    }

    public String getImageLogo() {
        return imageLogo;
    }

    public String getLink() {
        return link;
    }

    public String getCity() {
        return city;
    }

    public String getCapacity() {
        return capacity;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public String getDocumentId() {
        return documentId;
    }

    public String getUserId() {
        return userId;
    }

    public String getServiceProviderUserId() {
        return serviceProviderUserId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getNumRatings() {
        return numRatings;
    }
}
