package com.example.even_to.model;

import com.example.even_to.R;

public class Order {

    public  Order(){
        // empty constructor needed
    }

    private static final String KEY_USER_ID = "userId";
    private static final String KEY_SERVICE_ID = "serviceId";

    String userId, serviceId;

    public static int[] ServiceProviderImages= {
            R.drawable.service,
            R.drawable.service,
            R.drawable.service};

    public static String [] ServiceProviderName={
        "Anusri",
        "Shweta",
        "Manisha"
    };

    public static String [] ServiceProviderCategory={
            "Photography",
            "Photography",
            "Photography"
    };
    public static String [] ServiceProviderDescription={
            String.valueOf(R.string.service_description),
            "The contents within a card should follow their own accessibility guidelines, such as images having content descriptions set on them.",
            "The contents within a card should follow their own accessibility guidelines, such as images having content descriptions set on them."
    };


    public Order(String userId, String serviceId) {
        this.userId = userId;
        this.serviceId = serviceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
