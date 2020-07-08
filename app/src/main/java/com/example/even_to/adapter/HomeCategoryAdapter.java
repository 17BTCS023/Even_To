package com.example.even_to.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.even_to.R;

import java.util.HashMap;
import java.util.List;

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder>  {

    List<HashMap<String, Object>> categoryList;
    private OnCategoryListener mOnCategoryListener;


    public HomeCategoryAdapter(List<HashMap<String, Object>> categoryList, OnCategoryListener onCategoryListener) {
        this.categoryList = categoryList;
        this.mOnCategoryListener = onCategoryListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_category, viewGroup, false);
        return new ViewHolder(view, mOnCategoryListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        HashMap<String, Object> map = categoryList.get(position);
        viewHolder.imageCategory.setImageResource((Integer) map.get("Image"));
        viewHolder.textTitle.setText(String.valueOf(map.get("Title")));
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageCategory;
        TextView textTitle;
        OnCategoryListener onCategoryListener;

        public ViewHolder(@NonNull View itemView, OnCategoryListener onCategoryListener) {
            super(itemView);
            imageCategory = itemView.findViewById(R.id.image_category);
            textTitle = itemView.findViewById(R.id.text_title);
            this.onCategoryListener = onCategoryListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCategoryListener.onCategoryClick(getAdapterPosition());
        }
    }
    public interface OnCategoryListener {
        void onCategoryClick(int position);
    }
    @Override
    public int getItemCount() {
        return categoryList.size();
    }

}

