package com.example.even_to.HomeScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.even_to.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    Toolbar toolbarHome;
    RecyclerView recyclerHome;

    List<Integer> imageList = new ArrayList<>();
    List<String> titleList = new ArrayList<>();

    List<HashMap<String,Object>> categoryList = new ArrayList<>();
    CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }


    private void initialize() {

        toolbarHome = findViewById(R.id.toolbar_home);
        recyclerHome = findViewById(R.id.recycler_home);

        toolbarHome.setTitle("Evento");
        toolbarHome.setTitleTextColor(Color.WHITE);

        setSupportActionBar(toolbarHome);

        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerHome.setLayoutManager(layoutManager);

        imageList.add(R.drawable.food);
        imageList.add(R.drawable.drinks);
        imageList.add(R.drawable.bake);
        imageList.add(R.drawable.home_screen_gift);
        imageList.add(R.drawable.home_screen_party);
        imageList.add(R.drawable.home_screen_camera);

        titleList.add("Food");
        titleList.add("Drinks");
        titleList.add("Bakery");
        titleList.add("Gift");
        titleList.add("Decor");
        titleList.add("Photography");


        for(int i=0;i<imageList.size();i++){

            HashMap<String,Object> map = new HashMap<>();
            map.put("Image",imageList.get(i));
            map.put("Title",titleList.get(i));
            categoryList.add(map);
        }

        categoryAdapter = new CategoryAdapter(categoryList);
        recyclerHome.setAdapter(categoryAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_home,menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                categoryAdapter.getFilter().filter(s);

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}



