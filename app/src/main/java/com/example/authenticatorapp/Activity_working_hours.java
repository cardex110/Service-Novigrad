package com.example.authenticatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Activity_working_hours extends AppCompatActivity {

    Button buttonMonday ;
    Button buttonTuesday;
    Button buttonWednesday ;
    Button buttonThursday;
    Button buttonFriday;
    Button buttonSaturday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_working_hours);

        buttonMonday = findViewById(R.id.buttonMonday);
        buttonTuesday = findViewById(R.id.buttonTuesday);
        buttonWednesday = findViewById(R.id.buttonWednesday);
        buttonThursday = findViewById(R.id.buttonThursday);
        buttonFriday = findViewById(R.id.buttonFriday);
        buttonSaturday = findViewById(R.id.buttonSaturday);

        buttonMonday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_working_hours.this, Activity_Hours_Day.class);

                i.putExtra("Day","Monday");
                i.putExtra("username",getIntent().getStringExtra("username"));
                startActivity(i);
            }
        });
        buttonTuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_working_hours.this, Activity_Hours_Day.class);
                i.putExtra("Day","Tuesday");
                i.putExtra("username",getIntent().getStringExtra("username"));
                startActivity(i);
            }
        });
        buttonWednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_working_hours.this, Activity_Hours_Day.class);
                i.putExtra("Day","Wednesday");
                i.putExtra("username",getIntent().getStringExtra("username"));
                startActivity(i);
            }
        });
        buttonThursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_working_hours.this, Activity_Hours_Day.class);
                i.putExtra("Day","Thursday");
                i.putExtra("username",getIntent().getStringExtra("username"));
                startActivity(i);
            }
        });
        buttonFriday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_working_hours.this, Activity_Hours_Day.class);
                i.putExtra("Day","Friday");
                i.putExtra("username",getIntent().getStringExtra("username"));
                startActivity(i);
            }
        });
        buttonSaturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_working_hours.this, Activity_Hours_Day.class);
                i.putExtra("Day","Saturday");
                i.putExtra("username",getIntent().getStringExtra("username"));
                startActivity(i);
            }
        });
    }
}