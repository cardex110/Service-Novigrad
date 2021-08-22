package com.example.authenticatorapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Welcome extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Intent intent = getIntent();
        //String email = intent.getStringExtra("email");
        String username = intent.getStringExtra("username");
        //String password = intent.getStringExtra("password");
        String job = intent.getStringExtra("job");
        textView = (TextView)findViewById(R.id.textView3);
        textView.setText("Welcome "+username+" as "+job);


    }
}

