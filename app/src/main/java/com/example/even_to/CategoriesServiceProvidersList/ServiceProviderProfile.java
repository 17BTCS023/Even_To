package com.example.even_to.CategoriesServiceProvidersList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.even_to.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

import static android.widget.Toast.LENGTH_SHORT;

public class ServiceProviderProfile extends AppCompatActivity {


    private static final String TAG = "ServiceProviderProfile";
    //variables
    ImageView mProfilePic;
    MaterialTextView mFullName, mPhoneNumber,  mLocation, mAbout, mOccupation;
    MaterialButton mUpdateProfile;
    TextView mDisplayName, mDisplayAbout, mDisplayOccupation;

    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE_NO = "phoneNumber";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_ABOUT = "about";
    private static final String KEY_OCCUPATION = "occupation";
    private static final String KEY_PROFILE_PIC = "photo";

    FirebaseFirestore dbInstance = FirebaseFirestore.getInstance();

    private DocumentReference profileReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_profile);

        String userId = getIntent().getStringExtra("serviceUserId");


        mProfilePic = findViewById(R.id.user_profile_pic);
        mFullName = findViewById(R.id.user_profile_full_name);
        mPhoneNumber = findViewById(R.id.user_profile_phone_no);
        mLocation = findViewById(R.id.user_profile_location);

        mAbout = findViewById(R.id.et_user_profile_about);
        mOccupation = findViewById(R.id.et_user_profile_occupation);
        mDisplayName = findViewById(R.id.tv_user_profile_name);
        mDisplayAbout = findViewById(R.id.tv_user_profile_about);
        mDisplayOccupation =findViewById(R.id.tv_user_profile_occupation);
        mUpdateProfile = findViewById(R.id.profile_update);

        profileReference = dbInstance.collection("profile").document(userId);

    }

    @Override
    public void onStart() {
        super.onStart();

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
                            }

                        } else {
                            Toast.makeText(ServiceProviderProfile.this, "Document Not Found", LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ServiceProviderProfile.this, "Error!", LENGTH_SHORT).show();
                        Log.d(TAG, "onFailure: " + e.toString());
                    }
                });
    }
}
