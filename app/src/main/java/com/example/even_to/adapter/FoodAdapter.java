package com.example.even_to.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.even_to.Category.FoodServiceItem;
import com.example.even_to.R;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodServiceViewHolder> {
    private ArrayList<FoodServiceItem> mFoodServiceItems;
    private  OnItemClickListener mListener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
    public static class FoodServiceViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageResource;
        public TextView mServiceProviderName;
        private TextView mServiceProviderDescription;
        public TextView mServiceProviderSize;
        public TextView mRatingStar;


        public FoodServiceViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener !=null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
            mImageResource = itemView.findViewById(R.id.service_provider_image);
            mServiceProviderName = itemView.findViewById(R.id.food_service_provider_name);
            mServiceProviderDescription = itemView.findViewById(R.id.fodd_service_provider_desc);
            mServiceProviderSize = itemView.findViewById(R.id.service_provider_capacity);
            mRatingStar = itemView.findViewById(R.id.service_rating);
        }
    }

    public FoodAdapter(ArrayList<FoodServiceItem> serviceItems){
        mFoodServiceItems = serviceItems;
    }

    @NonNull
    @Override
    public FoodServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_food_category_list_item, parent, false);
        return new FoodServiceViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodServiceViewHolder holder, int position) {
        FoodServiceItem currentItem = mFoodServiceItems.get(position);
        holder.mImageResource.setImageResource(currentItem.getmImageResource());
        holder.mServiceProviderName.setText(currentItem.getmServiceProviderName());
        holder.mServiceProviderDescription.setText(currentItem.getmServiceProviderDescription());
        holder.mServiceProviderSize.setText(currentItem.getmServiceProviderSize());
        holder.mRatingStar.setText(currentItem.getmRatingStar());
    }


    @Override
    public int getItemCount() {
        return mFoodServiceItems.size();
    }



}
