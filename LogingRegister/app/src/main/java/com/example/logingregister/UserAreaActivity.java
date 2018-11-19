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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

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
        final TextView dataText = (TextView) findViewById(R.id.dataText);

        // Display user details
        String message = user_id + " " + name + " welcome to your user area";
        tvWelcomeMsg.setText(message);



        //Testin to get beacons data printed to Logcat

        // Response received from the server
        Response.Listener<String> responseListeneri = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();

                try {

                    JSONObject jsonResponse = new JSONObject(response);
                    Log.d("tag", ""+jsonResponse.toString());
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray dataArray = jsonResponse.getJSONArray("data");
                        Log.d("tag", "data success");
                        Log.d("tag", "" + dataArray.length());

                        int timeData = 0;
                        int keltainenTime = 0;

                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject jsonobject = dataArray.getJSONObject(i);
                            int seconds = jsonobject.getInt("seconds");
                            timeData += seconds;
                            String beacon_name = jsonobject.getString("beacon_name");
                            Log.d("tag", beacon_name);
                            if(beacon_name.equals("Keltainen")){
                                Log.d("tag", "Onko keltainen: " + beacon_name);
                                keltainenTime += seconds;
                            }
                        }
                        Log.d("tag", "time: " + timeData);
                        Log.d("tag", "time: " + keltainenTime);


                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(UserAreaActivity.this);
                        builder.setMessage("No data!")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        String id = Integer.toString(user_id);


        DataRequest dataRequest = new DataRequest(user_id, responseListeneri);
        RequestQueue queue = Volley.newRequestQueue(UserAreaActivity.this);
        queue.add(dataRequest);
    }
}
