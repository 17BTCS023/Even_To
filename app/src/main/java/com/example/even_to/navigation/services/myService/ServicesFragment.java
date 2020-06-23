package com.example.even_to.navigation.services.myService;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.even_to.R;
import com.example.even_to.adapter.MyServiceAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

public class ServicesFragment extends Fragment {

    private static final String TAG = "ServicesFragment";
    private RecyclerView serviceRecyclerViewList;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    private ImageView mEmptyView;
    private Query mQuery;
    private MyServiceAdapter mAdapter;


    //constructor
    public ServicesFragment() {
        // simply
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the Profile fragment
        View view = inflater.inflate(R.layout.fragment_services, container, false);
        mEmptyView = view.findViewById(R.id.view_empty);
        serviceRecyclerViewList = view.findViewById(R.id.service_recycler_view_list);
        // Initialize Firestore and the main RecyclerView
        initFirestore();
        initRecyclerView();
        return view;
    }

    private void initFirestore() {
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        mQuery = db.collection("services").document(firebaseAuth.getUid())
                .collection("myServices");

    }

    private void initRecyclerView() {
        if (mQuery == null) {
            Log.w(TAG, "No query, not initializing RecyclerView");
        }
        //mAdapter = new RestaurantAdapter(mQuery, this){
        mAdapter = new MyServiceAdapter(mQuery) {

            @Override
            protected void onDataChanged() {
                // Show/hide content if the query returns empty.

                if (getItemCount() == 0) {
                    serviceRecyclerViewList.setVisibility(View.GONE);
                    mEmptyView.setVisibility(View.VISIBLE);

                } else {
                    serviceRecyclerViewList.setVisibility(View.VISIBLE);
                    mEmptyView.setVisibility(View.GONE);
                }
            }

            @Override
            protected void onError(FirebaseFirestoreException e) {
                // Show a snackbar on errors
                Log.d("ERRROOOOORRRRRR!!!", e.toString());

            }
        };
        serviceRecyclerViewList.setLayoutManager(new LinearLayoutManager(getContext()));
        serviceRecyclerViewList.setAdapter(mAdapter);

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
