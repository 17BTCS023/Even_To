package com.example.even_to.navigation.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.even_to.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import static android.widget.Toast.LENGTH_SHORT;

public class ProfileFragment extends Fragment {

    //constructor
    public ProfileFragment(){
        // simply
    }

    //variables
    private ProfileViewModel profileViewModel;
    TextInputEditText mFullName, mPhoneNumber, mEmail, mPassword, mLocation, mAbout, mOccupartion;
    MaterialButton mUpdateProfile;
    TextView mDisplayName, mDisplayAbout, mDisplayOccupation;
    FirebaseDatabase firebaseDatabase;

    private static final String TAG = "ProfileFragment";
    private static final String KEY_NAME= "fullname";
    private static final String KEY_PHONE_NO= "phone";
    private static final String KEY_EMAIL= "email";
    private static final String KEY_PASSWORD= "password";
    private static final String KEY_LOCATION= "location";
    private static final String KEY_ABOUT= "about";
    private static final String KEY_OCCUPATION= "occupation";

    String userId, userEmail;

    FirebaseFirestore dbInstance = FirebaseFirestore.getInstance();
    private DocumentReference profileReference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the Profile fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mFullName = view.findViewById(R.id.user_profile_full_name);
        mPhoneNumber = view.findViewById(R.id.user_profile_phone_no);
        mEmail =  view.findViewById(R.id.user_profile_email);
        mPassword =  view.findViewById(R.id.user_profile_password);
        mLocation  =  view.findViewById(R.id.user_profile_location);

        mAbout = view.findViewById(R.id.et_user_profile_about);
        mOccupartion = view.findViewById(R.id.et_user_profile_occupation);
        mDisplayName = view.findViewById(R.id.tv_user_profile_name);
        mDisplayAbout = view.findViewById(R.id.tv_user_profile_about);
        mDisplayOccupation = view.findViewById(R.id.tv_user_profile_occupation);
        mUpdateProfile =  view.findViewById(R.id.profile_update);

        //get the user id
        FirebaseAuth auth = FirebaseAuth.getInstance();
        profileReference = dbInstance.collection("profile").document(auth.getCurrentUser().getUid());

        mUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dbFullName, dbPhoneNumber, dbEmail, dbPassword, dbLocation, dbAbout, dbOccupation;
                dbFullName = mFullName.getText().toString().trim();
                dbPhoneNumber = mPhoneNumber.getText().toString().trim();
                dbEmail = mEmail.getText().toString().trim();
                dbPassword =mPassword.getText().toString().trim();;
                dbLocation = mLocation.getText().toString().trim();;
                dbAbout =mAbout.getText().toString().trim();;
                dbOccupation = mOccupartion.getText().toString().trim();

                Log.d(TAG, "onClick: "+ dbFullName + ", " + dbAbout
                        + ", " + dbEmail + ", " + dbLocation
                        + ", " + dbPassword + ", " + dbPhoneNumber + ", " + dbOccupation);

                HashMap<String, Object> profile= new HashMap<>();
                profile.put(KEY_NAME, dbFullName);
                profile.put(KEY_EMAIL , dbEmail);
                profile.put(KEY_PASSWORD , dbPassword);
                profile.put(KEY_PHONE_NO , dbPhoneNumber);
                profile.put(KEY_LOCATION , dbLocation);
                profile.put(KEY_ABOUT, dbAbout);
                profile.put(KEY_OCCUPATION, dbOccupation);

                profileReference.set(profile)
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
                                Log.d(TAG, "onFailure: " + e.toString() );
                            }
                        });

            }
        });
        return view;


    }

    @Override
    public void onStart() {
        super.onStart();

        profileReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            HashMap<String, Object> loadedProfile= (HashMap<String, Object>) documentSnapshot.getData();
                            mDisplayName.setText((CharSequence) loadedProfile.get(KEY_NAME));
                            mFullName.setText((CharSequence) loadedProfile.get(KEY_NAME));
                            mAbout.setText((CharSequence) loadedProfile.get(KEY_ABOUT));
                            mDisplayAbout.setText((CharSequence) loadedProfile.get(KEY_ABOUT));
                            mEmail.setText((CharSequence) loadedProfile.get(KEY_EMAIL));
                            mPassword.setText((CharSequence) loadedProfile.get(KEY_PASSWORD));
                            mPhoneNumber.setText((CharSequence) loadedProfile.get(KEY_PHONE_NO));
                            mLocation.setText((CharSequence) loadedProfile.get(KEY_LOCATION));
                            mOccupartion.setText((CharSequence) loadedProfile.get(KEY_OCCUPATION));
                            mDisplayOccupation.setText((CharSequence) loadedProfile.get(KEY_OCCUPATION));

                        }else {
                            Toast.makeText(getContext(), "Document Not Found", LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Error!", LENGTH_SHORT).show();
                        Log.d(TAG, "onFailure: " + e.toString() );
                    }
                });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
