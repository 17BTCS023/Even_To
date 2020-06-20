package com.example.even_to.Category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import com.example.even_to.R;
import com.example.even_to.adapter.FoodAdapter;
import java.util.ArrayList;

public class FoodCategory extends AppCompatActivity {

    private RecyclerView mCategoryRecyclerView;
    private FoodAdapter mListIetmAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<FoodServiceItem> foodServiceItemsArrayList;
    private String mCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_category);
        mCategory = getIntent().getStringExtra("category");

        foodServiceItemsArrayList = new ArrayList<>();

        foodServiceItemsArrayList.add(new FoodServiceItem(R.drawable.service,
                "Mark Kistler",
                "We know how to mix love and health in taste",
                "Medium : 100 to 500 people",
                " 4.5"));

        foodServiceItemsArrayList.add(new FoodServiceItem(R.drawable.service,
                "Mark Kistler",
                "We know how to mix love and health in taste",
                "Medium : 100 to 500 people",
                "4.5"));

        foodServiceItemsArrayList.add(new FoodServiceItem(R.drawable.service,
                "Mark Kistler",
                "We know how to mix love and health in taste",
                "Medium : 100 to 500 people",
                "4.5"));

        foodServiceItemsArrayList.add(new FoodServiceItem(R.drawable.service,
                "Mark Kistler",
                "We know how to mix love and health in taste",
                "Medium : 100 to 500 people",
                " 4.5"));

        foodServiceItemsArrayList.add(new FoodServiceItem(R.drawable.service,
                "Mark Kistler",
                "We know how to mix love and health in taste",
                "Medium : 100 to 500 people",
                "4.5"));

        foodServiceItemsArrayList.add(new FoodServiceItem(R.drawable.service,
                "Mark Kistler",
                "We know how to mix love and health in taste",
                "Medium : 100 to 500 people",
                "4.5"));

        buildRecyclerView();
    }

    public void buildRecyclerView(){
        mCategoryRecyclerView = findViewById(R.id.food_items_list_view);
        mCategoryRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mListIetmAdapter = new FoodAdapter(foodServiceItemsArrayList);

        mCategoryRecyclerView.setLayoutManager(mLayoutManager);
        mCategoryRecyclerView.setAdapter(mListIetmAdapter);
        mListIetmAdapter.setOnItemClickListener(new FoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                Intent intent = new Intent(Intent.ACTION_SEND);
//                intent.putExtra()
                startActivity(new Intent(getApplicationContext(), ServiceProviderProfile.class));
            }
        });
    }

}
