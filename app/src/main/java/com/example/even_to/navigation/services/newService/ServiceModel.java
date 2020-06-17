package com.example.even_to.navigation.services.newService;

public class ServiceModel {


    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE_NO = "phone";
    private static final String KEY_CAPACITY = "capacity";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_EXPERIENCE = "experience";
    private static final String KEY_LINK = "link";

    String name, category, description, experience, link, capacity;
    long phone;


    public ServiceModel(String name, long phone, String capacity, String category, String experience,
                        String link,  String description) {
        this.name = name;
        this.phone = phone;
        this.capacity = capacity;
        this.category = category;
        this.experience = experience;
        this.link = link;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }



    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

}
