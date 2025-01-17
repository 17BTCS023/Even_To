package com.example.even_to.LoginAndSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.even_to.MainHomeScreen;
import com.example.even_to.utils.SharedPref;
import com.example.even_to.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    // initializing the components inside the xml file
    private EditText logInEmail;
    private EditText logInPassword;
    Button buttonlogIn;
    Button goToSignUp;
    CheckBox rememberMe;
    //Adding FirebaseAuth
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // creating a link between the variables declared in .java to .xml components
        // by using findViewById()
        logInEmail = findViewById(R.id.etLogInEmailName);
        logInPassword = findViewById(R.id.etLogInPassword);
        buttonlogIn = findViewById(R.id.btnLogIn);
        goToSignUp = findViewById(R.id.btnCreateAccount_GoToSignUp);
        rememberMe = findViewById(R.id.checkBox);
        progressBar = findViewById(R.id.progress_bar);


        // If NO, continue and let user login
        // creating on click listener for the login button

        buttonlogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setIndeterminate(true);
                final String logInEmailString = logInEmail.getText().toString().trim();
                final String logInPasswordString = logInPassword.getText().toString().trim();
                firebaseAuth = FirebaseAuth.getInstance();

                if (logInEmailString.isEmpty()) {
                    progressBar.setVisibility(View.GONE);
                    progressBar.setIndeterminate(false);
                    logInEmail.setError("Please enter your email id");
                    logInEmail.requestFocus();
                } else if (logInPasswordString.isEmpty()) {
                    progressBar.setVisibility(View.GONE);
                    progressBar.setIndeterminate(false);
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
                                Toast.makeText(LoginActivity.this, "Welcome Back! ", Toast.LENGTH_SHORT).show();
                                // If user has checked the remember me then store the values
                                if (rememberMe.isChecked()) {
                                    // If the login is successful save the email and password for the user
                                    Log.i("TEST", "The box was checked ");
                                    SharedPref sharedPreferences = new SharedPref(getApplicationContext());
                                    sharedPreferences.setRememberSecond();
                                }

                                progressBar.setVisibility(View.GONE);
                                progressBar.setIndeterminate(false);
                                // if the signup was successful, go to LogIn activity using intent
                                startActivity(new Intent(LoginActivity.this, MainHomeScreen.class));
                                finish();

                            } else {
                                // if not added
                                progressBar.setVisibility(View.GONE);
                                progressBar.setIndeterminate(false);
                                Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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
}
