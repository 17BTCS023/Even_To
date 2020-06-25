package com.example.even_to.navigation.orders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.even_to.CategoriesServiceProvidersList.Ratings.ServiceDetailActivity;
import com.example.even_to.R;
import com.example.even_to.adapter.OrderAdapter;
import com.example.even_to.model.Order;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class OrderFragment extends Fragment implements
        OrderAdapter.OnOrderSelectedListener {

    private RecyclerView orderRecyclerView;
    private static final String TAG = "OrderFragment";
    FirebaseAuth firebaseAuth;
    FirebaseFirestore dbInstance;
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
        dbInstance = FirebaseFirestore.getInstance();
        mQuery = dbInstance.collection("orders").whereEqualTo(Order.KEY_USER_ID, firebaseAuth.getUid())
                .orderBy(Order.KEY_NAME, Query.Direction.ASCENDING);
    }

    private void initRecyclerView() {
        if (mQuery == null) {
            Log.w(TAG, "No query, not initializing RecyclerView");
        }
        //mAdapter = new RestaurantAdapter(mQuery, this){
        mAdapter = new OrderAdapter(mQuery, this) {

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
                Log.d(TAG, e.toString());

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

    @Override
    public void onUnHireClick(final DocumentSnapshot order) {
        Log.d(TAG, "onUnHireClick: REACHED INSIDE UNHIRING");
        dbInstance.collection("orders")
                .whereEqualTo("userId", order.get("userId"))
                .whereEqualTo("documentId", order.get("documentId"))
                .limit(1)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            String orderDocumentId = documentSnapshot.getId();

                            DocumentReference orderRef = dbInstance.collection("orders").document(orderDocumentId);
                            orderRef.delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getContext(), "Fired!", Toast.LENGTH_SHORT).show();
                                            Log.d(TAG, "FIREEEEEEEEEED");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getContext(), "Error!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            break;
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Couldn't Hire, Try after sometime!", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    @Override
    public void onViewProfileClick(DocumentSnapshot order) {
        Intent intent = new Intent(getContext(), OrderDetailActivity.class);
        Order order1 = order.toObject(Order.class);
        String serviceId = order1.getDocumentId();
        intent.putExtra("serviceId", serviceId);
        startActivity(intent);
    }

}
