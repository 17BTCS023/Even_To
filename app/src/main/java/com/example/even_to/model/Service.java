package com.example.even_to.model;

/**
 * Service POJO.
 */

public class Service {

    public Service(){

    }

    public static final String KEY_NAME = "name";
    public static final String KEY_SERVICE_TYPE = "type";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_EXPERIENCE = "experience";
    public static final String KEY_PHONE_NO = "phone";
    public static final String KEY_LOGO = "imageLogo";
    public static final String KEY_WEBSITE_URL = "link";
    public static final String KEY_NUMBER_RATINGS = "numRatings";
    public static final String KEY_AVG_RATING = "avgRating";
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

    private int phoneNumber,numRatings;


    public Service(String name, String type, String category, String description, String experience,
                   String imageLogo, String link, double avgRating,
                   int phoneNumber, int numRatings, String city, String capacity)
    {
        this.name = name;
        this.type = type;
        this.category = category;
        this.description = description;
        this.experience = experience;
        this.imageLogo = imageLogo;
        this.link = link;
        this.avgRating = avgRating;
        this.phoneNumber = phoneNumber;
        this.numRatings = numRatings;
        this.city = city;
        this.capacity = capacity;

    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getImageLogo() {
        return imageLogo;
    }

    public void setImageLogo(String imageLogo) {
        this.imageLogo = imageLogo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double mAvgRating) {
        this.avgRating = mAvgRating;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(int numRatings) {
        this.numRatings = numRatings;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

}
