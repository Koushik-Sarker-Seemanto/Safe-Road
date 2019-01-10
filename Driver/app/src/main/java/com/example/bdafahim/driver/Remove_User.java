package com.example.bdafahim.driver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Remove_User extends AppCompatActivity {

    private EditText editText;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove__user);

        btn = findViewById(R.id.removeNow);
        editText = findViewById(R.id.removeUserText);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // FirebaseAuth.getInstance().deleteUser(FirebaseAuth.getInstance().getCurrentUser().getUid());
                Toast.makeText(Remove_User.this,"Feature will be added soon",Toast.LENGTH_LONG).show();

            }
        });
    }
}
