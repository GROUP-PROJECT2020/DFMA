package com.example.dfma_app_656995;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewCowActivity extends AppCompatActivity {
    ListView listView;
    ProgressDialog progressDialog;
    Button add, home;
    DatabaseReference databaseReference;
    ArrayList<DataBaseHelper> cowHelper = new ArrayList<>();
    ArrayList<String> helper = new ArrayList<>();
    DataBaseHelper Db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_cow_activity);

        ViewPager mViewPager = findViewById(R.id.viewCowPage);
        CowImageAdapter cowAdapterView = new CowImageAdapter(this);
        mViewPager.setAdapter(cowAdapterView);

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

        listView = (ListView) findViewById(R.id.cowView);
        final CowAdapter cowAdapter = new CowAdapter(ViewCowActivity.this, android.R.layout.simple_list_item_1,cowHelper);
        listView.setAdapter(cowAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Db.setId(helper.get(position));
                //Toast.makeText(getApplicationContext(), "You have selected an item:" + cowAdapter.getItem(position),Toast.LENGTH_LONG).show();
            }
        });

        /*listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final int which_item = position;
                new AlertDialog.Builder(ViewCowActivity.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are you sure?")
                        .setMessage("Would you like to delete this?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                cowHelper.remove(which_item);
                                final  String delete = Db.getId().substring(0, 6);
                                if (delete == "") {
                                    Toast.makeText(getApplicationContext(), "Select an item first", Toast.LENGTH_LONG).show();
                                }else
                                {
                                    databaseReference.child("Cow").child(delete).addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                                    @Override
                                                                                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                                                                        databaseReference.child(delete).removeValue();
                                                                                                                    }

                                                                                                                    @Override
                                                                                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                                                                                    }
                                                                                                                }
                                    );
                                    Toast.makeText(getApplicationContext(), "Successfully deleted", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(),ViewCowActivity.class);
                                }
                                cowAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();

                return true;
            }
        });*/

        progressDialog = new ProgressDialog(this);
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://dairy-farming-management.firebaseio.com//Cow");
        progressDialog.setMessage("Server Loading, Please wait");
        progressDialog.show();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Id;
                progressDialog.dismiss();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DataBaseHelper cowHelper1 = snapshot.getValue(DataBaseHelper.class);
                    cowHelper.add(cowHelper1);
                }

                CowAdapter adapterCow = new CowAdapter(ViewCowActivity.this, android.R.layout.simple_list_item_1, cowHelper);
                listView.setAdapter(adapterCow);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "You have selected an item:" + cowAdapter.getItem(position),Toast.LENGTH_LONG).show();
            }
        });
                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final int which_item = position;
                new AlertDialog.Builder(ViewCowActivity.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are you sure?")
                        .setMessage("Would you like to delete this?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                cowHelper.remove(which_item);
                                cowAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();

                    return true;
                    }
                    });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void homeOnClickListener() {
        Intent i = new Intent(ViewCowActivity.this, MainActivity.class);
        startActivity(i);
    }

    private void addOnClickListener() {
        Intent i = new Intent(ViewCowActivity.this, CowRecordsActivity.class);
        startActivity(i);
    }
    
}