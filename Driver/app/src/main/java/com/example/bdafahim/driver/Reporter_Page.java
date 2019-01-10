package com.example.bdafahim.driver;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Reporter_Page extends AppCompatActivity {

    private Button bt1,bt2;
    private ListView lv;
    private String domain;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private  String userID,Carno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporter__page);

        bt1 = findViewById(R.id.makeReport);
        bt2 = findViewById(R.id.reportView);
        lv = findViewById(R.id.reporterID);
        domain = "";


        Bundle b = getIntent().getExtras();
        if(b!=null)
            domain = (String)b.get("domain");


        VIEW();

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Reporter_Page.this,Incident_Report.class));
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Reporter_Page.this,Search.class));
            }
        });
    }
    private void VIEW()
    {
        myRef = FirebaseDatabase.getInstance().getReference()
        ;
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void showData(DataSnapshot dataSnapshot){
        for(DataSnapshot ds : dataSnapshot.getChildren())
        {
            Reporter_Info ri = new Reporter_Info();

            ri.setName(ds.child("Reporter").child(domain).getValue(Reporter_Info.class).getName());
            ri.setArea(ds.child("Reporter").child(domain).getValue(Reporter_Info.class).getArea());

            ri.setEmail(ds.child("Reporter").child(domain).getValue(Reporter_Info.class).getEmail());
            ri.setPhone(ds.child("Reporter").child(domain).getValue(Reporter_Info.class).getPhone());
            ri.setProfession(ds.child("Reporter").child(domain).getValue(Reporter_Info.class).getProfession());

            ArrayList<String> array = new ArrayList<>();
            array.add("Name    : "+ri.getName());
            array.add("Area    : "+ri.getArea());
            array.add("Phone   : "+ri.getPhone());
            array.add("Email   : "+ri.getEmail());
            array.add("Profession : "+ri.getProfession());

            ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,array);
            lv.setAdapter(adapter);
        }
    }
}
