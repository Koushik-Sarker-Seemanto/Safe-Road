package com.example.bdafahim.driver;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Driver_login extends AppCompatActivity {

    private EditText email,password,carNo;
    private Button signin,signup;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);

        email = findViewById(R.id.drivermailID);
        password = findViewById(R.id.driverPasswordId);
        signin = findViewById(R.id.driverSignInButtonId);
        signup = findViewById(R.id.driverSignUpButtonId);
        mAuth = FirebaseAuth.getInstance();
        carNo = findViewById(R.id.driverCarNo);
        progressDialog = new ProgressDialog(this);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SIGNIN();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Driver_login.this,Driver_SignUp.class));
            }
        });


    }
    private void SIGNIN()
    {

         String Email,Password;
        Email = email.getText().toString().trim();
        Password  = password.getText().toString().trim();
        final String plate = carNo.getText().toString().trim();

        if(TextUtils.isEmpty(Email)){
            //Toast.makeText(User_Login.this,"Please Enter your Email",Toast.LENGTH_SHORT).show();
            email.setError("Email Required");
            email.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(Password) || Password.length()<6){
            //Toast.makeText(User_Login.this,"Please Enter your Password",Toast.LENGTH_SHORT).show();
            password.setError("Valid password needed");
            password.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(plate))
        {
            carNo.setError("Carno. Required");
            carNo.requestFocus();
            return;
        }

        progressDialog.setMessage("Log in is in progress");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                             finish();
                            progressDialog.dismiss();
                            Toast.makeText(Driver_login.this,"User Login Successful",Toast.LENGTH_SHORT)
                                    .show();
                            //startActivity(new Intent(getApplicationContext(),Doctor_Profile.class));
                            Intent i =new Intent(Driver_login.this,Driver_Profile.class);
                            i.putExtra("CarNo",plate);
                            startActivity(i);
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(Driver_login.this,task.getException().getMessage(),Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });

    }
    /*@Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }*/
}
