package com.example.even_to.login_signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.even_to.MainActivity;
import com.example.even_to.R;
import com.example.even_to.Utils.SharedPref;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class LoginActivity extends AppCompatActivity {

    // initializing the components inside the xml file
    private EditText logInEmail;
    private EditText logInPassword;
    Button buttonlogIn;
    Button goToSignUp;
    CheckBox rememberMe;
    //Adding FirebaseAuth
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        /******* Check if the sharedPreferences contains Key-value pair of email id and password *****/
        // if YES, redirect to Main/ Home Activity

        // If NO, continue and let user login

        // creating a link between the variables declared in .java to .xml components
        // by using findViewById()
        logInEmail = findViewById(R.id.etLogInEmailName);
        logInPassword = findViewById(R.id.etLogInPassword);
        buttonlogIn = findViewById(R.id.btnLogIn);
        goToSignUp = findViewById(R.id.btnCreateAccount_GoToSignUp);
        rememberMe = findViewById(R.id.checkBox);

        // creating on click listener for the login button

        buttonlogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String logInEmailString = logInEmail.getText().toString();
                final String logInPasswordString = logInPassword.getText().toString();
                firebaseAuth = FirebaseAuth.getInstance();

                if (logInEmailString.isEmpty()) {
                    logInEmail.setError("Please enter your email id");
                    logInEmail.requestFocus();
                } else if (logInPasswordString.isEmpty()) {
                    logInPassword.setError("Please enter a password");
                    logInPassword.requestFocus();
                } else if (!logInEmailString.isEmpty() && !logInPasswordString.isEmpty()) {


                    /*check both conditions
                       Using the FA variable add the user by passing the two strings : inputemail and inputPassword */
                    firebaseAuth.signInWithEmailAndPassword(logInEmailString, logInPasswordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        /* If the user id added then,
                        check whether the user was added successfully or not?*/
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Successfully Logged in!", Toast.LENGTH_SHORT).show();
                                Toast.makeText(LoginActivity.this, "Welcome Back, " + logInEmailString, Toast.LENGTH_SHORT).show();

                                // if the signup was successful, go to LogIn activity using intent
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();

                                // If user has checked the remember me then store the values
                                if(rememberMe.isChecked()) {
                                    // If the login is successful save the email and password for the user

                                    SharedPref sharedPreferences = new SharedPref(getApplicationContext());
                                    sharedPreferences.iAmOldUser();
//                                    storeDetails(logInEmailString, logInPasswordString);
                                }

                            } else {
                                // if not added
                                Toast.makeText(LoginActivity.this, "Wrong Passwrod or email id", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        // creating onClickListener for gotosignup button
        goToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                finish();
            }
        });
    }
//
//    /*** u can use this if you want to store the values in file, but here we just needed a variable
    // not using this method
//    private void storeDetails(String logInEmailString, String logInPasswordString) {
//
//
//
////        // Write the key-value pair in sharedPref file using editor
////        editor.putString(logInEmailString,logInPasswordString);
////        editor.commit();
//    }
}
