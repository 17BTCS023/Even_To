package com.example.even_to.navigation.services.newService;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.even_to.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AddServiceImage extends AppCompatActivity {
    private static final String TAG = "AddServiceImage";

    /* Image size: 185px height ,275px width*/

    MaterialButton materialButtonSelectFile, materialButtonUplaodFile;
    ImageView serviceImage;
    private static boolean USER_ATTACHED_FILE = false, IMAGE_UPLOADED= false;
    private static final int PICK_IMAGE_REQUEST = 1;
    String displayNameOfFile,imageUri, mCategory;
    Uri selectedImageUri,downloadedImageUri;
    UploadTask UploadTask;

    //getting reference for StorageReference
    StorageReference mStorageReference;

    FirebaseFirestore dbInstance = FirebaseFirestore.getInstance();
    private DocumentReference manyServiceReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service_image);


        materialButtonSelectFile = findViewById(R.id.btn_select_file);
        materialButtonUplaodFile = findViewById(R.id.btn_upload_file);
        serviceImage =findViewById(R.id.image_view_service);
        mCategory = getIntent().getStringExtra("category");

        // get the reference of the database
        mStorageReference = FirebaseStorage.getInstance().getReference("serviceLogo ");
        
        materialButtonSelectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();
                Toast.makeText(getApplicationContext(), "Select File", Toast.LENGTH_SHORT).show();
            }
        });
        materialButtonUplaodFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(USER_ATTACHED_FILE){
                    imageUri = UploadImageToStorage();
                    UploadImageToFirestore(imageUri);
                }
                else{
                    Toast.makeText(AddServiceImage.this, "Cannot upload nothing", Toast.LENGTH_SHORT).show();
                    return;
                }

                
            }
        });
    }

    private void UploadImageToFirestore(String imageUri) {
        //get the user id
        FirebaseAuth auth = FirebaseAuth.getInstance();
        manyServiceReference = dbInstance
                .collection("services").document(auth.getCurrentUser().getUid())
                .collection("myServices").document(mCategory);

        ServiceImageModel serviceImage = new ServiceImageModel(imageUri);
        manyServiceReference.set(serviceImage, SetOptions.merge())
        .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AddServiceImage.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, e.toString());
            }
        });

    }

    private void chooseFile() {
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    // Find the extension of the file, so that it can be stored in that form
    private String getFileExtension(Uri uri) {
        ContentResolver typeOfFile = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(typeOfFile.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            USER_ATTACHED_FILE = true;
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
            serviceImage.setImageURI(selectedImageUri);
        }
    }

    private String UploadImageToStorage() {
        final StorageReference filePathReference = mStorageReference.child(System.currentTimeMillis() + "." + getFileExtension(selectedImageUri));
        UploadTask = (com.google.firebase.storage.UploadTask) filePathReference.putFile(selectedImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        //Get the image of the file
                        filePathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                IMAGE_UPLOADED= true;
                                downloadedImageUri = uri;
                                imageUri = downloadedImageUri.toString().trim();
                                Log.d("CHECK", "URL" + downloadedImageUri);
                                Log.d("CHECK", "String of URL" + imageUri);

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
                        Toast.makeText(AddServiceImage.this, "Service not added", Toast.LENGTH_SHORT).show();
                        
                        Log.d("CHECK", "onFailure: " + e.getMessage());
                    }
                });

        return imageUri;
    }
}