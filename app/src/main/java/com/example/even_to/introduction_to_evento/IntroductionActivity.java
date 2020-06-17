package com.example.even_to.introduction_to_evento;

import androidx.appcompat.app.AppCompatActivity;

import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.even_to.MainHomeScreen;
import com.example.even_to.R;
import com.example.even_to.utils.SharedPref;
import com.example.even_to.login_signup.LoginActivity;

public class IntroductionActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout layoutDot;
    private TextView[] dotstv;
    private int[] layouts;
    private Button btnSkip;
    private  Button btnNext;
    private MyPageAdapter myPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!isFirstTimeStarting()) {
            startLoginActivity();
            finish();
        }

        SharedPref sharedPreferences = new SharedPref(getApplicationContext());
        sharedPreferences.setDefault();
        /*Setting Up status bar transparent
         ******************************************/

        setStatusBarTransparent();

        setContentView(R.layout.activity_introduction);
        viewPager =findViewById(R.id.view_pager);
        layoutDot = findViewById(R.id.dotLayout);
        btnNext = findViewById(R.id.btn_next);
        btnSkip =findViewById(R.id.btn_skip);

        /* Handling Button click events
         *****************************************/
        // When user clicks on skip, start Main Activity
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoginActivity();
            }
        });






        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPage = viewPager.getCurrentItem()+1;
                if(currentPage < layouts.length){
                    // move to next page
                    viewPager.setCurrentItem(currentPage);
                }
                else {
                    startLoginActivity();
                }
            }
        });
        layouts = new int[] { R.layout.intro_slide_screen1, R.layout.intro_slide_screen2 ,
                R.layout.intro_slide_screen3,R.layout.intro_slide_screen4,
                R.layout.intro_slide_screen5,R.layout.intro_slide_screen6};
        myPageAdapter = new MyPageAdapter(layouts,getApplicationContext());
        viewPager.setAdapter(myPageAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == layouts.length -1){
                    // last page
                    btnNext.setText(R.string.start);
                    btnSkip.setVisibility(View.GONE);
                }else{
                    btnNext.setText(R.string.next);
                    btnSkip.setVisibility(View.VISIBLE);
                }
                setDotstatus(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setDotstatus(0);

    }

    private boolean isFirstTimeStarting(){
        SharedPreferences ref = getApplicationContext().getSharedPreferences("IntroSlider", Context.MODE_PRIVATE);
        return ref.getBoolean("FirstTimeStartFlag", true);
    }
    private void setFirstTimeStartStatus(boolean stt){
        SharedPreferences ref = getApplicationContext().getSharedPreferences("IntroSlider", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =  ref.edit();
        editor.putBoolean("FirstTimeStartFlag",stt);
        editor.apply();
    }

    private void setDotstatus(int page){
        layoutDot.removeAllViews();
        dotstv = new TextView[layouts.length];
        int i;
        for(i = 0; i < dotstv.length; i++){
            dotstv[i] = new TextView(this);
            dotstv[i].setText(Html.fromHtml("&#8226;"));
            dotstv[i].setTextSize(30);
            dotstv[i].setTextColor(Color.parseColor("#c6c6c6"));
            layoutDot.addView(dotstv[i]);
        }

        // Set current dot active
        if(dotstv.length >0){
            dotstv[page].setTextColor(Color.parseColor("#f9fafc"));
        }
    }

    private void startMainActivity(){
        setFirstTimeStartStatus(false);
        startActivity(new Intent(IntroductionActivity.this, MainHomeScreen.class));
        finish();
    }
    private void startLoginActivity(){
        setFirstTimeStartStatus(false);
        startActivity(new Intent(IntroductionActivity.this, LoginActivity.class));
        finish();
    }

    private void setStatusBarTransparent(){
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
    }


}
