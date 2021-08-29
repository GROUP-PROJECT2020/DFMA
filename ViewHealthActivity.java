package com.example.dfma_app_656995;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewHealthActivity extends AppCompatActivity {
    ListView listView;
    Button add, home;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference;
    ArrayList<HealthHelper> healthHelper = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_health_activity);

        ViewPager mViewPager = findViewById(R.id.viewHealthPage);
        HealthImageAdapter healthAdapterView = new HealthImageAdapter(this);
        mViewPager.setAdapter(healthAdapterView);

        listView = (ListView) findViewById(R.id.healthView);
        progressDialog = new ProgressDialog(this);
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://dairy-farming-management.firebaseio.com//Health");
        progressDialog.setMessage("Server Loading, Please wait");
        progressDialog.show();

        add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       addOnClickListener();
                                   }
                               }
        );

        home = findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        homeOnClickListener();
                                    }
                                }
        );

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HealthHelper healthHelper1 = snapshot.getValue(HealthHelper.class);
                    healthHelper.add(healthHelper1);
                }
                HealthAdapter healthAdapter = new HealthAdapter(getApplicationContext(), healthHelper);
                listView.setAdapter(healthAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void homeOnClickListener() {
        Intent i = new Intent(ViewHealthActivity.this, MainActivity.class);
        startActivity(i);
    }

    private void addOnClickListener() {
        Intent i = new Intent(ViewHealthActivity.this, HealthRecordsActivity.class);
        startActivity(i);
    }
}