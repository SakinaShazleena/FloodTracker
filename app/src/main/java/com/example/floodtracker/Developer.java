package com.example.floodtracker;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Developer extends AppCompatActivity {

    TextView link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);
//
//        link = findViewById(R.id.space);
//        link.setMovementMethod(LinkMovementMethod.getInstance());
    }
}