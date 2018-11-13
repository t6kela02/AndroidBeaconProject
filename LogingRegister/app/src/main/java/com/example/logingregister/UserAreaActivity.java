package com.example.logingregister;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class UserAreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        Log.d("tag", "UserAreaActivity");

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        int user_id = intent.getIntExtra("user_id", -1);

        TextView tvWelcomeMsg = (TextView) findViewById(R.id.tvWelcomeMsg);

        // Display user details
        String message = user_id + " " + name + " welcome to your user area";
        tvWelcomeMsg.setText(message);



        //Testin to get beacons data printed to Logcat

        // Response received from the server
        Response.Listener<String> responseListeneri = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    Log.d("tag", ""+jsonResponse.toString());
                    boolean success = jsonResponse.getBoolean("success");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        String id = Integer.toString(user_id);

        DataRequest dataRequest = new DataRequest(id, responseListeneri);
        RequestQueue queue = Volley.newRequestQueue(UserAreaActivity.this);
        queue.add(dataRequest);
    }
}
