package com.example.authenticatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Rate_Activity extends AppCompatActivity {
    RatingBar ratingBar;
    EditText comments;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_);

        ratingBar = findViewById(R.id.ratingBar);
        comments = findViewById(R.id.editTextTextMultiLineComments);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Rate_Activity.this, "Comments and rating sent succesfully" ,Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    public boolean checkComment(String comment){
        Pattern patternNumber = Pattern.compile("-?\\d+(\\.\\d+)?");
        if(patternNumber.matcher(comment).matches()){
            return false;
        }
        return true;
    }
}