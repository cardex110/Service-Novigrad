package com.example.authenticatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
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

public class Services_for_customer extends AppCompatActivity {

    ListView listServices;
    ArrayList<String> services;
    ArrayAdapter adapter;
    TextView sentence;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_for_customer);

        listServices = (ListView) findViewById(R.id.servicesToPurchase);
        services= new ArrayList<>();
        sentence = findViewById(R.id.textView12);
        DB = new DBHelper(this);

        //TODO faut changer la ligne suivante pour qu elle puise imprimer le nom du branch dans la phrase au lieu du mail si possible
        String email = getIntent().getStringExtra("username");
        int index = email.indexOf("@");
        email = email.substring(0,index);
        sentence.setText( "Select a service provided by the branch"+" "+email+" "+"that you would like to purchase" );

        viewData();

        adapter = new ArrayAdapter<>(Services_for_customer.this, android.R.layout.simple_list_item_1, services);

        listServices.setAdapter(adapter);

        listServices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Toad = (String) adapter.getItem(position);
                Toast.makeText(Services_for_customer.this,Toad,Toast.LENGTH_LONG).show();
                Intent i = new Intent(Services_for_customer.this, Fill_Activity.class);
                i.putExtra("service",Toad);
                String email = getIntent().getStringExtra("username");
                int index = email.indexOf('@');
                email=email.substring(0,index);
                i.putExtra("username",email);
                i.putExtra("email",getIntent().getStringExtra("email"));
                startActivity(i);
            }
        });


    }
    private void viewData(){
        Cursor cursor = DB.ViewServices(getIntent().getStringExtra("username"));

        if (cursor.getCount() ==0){
            Toast.makeText(this,"No data to show", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                services.add(cursor.getString(0)); //index 0 is the name of the service
            }
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1 , services);
            listServices.setAdapter(adapter);
        }
    }


}