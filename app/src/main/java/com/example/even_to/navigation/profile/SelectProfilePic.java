package com.example.even_to.navigation.profile;

import androidx.activity.OnBackPressedCallback;
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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.even_to.MainHomeScreen;
import com.example.even_to.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SelectProfilePic extends AppCompatActivity {

    private static final String TAG = "SelectProfilePic";

    private static final String KEY_PROFILE_PIC = "photo";

    private static final int PICK_IMAGE_REQUEST = 1;
    private  boolean IMAGE_UPLOADED = false ;
    private boolean USER_ATTACHED_FILE = false;
    String displayNameOfFile,imageUri;
    private Uri selectedImageUri,downloadedImageUri;
    ImageView mProfilePic;

    ProgressBar progressBar ;

    UploadTask UploadTask;
    FirebaseAuth auth;

    MaterialButton btnSelect, btnUplaod;
    //getting reference for StorageReference
    StorageReference mStorageReference;
    private DocumentReference profileReference;
    int count = 0;

    @Override
    protected void onStart() {
        super.onStart();
        if(count == 0) {
            chooseFile();
            count = -1;
        };
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_profile_pic);

        mProfilePic = findViewById(R.id.selected_profile_pic);
        btnSelect = findViewById(R.id.btn_select_image_again);
        btnUplaod = findViewById(R.id.btn_ok);

        progressBar = findViewById(R.id.progress_bar);


        FirebaseFirestore dbInstance = FirebaseFirestore.getInstance();

        mStorageReference = FirebaseStorage.getInstance().getReference("profilePic");

        //get the user id
        auth = FirebaseAuth.getInstance();
        profileReference = dbInstance.collection("profile").document(auth.getUid());

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();
            }
        });

        btnUplaod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(USER_ATTACHED_FILE) {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setIndeterminate(true);
                    UploadImageToStorage();
                }
                else{
                    Toast.makeText(SelectProfilePic.this, "Select a photo", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }

    private String getFileExtension(Uri uri) {
        // Find the extension of the file, so that it can be stored in that form
        ContentResolver typeOfFile = getApplicationContext().getContentResolver();
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
            USER_ATTACHED_FILE = true;
            // Get the Uri of the selected file
            selectedImageUri = data.getData();
            String uriString = selectedImageUri.toString();
            File myFile = new File(uriString);
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
            mProfilePic.setImageURI(selectedImageUri);
        }
    }

    private void UploadImageToStorage() {
        final StorageReference filePathReference = mStorageReference.child(System.currentTimeMillis() + "." + getFileExtension(selectedImageUri));
        UploadTask = (com.google.firebase.storage.UploadTask) filePathReference.putFile(selectedImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        //Get the image of the file
                        filePathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                IMAGE_UPLOADED = true;
                                downloadedImageUri = uri;
                                imageUri = downloadedImageUri.toString().trim();
                                UploadImageToFirestore(imageUri);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: Could not get URL");
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: " + e.getMessage());
                    }
                });
    }



    private void UploadImageToFirestore(String imageUri) {
        Map<String, Object> photo = new HashMap<>();
        photo.put(KEY_PROFILE_PIC, imageUri);
        profileReference.set(photo, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent intent = new Intent(getApplicationContext(), MainHomeScreen.class);
                        progressBar.setVisibility(View.GONE);
                        progressBar.setIndeterminate(false);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, e.toString());
                    }
                });

    }

}