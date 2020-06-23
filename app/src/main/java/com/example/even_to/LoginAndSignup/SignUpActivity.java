package com.example.even_to.LoginAndSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.even_to.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailId;
    private EditText password;
    Button signUp;
    Button goBackToLogIn;

    //Adding FirebaseAuth
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // to find out the component using email id
        emailId = findViewById(R.id.etEmalName);
        password = findViewById(R.id.etPassword);
        signUp =findViewById(R.id.btnSignUp);
        goBackToLogIn = findViewById(R.id.btnGoBackToLogIN);

        //if login button is pressed
        goBackToLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                finish();
            }
        });

        // When you click on SignUp button
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // take input from 2 edit texts in form of Strings
                String inputEmail =emailId.getText().toString().trim();
                String inputPassword =password.getText().toString().trim();
                firebaseAuth = FirebaseAuth.getInstance();  // Calling the variable for FirebaseAuth

                if(inputEmail.isEmpty()){  // If user submits null info
                    emailId.setError("Please enter your email id!");  // display error
                    emailId.requestFocus();
                }else if (inputPassword.isEmpty() ){  // check if password field is empty
                    password.setError("Please enter a password");  // display error
                    password.requestFocus();

                }else if(inputPassword.length() < 8 ){
                    password.setError("Min password length is 8");  // display error
                    password.requestFocus();
                }
                else if (!inputEmail.isEmpty()&&!inputPassword.isEmpty()){
                    /*check both conditions
                      Using the FA variable add the user by passing the two strings : inputemail and inputPassword
                    */
                    firebaseAuth.createUserWithEmailAndPassword(inputEmail,inputPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        /*
                        If the user id added then,
                        check whether the user was added successfully or not?
                         */
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SignUpActivity.this, "Account created!",Toast.LENGTH_SHORT).show();
                                // if the signup was successful, go to LogIn activity using intent
                                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                finish();
                                // show this toast
                            }else{
                                // if not added
                                Toast.makeText(SignUpActivity.this, task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
