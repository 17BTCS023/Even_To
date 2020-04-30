package com.example.even_to.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.even_to.login_signup.LoginActivity;

public class SharedPref {

    // creating shared preferences file
    SharedPreferences sharedPreferences;

    // to edit the shared preferences file
    SharedPreferences.Editor editor ;

    // Context will pass the reference to other class
    Context context;

    // setting mode as private for sharedpref file
    int mode =0;

    // name of the SP file
    String FileName = "sharedPrefFile";

    // A boolean variable: true, if key present; false, if key not present
    String Data = "b";

    // Constructor that will save memory for file during runtime


    public SharedPref(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(FileName, mode);
        editor = sharedPreferences.edit();
    }

    // If a user is opening application for second time, after logging In in first time
    public void iAmOldUser(){
        editor.putBoolean(Data, true);
        editor.commit();
    }

    // If the user logs in freshly
    public void newUser(){
        if(!this.login()){
            // If the user has not logged in before move to LogIn Activity

            Intent intent = new Intent(context, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);

        }
    }

    // To get default value as false for any login
    private boolean login() {
       return sharedPreferences.getBoolean(Data, false);
    }


}
