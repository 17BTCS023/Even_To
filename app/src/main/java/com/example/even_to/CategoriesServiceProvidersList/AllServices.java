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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    private FirebaseFirestore dbInstance;
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

        mFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFilterClicked();
            }
        });
        mClearFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClearFilterClicked();
            }
        });
    }

    public void onFilterClicked() {

        Bundle bundle = new Bundle();
        bundle.putString("category", mCategory);
        mFilterDialog.setArguments(bundle);
        mFilterDialog.show(getSupportFragmentManager(), FilterDialogFragment.TAG);
    }

    public void onClearFilterClicked() {
        mFilterDialog.resetFilters();
        onFilter(Filters.getDefault());
        Toast.makeText(this, "Removing Filter", Toast.LENGTH_SHORT).show();
    }

    private void initFirestore() {
        dbInstance = FirebaseFirestore.getInstance();
        mQuery = dbInstance.collection("services")
                .limit(LIMIT);
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
        intent.putExtra("serviceId", service.getId());
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
        // Construct a basic query
        Query query = dbInstance.collection("services").whereEqualTo("category",mCategory);

        // Category (equality filter)
        if(filters.hasCategory()){
            query = query.whereEqualTo("type", filters.getType());
        }
        // City (equality filter)
        if(filters.hasCity()){
            query = query.whereEqualTo("city", filters.getCity());
        }
        // Experience (equality filter)
        if(filters.hasExperience()){
            query = query.whereEqualTo("experience", filters.getExperience());
        }
         //Capacity (equality filter)
        if(filters.hasCapacity()){
            query = query.whereEqualTo("capacity", filters.getCapacity());
        }
        // Sort by (orderBy with direction)
        if(filters.hasSortBy()){
            query = query.orderBy(filters.getSortBy(), filters.getSortDirection());
        }
        // List Items
        query = query.limit(LIMIT);

        // Update the query
        mQuery = query;
        mAdapter.setQuery(mQuery);
        // Set header
        mCurrentSearchView.setText(Html.fromHtml(filters.getSearchDescription(this)));
        mCurrentSortByView.setText(filters.getOrderDescription(this));

        // Save filters
        mViewModel.setFilters(filters);
    }
}
