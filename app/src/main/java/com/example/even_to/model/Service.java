package com.example.even_to.model;

/**
 * Service POJO.
 */

public class Service {

    private static final String KEY_NAME = "name";
    private static final String KEY_SERVICE_TYPE = "type";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_EXPERIENCE = "experience";
    private static final String KEY_PHONE_NO = "phone";
    private static final String KEY_LOGO = "imageLogo";
    private static final String KEY_WEBSITE_URL = "link";
    public static final String KEY_NUMBER_RATINGS = "numRatings";
    public static final String KEY_AVG_RATING = "avgRating";

    private String name;
    private String type;
    private String category;
    private String description;
    private String experience;
    private String imageLogo;
    private String link;


    private String imageUri;
    private double mAvgRating;
    private int mPhoneNumber,numRatings;

    public Service(String name, String type, String category, String description, String experience,
                   String imageLogo, String link, double mAvgRating,
                   int mPhoneNumber, int numRatings)
    {
        this.name = name;
        this.type = type;
        this.category = category;
        this.description = description;
        this.experience = experience;
        this.imageLogo = imageLogo;
        this.link = link;
        this.mAvgRating = mAvgRating;
        this.mPhoneNumber = mPhoneNumber;
        this.numRatings = numRatings;
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

    public double getmAvgRating() {
        return mAvgRating;
    }

    public void setmAvgRating(double mAvgRating) {
        this.mAvgRating = mAvgRating;
    }

    public int getmPhoneNumber() {
        return mPhoneNumber;
    }

    public void setmPhoneNumber(int mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(int numRatings) {
        this.numRatings = numRatings;
    }
}
