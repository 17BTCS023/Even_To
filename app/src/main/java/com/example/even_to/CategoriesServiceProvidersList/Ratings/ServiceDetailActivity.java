package com.example.even_to.CategoriesServiceProvidersList.Ratings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.even_to.CategoriesServiceProvidersList.ServiceProviderProfile;
import com.example.even_to.R;
import com.example.even_to.adapter.RatingAdapter;
import com.example.even_to.model.Order;
import com.example.even_to.model.Rating;
import com.example.even_to.model.Service;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.Transaction;

public class ServiceDetailActivity extends AppCompatActivity implements
        EventListener<DocumentSnapshot>,
        RatingDialogFragment.RatingListener {
    private static final String TAG = "ServiceDetail";

    public static final String KEY_SERVICE_ID = "serviceId";
    Service service;
    private ImageView mImageView;
    private RatingBar mRatingIndicator;
    private MaterialTextView mNameView, mNumRatingsView, mCityView, mContactView, mWebsiteView, mCategoryView, mDescriptionView, mOptionsView, mExperienceView;
    private MaterialButton mHire, mViewProfile;
    FloatingActionButton mAddReview;


    private ViewGroup mEmptyView;
    private RecyclerView mRatingsRecycler;

    private RatingDialogFragment mRatingDialog;

    private FirebaseFirestore dbInstance;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    private DocumentReference mServiceRef;
    private ListenerRegistration mServiceRegistration;

    private RatingAdapter mRatingAdapter;

    String serviceId, userId;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_detail);

        mImageView = findViewById(R.id.service_detail_image);
        mNameView = findViewById(R.id.service_detail_name);
        mRatingIndicator = findViewById(R.id.service_detail_rating);
        mNumRatingsView = findViewById(R.id.service_detail_num_ratings);
        mCityView = findViewById(R.id.service_detail_city);
        mContactView = findViewById(R.id.service_detail_contact_number);
        mWebsiteView = findViewById(R.id.service_detail_website_link);
        mCategoryView = findViewById(R.id.service_detail_category);
        mDescriptionView = findViewById(R.id.service_detail_description);
        mExperienceView = findViewById(R.id.service_detail_experience);

        mHire = findViewById(R.id.service_detail_hire);
        mViewProfile = findViewById(R.id.btn_view_service_provider_profile);

        mEmptyView = findViewById(R.id.view_empty_ratings);
        mRatingsRecycler = findViewById(R.id.recycler_ratings);

        progressBar = findViewById(R.id.progress_bar);
        userId = auth.getUid();

        // Get service ID from extras
        serviceId = getIntent().getStringExtra(KEY_SERVICE_ID);
        if (serviceId == null) {
            throw new IllegalArgumentException("Must pass extra " + KEY_SERVICE_ID);
        }
        // Initialize Firestore
        dbInstance = FirebaseFirestore.getInstance();

        // Get reference to the restaurant
        mServiceRef = dbInstance.collection("services").document(serviceId);
        mHire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setIndeterminate(true);
                hire();
            }
        });
        mViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProfile();
            }
        });

        // Get ratings
        Query ratingsQuery = mServiceRef
                .collection("ratings")
                .limit(50);
        // RecyclerView
        mRatingAdapter = new RatingAdapter(ratingsQuery) {
            @Override
            protected void onDataChanged() {
                if (getItemCount() == 0) {
                    mRatingsRecycler.setVisibility(View.GONE);
                    mEmptyView.setVisibility(View.VISIBLE);
                } else {
                    mRatingsRecycler.setVisibility(View.VISIBLE);
                    mEmptyView.setVisibility(View.GONE);
                }
            }
        };

        mRatingsRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRatingsRecycler.setAdapter(mRatingAdapter);

        mRatingDialog = new RatingDialogFragment();
    }

    private void showProfile() {
        Intent intent = new Intent(ServiceDetailActivity.this, ServiceProviderProfile.class);
        String serviceUserId = service.getUserId();
        intent.putExtra("serviceUserId", serviceUserId);
        startActivity(intent);
    }

    private void hire() {
        DocumentReference orderRef = dbInstance.collection("orders").document();
        Order order = new Order(userId, service.getDocumentId(), service.getCategory(),
                service.getImageLogo(), service.getAvgRating(),
                service.getCapacity(), service.getCity(), service.getDescription(),
                service.getPhoneNumber(), service.getLink(), service.userId,
                service.getName(), service.getExperience(), service.getNumRatings(), service.getType());
        orderRef.set(order)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(ServiceDetailActivity.this, "Hired!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(ServiceDetailActivity.this, "Couldn't Hire, Try after sometime!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        mRatingAdapter.startListening();
        mServiceRegistration = mServiceRef.addSnapshotListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();

        mRatingAdapter.stopListening();

        if (mServiceRegistration != null) {
            mServiceRegistration.remove();
            mServiceRegistration = null;
        }
    }

    public void onAddRatingClicked(View view) {
        mRatingDialog.show(getSupportFragmentManager(), RatingDialogFragment.TAG);
    }

    private Task<Void> addRating(final DocumentReference serviceRef,
                                 final Rating rating) {
        // Create reference for new rating, for use inside the transaction
        final DocumentReference ratingRef = serviceRef.collection("ratings")
                .document();
        // In a transaction add a new rating and update the aggregate totals
        return dbInstance.runTransaction(new Transaction.Function<Void>() {
            @Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                Service service = transaction.get(serviceRef).toObject(Service.class);
                // Compute new number of ratings
                assert service != null;
                int newNumRating = service.getNumRatings() + 1;

                // compute new average rating
                double oldRatingTotal = service.getAvgRating() * service.getNumRatings();
                double newAvgRating = (oldRatingTotal + rating.getRating()) / newNumRating;

                //Set new service info
                service.setNumRatings(newNumRating);
                service.setAvgRating(newAvgRating);

                // commit to firestore
                transaction.set(serviceRef, service);
                transaction.set(ratingRef, rating);

                return null;
            }
        });
    }

    @Override
    public void onRating(Rating rating) {
        addRating(mServiceRef, rating)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Rating added");

                        // Hide keyboard and scroll to top
                        hideKeyboard();
                        mRatingsRecycler.smoothScrollToPosition(0);
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Add rating failed", e);

                        // Show failure message and hide keyboard
                        hideKeyboard();
                        Snackbar.make(findViewById(android.R.id.content), "Failed to add rating",
                                Snackbar.LENGTH_SHORT).show();
                    }
                });
    }

    private void onServiceLoaded(Service service) {
        mNameView.setText(service.getName());
        mRatingIndicator.setRating((float) service.getAvgRating());
        mNumRatingsView.setText(getString(R.string.fmt_num_ratings, service.getNumRatings()));
        mCityView.setText(service.getCity());
        mCategoryView.setText(service.getCategory());
        mExperienceView.setText(service.getExperience());
        mDescriptionView.setText(service.getDescription());
//        mOptionsView.setText("Here there will be an array of type of sub categories of service");
        mWebsiteView.setText(service.getLink());
        mContactView.setText(String.valueOf(service.getPhoneNumber()));
        // Background image
        Glide.with(mImageView.getContext())
                .load(service.getImageLogo())
                .into(mImageView);
    }

    @Override
    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
        if (e != null) {
            Log.w(TAG, "Service:onEvent", e);
            return;
        }
        assert documentSnapshot != null;
        service = documentSnapshot.toObject(Service.class);
        service.setDocumentId(documentSnapshot.getId());
        onServiceLoaded(service);
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}