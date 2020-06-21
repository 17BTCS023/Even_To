package com.example.even_to.CategoriesServiceProvidersList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.even_to.R;
import com.example.even_to.adapter.ServiceAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

public class ListOfServiceProviders extends AppCompatActivity implements
        ServiceAdapter.OnServiceSelectedListener {


    private static final String TAG = "PINEAPPPLEE!!!";
    private static final int LIMIT = 50;
   // private Toolbar mToolbar;
    private TextView mCurrentSearchView;
    private TextView mCurrentSortByView;
    private RecyclerView mServicesRecycler;
    private ViewGroup mEmptyView;

    private FirebaseFirestore mFirestore;
    private Query mQuery;
    
    private ServiceAdapter  mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String mCategory;

    private ListOfServiceProviders mViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_category);
        mCategory = getIntent().getStringExtra("category");

//        mToolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);

        mCurrentSearchView = findViewById(R.id.text_current_search);
        mCurrentSortByView = findViewById(R.id.text_current_sort_by);
        mServicesRecycler = findViewById(R.id.recycler_services);
        mEmptyView = findViewById(R.id.view_empty);
        // Initialize Firestore and the main RecyclerView
        initFirestore();
        initRecyclerView();
    }

    private void initFirestore() {
        mFirestore = FirebaseFirestore.getInstance();
        // Get the 50 highest rated restaurants
        mQuery = mFirestore.collection("listservices")
                .orderBy("avgRating", Query.Direction.DESCENDING);
    }

    private void initRecyclerView() {
        if (mQuery == null) {
            Log.w(TAG, "No query, not initializing RecyclerView");
        }

        mAdapter = new ServiceAdapter(mQuery, this) {
            @Override
            protected void onDataChanged() {
                // Show/hide content if the query returns empty.
                if (getItemCount() == 0) {
                    mServicesRecycler.setVisibility(View.GONE);
                    mEmptyView.setVisibility(View.VISIBLE);
                } else {
                    mServicesRecycler.setVisibility(View.VISIBLE);
                    mEmptyView.setVisibility(View.GONE);
                }
            }
            @Override
            protected void onError(FirebaseFirestoreException e) {
                // Show a snackbar on errors
                Snackbar.make(findViewById(android.R.id.content),
                        "Error: check logs for info.", Snackbar.LENGTH_LONG).show();
            }
        };

        mServicesRecycler.setLayoutManager(new LinearLayoutManager(ListOfServiceProviders.this));
        mServicesRecycler.setAdapter(mAdapter);
    }
    @Override
    public void onServiceSelected(DocumentSnapshot service) {
        // Go to the details page for the selected restaurant
        Intent intent = new Intent(this, ServiceDetailActivity.class);
        intent.putExtra("service_id", service.getId());
        startActivity(intent);
    }
    @Override
    public void onStart() {
        super.onStart();
        // Start listening for Firestore updates
        if (mAdapter != null) {
            mAdapter.startListening();
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }
}
