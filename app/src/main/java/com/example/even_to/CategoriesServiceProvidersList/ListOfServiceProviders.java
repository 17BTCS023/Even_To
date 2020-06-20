package com.example.even_to.CategoriesServiceProvidersList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import com.example.even_to.R;
import com.example.even_to.adapter.FoodAdapter;
import java.util.ArrayList;

public class ListOfServiceProviders extends AppCompatActivity {

    private RecyclerView mCategoryRecyclerView;
    private FoodAdapter mListIetmAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<ServiceItem> serviceItemsArrayList;
    private String mCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_category);
        mCategory = getIntent().getStringExtra("category");

        serviceItemsArrayList = new ArrayList<>();

        serviceItemsArrayList.add(new ServiceItem(R.drawable.service,
                "Mark Kistler",
                "We know how to mix love and health in taste",
                "Medium : 100 to 500 people",
                " 4.5"));

        serviceItemsArrayList.add(new ServiceItem(R.drawable.service,
                "Mark Kistler",
                "We know how to mix love and health in taste",
                "Medium : 100 to 500 people",
                "4.5"));

        serviceItemsArrayList.add(new ServiceItem(R.drawable.service,
                "Mark Kistler",
                "We know how to mix love and health in taste",
                "Medium : 100 to 500 people",
                "4.5"));

        serviceItemsArrayList.add(new ServiceItem(R.drawable.service,
                "Mark Kistler",
                "We know how to mix love and health in taste",
                "Medium : 100 to 500 people",
                " 4.5"));

        serviceItemsArrayList.add(new ServiceItem(R.drawable.service,
                "Mark Kistler",
                "We know how to mix love and health in taste",
                "Medium : 100 to 500 people",
                "4.5"));

        serviceItemsArrayList.add(new ServiceItem(R.drawable.service,
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
        mListIetmAdapter = new FoodAdapter(serviceItemsArrayList);

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
