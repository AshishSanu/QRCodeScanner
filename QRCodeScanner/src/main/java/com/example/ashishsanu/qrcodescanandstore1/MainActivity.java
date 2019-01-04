package com.example.ashishsanu.qrcodescanandstore1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.sql.Timestamp;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    private Button scan_btn, view_btn, delete_btn;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        scan_btn = (Button) findViewById(R.id.scan_btn);
        textView = (TextView) findViewById(R.id.result_txt);
        view_btn = (Button) findViewById(R.id.view_btn);
        delete_btn = (Button) findViewById(R.id.delete_btn);
        viewAll();
        delete();

        final Activity activity = this;

        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

    }

    public void delete() {
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Delete.class);
                startActivity(i);
            }
        });
    }

    public void viewAll() {
        view_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Display.class);
                startActivity(i);

              /*  Cursor res = myDb.getAllData();
                if(res.getCount() == 0) {

                    showMessage("Error","Nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Id :"+ res.getString(0)+"\n");

                    buffer.append("QR Data :"+ res.getString(1)+"\n");
                 //   buffer.append("Marks :"+ res.getString(3)+"\n\n");
                 //   buffer.append("Time :"+ Timestamp.valueOf(res.getString(4))+"\n\n");
                }


                showMessage("Data",buffer.toString());*/
            }
        });
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {

                Toast.makeText(this, "You Cancled Scanning", Toast.LENGTH_LONG).show();
            } else {

                //textView.setText(result.getContents());
                String str = result.getContents();
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("key", str);
                startActivity(intent);

            }
        } else {

            super.onActivityResult(requestCode, resultCode, data);
        }

    }
}