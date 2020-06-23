package com.example.even_to.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.even_to.R;
import com.example.even_to.model.Order;
import com.google.android.material.button.MaterialButton;

public class OrderAdapter extends RecyclerView.Adapter{


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_order_list_item, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder) holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return Order.ServiceProviderImages.length;
    }
    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView mServiceProviderImage;
        private TextView mServiceProviderName, mServiceProviderCategory, mServiceProviderDescription;
        private MaterialButton mActionChat, mActionUnhire;

        public ListViewHolder(View itemView){
            super(itemView);
            mServiceProviderImage = itemView.findViewById(R.id.service_provider_image);
            mServiceProviderName = itemView.findViewById(R.id.service_provider_name);
            mServiceProviderCategory =  itemView.findViewById(R.id.service_provider_category);
            mServiceProviderDescription =  itemView.findViewById(R.id.service_detail_description);
            mActionChat =  itemView.findViewById(R.id.service_provider_chat);
            mActionUnhire =   itemView.findViewById(R.id.service_provider_unhire);
            itemView.setOnClickListener(this);
        }

        public void bindView(int position){
            mServiceProviderImage.setImageResource(Order.ServiceProviderImages[position]);
            mServiceProviderName.setText(Order.ServiceProviderName[position]);
            mServiceProviderDescription.setText(Order.ServiceProviderDescription[position]);
            mServiceProviderCategory.setText(Order.ServiceProviderCategory[position]);
        }

        @Override
        public void onClick(View v) {

        }
    }
}




