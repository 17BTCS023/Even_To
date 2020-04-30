package com.example.even_to;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.even_to.Utils.SharedPref;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPref sharedPreferences = new SharedPref(getApplicationContext());
        // check if user is old or new
        sharedPreferences.iAmOldUser();
    }
}
