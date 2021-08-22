package com.example.authenticatorapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.regex.*;

import java.util.ArrayList;

public class Services_profile extends AppCompatActivity {

    ListView listServices;
    ListView listServicesAdd;
    ArrayList<String> services;
    ArrayList<String> services1;
    ArrayAdapter adapter;
    ArrayAdapter adapterAdd;
    Button buttonDialogAdd;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_profile);

        listServices = (ListView) findViewById(R.id.ListPossibleServices);

        services= new ArrayList<>();
        buttonDialogAdd = findViewById(R.id.buttonDialogAdd);
        DB = new DBHelper(this);

        DB.addService(new Service("Driver's license","Including customer first name, last name , date of birth,adress,license type(G1,G2,G)","Proof of residence","10$",null,null));
        DB.addService(new Service("Health card","Including customer first name, last name , date of birth,adress","Proof of residence - Proof of status","15$",null,null));
        DB.addService(new Service("Photo ID","Including customer first name, last name , date of birth,adress","Proof of residence - A photo of the customer","20$",null,null));

        viewDataBranch();
        buttonDialogAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showAddDialog();
            }
        });

        listServices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ServiceToadd1 = services.get(position);
                DB.deleteServiceBranch(ServiceToadd1,getIntent().getStringExtra("username"));
                services.clear();
                adapter.notifyDataSetChanged();
                viewDataBranch();

            }
        });

    }


    private void viewDataBranch(){
        services.clear();
        Cursor cursor = DB.viewDataBranch(getIntent().getStringExtra("username"));

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


    private void showAddDialog() {


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setCancelable(true);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_service_profile, null);
        dialogBuilder.setView(dialogView);
        services1=new ArrayList<>();

        final AlertDialog b = dialogBuilder.create();
        b.show();


//        JUST TO TEST MUST REMOVE AS SOON AS TESTED

//        JUST TO TEST MUST REMOVE AS SOON AS TESTED

        listServicesAdd = (ListView) dialogView.findViewById(R.id.ListPossibleServicesAdd);
        Cursor cursorAdd = DB.viewData();

        if (cursorAdd.getCount() ==0){
            Toast.makeText(this,"No data to show", Toast.LENGTH_SHORT).show();
        }else{
            while(cursorAdd.moveToNext()){
                services1.add(cursorAdd.getString(0)); //index 0 is the name of the service
            }
            adapterAdd = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1 , services1);
            listServicesAdd.setAdapter(adapterAdd);
        }

        listServicesAdd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ServiceToadd = services1.get(position);
                Cursor cursor = DB.returnService(ServiceToadd);
                if(cursor.moveToNext()){
                    Service newService = new Service(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),getIntent().getStringExtra("username"),cursor.getString(0)+getIntent().getStringExtra("username"));
                    boolean add = DB.addBranchService(newService);
                    if(!add){
                        Toast.makeText(Services_profile.this,"Service already exists !", Toast.LENGTH_SHORT).show();
                    }
                    viewDataBranch();

                }

            }
        });


    }
    public String validateServices(ArrayList<String> services){
        boolean validate = true;
        if(services.isEmpty()||services==null){
            return "Valid content";
        }
        for(int i=0;i<services.size();i++){
            if(services.get(i)==null){
                validate=false;
            }
        }
        if(validate){
            return "Valid content";
        }else{
            return "Invalid,content invalid";
        }
    }

}