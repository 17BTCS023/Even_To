package com.example.even_to.Category;

public class FoodServiceItem {

    private int mImageResource;
    private String mServiceProviderName;
    private String mServiceProviderDescription;
    private String mServiceProviderSize;
    private String mRatingStar;


    public FoodServiceItem(int mImageResource, String mServiceProviderName, String mServiceProviderDescription, String mServiceProviderSize, String mRatingStar) {
        this.mImageResource = mImageResource;
        this.mServiceProviderName = mServiceProviderName;
        this.mServiceProviderDescription = mServiceProviderDescription;
        this.mServiceProviderSize = mServiceProviderSize;
        this.mRatingStar = mRatingStar;
    }

    public int getmImageResource() {
        return mImageResource;
    }

    public void setmImageResource(int mImageResource) {
        this.mImageResource = mImageResource;
    }

    public String getmServiceProviderName() {
        return mServiceProviderName;
    }

    public void setmServiceProviderName(String mServiceProviderName) {
        this.mServiceProviderName = mServiceProviderName;
    }

    public String getmServiceProviderDescription() {
        return mServiceProviderDescription;
    }

    public void setmServiceProviderDescription(String mServiceProviderDescription) {
        this.mServiceProviderDescription = mServiceProviderDescription;
    }

    public String getmServiceProviderSize() {
        return mServiceProviderSize;
    }

    public void setmServiceProviderSize(String mServiceProviderSize) {
        this.mServiceProviderSize = mServiceProviderSize;
    }

    public String  getmRatingStar() {
        return mRatingStar;
    }

    public void setmRatingStar(String mRatingStar) {
        this.mRatingStar = mRatingStar;
    }
}
