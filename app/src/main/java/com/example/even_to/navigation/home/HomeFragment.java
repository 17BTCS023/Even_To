package com.example.even_to.navigation.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.even_to.CategoriesServiceProvidersList.ListOfServiceProviders;
import com.example.even_to.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class HomeFragment extends Fragment implements CategoryAdapter.OnCategoryListener {


    RecyclerView recyclerHome;

    List<Integer> imageList = new ArrayList<>();
    List<String> titleList = new ArrayList<>();


    List<HashMap<String,Object>> categoryList = new ArrayList<>();
    CategoryAdapter categoryAdapter;

    public HomeFragment(){
        // simply
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the Home fragment
        View view = inflater.inflate(R.layout.fragment_home, container,false);
        //Getting reference to the recycler view
        recyclerHome = view.findViewById(R.id.recycler_home);
        initialize();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    private void initialize() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        //Getting reference to the recycler view
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

        categoryAdapter = new CategoryAdapter(categoryList, this);
        recyclerHome.setAdapter(categoryAdapter);

    }

    @Override
    public void onCategoryClick(int position) {

        String cat = titleList.get(position);
        Log.d("ERRRROOOORRRR!!!", "onCategoryClick: " + cat);
        switch (cat){
            case "Food" :
                Intent intent1 = new Intent(getContext(), ListOfServiceProviders.class);
                intent1.putExtra("category", "Food");
                startActivity(intent1);
                break;

            case "Drinks" :
                Intent intent2 = new Intent(getContext(), ListOfServiceProviders.class);
                intent2.putExtra("category", "Drinks");
                startActivity(intent2);
                break;

            case "Bakery" :
                Intent intent3 = new Intent(getContext(), ListOfServiceProviders.class);
                intent3.putExtra("category", "Bakery");
                startActivity(intent3);
                break;

            case "Gift" :
                Intent intent4 = new Intent(getContext(), ListOfServiceProviders.class);
                intent4.putExtra("category", "Gift");
                startActivity(intent4);
                break;

            case "Decor" :
                Intent intent5 = new Intent(getContext(), ListOfServiceProviders.class);
                intent5.putExtra("category", "Decor");
                startActivity(intent5);
                break;

            case "Photography" :
                Intent intent6 = new Intent(getContext(), ListOfServiceProviders.class);
                intent6.putExtra("category", "Photography");
                startActivity(intent6);
                break;
        }
    }
}
