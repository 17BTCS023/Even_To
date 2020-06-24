package com.example.even_to.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.even_to.LoginAndSignup.LoginActivity;
import com.example.even_to.MainHomeScreen;

public class SharedPref {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    // name of the SP file
    private String FileName = "sharedPrefFile";
    private String Remember;
    public static final String KEY_PROFILE_UPDATED = "profileUpdated";


    public SharedPref(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(FileName, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setProfileDefault(){
        editor.putBoolean(KEY_PROFILE_UPDATED, false);
        editor.apply();
    }
    public void setProfileStatus(){
        editor.putBoolean(KEY_PROFILE_UPDATED, true);
        editor.apply();
    }
    public boolean getProfileStatus() {
        return sharedPreferences.getBoolean(KEY_PROFILE_UPDATED, false);
    }

    // If a user is opening application for second time, after logging In in first time
    public void setRememberSecond() {
        editor.putString("Remember", "true");
        editor.apply();
    }

    // If the user logs in freshly
    public void setRememberFirst() {
        Remember = sharedPreferences.getString("Remember", "");
        if (Remember == "false") {
            // If the user has not logged in before move to LogIn Activity
            Intent intent = new Intent(context, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);

        }
    }

    /*** use this method during onClick event of Logout button *****/
    public void setDefault() {
        editor.putString("Remember", "false");
        editor.apply();
    }

    public String getRemember() {
        return sharedPreferences.getString("Remember", "dontknow");
    }


}
