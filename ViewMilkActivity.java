package com.example.dfma_app_656995;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewMilkActivity extends AppCompatActivity {

    ListView listView;
    ProgressDialog progressDialog;
    Button add, home;
    DatabaseReference databaseReference;
    ArrayList<MilkHelper> milkHelper = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_milk_activity);

        ViewPager mViewPager = findViewById(R.id.viewMilkPage);
        MilkImageAdapter milkAdapterView = new MilkImageAdapter(this);
        mViewPager.setAdapter(milkAdapterView);

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

        listView = (ListView) findViewById(R.id.milkView);
        progressDialog = new ProgressDialog(this);
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://dairy-farming-management.firebaseio.com//Milk");
        progressDialog.setMessage("Server Loading, Please wait");
        progressDialog.show();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MilkHelper milkHelper1 = snapshot.getValue(MilkHelper.class);
                    milkHelper.add(milkHelper1);
                }
                MilkAdapter milkadapter = new MilkAdapter(getApplicationContext(), milkHelper);
                listView.setAdapter(milkadapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void homeOnClickListener() {
        Intent i = new Intent(ViewMilkActivity.this, MainActivity.class);
        startActivity(i);
    }

    private void addOnClickListener() {
        Intent i = new Intent(ViewMilkActivity.this, MilkRecordsActivity.class);
        startActivity(i);
    }
}
