package com.example.even_to.navigation.orders;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.even_to.R;
import com.example.even_to.adapter.MyServiceAdapter;
import com.example.even_to.adapter.OrderAdapter;
import com.example.even_to.model.Order;
import com.example.even_to.model.User;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

public class OrderFragment extends Fragment {

    private RecyclerView orderRecyclerView;
    private static final String TAG = "OrderFragment";
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    private ImageView mEmptyView;
    private Query mQuery;
    private OrderAdapter mAdapter;
    MaterialButton mUnHire, mViewService;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);

        mEmptyView = view.findViewById(R.id.view_empty);
        orderRecyclerView = view.findViewById(R.id.orders_recycler_view_list);
        mUnHire = view.findViewById(R.id.btn_unhire);
        mViewService = view.findViewById(R.id.btn_view_service);

        initFirestore();
        initRecyclerView();
        return view;
    }
    private void initFirestore() {
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        mQuery = db.collection("orders").whereEqualTo(Order.KEY_USER_ID, firebaseAuth.getUid())
        .orderBy(Order.KEY_NAME, Query.Direction.ASCENDING);
    }

    private void initRecyclerView() {
        if (mQuery == null) {
            Log.w(TAG, "No query, not initializing RecyclerView");
        }
        //mAdapter = new RestaurantAdapter(mQuery, this){
        mAdapter = new OrderAdapter(mQuery) {

            @Override
            protected void onDataChanged() {
                // Show/hide content if the query returns empty.

                if (getItemCount() == 0) {
                    orderRecyclerView.setVisibility(View.GONE);
                    mEmptyView.setVisibility(View.VISIBLE);

                } else {
                    orderRecyclerView.setVisibility(View.VISIBLE);
                    mEmptyView.setVisibility(View.GONE);
                }
            }
            @Override
            protected void onError(FirebaseFirestoreException e) {
                // Show a snackbar on errors
                Log.d(TAG , e.toString());

            }
        };
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        orderRecyclerView.setAdapter(mAdapter);

    }

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
