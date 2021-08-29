package com.example.dfma_app_656995;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class MilkRecordsActivity extends Activity {

    public EditText edit;
        public int mYear;
        public int mMonth;
        public int mDay;
        static final int DATE_DIALOG_ID = 0;
        public MilkHelper myDb;
        public DatabaseReference ref;
        public Button saveMilk;
        public EditText tag, farmerName, morning, midday, evening, domestic, sale, buyer, dateToday;
        //public int total, milkSale;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.milk_records);

                tag = findViewById(R.id.e_tag);
                farmerName = findViewById(R.id.e_farmer_name);
                morning = findViewById(R.id.e_morning);
                midday = findViewById(R.id.e_mid_morning);
                evening = findViewById(R.id.e_evening);
                domestic = findViewById(R.id.e_domestic);
                sale = findViewById(R.id.e_sale);
                buyer = findViewById(R.id.e_buyer);
                dateToday = findViewById(R.id.e_date_today);
                myDb = new MilkHelper();
                ref = FirebaseDatabase.getInstance().getReference().child("Milk");

                Button milkButton = findViewById(R.id.view_milk_bt);
                milkButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewMilkOnButtonClick();
                }
            });

                saveMilk = findViewById(R.id.save_milk);
                saveMilk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tag.getText().toString().isEmpty()) {
                        tag.setError("Please Enter Tag");
                    } else if (morning.getText().toString().isEmpty()) {
                        morning.setError("Please Enter Morning Amount");
                    } else if (midday.getText().toString().isEmpty()) {
                        midday.setError("Please Enter Midday Amount");
                    } else if (evening.getText().toString().isEmpty()) {
                        evening.setError("Please Enter Evening amount");
                    } else if (sale.getText().toString().isEmpty()) {
                        sale.setError("Please Enter Sale amount");
                    }else if (dateToday.getText().toString().isEmpty()) {
                        dateToday.setError("Please Enter Today's date");
                    } else {
//                    total = Integer.parseInt(morning.getText().toString() + midday.getText().toString() + evening.getText().toString());
//                    milkSale = Integer.parseInt(domestic.getText().toString())- total;
                        myDb.setTag(tag.getText().toString().trim());
                        myDb.setFarmerName(farmerName.getText().toString().trim());
                        myDb.setMorning(morning.getText().toString().trim());
                        myDb.setMidDay(midday.getText().toString().trim());
                        myDb.setEvening(evening.getText().toString().trim());
                        myDb.setDomestic(domestic.getText().toString().trim());
                        myDb.setSale(sale.getText().toString().trim());
                        myDb.setBuyer(buyer.getText().toString().trim());
                        myDb.setDateToday(dateToday.getText().toString().trim());

                        ref.push().setValue(myDb);
                        Toast.makeText(MilkRecordsActivity.this, "Insertion Success", Toast.LENGTH_SHORT).show();
                       // nextOnButtonClick();
                    }
                }
            });
        }

    private void viewMilkOnButtonClick() {
        Intent i=new Intent(this,ViewMilkActivity.class);
        startActivity(i);
    }

    private void nextOnButtonClick() {
        Intent i = new Intent(MilkRecordsActivity.this, ViewMilkActivity.class);
        startActivity(i);
    }

    @SuppressLint("CutPasteId")
        public void datePickOnButtonClick(View v) {
                edit = findViewById(R.id.e_date_today);
                EditText dobPick = findViewById(R.id.e_date_today);
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                updateDisplay();

                dobPick.setOnClickListener(new View.OnClickListener() {
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
            if (id == DATE_DIALOG_ID) { // set date picker as current time
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
                        mDay);
            }

                return null;
        }// date complete

}


