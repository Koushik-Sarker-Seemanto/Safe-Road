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

public class Reporter_Login extends AppCompatActivity {

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

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SIGNIN();
            }
        });

        progressDialog = new ProgressDialog(this);
    }

    private void SIGNIN()
    {
        progressDialog.show();

        final String Email,Password;
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


        fAuto.signInWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            finish();
                            progressDialog.dismiss();
                            Toast.makeText(Reporter_Login.this,"User Login Successful",Toast.LENGTH_SHORT)
                                    .show();
                            Intent i = new Intent(getApplicationContext(),Reporter_Page.class);
                            String temp = Email.substring(0,Email.lastIndexOf('@'));
                            i.putExtra("domain",temp);
                            startActivity(i);
                        } else {
                             progressDialog.dismiss();
                            Toast.makeText(Reporter_Login.this,task.getException().getMessage(),Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });

    }
}
