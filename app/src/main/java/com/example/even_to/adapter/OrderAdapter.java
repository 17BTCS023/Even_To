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
import com.example.even_to.model.Order;
import com.example.even_to.model.Service;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

public class OrderAdapter extends FirestoreAdapter<OrderAdapter.ViewHolder>{

    public interface OnOrderSelectedListener {
//        void onOrderSelected(DocumentSnapshot order);
        void onUnHireClick(DocumentSnapshot order);
        void onViewProfileClick(DocumentSnapshot order);

    }


    private OnOrderSelectedListener mListener;

    public OrderAdapter(Query query, OnOrderSelectedListener listener) {
        super(query);
        this.mListener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.order_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        holder.bind(getSnapshot(position), mListener);
    }
    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView nameView, descriptionView, linkView, categoryView, cityView, contactView;
        FloatingActionButton edit;
        MaterialButton mUnHire, mViewService;


        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.service_detail_image);
            nameView = itemView.findViewById(R.id.service_detail_name);
            edit = itemView.findViewById(R.id.fab_service_detail_add_review);
            mUnHire = itemView.findViewById(R.id.btn_unhire);
            mViewService = itemView.findViewById(R.id.btn_view_service);
            descriptionView = itemView.findViewById(R.id.service_detail_description);
            linkView = itemView.findViewById(R.id.service_detail_website_link);
            categoryView = itemView.findViewById(R.id.service_detail_category);
            cityView = itemView.findViewById(R.id.service_detail_city);
            contactView = itemView.findViewById(R.id.service_detail_contact_number);
        }
        public void bind(final DocumentSnapshot snapshot,
                         final OnOrderSelectedListener listener) {

            Order order = snapshot.toObject(Order.class);
            order.setImageLogo((String) snapshot.get(Service.KEY_LOGO));
            Resources resources = itemView.getResources();

            Log.d("M!!!", "bind: " +"uri :" +order.getImageLogo()+
                    "\nname:"+ order.getName()+
                    "\ncity: " +order.getCity()+
                    "\ncategory: " + order.getCategory()+
                    "\ndescription" + order.getDescription()+
                    "\nlink: " + order.getLink()+
                    "\ncontact: "+ order.getPhoneNumber()) ;

            assert order != null;
            Glide.with(imageView.getContext())
                    .load(order.getImageLogo())
                    .into(imageView);

            nameView.setText(order.getName());
            cityView.setText(order.getCity());
            categoryView.setText(order.getCategory());
            descriptionView.setText(order.getDescription());
            linkView.setText(order.getLink());
            contactView.setText(String.valueOf(order.getPhoneNumber()));

            // Click listener
            mUnHire.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onUnHireClick(snapshot);
                    }
                }
            });
            mViewService.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onViewProfileClick(snapshot);
                    }
                }
            });

        }

    }
}





