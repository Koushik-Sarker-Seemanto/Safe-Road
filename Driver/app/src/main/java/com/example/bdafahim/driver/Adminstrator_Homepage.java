package com.example.bdafahim.driver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Adminstrator_Homepage extends AppCompatActivity {

    private Button Reporter, View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminstrator__homepage);

        Reporter = findViewById(R.id.create_reporter);
        View = findViewById(R.id.viewId) ;

        Reporter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                startActivity(new Intent(Adminstrator_Homepage.this,Reporter_SignUp.class));
            }
        });

        View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                startActivity(new Intent(Adminstrator_Homepage.this,Search.class));
            }
        });
    }
}
