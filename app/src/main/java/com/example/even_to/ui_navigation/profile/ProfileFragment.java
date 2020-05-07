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

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;

    //constructor
    public ProfileFragment(){
        // simply
    }

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        // Inflate the Profile fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        return view;
    }

    /***
     *  Pending:
     *
     *  1.  Storing the profile attributes into firebase
     *
     *
     * */
}
