package com.example.even_to.ui_navigation.services.newService;

public class UploadServiceInfo {

    String mName, mType, mCategory, mPhone, mDescription, mExperience, mInfo, mSkills;

    public String imageName;
    public String imageURL;

    public UploadServiceInfo() {
        // empty constructor needed
    }

    public UploadServiceInfo(String name, String url) {
        this.imageName = name;
        this.imageURL = url;
    }



     UploadServiceInfo(String portfolio_name, String url, String user_name, String service_category, String mPhone, String mDescription, String mExperience, String mInfo, String mSkills) {
        if(user_name.trim().equals("")) user_name = "No name";
        this.imageName = portfolio_name;
        this.imageURL = url;
        this.mName = user_name;
        this.mCategory = service_category;
        this.mExperience = mExperience;
        this.mPhone = mPhone;
        this.mDescription = mDescription;
        this.mSkills = mSkills;
        this.mInfo = mInfo;

    }


   public String getImageName() {
        return imageName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getmCategory() {
        return mCategory;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getmName() {
        return mName;
    }

    public String getmInfo() {
        return mInfo;
    }

    public String getmType() {
        return mType;
    }

    public String getmExperience() {
        return mExperience;
    }

    public String getmPhone() {
        return mPhone;
    }

    public String getmSkills() {
        return mSkills;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public void setmCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public void setmExperience(String mExperience) {
        this.mExperience = mExperience;
    }

    public void setmInfo(String mInfo) {
        this.mInfo = mInfo;
    }

    public void setmSkills(String mSkills) {
        this.mSkills = mSkills;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
