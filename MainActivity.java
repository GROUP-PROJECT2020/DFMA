package com.example.dfma_app_656995;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {

    DataBaseHelper myDb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager mViewPager = findViewById(R.id.viewPage);
        ImageAdapter adapterView = new ImageAdapter(this);
        mViewPager.setAdapter(adapterView);

        Button addButton = findViewById(R.id.add_bt);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOnButtonClick();
            }
        });

        Button viewButton = findViewById(R.id.view_bt);
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewOnButtonClick();
            }
        });

        Button AboutButton = findViewById(R.id.info_bt);
        AboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aboutOnButtonClick();
            }
        });

        //Button settingsButton = findViewById(R.id.settings_bt);
        //viewButton.setOnClickListener(new View.OnClickListener() {
       //     @Override
         //   public void onClick(View v) {
           //     viewOnButtonClick();
            //}
        //});

        //Toast.makeText(this, "Connection Success",Toast.LENGTH_SHORT).show();
    }

    private void aboutOnButtonClick() {
        Intent i = new Intent(MainActivity.this, About.class);
        startActivity(i);
    }

    public void addOnButtonClick() {
        Intent i = new Intent(MainActivity.this, SelectEventActivity.class);
        startActivity(i);
    }

    public void viewOnButtonClick() {
        Intent i = new Intent(MainActivity.this, ViewRecordsActivity.class);
        startActivity(i);
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        Intent i;
        switch (item.getItemId()) {

            case R.id.add_bt:
                i = new Intent(MainActivity.this, SelectEventActivity.class);
                startActivity(i);
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_LONG).show();
                return true;

            case R.id.view_bt:
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_LONG).show();
                i = new Intent(MainActivity.this, ViewRecordsActivity.class);
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}



