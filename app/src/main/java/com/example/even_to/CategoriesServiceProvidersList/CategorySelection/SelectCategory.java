package com.example.even_to.CategoriesServiceProvidersList.CategorySelection;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.even_to.R;
import com.example.even_to.navigation.services.newService.AddNewService;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SelectCategory extends AppCompatActivity {

    private static final String TAG = "SelectCategory";

    ProgressBar progressBar ;

    AutoCompleteTextView mCategory;
    MaterialButton mSaveCategory;
    String[] CATEGORIES = new String[]{"Food", "Drinks", "Bakery", "Gift", "Decor", "Photography"};

    FirebaseFirestore dbInstance = FirebaseFirestore.getInstance();
    CollectionReference categoryRef ;

    String serviceCategory = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_category);

        mCategory = findViewById(R.id.new_service_category);
        mSaveCategory = findViewById(R.id.selectCategory);
        progressBar = findViewById(R.id.progress_bar);


        ArrayAdapter categoryAdapter = new ArrayAdapter(SelectCategory.this,R.layout.support_simple_spinner_dropdown_item,CATEGORIES);
        mCategory.setAdapter(categoryAdapter);

        mSaveCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceCategory = mCategory.getText().toString().trim();
                Intent intent = new Intent(SelectCategory.this, AddNewService.class);
                intent.putExtra("category", serviceCategory);
                startActivity(intent);
            }
        });
    }
}