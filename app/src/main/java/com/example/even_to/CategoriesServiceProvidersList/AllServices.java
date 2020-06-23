package com.example.even_to.CategoriesServiceProvidersList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.even_to.CategoriesServiceProvidersList.Filters.FilterDialogFragment;
import com.example.even_to.CategoriesServiceProvidersList.Filters.Filters;
import com.example.even_to.CategoriesServiceProvidersList.Ratings.ServiceDetailActivity;
import com.example.even_to.R;
import com.example.even_to.adapter.ServiceAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

public class AllServices extends AppCompatActivity implements
        FilterDialogFragment.FilterListener,
        ServiceAdapter.OnServiceSelectedListener {


    private static final String TAG = "CHECK THIS";
    private static final int LIMIT = 50;
    private TextView mCurrentSearchView;
    private TextView mCurrentSortByView;
    private RecyclerView mServicesRecycler;
    private ImageView mEmptyView;

    private FirebaseFirestore mFirestore;
    private Query mQuery;

    private FilterDialogFragment mFilterDialog;
    private ServiceAdapter  mAdapter;

    private String mCategory;

    private AllServicesViewModel mViewModel;

    ImageView mFilter, mClearFilter;

    CardView mFilterBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_services);
        mCategory = getIntent().getStringExtra("category");

        mCurrentSearchView = findViewById(R.id.text_current_search);
        mCurrentSortByView = findViewById(R.id.text_current_sort_by);
        mServicesRecycler = findViewById(R.id.recycler_services);
        mEmptyView = findViewById(R.id.view_empty);
        mFilterBar = findViewById(R.id.filter_bar);
        mFilter = findViewById(R.id.button_filter);
        mClearFilter = findViewById(R.id.button_clear_filter);

        // View model
        mViewModel = new ViewModelProvider(this).get(AllServicesViewModel.class);

        // Initialize Firestore and the main RecyclerView
        initFirestore();
        initRecyclerView();

        // Filter Dialog
        mFilterDialog = new FilterDialogFragment();


        mFilterBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFilterClicked();
            }
        });

        mFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClearFilterClicked();
            }
        });
    }

    public void onFilterClicked() {
        // Show the dialog containing filter options
        mFilterDialog.show(getSupportFragmentManager(), FilterDialogFragment.TAG);
    }

    public void onClearFilterClicked() {
        mFilterDialog.resetFilters();
        onFilter(Filters.getDefault());
    }
    private void initFirestore() {
        mFirestore = FirebaseFirestore.getInstance();
        // Get the 50 highest rated restaurants
        mQuery = mFirestore.collection("service")
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

        mServicesRecycler.setLayoutManager(new LinearLayoutManager(AllServices.this));
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
        // Apply filters
        onFilter(mViewModel.getFilters());

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
    @Override
    public void onFilter(Filters filters) {

    }
}
