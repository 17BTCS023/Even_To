package com.example.even_to.adapter;

import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.even_to.R;
import com.example.even_to.model.Service;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

public class ServiceAdapter extends FirestoreAdapter<ServiceAdapter.ViewHolder>{

    @NonNull
    @Override
    public ServiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.item_service, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceAdapter.ViewHolder holder, int position) {
        holder.bind(getSnapshot(position), mListener);
    }

    public interface OnServiceSelectedListener{
        void onServiceSelected(DocumentSnapshot service);
    }
    private OnServiceSelectedListener mListener;

    public ServiceAdapter(Query query, OnServiceSelectedListener listener) {
        super(query);
        this.mListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView nameView;
        RatingBar ratingBar;
        TextView numRatingsView;
        TextView categoryView;
        TextView cityView;
        TextView experienceView;
        TextView capacityView;


        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.service_item_image);
            nameView = itemView.findViewById(R.id.service_item_name);
            ratingBar = itemView.findViewById(R.id.service_item_rating);
            numRatingsView = itemView.findViewById(R.id.service_item_num_ratings);
            experienceView = itemView.findViewById(R.id.service_item_experience);
            capacityView = itemView.findViewById(R.id.service_item_capacity);
            categoryView = itemView.findViewById(R.id.service_item_category);
            cityView = itemView.findViewById(R.id.service_item_city);
        }
        public void bind(final DocumentSnapshot snapshot, final OnServiceSelectedListener mListener) {
            Service service = snapshot.toObject(Service.class);
            Resources resources = itemView.getResources();
            // Load image
            assert service != null;
            Glide.with(imageView.getContext()).load(service.getImageLogo()).into(imageView);
            nameView.setText(service.getName());
            ratingBar.setRating((float) service.getAvgRating());
            cityView.setText(service.getCity());
            categoryView.setText(service.getCategory());
            numRatingsView.setText(resources.getString(R.string.fmt_num_ratings, service.getNumRatings()));
            experienceView.setText(service.getExperience());
            capacityView.setText(service.getCapacity());
            categoryView.setText(service.getCategory());

            // Click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.onServiceSelected(snapshot);
                    }
                }
            });
        }
        }

}

