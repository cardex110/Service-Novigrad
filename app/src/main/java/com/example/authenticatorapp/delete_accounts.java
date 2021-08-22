package com.example.authenticatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class delete_accounts extends AppCompatActivity {

    Button deleteButton;
    DBHelper DB;
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> accounts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_accounts);
        DB = new DBHelper(this);
        deleteButton = findViewById(R.id.deleteAccountButton);
        listView = findViewById(R.id.listAccounts);
        accounts = new ArrayList<String>();
        final EditText email = (EditText) findViewById(R.id.emailToDelete);

        //TODO VIEW DATA();
        viewData();

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DB.deleteUser(email.getText().toString());
                Toast.makeText(delete_accounts.this,"deleted",Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
    private void viewData(){
        Cursor cursor = DB.ViewU();

        if (cursor.getCount() ==0){
            Toast.makeText(this,"No data to show", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                accounts.add(cursor.getString(0)); //index 0 is the name of the service
            }
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1 , accounts);
            listView.setAdapter(adapter);
        }
    }


}