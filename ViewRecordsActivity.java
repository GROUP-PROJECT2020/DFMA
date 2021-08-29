package com.example.dfma_app_656995;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewRecordsActivity extends Activity {

    DataBaseHelper myDb;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_buttons);

        Button cowButton = findViewById(R.id.view_cow_bt);
        cowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewCowOnButtonClick();
            }
        });
        Button milkButton = findViewById(R.id.view_milk_bt);
        milkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewMilkOnButtonClick();
            }
        });
        Button healthButton = findViewById(R.id.view_health_bt);
        healthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHealthOnButtonClick();
            }
        });
        Button btnSet=findViewById(R.id.remind_health);
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remindOnButtonClick();
            }
        });
    }

    private void remindOnButtonClick() {
        Intent i = new Intent(this, AlarmRecordsActivity.class);
        startActivity(i);
    }

    public void viewCowOnButtonClick()
    {
        Intent i=new Intent(this,ViewCowActivity.class);
        startActivity(i);
    }
    public void viewMilkOnButtonClick()
    {
        Intent i=new Intent(this,ViewMilkActivity.class);
        startActivity(i);
    }
    public void viewHealthOnButtonClick()
    {
        Intent i=new Intent(this,ViewHealthActivity.class);
        startActivity(i);
    }
}


