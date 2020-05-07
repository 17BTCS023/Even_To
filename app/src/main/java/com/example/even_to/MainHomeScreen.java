package com.example.even_to;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.even_to.Utils.SharedPref;
import com.example.even_to.ui_navigation.home.HomeFragment;
import com.example.even_to.ui_navigation.messages.MessagesFragment;
import com.example.even_to.ui_navigation.orders.OrderFragment;
import com.example.even_to.ui_navigation.profile.ProfileFragment;
import com.example.even_to.ui_navigation.services.ServicesFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.even_to.Utils.SharedPref;

public class MainHomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    AppBarConfiguration mAppBarConfiguration;
    DrawerLayout drawer;
    FloatingActionButton fab;
    private boolean isStartup = true;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home_screen);

        /*** finding the toolbar for our main screen ****/
        toolbar = findViewById(R.id.toolbar);
        // setSupportActionBar sets up the toolbar as the actionbar
        setSupportActionBar(toolbar);

        /*** finding the fab button ****/
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add new Service", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        /****  finding and inflating drawer layout ***/
        drawer = findViewById(R.id.drawer_layout);

        /*** To have an hamburger icon with rotation action ***/
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        /*** we dont want to loose the state of the activity ***/
        if(savedInstanceState == null) {
            /* Creating instance of the HomeFragment and display it using
            // FragmentManager and Transaction **/
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, new HomeFragment())
                    .commit();
            navigationView.setCheckedItem(R.id.nav_home);

        }
    }

    /********* When we press back, we should leave the activity immedieately, but close the drawer first *******/
    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_home_screen, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    // checking , if the elements are clickable
    public void clicked(View view) {
        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
    }

    /**** When the profile is seclected from the menu, the profile_fragment should be loaded ****/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // getting the id of the selected item
        int menuItemId = item.getItemId();
        // Using switch statement, identify the item selected
        //Use a FragmentManager and Transaction tp add the Fragment to the screen
//        FragmentManager fragmentManager = getSupportFragmentManager();
        if(isStartup){
            ((FrameLayout) findViewById(R.id.nav_host_fragment)).removeAllViews();
            isStartup = false;
        }
        switch (menuItemId){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment,new HomeFragment())
                        .commit();
                break;
            case R.id.nav_my_profile:

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment,new ProfileFragment())
                        .commit();
                fab.setImageResource(R.drawable.ic_edit);
                toolbar.setTitle(R.string.Profile);
                break;

            case R.id.nav_my_orders:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment,new OrderFragment())
                        .commit();
                fab.setImageResource(R.drawable.ic_edit);
                toolbar.setTitle(R.string.Profile);
                break;


            case R.id.nav_my_services:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment,new ServicesFragment())
                        .commit();
                fab.setImageResource(R.drawable.ic_edit);
                toolbar.setTitle(R.string.Profile);
                break;

            case R.id.nav_messages:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment,new MessagesFragment())
                        .commit();
                fab.setImageResource(R.drawable.ic_edit);
                toolbar.setTitle(R.string.Profile);
                break;

            case R.id.nav_logout:
                /*** Set sharedPref remember as false ***/
                SharedPref sharedPref = new SharedPref(this);
                sharedPref.setDefault();
                sharedPref.setRememberFirst();
                Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}






//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.menu_home,menu);
//
//        MenuItem menuItem = menu.findItem(R.id.action_search);
//
//        SearchView searchView = (SearchView) menuItem.getActionView();
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//
//                categoryAdapter.getFilter().filter(s);
//
//                return false;
//            }
//        });
//
//        return super.onCreateOptionsMenu(menu);
//    }