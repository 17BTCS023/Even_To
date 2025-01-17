package com.example.even_to;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.example.even_to.CategoriesServiceProvidersList.CategorySelection.SelectCategory;
import com.example.even_to.navigation.home.HomeFragment;
import com.example.even_to.navigation.orders.OrderFragment;
import com.example.even_to.navigation.profile.ProfileFragment;
import com.example.even_to.navigation.services.myService.MyServicesFragment;
import com.example.even_to.utils.SharedPref;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class MainHomeScreen extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    private static final String KEY_PROFILE_PIC = "photo";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    public static final String KEY_PROFILE_UPDATED = "profileUpdated";
    AppBarConfiguration mAppBarConfiguration;
    DrawerLayout drawer;
    FloatingActionButton fab;
    private boolean isStartup = true;
    Toolbar toolbar;
    TextView userEmail, userName;
    FirebaseAuth auth;
    SharedPref sharedPref;
    ImageView mProfileImage;

    @Override
    protected void onStop() {
        super.onStop();
        this.finishActivity(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home_screen);
        auth = FirebaseAuth.getInstance();
        sharedPref = new SharedPref(this);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainHomeScreen.this, SelectCategory.class));
            }
        });
        drawer = findViewById(R.id.drawer_layout);

        /* To have an hamburger icon with rotation action */
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*** we dont want to loose the state of the activity ***/
        if (savedInstanceState == null) {
            /* Creating instance of the HomeFragment and display it using FragmentManager and Transaction **/
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, new HomeFragment())
                    .commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    /* When we press back, we should leave the activity immediately, but close the drawer first *******/
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_home_screen, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /* When the profile is selected from the menu, the profile_fragment should be loaded ****/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int menuItemId = item.getItemId();
        if (isStartup) {
            ((FrameLayout) findViewById(R.id.nav_host_fragment)).removeAllViews();
            isStartup = false;
        }
        userEmail = findViewById(R.id.nav_bar_user_email);
        userName = findViewById(R.id.nav_bar_user_name) ;
        mProfileImage = findViewById(R.id.profile_image);
        if (sharedPref.getProfileStatus()) {
            ChangeDisplayInfo(auth.getUid(), userName, userEmail, mProfileImage);
        }
        switch (menuItemId) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, new HomeFragment())
                        .commit();
                fab.setVisibility(View.VISIBLE);
                toolbar.setTitle("Home");
                break;
            case R.id.nav_my_profile:

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, new ProfileFragment())
                        .commit();
                fab.setVisibility(View.GONE);
                toolbar.setTitle(R.string.Profile);
                break;

            case R.id.nav_my_orders:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, new OrderFragment())
                        .commit();
                fab.setVisibility(View.GONE);
                toolbar.setTitle(R.string.my_orders);
                break;


            case R.id.nav_my_services:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, new MyServicesFragment())
                        .commit();
                fab.setImageResource(R.drawable.add_service);
                toolbar.setTitle(R.string.my_services);
                break;

            case R.id.nav_logout:
                /* Set sharedPref remember as false */
                SharedPref sharedPref = new SharedPref(this);
                sharedPref.setDefault();
                sharedPref.setProfileDefault();
                sharedPref.setRememberFirst();
                Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
    private void ChangeDisplayInfo(String uid, final TextView userName, final TextView userEmail, final ImageView mProfileImage) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference proRef = db.collection("profile").document(uid);
        proRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Map<String, Object> map = documentSnapshot.getData();
                        userName.setText((CharSequence) map.get(KEY_NAME));
                        userEmail.setText((CharSequence) map.get(KEY_EMAIL));
                        if (map.containsKey("photo")) {
                            Glide.with(mProfileImage.getContext())
                                    .load(map.get(KEY_PROFILE_PIC))
                                    .into(mProfileImage);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainHomeScreen.this, "Select My Profile!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void goBackToHome(MenuItem item) {
        startActivity(new Intent(getApplicationContext(), MainHomeScreen.class ));
        finish();
    }
}