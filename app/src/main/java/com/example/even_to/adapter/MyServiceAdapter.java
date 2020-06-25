package com.example.even_to.adapter;

import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.even_to.R;
import com.example.even_to.model.Service;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;


/**
 * RecyclerView adapter for a list of Services.
 */
public class MyServiceAdapter extends FirestoreAdapter<MyServiceAdapter.ViewHolder> {

    public MyServiceAdapter(Query query) {
        super(query);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.service_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.bind(getSnapshot(position), mListener);
        holder.bind(getSnapshot(position));

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView nameView, descriptionView, linkView, categoryView, cityView, contactView;
        FloatingActionButton edit;



        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.service_detail_image);
            nameView = itemView.findViewById(R.id.service_detail_name);
            descriptionView = itemView.findViewById(R.id.service_detail_description);
            linkView = itemView.findViewById(R.id.service_detail_website_link);
            categoryView = itemView.findViewById(R.id.service_detail_category);
            cityView = itemView.findViewById(R.id.service_detail_city);
            contactView = itemView.findViewById(R.id.service_detail_contact_number);
        }

        //        public void bind(final DocumentSnapshot snapshot,
//                         final OnRestaurantSelectedListener listener) {
        public void bind(final DocumentSnapshot snapshot) {

            Service serviceModel = snapshot.toObject(Service.class);
            serviceModel.setImageLogo((String) snapshot.get(Service.KEY_LOGO));
            Resources resources = itemView.getResources();

            Log.d("M!!!", "bind: " +"uri :" +serviceModel.getImageLogo()+
                    "\nname:"+ serviceModel.getName()+
                    "\ncity: " +serviceModel.getCity()+
                    "\ncategory: " + serviceModel.getCategory()+
                    "\ndescription" + serviceModel.getDescription()+
                    "\nlink: " + serviceModel.getLink()+
                    "\ncontact: "+ serviceModel.getPhoneNumber()) ;

            assert serviceModel != null;
            Glide.with(imageView.getContext())
                    .load(serviceModel.getImageLogo())
                    .into(imageView);

            nameView.setText(serviceModel.getName());
            cityView.setText(serviceModel.getCity());
            categoryView.setText(serviceModel.getCategory());
            descriptionView.setText(serviceModel.getDescription());
            linkView.setText(serviceModel.getLink());
            contactView.setText(String.valueOf(serviceModel.getPhoneNumber()));

//            // Click listener
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (listener != null) {
//                        listener.onRestaurantSelected(snapshot);
//                    }
//                }
//            });
        }

    }
}
