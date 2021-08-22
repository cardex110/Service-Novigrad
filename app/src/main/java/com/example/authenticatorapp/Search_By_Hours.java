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

public class Search_By_Hours extends AppCompatActivity {

    ListView listView;
    DBHelper DB;
    ArrayList<String> hours;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DB = new DBHelper(this);
        setContentView(R.layout.activity_search__by__hours);

        listView = findViewById(R.id.listHours);
        hours = new ArrayList<String>();



        adapter = new ArrayAdapter<>(Search_By_Hours.this, android.R.layout.simple_list_item_1, hours);

        listView.setAdapter(adapter);
        viewData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Toadd = adapter.getItem(position);
                String[] parts = Toadd.split(" - ");
                String start = parts[0];
                String end = parts[1];
                Cursor cursor = DB.viewUser(start,end,getIntent().getStringExtra("day")); //TODO to change
                if(cursor.moveToNext()){
                    String branchName = cursor.getString(0);
                    Intent intent = new Intent(Search_By_Hours.this, Services_for_customer.class);
                    intent.putExtra("username",branchName);
                    intent.putExtra("email",getIntent().getStringExtra("email"));
                    startActivity(intent);
                }
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
        Cursor cursor = DB.viewStart(getIntent().getStringExtra("day"));
        Cursor cursor1 = DB.viewEnd(getIntent().getStringExtra("day"));

        if (cursor.getCount() ==0){
            Toast.makeText(this,"No data to show", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()&&cursor1.moveToNext()){
                hours.add(cursor.getString(0)+" - "+cursor1.getString(0)); //index 0 is the name of the service
            }
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1 , hours);
            listView.setAdapter(adapter);
        }
    }

    public String checkHourValidToSearch(String hour){
        if(hour.equals("10:45")){
            return "Valid content";
        }
        return "Invalid,content invalid";
    }


}