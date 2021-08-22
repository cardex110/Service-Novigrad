package com.example.authenticatorapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CustomerWelcome extends AppCompatActivity {



    Button buttonByAdress;
    Button buttonByHours;
    Button buttonByServices;
    Button buttonBooking;
    Button rate;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_welcome);
        DB=new DBHelper(this);
        buttonByAdress = findViewById(R.id.buttonByAdress);
        buttonByHours = findViewById(R.id.buttonByHours);
        buttonByServices= findViewById(R.id.buttonByServices);
        rate = findViewById(R.id.buttonRate);
        buttonBooking = findViewById(R.id.buttonBooking);
        String mail = getIntent().getStringExtra("email");
        Cursor cursor = DB.viewRequestsUser(mail);
        Cursor cursor1 = DB.viewRequestsUser1(mail);
        if(cursor.getCount()!=0){
            //Toast.makeText(CustomerWelcome.this, "accepted", Toast.LENGTH_SHORT).show();
            String notification = "Your request has been accepted";
            NotificationCompat.Builder builder = new NotificationCompat.Builder(CustomerWelcome.this).setSmallIcon(R.drawable.ic_notif)
                    .setContentTitle("New Notification")
                    .setContentText(notification)
                    .setAutoCancel(true);
            NotificationManager notificationManager = (NotificationManager)getSystemService(
                    Context.NOTIFICATION_SERVICE
            );
            notificationManager.notify(0,builder.build());
        }
        if(cursor1.getCount()!=0){
            //Toast.makeText(CustomerWelcome.this, "declined", Toast.LENGTH_LONG).show();
            String notification = "Your request has been refused";
            NotificationCompat.Builder builder = new NotificationCompat.Builder(CustomerWelcome.this).setSmallIcon(R.drawable.ic_notif)
                    .setContentTitle("New Notification")
                    .setContentText(notification)
                    .setAutoCancel(true);
            NotificationManager notificationManager = (NotificationManager)getSystemService(
                    Context.NOTIFICATION_SERVICE
            );
            notificationManager.notify(1,builder.build());
        }





        buttonByAdress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CustomerWelcome.this, Search_By_Adress.class);
                i.putExtra("email",getIntent().getStringExtra("email"));
                startActivity(i);

            }
        });

        buttonByHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showDaysDialog();

            }
        });

        buttonByServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CustomerWelcome.this, Search_By_Services.class);
                i.putExtra("email",getIntent().getStringExtra("email"));
                startActivity(i);


            }
        });

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CustomerWelcome.this, Services_To_Rate.class);
                String e = getIntent().getStringExtra("username");
                i.putExtra("username",e);
                i.putExtra("email",getIntent().getStringExtra("email"));
                startActivity(i);
            }
        });

        buttonBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CustomerWelcome.this, Booking_branches.class);
                startActivity(i);
            }
        });



    }

    private void showDaysDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.show_days, null);
        dialogBuilder.setView(dialogView);

        final Button LundiButton = (Button) dialogView.findViewById(R.id.buttonMonday);
        final Button mardiButton = (Button) dialogView.findViewById(R.id.buttonTuesday);
        final Button mercrediButton = (Button) dialogView.findViewById(R.id.buttonWednesday);
        final Button jeudiButton = (Button) dialogView.findViewById(R.id.buttonThursday);
        final Button vendrediButton = (Button) dialogView.findViewById(R.id.buttonFriday);
        final Button samediButton = (Button) dialogView.findViewById(R.id.buttonSaturday);

        //dialogBuilder.setTitle(ServiceName);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        LundiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String day = "Monday" ;
                Intent intent = new Intent(CustomerWelcome.this, Search_By_Hours.class);
                intent.putExtra("day",day);
                intent.putExtra("email",getIntent().getStringExtra("email"));
                startActivity(intent);
                b.dismiss();
            }
        });

        mardiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String day = "Tuesday" ;
                Intent intent = new Intent(CustomerWelcome.this, Search_By_Hours.class);
                intent.putExtra("day",day);
                intent.putExtra("email",getIntent().getStringExtra("email"));
                startActivity(intent);
                b.dismiss();
            }
        });

        mercrediButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String day = "Wednesday" ;
                Intent intent = new Intent(CustomerWelcome.this, Search_By_Hours.class);
                intent.putExtra("day",day);
                intent.putExtra("email",getIntent().getStringExtra("email"));
                startActivity(intent);
                b.dismiss();
            }
        });

        jeudiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String day = "Thursday" ;
                Intent intent = new Intent(CustomerWelcome.this, Search_By_Hours.class);
                intent.putExtra("day",day);
                intent.putExtra("email",getIntent().getStringExtra("email"));
                startActivity(intent);
                b.dismiss();
            }
        });

        vendrediButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String day = "Friday" ;
                Intent intent = new Intent(CustomerWelcome.this, Search_By_Hours.class);
                intent.putExtra("day",day);
                intent.putExtra("email",getIntent().getStringExtra("email"));
                startActivity(intent);
                b.dismiss();
            }
        });

        samediButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String day = "Saturday" ;
                Intent intent = new Intent(CustomerWelcome.this, Search_By_Hours.class);
                intent.putExtra("day",day);
                intent.putExtra("email",getIntent().getStringExtra("email"));
                startActivity(intent);
                b.dismiss();
            }
        });


    }
}