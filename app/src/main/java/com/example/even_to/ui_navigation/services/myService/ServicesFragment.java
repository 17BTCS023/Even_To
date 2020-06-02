package com.example.even_to.ui_navigation.services.myService;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.even_to.R;
import com.example.even_to.ui_navigation.orders.ListAdapter;
import com.example.even_to.ui_navigation.profile.ProfileViewModel;

public class ServicesFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private RecyclerView serviceRecyclerViewList;

    //constructor
    public ServicesFragment(){
        // simply
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        // Inflate the Profile fragment
        View view = inflater.inflate(R.layout.fragment_services, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        serviceRecyclerViewList = view.findViewById(R.id.service_recycler_view_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        serviceRecyclerViewList.setLayoutManager(layoutManager);
        ListAdapter orderAdapter = new ListAdapter();
        serviceRecyclerViewList.setAdapter(orderAdapter);
    }
}
