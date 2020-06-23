package com.example.even_to.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.even_to.LoginAndSignup.LoginActivity;

public class SharedPref {

    // creating shared preferences file
    private SharedPreferences sharedPreferences;

    // to edit the shared preferences file
    private SharedPreferences.Editor editor;

    // Context will pass the reference to other class
    private Context context;

    // name of the SP file
    private String FileName = "sharedPrefFile";

    // A boolean variable: true, if key present; false, if key not present
    private String Remember;

    // Constructor that will save memory for file during runtime


    public SharedPref(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(FileName, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    // If a user is opening application for second time, after logging In in first time
    public void setRememberSecond() {
        editor.putString("Remember", "true");
        editor.apply();
        String demo = sharedPreferences.getString("Remember", "dumb");
        Log.i("TEST", demo + "inside set Remember second");

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
        String demo = sharedPreferences.getString("Remember", "dummyValue");
        //Log.i("TEST", demo + "inside set default");
    }

    public String getRemember() {
        return sharedPreferences.getString("Remember", "dontknow");
    }


}
