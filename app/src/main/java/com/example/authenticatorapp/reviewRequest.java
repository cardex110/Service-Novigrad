package com.example.authenticatorapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class reviewRequest extends AppCompatActivity {

    //2 if rejected / 1 if accepted
    int notified;

    ListView listView;
    ArrayAdapter<String> adapter;
    DBHelper DB;
    ArrayList<String> requests;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        DB = new DBHelper(this);
        listView = (ListView)findViewById(R.id.listview);
        String user = getIntent().getStringExtra("username");
        int index = user.indexOf("@");
        user = user.substring(0,index);
        requests = new ArrayList<>();
        viewRequests();


        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,requests);


        listView.setAdapter(arrayAdapter);

        final String finalUser = user;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(reviewRequest.this);
                builder.setTitle("Accept");
                builder.setMessage("Accept request ?");
                builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setNotifiedAccept();
                        DB.updateRequest(adapter.getItem(position), finalUser,"Accepted");
                        requests.remove(position);
                        listView.requestLayout();
                        Toast.makeText(reviewRequest.this,"Accepted",Toast.LENGTH_SHORT).show();


                    }
                });

                builder.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setNotifiedDecline();
                        DB.updateRequest(adapter.getItem(position), finalUser,"Declined");
                        requests.remove(position);
                        listView.requestLayout();
                        Toast.makeText(reviewRequest.this,"Declined",Toast.LENGTH_SHORT).show();

                    }
                });

                builder.create().show();
            }
        });


    }
    private void viewRequests(){
        String user = getIntent().getStringExtra("username");
        int index = user.indexOf("@");
        user = user.substring(0,index);
        Cursor cursor = DB.viewRequests(user);

        if (cursor.getCount() ==0){
            Toast.makeText(this,"No data to show", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                requests.add(cursor.getString(0)); //index 0 is the name of the service
            }
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1 , requests);
            listView.setAdapter(adapter);
        }
    }

    public void setNotifiedAccept(){
        notified =1;
    }
    public void setNotifiedDecline(){
        notified =2;
    }

}