package com.example.even_to.ui_navigation.orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.even_to.R;
import com.google.android.material.button.MaterialButton;

public class OrderFragment extends Fragment {

    private OrderViewModel orderViewModel;
    private RecyclerView orderRecyclerView;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        orderRecyclerView = view.findViewById(R.id.orders_recycler_view_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        System.out.println(orderRecyclerView);
        orderRecyclerView.setLayoutManager(layoutManager);
        ListAdapter orderAdapter = new ListAdapter();
        orderRecyclerView.setAdapter(orderAdapter);

    }

}
