package com.example.even_to.navigation.orders;

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
import com.example.even_to.adapter.ListAdapter;

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
        orderRecyclerView.setLayoutManager(layoutManager);
        ListAdapter orderAdapter = new ListAdapter();
        orderRecyclerView.setAdapter(orderAdapter);

    }

}
