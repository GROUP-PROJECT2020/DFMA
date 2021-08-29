package com.example.dfma_app_656995;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.Calendar;

public class CowRecordsActivity extends Activity implements Serializable {
    EditText edit;
    private int mYear;
    private int mMonth;
    private int mDay;
    static final int DATE_DIALOG_ID = 0;

    public FirebaseDatabase Database = FirebaseDatabase.getInstance();
    public DatabaseReference ref;
    public DataBaseHelper myDb;
    public EditText tag, breed, source, sourceContact, dob, mother, father, category;

    @SuppressLint("WrongViewCast")
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.cow_records);
        tag = (EditText) findViewById(R.id.e_tag);
        breed = (EditText) findViewById(R.id.e_breed);
        source = (EditText) findViewById(R.id.e_source);
        sourceContact = (EditText) findViewById(R.id.e_source_contact);
        dob = (EditText) findViewById(R.id.e_date_born);
        mother = (EditText) findViewById(R.id.e_mother);
        father = (EditText) findViewById(R.id.e_father);
        category = findViewById(R.id.e_category);
        myDb = new DataBaseHelper();
        ref = Database.getReference().child("Cow");

        Button cowButton = findViewById(R.id.view_cow_bt);
        cowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewCowOnButtonClick();
            }
        });

        Button saveCow = (Button) findViewById(R.id.save_cow);
        saveCow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tag.getText().toString().isEmpty()) {
                    tag.setError("Please Enter Tag");
                }
                else if(breed.getText().toString().isEmpty()) {
                    breed.setError("Please Enter Breed");
                }
                else if(source.getText().toString().isEmpty()) {
                    source.setError("Please Enter the Source");
                }
                else if(dob.getText().toString().isEmpty()) {
                    dob.setError("Please Enter Date of Birth");
                }
                else if(category.getText().toString().isEmpty()) {
                    category.setError("Please Enter Cow Category");
                }
                else {
                    String id = ref.push().getKey();
                    //myDb.setId(id);
                    myDb.setTag(tag.getText().toString().trim());
                    myDb.setBreed(breed.getText().toString().trim());
                    myDb.setSource(source.getText().toString().trim());
                    //myDb.setSourceContact(sourceContact.getText().toString().trim());
                    myDb.setDob(dob.getText().toString().trim());
                    myDb.setMother(mother.getText().toString().trim());
                    myDb.setFather(father.getText().toString().trim());
                    myDb.setCategory(category.getText().toString().trim());

                    ref.push().setValue(myDb);
                    Toast.makeText(CowRecordsActivity.this, "Insertion Success", Toast.LENGTH_SHORT).show();
 //                   nextOnButtonClick();
                }
            }
        });
    }

    private void viewCowOnButtonClick() {
        Intent i=new Intent(this,ViewCowActivity.class);
        startActivity(i);
    }

    private void nextOnButtonClick() {
        Intent i = new Intent(CowRecordsActivity.this, ViewCowActivity.class);
        startActivity(i);
    }

    public void datePickOnButtonClick(View v) {
        edit = (EditText) findViewById(R.id.e_date_born);
        EditText datePick = (EditText) findViewById(R.id.e_date_born);

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        updateDisplay();

        datePick.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

                showDialog(DATE_DIALOG_ID);

            }
        });
    }

    private void updateDisplay() {
        edit.setText(new StringBuilder().append(mDay).append("-")
                .append(mMonth + 1).append("-").append(mYear).append(" "));
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            updateDisplay();
        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID: // set date picker as current time
//                DatePickerDialog datePickerDialog = new DatePickerDialog(CowRecordsActivity.this, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        mYear = year;
//                        mMonth = month;
//                        mDay = dayOfMonth;
//                    }
//                },mYear,mMonth,mDay);
//
                //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                //datePickerDialog.show();
                //updateDisplay();

                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
                       mDay);
        }

        return null;
    }

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }
}

