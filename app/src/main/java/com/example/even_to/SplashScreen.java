package com.example.even_to;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.even_to.utils.SharedPref;
import com.example.even_to.introduction_to_evento.IntroductionActivity;


public class SplashScreen extends AppCompatActivity {

    private Handler mHandler;
    private Runnable mRunnable;
    private String remember;

    @Override
    protected void onStart() {
        super.onStart();
        /******* Check if the sharedPreferences contains Key-value pair of email id and password *****/
        SharedPref sharedPreferences = new SharedPref(getApplicationContext());
        remember = sharedPreferences.getRemember();
        Log.i("TEST", remember);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mRunnable = new Runnable() {
            @Override
            public void run() {

                if (remember.contentEquals("true")) {
                    //Log.i("TEST", "User is old");
                    // if YES, redirect to Main/ Home Activity
                    startActivity(new Intent(SplashScreen.this, MainHomeScreen.class));
                    finish();
                } else {
                    //Log.i("TEST", "User is new");
                    startActivity(new Intent(getApplicationContext(), IntroductionActivity.class));
                    finish();
                }
            }
        };

        /********************* Wait before moving to next screen ************/
        mHandler = new Handler();
        mHandler.postDelayed(mRunnable, 2000);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*********** Destroying the handler and runnable object *******/
        if (mHandler != null && mRunnable != null) {
            mHandler.removeCallbacks(mRunnable);
        }
    }

}
