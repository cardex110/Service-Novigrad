package com.example.authenticatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Branches extends AppCompatActivity {

    ListView listBranch;
    ArrayList<String> branches;
    ArrayAdapter adapter;
    TextView textView;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branches);
        DB = new DBHelper(this);
        listBranch = findViewById(R.id.listBranchSearch);
        branches= new ArrayList<>();

        //TODO view from data
        viewData();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1 , branches);
        listBranch.setAdapter(adapter);
        textView = findViewById(R.id.textViewService);
        textView.setText("Click with which branch you want the following service: "+ getIntent().getStringExtra("service"));

        listBranch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Toad = (String) adapter.getItem(position);
                Toast.makeText(Branches.this,Toad,Toast.LENGTH_LONG).show();
                Intent i = new Intent(Branches.this, Fill_Activity.class);
                i.putExtra("service",getIntent().getStringExtra("service"));
                i.putExtra("username",Toad);
                i.putExtra("email",getIntent().getStringExtra("email"));
                startActivity(i);
            }
        });
    }
    private void viewData(){
        Cursor cursor = DB.ViewBranchServices(getIntent().getStringExtra("service"));

        if (cursor.getCount() ==0){
            Toast.makeText(this,"No data to show", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                String email =cursor.getString(0);
                int index = email.indexOf('@');
                email=email.substring(0,index);
                branches.add(email); //index 0 is the name of the service
            }
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1 , branches);
            listBranch.setAdapter(adapter);
        }
    }
}