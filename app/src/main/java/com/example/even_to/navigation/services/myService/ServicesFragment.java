package com.example.even_to.navigation.services.myService;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.even_to.R;
import com.example.even_to.adapter.ListAdapter;
import com.example.even_to.navigation.profile.ProfileViewModel;
import com.example.even_to.navigation.services.newService.ServiceModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ServicesFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private RecyclerView serviceRecyclerViewList;
    TextView textView;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference serviceRef ;

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
        textView = view.findViewById(R.id.text_view_data);


    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth = FirebaseAuth.getInstance();
        serviceRef = db.collection("services").document(firebaseAuth.getUid())
                .collection("myServices");
        serviceRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
               String data = "";
                if(e!= null){
                    Log.d("CHECK", e.toString());
                    return;
                }
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    ServiceModel myService = documentSnapshot.toObject(ServiceModel.class);
                    myService.setDocumentId(documentSnapshot.getId());

                    String documentId = myService.getDocumentId();
                    String name = myService.getName();
                    String category = myService.getCategory();
                    String capacity = myService.getCapacity();
                    String experience = myService.getExperience();
                    Long phone = myService.getPhone();
                    String description = myService.getDescription();
                    String link = myService.getLink();

                    data += "\nid: "+ documentId + "\nname: " + name + "\ncategory: " + category +
                            "\ncapacity: " + capacity + "\nexp: " + experience + "\nphone: " + phone+
                            "\ndescription: " + description + "\nlink" + link + "_________________";
                }
                textView.setText(data);
            }
        });
    }
}
