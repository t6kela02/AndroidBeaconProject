package com.example.logingregister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class UserAreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        Log.d("tag", "UserAreaActivity");

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        TextView tvWelcomeMsg = (TextView) findViewById(R.id.tvWelcomeMsg);

        // Display user details
        String message = name + " welcome to your user area";
        tvWelcomeMsg.setText(message);
    }
}
