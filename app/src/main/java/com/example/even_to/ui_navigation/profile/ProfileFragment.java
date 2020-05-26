package com.example.even_to.ui_navigation.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.even_to.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileFragment extends Fragment {

    //constructor
    public ProfileFragment(){
        // simply
    }

    //variables
    private ProfileViewModel profileViewModel;
    TextInputEditText mFullName, mPhoneNumber, mEmail, mPassword, mLocation;
    MaterialButton mUpdateProfile;
    FirebaseDatabase firebaseDatabase;




    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        // Inflate the Profile fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mFullName = view.findViewById(R.id.user_profile_name);
        mPhoneNumber = view.findViewById(R.id.user_profile_phone_no);
        mEmail =  view.findViewById(R.id.user_profile_email);
        mPassword =  view.findViewById(R.id.user_profile_password);
        mLocation  =  view.findViewById(R.id.user_profile_location);
        mUpdateProfile =  view.findViewById(R.id.profile_update);

        mUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        return view;
    }



    /***
     *  Pending:
     *
     *  1.  Storing the profile attributes into firebase
     *  2.  Updating and editing profile
     *  3.  Retrieve data from firebase
     *
     * */
}
