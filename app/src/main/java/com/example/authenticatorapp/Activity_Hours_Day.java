package com.example.authenticatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;

public class Activity_Hours_Day extends AppCompatActivity {
    TextView textViewDay;
    TextView StartTimeTextView;
    TextView EndTimeTextView;
    Button EditStartTime;
    Button EditEndTime;
    Button save;
    Context myContext = this;
    String day;
    DBHelper DB;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__hours__day);

        DB = new DBHelper(this);
        textViewDay = (TextView) findViewById(R.id.textViewDay);
        StartTimeTextView = (TextView) findViewById(R.id.textViewTimeStart);
        EndTimeTextView = (TextView) findViewById(R.id.textViewTimeEnd);
        Calendar calendar = Calendar.getInstance();
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);
        EditStartTime = findViewById(R.id.buttonEditStartTime);
        EditEndTime = findViewById(R.id.buttonEditEndTime);
        save = findViewById(R.id.buttonSave);
        day = getIntent().getExtras().getString("Day"); //TO USE FOR DATA
        textViewDay.setText(day);
        if(DB.checkDay(day,getIntent().getStringExtra("username"))){
            cursor = DB.returnTime(day,getIntent().getStringExtra("username"));
            cursor.moveToNext();
            StartTimeTextView.setText(cursor.getString(1));
            EndTimeTextView.setText(cursor.getString(2));

        }

        EditStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(myContext, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        StartTimeTextView.setText(hourOfDay+ ":"+ minute); //Hoursofday and minute to use for DATA
                    }
                },hour,minute,android.text.format.DateFormat.is24HourFormat(myContext));
                timePickerDialog.show();
            }
        });
        EditEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(myContext, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        EndTimeTextView.setText(hourOfDay+ ":"+ minute);
                    }
                },hour,minute,android.text.format.DateFormat.is24HourFormat(myContext));
                timePickerDialog.show();
            }
        });



        save.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                String day = textViewDay.getText().toString().trim();
                String start = StartTimeTextView.getText().toString().trim();
                String end = EndTimeTextView.getText().toString().trim();
                int s = Integer.parseInt(start.replaceAll("[-+.^:,]",""));
                int e = Integer.parseInt(start.replaceAll("[-+.^:,]",""));
                    if(DB.checkDay(day,getIntent().getStringExtra("username"))){
                        DB.updateTime(day,start,end,getIntent().getStringExtra("username"));
                    }else {
                        DB.insertHour(day,start,end,getIntent().getStringExtra("username"));
                    }
                    Toast.makeText(Activity_Hours_Day.this,"Saved succesfuly", Toast.LENGTH_SHORT).show();



            }
        });


    }
    public String validateTimeInferior(String starttime,String endtime){
        if(starttime==null||endtime==null){
            return "Invalid,content invalid";
        }
        else if(starttime.compareTo(endtime)>0){
            return "Invalid,content invalid";
        }
        else{
            return "Valid content";
        }
    }
    public String timeIsValid(String time){
        boolean validated = time.startsWith("0")||time.startsWith("1")||time.startsWith("20")||time.startsWith("21")||time.startsWith("22")||time.startsWith("23");
        if(validated){
            return "Valid content";
        }else{
            return "Invalid,content invalid";
        }
    }
}