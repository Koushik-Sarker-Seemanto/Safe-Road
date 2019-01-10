package com.example.bdafahim.driver;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Driver_Profile extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private  String userID,Carno;

    private ListView mListView;
    private Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__profile);

         Carno ="";
        Bundle b = getIntent().getExtras();
        if(b!=null)
            Carno = (String)b.get("CarNo");
        Toast.makeText(Driver_Profile.this,Carno,Toast.LENGTH_LONG).show();

        btn = findViewById(R.id.ViewOwnInfo);

        mListView = findViewById(R.id.myList);

        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Driver_Profile.this,Driver_Incident.class);
                i.putExtra("Plate",Carno);
                startActivity(i);
            }
        });

    }

    private void showData(DataSnapshot dataSnapshot)
    {
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            Driver_Info di = new Driver_Info();
            di.setName(ds.child("Drivers").child(Carno).getValue(Driver_Info.class).getName());
            di.setAge(ds.child("Drivers").child(Carno).getValue(Driver_Info.class).getAge());
            di.setArea(ds.child("Drivers").child(Carno).getValue(Driver_Info.class).getArea());
            di.setPhoneNo(ds.child("Drivers").child(Carno).getValue(Driver_Info.class).getPhoneNo());
            di.setPlateno(ds.child("Drivers").child(Carno).getValue(Driver_Info.class).getPlateno());
            di.setMail(ds.child("Drivers").child(Carno).getValue(Driver_Info.class).getMail());
            di.setPoints(ds.child("Drivers").child(Carno).getValue(Driver_Info.class).getPoints());
            di.setType(ds.child("Drivers").child(Carno).getValue(Driver_Info.class).getType());

            ArrayList<String> array = new ArrayList<>();
            array.add("Name    : "+di.getName());
            array.add("PlateNO : "+di.getPlateno());
            array.add("Type    : "+di.getType());
            array.add("Area    : "+di.getArea());
            array.add("Phone   : "+di.getPhoneNo());
            array.add("Penalty : "+di.getPoints());

            ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,array);
            mListView.setAdapter(adapter);
        }
    }
}
