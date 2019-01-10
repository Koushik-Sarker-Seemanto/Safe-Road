package com.example.bdafahim.driver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home_Page extends AppCompatActivity {

    private Button AdminButton,ReporterButton,DriverButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        DriverButton = findViewById(R.id.driver_login);

        AdminButton = findViewById(R.id.add_login);
        ReporterButton = findViewById(R.id.reporter_login);

        DriverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home_Page.this,Driver_login.class));
            }
        });

        AdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home_Page.this,Admin_login.class));
            }
        });

        ReporterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home_Page.this,Reporter_Login.class));
            }
        });
    }
}
