package com.example.authenticatorapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class employeeWelcome extends AppCompatActivity {

    Button buttonProfile;
    Button buttonServices;
    Button buttonHoursDialog;
    Button buttonRequest;
    DBHelper DB;
    Boolean inserted = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_welcome);

        buttonProfile = findViewById(R.id.buttonProfile);
        buttonServices = findViewById(R.id.buttonServices);
        buttonHoursDialog= findViewById(R.id.buttonHoursDialog);
        buttonRequest = findViewById(R.id.buttonRequest);
        DB = new DBHelper(this);

        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCompleteProfileDialog();
            }
        });

        buttonServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent i = new Intent(employeeWelcome.this, Services_profile.class);
                    i.putExtra("username",getIntent().getStringExtra("username"));
                    startActivity(i);
            }
        });

        buttonHoursDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent i = new Intent(employeeWelcome.this, Activity_working_hours.class);
                    i.putExtra("username",getIntent().getStringExtra("username"));
                    startActivity(i);

            }
        });

        buttonRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent i = new Intent(employeeWelcome.this, reviewRequest.class);
                    i.putExtra("username",getIntent().getStringExtra("username"));
                    startActivity(i);

            }
        });


        }

    private void showCompleteProfileDialog(){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.complete_profile, null);
        dialogBuilder.setView(dialogView);

        final EditText streetNb = (EditText) dialogView.findViewById(R.id.editTextStreetNumber);
        final EditText streetName = (EditText) dialogView.findViewById(R.id.editTextStreetName);
        final EditText city = (EditText) dialogView.findViewById(R.id.editTextCity);
        final EditText phone = (EditText) dialogView.findViewById(R.id.editTextPhone);
        final Button confirm = (Button) dialogView.findViewById(R.id.buttonConfirm);

        //dialogBuilder.setTitle(ServiceName);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String streetNbb = streetNb.getText().toString().trim();
                String streetNamee = streetName.getText().toString().trim();
                String cityy = city.getText().toString().trim();
                String phonee = phone.getText().toString().trim();
                Pattern patternNumber = Pattern.compile("-?\\d+(\\.\\d+)?");
                Pattern patternLetters = Pattern.compile("[a-zA-Z]+");
                if(streetNbb.isEmpty() || streetNamee.isEmpty() || cityy.isEmpty() || phonee.isEmpty()){
                    Toast.makeText(employeeWelcome.this, "Please enter all details", Toast.LENGTH_LONG).show();
                }else if(!patternNumber.matcher(streetNbb).matches()){
                    Toast.makeText(employeeWelcome.this, "Street Number invalid", Toast.LENGTH_LONG).show();
                }else if(!patternLetters.matcher(streetNamee).matches()){
                    Toast.makeText(employeeWelcome.this, "Street Name invalid", Toast.LENGTH_LONG).show();
                }else if(!patternLetters.matcher(cityy).matches()){
                    Toast.makeText(employeeWelcome.this, "City name invalid", Toast.LENGTH_LONG).show();
                }else if(!patternNumber.matcher(phonee).matches()){
                    Toast.makeText(employeeWelcome.this, "Phone number invalid", Toast.LENGTH_LONG).show();
                }else{
                        DB.insertDataBranch(getIntent().getStringExtra("username"),phonee,streetNbb,streetNamee,cityy);
                        Toast.makeText(employeeWelcome.this, "sucess", Toast.LENGTH_LONG).show();
                        b.dismiss();


                }
            }
        });

    }
    


    }
