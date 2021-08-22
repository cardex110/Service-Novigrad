package com.example.authenticatorapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class Book extends AppCompatActivity {
    TextView date;
    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        date = findViewById(R.id.textViewDate);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Book.this ,android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String datestring = day+"/"+ month+"/"+ year;
                if(validDate(year,month,dayOfMonth)){
                    date.setText(datestring);
                    Toast.makeText(Book.this,"Yes! Booking appointment done succesfuly",Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(Book.this,"Oups! You have selected a date that already passed",Toast.LENGTH_LONG).show();
                }

            }
        };
    }

    public boolean validDate(int year, int month , int dayOfMonth) {
        Calendar now = Calendar.getInstance();
        int currentYear = now.get(Calendar.YEAR);
        int currentMonth = now.get(Calendar.MONTH)+1;
        int currentday = now.get(Calendar.DATE);
        if(year<currentYear){
            return false;
        }
        if(month<currentMonth && year == currentYear){
            return false;
        }
        if(dayOfMonth<=currentday && month==currentMonth){
            return false;
        }
        return true;

    }
}