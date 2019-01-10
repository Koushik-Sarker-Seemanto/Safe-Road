package com.example.bdafahim.driver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Driver_Incident extends AppCompatActivity {

    private DriversAdapter adapter;
    private List<Driver_Info> driverList;
    private ArrayList<Incident> in;
    private EditText editText;
    DatabaseReference dr;
   private ListView lv;
    private String carno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__incident);

        Bundle b = getIntent().getExtras();
        carno = "";
        if(b!=null)
            carno = (String)b.get("Plate");
        driverList = new ArrayList<>();
        adapter = new DriversAdapter(this, driverList);

        editText = findViewById(R.id.search_vehicle);
        lv = findViewById(R.id.driverIncident);


       // dr = FirebaseDatabase.getInstance().getReference("Users").child("Drivers");

//        PROCESS();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users")//.child()
                .child("Drivers").child(carno).child("Incidents");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                collectData((Map<String,Object>)dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    private void PROCESS(){

    }
    public void collectData(Map<String,Object>users)
    {
        ArrayList<String> array = new ArrayList<>();
        for(Map.Entry<String,Object>entry:users.entrySet()){
            Map singleUser = (Map) entry.getValue();

            array.add((String) singleUser.get("incidents"));
            //array.add((String) singleUser.get("points"));
            //array.add((String) singleUser.get("UphoneNo"));
            array.add((String)"\n");
        }
       // ArrayList<Integer>array1 = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,array);
        lv.setAdapter(adapter);

    }
}
