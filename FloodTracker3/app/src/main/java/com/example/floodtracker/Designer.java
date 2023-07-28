package com.example.floodtracker;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Designer extends AppCompatActivity {

    TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designer);

        txtTitle = findViewById(R.id.textView_title);
        txtTitle.setMovementMethod(LinkMovementMethod.getInstance());
    }
}