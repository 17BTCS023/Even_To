package com.example.even_to.navigation.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.even_to.R;
import com.example.even_to.model.User;
import com.example.even_to.utils.SharedPref;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import static android.widget.Toast.LENGTH_SHORT;

public class ProfileFragment extends Fragment {

    //constructor
    public ProfileFragment() {
        // simply
    }

    //variables
    ImageView mProfilePic;
    TextInputEditText mFullName, mPhoneNumber, mLocation, mAbout, mOccupation;
    MaterialButton mUpdateProfile;
    TextView mDisplayName, mDisplayAbout, mDisplayOccupation;
    FloatingActionButton mSelectProfilePic;
    StorageReference mStorageReference;

    ProgressBar progressBar ;

    SharedPref sharedPref;

    private static final String TAG = "ProfileFragment";

    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE_NO = "phoneNumber";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_ABOUT = "about";
    private static final String KEY_OCCUPATION = "occupation";
    private static final String KEY_PROFILE_PIC = "photo";

    String userId, userEmail;

    FirebaseFirestore dbInstance = FirebaseFirestore.getInstance();

    private DocumentReference profileReference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the Profile fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        sharedPref = new SharedPref(getContext().getApplicationContext());

        mProfilePic = view.findViewById(R.id.user_profile_pic);
        mFullName = view.findViewById(R.id.user_profile_full_name);
        mPhoneNumber = view.findViewById(R.id.user_profile_phone_no);
        mLocation = view.findViewById(R.id.user_profile_location);

        mAbout = view.findViewById(R.id.et_user_profile_about);
        mOccupation = view.findViewById(R.id.et_user_profile_occupation);
        mDisplayName = view.findViewById(R.id.tv_user_profile_name);
        mDisplayAbout = view.findViewById(R.id.tv_user_profile_about);
        mDisplayOccupation = view.findViewById(R.id.tv_user_profile_occupation);
        mUpdateProfile = view.findViewById(R.id.profile_update);
        mSelectProfilePic = view.findViewById(R.id.fab_add_picture);
        progressBar = view.findViewById(R.id.progress_bar);
        mStorageReference = FirebaseStorage.getInstance().getReference("profilePic");

        //get the user id
        FirebaseAuth auth = FirebaseAuth.getInstance();
        profileReference = dbInstance.collection("profile").document(auth.getCurrentUser().getUid());

        mSelectProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SelectProfilePic.class));
            }
        });

        mUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateProfile();
            }
        });
        return view;
    }


    private void UpdateProfile() {
        String dbFullName, dbPhoneNumber, dbLocation, dbAbout, dbOccupation;
        dbFullName = mFullName.getText().toString().trim();
        dbPhoneNumber = mPhoneNumber.getText().toString().trim();
        dbLocation = mLocation.getText().toString().trim();
        dbAbout = mAbout.getText().toString().trim();
        dbOccupation = mOccupation.getText().toString().trim();
        userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //Log.d(TAG, "onClick: " + dbFullName + ", " + dbAbout + ",  + dbEmail" + ", " + dbLocation + ",  + dbPassword " + ", " + dbPhoneNumber + ", " + dbOccupation);

        User user = new User(dbFullName, userEmail, userId, dbPhoneNumber, dbAbout, dbOccupation, dbLocation);

        profileReference.set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "Profile Updated", LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Not saved", LENGTH_SHORT).show();
                        Log.d(TAG, "onFailure: " + e.toString());
                    }
                });
    }


    @Override
    public void onStart() {
        super.onStart();
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
        profileReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            HashMap<String, Object> loadedProfile = (HashMap<String, Object>) documentSnapshot.getData();
                            mDisplayName.setText((CharSequence) loadedProfile.get(KEY_NAME));
                            mFullName.setText((CharSequence) loadedProfile.get(KEY_NAME));
                            mAbout.setText((CharSequence) loadedProfile.get(KEY_ABOUT));
                            mDisplayAbout.setText((CharSequence) loadedProfile.get(KEY_ABOUT));
                            mPhoneNumber.setText((CharSequence) loadedProfile.get(KEY_PHONE_NO));
                            mLocation.setText((CharSequence) loadedProfile.get(KEY_LOCATION));
                            mOccupation.setText((CharSequence) loadedProfile.get(KEY_OCCUPATION));
                            mDisplayOccupation.setText((CharSequence) loadedProfile.get(KEY_OCCUPATION));

                            if (loadedProfile.containsKey(KEY_PROFILE_PIC)) {
                                Glide.with(mProfilePic.getContext())
                                        .load(loadedProfile.get(KEY_PROFILE_PIC))
                                        .into(mProfilePic);
                                sharedPref.setProfileStatus();
                            }
                            progressBar.setVisibility(View.GONE);
                            progressBar.setIndeterminate(false);

                        } else {
                            progressBar.setVisibility(View.GONE);
                            progressBar.setIndeterminate(false);
                            Toast.makeText(getContext(), "Document Not Found", LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Error!", LENGTH_SHORT).show();
                        Log.d(TAG, "onFailure: " + e.toString());
                        progressBar.setVisibility(View.GONE);
                        progressBar.setIndeterminate(false);
                    }
                });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
