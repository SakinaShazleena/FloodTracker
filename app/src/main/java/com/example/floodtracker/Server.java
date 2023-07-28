package com.example.floodtracker;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import java.util.HashMap;
import java.util.Map;



public class Server extends AppCompatActivity {
    EditText description,location,latitud,longitud;

    RequestQueue queue;
    //ipaddr : 192.168.0.102
    final String URL = "http://192.168.0.105/flood/api.php";


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);

        queue = Volley.newRequestQueue(getApplicationContext());

        location = (EditText) findViewById(R.id.location);
        description = (EditText) findViewById(R.id.desc);
        latitud = (EditText) findViewById(R.id.lat);
        longitud = (EditText) findViewById(R.id.lng);

        Button button = (Button) findViewById(R.id.submitbtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //volley

                makeRequest();
            }

        });

    }

    public void makeRequest() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();

                Log.d("Response", response);
            }
        }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle the error
                Log.e("Error", error.toString());
            }
        }
        ){
            @Override
            protected Map<String,String> getParams (){

                // POST parameters to be sent to the PHP script
                final Map<String, String> params = new HashMap<>();
                params.put("location", location.getText().toString());
                params.put("description", description.getText().toString());
                params.put("latitud", latitud.getText().toString());
                params.put("longitud", longitud.getText().toString());

                return params;
            }
        };
        queue.add(stringRequest);

    }

    public Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

        }
    };


}
