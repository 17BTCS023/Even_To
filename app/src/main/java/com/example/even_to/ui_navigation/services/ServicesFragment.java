package com.example.even_to.ui_navigation.services;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.even_to.R;
import com.example.even_to.ui_navigation.profile.ProfileViewModel;

public class ServicesFragment extends Fragment {

    private ProfileViewModel profileViewModel;

    //constructor
    public ServicesFragment(){
        // simply
    }

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        // Inflate the Profile fragment
        View view = inflater.inflate(R.layout.fragment_services, container, false);
        return view;
    }

}
