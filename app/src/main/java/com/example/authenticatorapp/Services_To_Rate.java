package com.example.authenticatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Services_To_Rate extends AppCompatActivity {

    ListView listBranch;
    ArrayList<String> branches;
    ArrayAdapter adapter;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services__to__rate);
        DB = new DBHelper(this);
        listBranch = (ListView) findViewById(R.id.listBranch);
        branches= new ArrayList<>();

        //TODO DATABASE
        viewData();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1 , branches);
        listBranch.setAdapter(adapter);

        listBranch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(Services_To_Rate.this, Rate_Activity.class);
                startActivity(i);
            }
        });
    }
    private void viewData(){
        Cursor cursor = DB.ViewName("Employee");

        if (cursor.getCount() ==0){
            Toast.makeText(this,"No data to show", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                branches.add("branch "+cursor.getString(0)); //index 0 is the name of the service
            }
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1 , branches);
            listBranch.setAdapter(adapter);
        }
    }
}