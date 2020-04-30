package com.example.even_to.HomeScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;

import com.example.even_to.R;
import com.example.even_to.Utils.SharedPref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    Toolbar toolbarHome;
    RecyclerView recyclerHome;

    List<Integer> imageList = new ArrayList<>();
    List<String> titleList = new ArrayList<>();

    List<HashMap<String,Object>> furnitureList = new ArrayList<>();
    CategoryAdapter furnitureAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }


    private void initialize() {

        toolbarHome = (Toolbar)findViewById(R.id.toolbar_home);
        recyclerHome = findViewById(R.id.recycler_home);

        toolbarHome.setTitle("Evento");
        toolbarHome.setTitleTextColor(Color.WHITE);

        setSupportActionBar(toolbarHome);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);

        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerHome.setLayoutManager(layoutManager);

        imageList.add(R.drawable.food);
        imageList.add(R.drawable.drinks);
        imageList.add(R.drawable.bake);
        imageList.add(R.drawable.gift);
        imageList.add(R.drawable.party);
        imageList.add(R.drawable.camera);

        titleList.add("Food");
        titleList.add("Drinks");
        titleList.add("Bakery");
        titleList.add("Gift");
        titleList.add("Party");
        titleList.add("Camera");


        for(int i=0;i<imageList.size();i++){

            HashMap<String,Object> map = new HashMap<>();
            map.put("Image",imageList.get(i));
            map.put("Title",titleList.get(i));

            furnitureList.add(map);

        }

        furnitureAdapter = new CategoryAdapter(furnitureList);
        recyclerHome.setAdapter(furnitureAdapter);

    }
}



