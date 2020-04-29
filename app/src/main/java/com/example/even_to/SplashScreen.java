package com.example.even_to;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    private Handler mHandler;
    private Runnable mRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mRunnable = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class ));
                finish();
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
        if(mHandler!=  null && mRunnable != null) {
            mHandler.removeCallbacks(mRunnable);
        }
    }
}
