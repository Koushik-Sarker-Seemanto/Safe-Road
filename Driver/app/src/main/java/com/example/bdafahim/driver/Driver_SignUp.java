package com.example.bdafahim.driver;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Driver_SignUp extends AppCompatActivity {

    private EditText name, area, age, plateno, phoneNo, email,password ;
    private Button signUpButton ;
    private RadioGroup radioGroup;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
private String Name,Area,Age,Plateno,Email,Password,Phone;
private int points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__sign_up);

        name = findViewById(R.id.DriverName_id);
        area = findViewById(R.id.driverAreaId);
        age = findViewById(R.id.driverAgeId);
        plateno = findViewById(R.id.PlatenumberId);
        email = findViewById(R.id.driverEmail_Id);
        password = findViewById(R.id.driverPassword_Id);
        radioGroup = findViewById(R.id.radio_group);
        phoneNo = findViewById(R.id.driverPhoneId);

        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        signUpButton=findViewById(R.id.driverSignUpButton_Id);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NEW_USER();
            }
        });
    }

    private void NEW_USER(){

        progressDialog.setMessage("Registering in process...");
        progressDialog.show();
         Name = name.getText().toString().trim();
         Area = area.getText().toString().trim();
         Age = age.getText().toString().trim();
         Plateno = plateno.getText().toString().trim();
         Email = email.getText().toString().trim();
         Password = password.getText().toString().trim();
         Phone = phoneNo.getText().toString().trim();
         points = 1000;

         int selected = radioGroup.getCheckedRadioButtonId();
        RadioButton rb =(RadioButton) findViewById(selected);

        final String type = rb.getText().toString();


        if(TextUtils.isEmpty(Email)){
            email.setError("Email Can't be Empty");
            email.requestFocus();
            return;

        }

        if(TextUtils.isEmpty(Password)){
            password.setError("Password Can't be Empty");
            password.requestFocus();
            return;

        }
        if (Password.length() < 6) {
            password.setError("Minimum Password length 6");
            password.requestFocus();
            return;
        }

        if (Phone.isEmpty()) {
            phoneNo.setError("phone can't be empty");
            phoneNo.requestFocus();
            return;
        }

        if (Phone.length() != 11) {
            phoneNo.setError("Enter valid Phone no");
            phoneNo.requestFocus();
            return;
        }
        if(Area.isEmpty())
        {
            area.setError("Address ccan't be empty");
            area.requestFocus();
            return;
        }
        if(Name.isEmpty())
        {
            name.setError("Name ccan't be empty");
            name.requestFocus();
            return;
        }

        progressDialog.setMessage("Registration in process...");
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();


                            Driver_Info user = new Driver_Info(Name,Area,type,Age,Plateno,Phone,Email,points);

                            FirebaseDatabase.getInstance().getReference("Users").child("Drivers")
                                    .child(Plateno)
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> ttask) {

                                    if (ttask.isSuccessful()) {
                                        Toast.makeText(Driver_SignUp.this, "Resistration Successful", Toast.LENGTH_LONG).show();
                                        finish();
                                    } else {
                                        //display a failure message
                                        Toast.makeText(Driver_SignUp.this,ttask.getException().getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                            //startActivity(new Intent(getApplicationContext(),UserCatagory.class));
                            //Toast.makeText(SignUp.this,"Registered Succeessful",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            progressDialog.dismiss();
                            Toast.makeText(Driver_SignUp.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();                        }

                    }
                });

    }
}
