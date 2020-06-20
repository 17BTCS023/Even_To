package com.example.even_to.navigation.services.newService;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.even_to.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static android.widget.Toast.LENGTH_SHORT;

public class AddNewService extends AppCompatActivity {

    private static final String TAG = "AddNewService";

    TextInputEditText name, phone, description, link, city;
    AutoCompleteTextView experience, capacity;
    Button addService;

    //getting reference for StorageReference
    StorageReference mStorageReference;

    FirebaseFirestore dbInstance = FirebaseFirestore.getInstance();
    private DocumentReference manyServiceReference;
    String mName, mCapacity, mCategory, mDescription, mExperience, mLink, mPhone,mCity;

    String[] EXPERIENCE = new String[]{"1-6 months", "7-10 months", "1-2 year", "2-3years", "more than 3 years"};
    String[] CAPACITY = new String[]{"10-30", "50-100", "100-500"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_service);


        name = findViewById(R.id.new_service_name);
        capacity = findViewById(R.id.new_service_capacity);
        phone = findViewById(R.id.new_service_mobile_number);
        description = findViewById(R.id.new_service_description);
        experience = findViewById(R.id.new_service_experience);
        link = findViewById(R.id.new_service_additional_info);
        addService = findViewById(R.id.add_service);
        city = findViewById(R.id.new_service_city);
        mCategory = getIntent().getStringExtra("category");

        // get the reference of the database
        mStorageReference = FirebaseStorage.getInstance().getReference("serviceLogo ");

        ArrayAdapter experienceAdapter =
                new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, EXPERIENCE);
        experience.setAdapter(experienceAdapter);

        ArrayAdapter adapter2 =
                new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, CAPACITY);
        capacity.setAdapter(adapter2);


        addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadService();

            }
        });
    }

    private void UploadService() {

        mName = name.getText().toString().trim();
        mCapacity = capacity.getText().toString().trim();
        mDescription = description.getText().toString().trim();
        mPhone = phone.getText().toString().trim();
        mExperience = experience.getText().toString().trim();
        mLink = link.getText().toString().trim();
        mCity = city.getText().toString().trim();
        if(mName.isEmpty()||mCategory.isEmpty() || mDescription.isEmpty() || mExperience.isEmpty()|| mLink.isEmpty() || mCity.isEmpty()){
            Toast.makeText(this, "Fill everything! '_' ", LENGTH_SHORT).show();
            return;
        }
        Log.d("CHECK", "onClick: " + mName + ", " + mCapacity + ", " + mCategory + ", " + mDescription + ", " + mPhone + ", " + mExperience + "," + mLink );

        ServiceModel newService = new ServiceModel(mName, mPhone, mCapacity, mCategory, mExperience,
                mLink,  mDescription, mCity);

        //get the user id
        FirebaseAuth auth = FirebaseAuth.getInstance();

        manyServiceReference = dbInstance
                .collection("services").document(auth.getCurrentUser().getUid())
                .collection("myServices").document(mCategory);
        manyServiceReference.set(newService)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddNewService.this, "Service Added", LENGTH_SHORT).show();
                        Intent intent = new Intent(AddNewService.this, AddServiceImage.class);
                        intent.putExtra("category", mCategory);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddNewService.this, "Error!", LENGTH_SHORT).show();
                        Log.d(TAG, "onFailure: " + e.toString());
                    }
                });
    }
}