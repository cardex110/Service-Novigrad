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
import java.util.regex.Pattern;

public class Search_By_Adress extends AppCompatActivity {
    ListView listView;
    ArrayAdapter<String> adapter;
    DBHelper DB;
    ArrayList<String> adresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__by__adress);
        DB = new DBHelper(this);
        listView = findViewById(R.id.listAdresses);
        adresses = new ArrayList<String>();

        viewData();

        adapter = new ArrayAdapter<>(Search_By_Adress.this, android.R.layout.simple_list_item_1, adresses);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Toadd = adapter.getItem(position);
                Cursor cursor = DB.viewB(Toadd);
                if(cursor.moveToNext()){
                    String branchName = cursor.getString(0);
                    Intent intent = new Intent(Search_By_Adress.this, Services_for_customer.class);
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
        Cursor cursor = DB.viewAddress();

        if (cursor.getCount() ==0){
            Toast.makeText(this,"No data to show", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                adresses.add(cursor.getString(0)); //index 0 is the name of the service
            }
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1 , adresses);
            listView.setAdapter(adapter);
        }
    }

    public boolean checkAdressToSearch(String adresse){
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]" ,Pattern.CASE_INSENSITIVE);
        if(pattern.matcher(adresse).matches()){
            return false;
        }
        return true;
    }
}