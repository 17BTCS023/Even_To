package com.example.even_to.ui_navigation.services.newService;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.even_to.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;

import static android.widget.Toast.LENGTH_SHORT;

public class AddNewService extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static boolean USER_ATTACHED_FILE = false, IMAGE_UPLOADED= false;

    private static final String TAG = "AddNewService";

    private static final String KEY_SKILLS = "skills";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE_NO = "phone";
    private static final String KEY_SERVICE_TYPE = "type";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_EXPERIENCE = "experience";
    private static final String KEY_PORTFOLIO = "image_name";
    private static final String KEY_LOGO = "image_logo";
    private static final String KEY_LINK = "link";

    TextInputEditText name, type, phone, description, skills, info, portfolio;
    AutoCompleteTextView category, experience;
    ImageButton attach;
    Button addService;

    ProgressDialog progressDialog;
    String displayNameOfFile;
    UploadServiceInfo data;

    //getting reference for StorageReference
    StorageReference mStorageReference;

    FirebaseFirestore dbInstance = FirebaseFirestore.getInstance();
    private DocumentReference manyServiceReference;

    Uri selectedImageUri;
    Uri downloadedImageUri;
    String mLogo;
    String imageUri;

    String mName,mType,mCategory,mPhone,mDescription,mExperience,mInfo,mSkills,mPortfolio;

    String[] COUNTRIES = new String[]{"1-6 months", "7-10 months", "1-2 year", "2-3years", "more than 3 years"};
    String[] CATEGORIES = new String[]{"Food", "Drinks", "Bakery", "Gift", "Decor", "Photography"};
    UploadTask UploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_service);
        name = findViewById(R.id.order_name);
        type = findViewById(R.id.order_service_type);
        category = findViewById(R.id.order_category);
        phone = findViewById(R.id.order_mobile_number);
        description = findViewById(R.id.order_description);
        experience = findViewById(R.id.order_experience);
        skills = findViewById(R.id.order_skills);
        info = findViewById(R.id.order_additional_info);
        portfolio = findViewById(R.id.order_portfolio);
        attach = findViewById(R.id.attach_file);
        addService = findViewById(R.id.add_service);
        progressDialog = new ProgressDialog(getApplicationContext());// context name as per your project name


        // get the reference of the database
        mStorageReference = FirebaseStorage.getInstance().getReference("serviceLogo ");

        ArrayAdapter adapter =
                new ArrayAdapter(this,
                        R.layout.support_simple_spinner_dropdown_item,
                        COUNTRIES);
        experience.setAdapter(adapter);

        ArrayAdapter adapter5 =
                new ArrayAdapter(
                        this,
                        R.layout.support_simple_spinner_dropdown_item,
                        CATEGORIES);
        category.setAdapter(adapter5);


        attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                USER_ATTACHED_FILE = true;
                chooseFile();
                Toast.makeText(getApplicationContext(), "Select File", Toast.LENGTH_SHORT).show();
            }
        });

        addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (USER_ATTACHED_FILE) {
                    imageUri = UploadImage();
                    Log.d("CHECK", "onClick: Yes, We will upload the image");
                }if (IMAGE_UPLOADED) {
                    UploadService(imageUri);
                }
            }
        });
    }

    private void UploadService(String imageUri) {

        mName = name.getText().toString().trim();
        mType = type.getText().toString().trim();
        mCategory = category.getText().toString().trim();
        mDescription = description.getText().toString().trim();
        mPhone = phone.getText().toString().trim();
        mExperience = experience.getText().toString().trim();
        mPortfolio = portfolio.getText().toString().trim();
        mInfo = info.getText().toString().trim();
        mLogo = imageUri;
        mSkills = skills.getText().toString().trim();

        Log.d("CHECK", "onClick: " + mName + ", " + mType
                + ", " + mCategory + ", " + mDescription
                + ", " + mPhone + ", " + mExperience + ", " + mPortfolio
                + ", " + mInfo + ", " + mSkills + ", " +mLogo);

        HashMap<String, Object> newService = new HashMap<>();
        newService.put(KEY_NAME, mName);
        newService.put(KEY_PHONE_NO, mPhone);
        newService.put(KEY_SERVICE_TYPE, mType);
        newService.put(KEY_CATEGORY, mCategory);
        newService.put(KEY_EXPERIENCE, mExperience);
        newService.put(KEY_LINK, mInfo);
        newService.put(KEY_SKILLS, mSkills);
        newService.put(KEY_PORTFOLIO, mPortfolio);
        newService.put(KEY_DESCRIPTION, mDescription);
        newService.put(KEY_LOGO,mLogo);

        //get the user id
        FirebaseAuth auth = FirebaseAuth.getInstance();
        manyServiceReference = dbInstance
                .collection("services").document(auth.getCurrentUser().getUid())
                .collection(mCategory).document(mName);

        manyServiceReference.set(newService)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        Toast.makeText(AddNewService.this, "Service Added", LENGTH_SHORT).show();
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

    private String UploadImage() {
        final StorageReference filePathReference = mStorageReference.child(System.currentTimeMillis() + "." + getFileExtension(selectedImageUri));
        UploadTask = (com.google.firebase.storage.UploadTask) filePathReference.putFile(selectedImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        //Get the image of the file
                        filePathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                downloadedImageUri = uri;
                                imageUri = downloadedImageUri.toString().trim();
                                Log.d("CHECK", "URL" + downloadedImageUri);
                                Log.d("CHECK", "String of URL" + imageUri);

                                IMAGE_UPLOADED= true;
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("CHECK", "onFailure: Could not get URL");
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddNewService.this, "Service not added", Toast.LENGTH_SHORT).show();
                        Log.d("CHECK", "onFailure: " + e.getMessage());
                    }
                });

        return imageUri;
    }

    // Find the extension of the file, so that it can be stored in that form
    private String getFileExtension(Uri uri) {
        ContentResolver typeOfFile = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(typeOfFile.getType(uri));
    }

    private void chooseFile() {
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            // Get the Uri of the selected file
            selectedImageUri = data.getData();
            String uriString = selectedImageUri.toString();
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();
            displayNameOfFile = null;

            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = getApplicationContext().getContentResolver().query(selectedImageUri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayNameOfFile = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                displayNameOfFile = myFile.getName();
            }
            portfolio.setText(displayNameOfFile);
        }

    }
}
