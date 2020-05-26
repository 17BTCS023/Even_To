package com.example.even_to.ui_navigation.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.even_to.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class HomeFragment extends Fragment {


    RecyclerView recyclerHome;

    List<Integer> imageList = new ArrayList<>();
    List<String> titleList = new ArrayList<>();

    List<HashMap<String,Object>> categoryList = new ArrayList<>();
    CategoryAdapter categoryAdapter;

    public HomeFragment(){
        // simply
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

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

        categoryAdapter = new CategoryAdapter(categoryList);
        recyclerHome.setAdapter(categoryAdapter);

    }

}
