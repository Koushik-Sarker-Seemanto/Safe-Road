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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Search extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DriversAdapter adapter;
    private List<Driver_Info> driverList;
    private ArrayList<Incident>in;
    private EditText editText;
    private Button btn;
    DatabaseReference dr;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        driverList = new ArrayList<>();
        adapter = new DriversAdapter(this, driverList);
        recyclerView.setAdapter(adapter);

        editText = findViewById(R.id.search_vehicle);
        btn = findViewById(R.id.search_btn);
        lv = findViewById(R.id.list_item);


        dr = FirebaseDatabase.getInstance().getReference("Users").child("Drivers");


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PROCESS();
                PROCESS2();
            }
        });


    }

    void PROCESS()
    {
        String plate = editText.getText().toString().trim();


        Query query = FirebaseDatabase.getInstance().getReference("Users").child("Drivers")
                .orderByChild("plateno")
                .equalTo(plate);
        query.addListenerForSingleValueEvent(valueEventListener);

    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            driverList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Driver_Info driver = snapshot.getValue(Driver_Info.class);
                    driverList.add(driver);


                }
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    void PROCESS2()
    {
        String plate = editText.getText().toString().trim();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users")
                .child("Drivers").child(plate).child("Incidents");
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
    public void collectData(Map<String,Object>users)
    {
        ArrayList<String> array = new ArrayList<>();
        for(Map.Entry<String,Object>entry:users.entrySet()){
            Map singleUser = (Map) entry.getValue();

            array.add((String) singleUser.get("incidents"));
            String lati = Double.toString((Double) singleUser.get("lati"));
            String longi = Double.toString((Double) singleUser.get("longi"));
            array.add("\nLatitute: "+lati);
            array.add("\nLongitute: "+longi);
            //array.add((String) singleUser.get("points"));
            //array.add((String) singleUser.get("UphoneNo"));
            array.add((String)"\n");
        }
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,array);
        lv.setAdapter(adapter);

    }
}
