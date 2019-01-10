package com.example.bdafahim.driver;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Admin_login extends AppCompatActivity {

    private EditText email,password;
    private Button signIn ;
    private FirebaseAuth fAuto ;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        email = findViewById(R.id.adminmailID);
        password = findViewById(R.id.adminPasswordId) ;
        signIn = findViewById(R.id.adminSignInButtonId) ;

        fAuto = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SIGNIN();
            }
        });
    }

    private void SIGNIN()
    {

        String Email,Password;
        Email = email.getText().toString().trim();
        Password  = password.getText().toString().trim();

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


        progressDialog.setMessage("Log in is in progress");
        progressDialog.show();
        fAuto.signInWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(this,
                        new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            finish();
                            progressDialog.dismiss();
                            startActivity(new Intent(Admin_login.this,Adminstrator_Homepage.class));
                            Toast.makeText(Admin_login.this,"User Login Successful",Toast.LENGTH_SHORT)
                                    .show();
                            //startActivity(new Intent(getApplicationContext(),Doctor_Profile.class));
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(Admin_login.this,task.getException().getMessage(),Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });

    }
}
