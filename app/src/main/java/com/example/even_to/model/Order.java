package com.example.even_to.model;

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

    public void setImageLogo(String imageLogo) {
        this.imageLogo = imageLogo;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getImageLogo() {
        return imageLogo;
    }

    public String getLink() {
        return link;
    }

    public String getCity() {
        return city;
    }

     public String getDocumentId() {
        return documentId;
    }

    public String getUserId() {
        return userId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

}
