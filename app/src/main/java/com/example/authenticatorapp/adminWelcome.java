package com.example.authenticatorapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class adminWelcome extends AppCompatActivity {

    EditText formRequired;
    EditText serviceName;
    EditText documentRequired;
    EditText rate;
    ListView listServices;
    ArrayList<String> services;
    ArrayAdapter adapter;

    Button addButton;
    Button deleteButton;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_welcome);
        serviceName = (EditText) findViewById(R.id.nameService);
        formRequired = (EditText) findViewById(R.id.formService);
        documentRequired = (EditText) findViewById(R.id.documentsService);
        rate=(EditText) findViewById(R.id.rateService);
        listServices = (ListView) findViewById(R.id.listServices);
        DB = new DBHelper(this);
        services= new ArrayList<>();
        addButton = findViewById(R.id.addServiceButton);
        deleteButton = findViewById(R.id.deleteAccountButton2);


        DB.addService(new Service("Driver's license","Including customer first name, last name , date of birth,adress,license type(G1,G2,G)","Proof of residence","10$",null,null));
        DB.addService(new Service("Health card","Including customer first name, last name , date of birth,adress","Proof of residence - Proof of status","15$",null,null));
        DB.addService(new Service("Photo ID","Including customer first name, last name , date of birth,adress","Proof of residence - A photo of the customer","20$",null,null));
        viewData();



        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = serviceName.getText().toString().trim();
                String forms = formRequired.getText().toString().trim();
                String docs = documentRequired.getText().toString().trim();
                String _rate = rate.getText().toString().trim();
                if(!(forms.isEmpty()||docs.isEmpty()||name.isEmpty())){
                    Boolean insert = DB.addService(new Service(name,forms,docs,_rate,null,null));
                    if(insert){
                        Toast.makeText(adminWelcome.this,"added to database",Toast.LENGTH_LONG).show();
                        services.clear();
                        viewData();
                    }else {
                        Toast.makeText(adminWelcome.this,"data already exists",Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(adminWelcome.this,"Please enter all data", Toast.LENGTH_LONG).show();
                }

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(adminWelcome.this, delete_accounts.class);
                startActivity(i);
            }
        });

        listServices.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                showUpdateDeleteDialog();
                return true;
            }
        });
    }


    private void viewData(){
        Cursor cursor = DB.viewData();

        if (cursor.getCount() ==0){
            Toast.makeText(this,"No data to show", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                services.add(cursor.getString(0)+" (forms:"+cursor.getString(1)+")(documents: "+cursor.getString(2)+")(rate: "+cursor.getString(3)+")"); //index 0 is the name of the service
            }
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1 , services);
            listServices.setAdapter(adapter);
        }
    }

    private void showUpdateDeleteDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.edit_services, null);
        dialogBuilder.setView(dialogView);

        final EditText nameService = (EditText) dialogView.findViewById(R.id.nameService);
        final EditText formService  = (EditText) dialogView.findViewById(R.id.formService);
        final EditText documentsService  = (EditText) dialogView.findViewById(R.id.documentsService);
        final EditText rateService = (EditText) dialogView.findViewById(R.id.rateEdit);
        final Button updateServiceButton = (Button) dialogView.findViewById(R.id.updateServiceButton);
        final Button deleteServiceButton = (Button) dialogView.findViewById(R.id.deleteServiceButton);

        //dialogBuilder.setTitle(ServiceName);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        updateServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DB.updateService(nameService.getText().toString(),formService.getText().toString(),documentsService.getText().toString(),rateService.getText().toString());
                Toast.makeText(adminWelcome.this, "sucess", Toast.LENGTH_LONG).show();
                services.clear();
                viewData();
                b.dismiss();
            }
        });

        deleteServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DB.deleteService(nameService.getText().toString());
                Toast.makeText(adminWelcome.this, "sucess", Toast.LENGTH_LONG).show();
                services.clear();
                viewData();
                b.dismiss();
            }
        });

    }

    public void newService(View view){
        String name = serviceName.getText().toString();
        String forms = formRequired.getText().toString();
        String doc = documentRequired.getText().toString();
        String r = rate.getText().toString();

        Service service = new Service(name,forms,doc,r,null,null);
        DBHelper dbHandler = new DBHelper(this);
        dbHandler.addService(service);

        serviceName.setText("");
        formRequired.setText("");
        documentRequired.setText("");

        Toast.makeText(this,"added service",Toast.LENGTH_SHORT).show();

    }

    public void removeService(View view){
        DBHelper dbHandler = new DBHelper(this);
        dbHandler.deleteService(serviceName.getText().toString());

    }
    public String validateName(Service service){
        boolean validate = true;
        String regex = "[0-9]+";
        Pattern p = Pattern.compile(regex);
        String name = service.getName();
        Matcher m = p.matcher(name);
        if(m.matches()){
            return "Invalid,content invalid";
        }else{
            return "Valid content";
        }
    }

    public String validateDeleteUser(String email){
        final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        final Matcher matcher = pattern.matcher(email);

        if(email==null){
            return "Invalid,content invalid";
        }

        else if (!matcher.matches()){
            return "Invalid,content invalid";
        }

        else{
            return "Valid content";
        }
    }

    public String validateForm(Service service){
        boolean validate = true;
        String regex = "[0-9]+";
        Pattern p = Pattern.compile(regex);
        String Form = service.getForms();
        Matcher m = p.matcher(Form);
        if(m.matches()){
            return "Invalid,content invalid";
        }else{
            return "Valid content";
        }
    }


}