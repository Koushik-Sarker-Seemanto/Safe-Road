package com.example.bdafahim.driver;

import android.app.ProgressDialog;
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
import com.google.firebase.database.FirebaseDatabase;

public class Reporter_SignUp extends AppCompatActivity {

    private EditText email,phoneNo,pass,area,profession,Name;
    private String Email,Phone,Pass,Area,Pro,name;
    private FirebaseAuth mAuth;
    private Button btn;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporter__sign_up);

        email = findViewById(R.id.reporterMailID);
        phoneNo = findViewById(R.id.reporterPhoneID);
        pass = findViewById(R.id.reporterPasswordId);
        area = findViewById(R.id.reporterAreaId);
        profession = findViewById(R.id.reporterProfessionId);
        btn = findViewById(R.id.reporterSignInButtonId);
        Name = findViewById(R.id.reporterNameID);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CREATE();
            }
        });
    }

    private void CREATE()
    {
        Email = email.getText().toString().trim();
        Phone= phoneNo.getText().toString().trim();
        Pass = pass.getText().toString().trim();
        Area = area.getText().toString().trim();
        Pro = profession.getText().toString().trim();
        name = Name.getText().toString().trim();

        if(TextUtils.isEmpty(Email)){
            //Toast.makeText(User_Login.this,"Please Enter your Email",Toast.LENGTH_SHORT).show();
            email.setError("Email Required");
            email.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(Pass) || Pass.length()<6){
            //Toast.makeText(User_Login.this,"Please Enter your Password",Toast.LENGTH_SHORT).show();
            pass.setError("Valid password needed");
            pass.requestFocus();
            return;
        }

        progressDialog.setMessage("Signup in is in progress");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(Email,Pass)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    finish();
                    progressDialog.dismiss();
                    Reporter_Info ri = new Reporter_Info(Email,Phone,Area,Pro,name);
                    String temp = Email.substring(0,Email.lastIndexOf('@'));
                    FirebaseDatabase.getInstance().getReference("Users").child("Reporter")
                            .child(temp).setValue(ri).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> ttask) {

                        if (ttask.isSuccessful()) {
                            Toast.makeText(Reporter_SignUp.this, "Resistration Successful", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            //display a failure message
                            Toast.makeText(Reporter_SignUp.this,ttask.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
                    Toast.makeText(Reporter_SignUp.this,"User Signup Successful",Toast.LENGTH_SHORT)
                            .show();
                    //startActivity(new Intent(getApplicationContext(),Doctor_Profile.class));
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(Reporter_SignUp.this,task.getException().getMessage(),Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }
}
