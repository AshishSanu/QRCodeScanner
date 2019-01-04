package com.example.ashishsanu.qrcodescanandstore1;

import android.database.Cursor;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;


import java.util.ArrayList;

public class Display extends AppCompatActivity {
    DatabaseHelper myDb;
    ListView userList;

    ArrayList<String> listItem;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        myDb = new DatabaseHelper(this);

        listItem = new ArrayList<>();
        userList=(ListView)findViewById(R.id.display_list);

        viewData();

    }

    public void viewData() {
        Cursor cursor=myDb.getAllData();

        if(cursor.getCount() == 0) {

            Toast.makeText(this,"No Data To Show",Toast.LENGTH_LONG).show();
        }
        else{
            while (cursor.moveToNext()){
                listItem.add(cursor.getString(0));
                listItem.add(cursor.getString(1));
            }

            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listItem);
            userList.setAdapter(adapter);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);

        MenuItem searchItem = menu.findItem(R.id.item_search);
        SearchView searchView =(SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<String> userlist = new ArrayList<>();
                for (String user : listItem){
                    if (user.toLowerCase().contains(newText.toLowerCase())){
                        userlist.add(user);
                    }
                }

                ArrayAdapter<String> adapter= new ArrayAdapter<String>(Display.this,android.R.layout.simple_list_item_1,userlist);
                userList.setAdapter(adapter);

                return true;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }
}

