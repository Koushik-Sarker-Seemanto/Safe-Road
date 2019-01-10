package com.example.bdafahim.driver;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class Incident_Report extends AppCompatActivity {

    private EditText plateNo,newI,newP;
    private CheckBox gen, acc, fit, over, lane;
    private Button submit;
    private String plate,fines;
    private int points,p ;

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef,mDatabase;
    private String userID;
    private ProgressDialog progressDialog;
    private GpsTracker gpsTracker;
    private double lati,longi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident__report);


        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }


        plateNo = findViewById(R.id.platenumber);
        gen = findViewById(R.id.general_fine);
        acc = findViewById(R.id.accidental);
        fit = findViewById(R.id.fitenss);
        over = findViewById(R.id.overloading);
        lane = findViewById(R.id.lane_violation);
        submit = findViewById(R.id.submit_incident);
        points = 0;
        progressDialog = new ProgressDialog(this);
        fines = "";
        newI = findViewById(R.id.newIncident);
        newP = findViewById(R.id.newPanalty);
        //PRE();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation(view);
                SUBMIT();
            }
        });

    }
    void PRE()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("Users");

        mDatabase.child("Drivers").child(plate).child("points")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        p = Integer.parseInt(dataSnapshot.getValue().toString());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }


    private void SUBMIT() {

        progressDialog.setMessage("Posting in process...");
        progressDialog.show();
        plate = plateNo.getText().toString().trim();
        PRE();
        Toast.makeText(Incident_Report.this,plate,Toast.LENGTH_LONG).show();

        if(acc.isChecked())
        {
            points = points + 50;
            fines += "Accident ";
            //p = p + 50;
        }

        if(gen.isChecked())
        {
            points = points + 20;
           // p = p + 20;
            fines += "  General  ";
        }

        if(fit.isChecked())
        {
            points = points + 70;
           // p = p + 70;
            fines += "  No Fitenss or regis  ";
        }

        if(over.isChecked())
        {
            points = points + 50;
           // p = p + 50;
            fines += "  Overloadding ";
        }

        if(lane.isChecked())
        {
            points = points + 100;
            //p = p + 100;
            fines += "  Lane Violation ";
        }
        String ini = newI.getText().toString().trim();

        if(!ini.isEmpty()) {
            int pa = Integer.parseInt(newP.getText().toString().trim());

            p += pa;
            fines += ini;
        }

        p+=points;
        Toast.makeText(Incident_Report.this,fines,Toast.LENGTH_LONG).show();

        mDatabase.child("Drivers").child(plate).child("points").setValue(p);
         Incident in = new Incident(points,fines,lati,longi);

        FirebaseDatabase.getInstance().getReference("Users")
                .child("Drivers").child(plate)
                .child("Incidents").push().setValue(in).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(Incident_Report.this, "Push Successful", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    progressDialog.dismiss();
                    //display a failure message
                    Toast.makeText(Incident_Report.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

//        mDatabase.child("Drivers").child(plate).child("points").setValue(p);

    }
    public void getLocation(View view){
        gpsTracker = new GpsTracker(Incident_Report.this);
        if(gpsTracker.canGetLocation()){
            lati = gpsTracker.getLatitude();
            longi= gpsTracker.getLongitude();

        }else{
            gpsTracker.showSettingsAlert();
        }
    }


}


