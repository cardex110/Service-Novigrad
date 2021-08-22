package com.example.authenticatorapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class Search_By_Services extends AppCompatActivity {

    ListView listView;
    DBHelper DB;
    ArrayList<String> typeOfService;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__by__services);
        DB=new DBHelper(this);
        listView = findViewById(R.id.listServices);
        typeOfService = new ArrayList<String>();
       viewData();
        //TODO implement viewData()

        adapter = new ArrayAdapter<>(Search_By_Services.this, android.R.layout.simple_list_item_1, typeOfService);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Toad = (String) adapter.getItem(position);
                Intent i = new Intent(Search_By_Services.this, Branches.class);
                i.putExtra("service",Toad);
                i.putExtra("email",getIntent().getStringExtra("email"));
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search,menu);
        MenuItem menuItem = menu.findItem(R.id.search_view);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    private void viewData(){
        Cursor cursor = DB.viewData();

        if (cursor.getCount() ==0){
            Toast.makeText(this,"No data to show", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                typeOfService.add(cursor.getString(0)); //index 0 is the name of the service
            }
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1 , typeOfService);
            listView.setAdapter(adapter);
        }
    }

    public String checkServiceValidToSearch(String service){
        if(service.equals("Health Card")){
            return "Valid content";
        }
        return "Invalid,content invalid";
    }
}