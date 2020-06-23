package com.example.even_to.utils;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.even_to.R;
import com.google.firebase.firestore.model.Values;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubCategoriesArray extends Application {
    List<String> foodSubCategory = new ArrayList<>();
    List<String> drinksSubCategory = new ArrayList<>();
    List<String> bakerySubCategory = new ArrayList<>();
    List<String> giftSubCategory = new ArrayList<>();
    List<String> decorSubCategory = new ArrayList<>();
    List<String> photographySubCategory = new ArrayList<>();


    public SubCategoriesArray(Context context) {
        foodSubCategory = Arrays.asList(context.getResources().getStringArray(R.array.food_sub_categories));
        drinksSubCategory = Arrays.asList(context.getResources().getStringArray(R.array.drinks_sub_categories));
        bakerySubCategory  = Arrays.asList(context.getResources().getStringArray(R.array.bakery_sub_categories));
        giftSubCategory  = Arrays.asList(context.getResources().getStringArray(R.array.gifts_sub_categories));
        decorSubCategory  = Arrays.asList(context.getResources().getStringArray(R.array.decor_sub_categories));
        photographySubCategory  = Arrays.asList(context.getResources().getStringArray(R.array.photography_sub_categories));
    }


}