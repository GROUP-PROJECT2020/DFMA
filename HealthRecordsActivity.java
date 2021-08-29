package com.example.dfma_app_656995;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class HealthRecordsActivity  extends Activity {
    EditText edit;
    Spinner repeat;

    private int mYear;
    private int mMonth;
    private int mDay;
    static final int DATE_DIALOG_ID = 0, DATE_DIALOG_ID_1 = 1;
    TimePicker timePicker;

    private EditText tvDisplayTime;
    int hour;
    int minute;
    static final int TIME_DIALOG_ID = 999;
    private int notificationId =1;
    EditText tag, condition, doctor, contact1, contact2, date, time;
    public FirebaseDatabase Database = FirebaseDatabase.getInstance();
    public DatabaseReference ref;
    public HealthHelper myDb;

    @SuppressLint("WrongViewCast")
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.health_records);
        tag = (EditText) findViewById(R.id.e_tag);
        condition = (EditText) findViewById(R.id.e_condition);
        doctor = (EditText) findViewById(R.id.e_doc_name);
        contact1 = (EditText) findViewById(R.id.e_doc_contact);
        contact2 = (EditText) findViewById(R.id.e_farmer_contact);
        int length = 10;
        contact1.setFilters(new InputFilter.LengthFilter[]{new InputFilter.LengthFilter(length)});
        contact2.setFilters(new InputFilter.LengthFilter[]{new InputFilter.LengthFilter(length)});

        //date = (EditText) findViewById(R.id.e_date_due);
        //time = (EditText) findViewById(R.id.e_time_due);
        //repeat = (Spinner) findViewById(R.id.e_repeat);
        myDb = new HealthHelper();
        ref = Database.getReference().child("Health");

        Button btnSet=findViewById(R.id.remind_health);
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remindOnButtonClick();
            }
        });

        Button healthButton = findViewById(R.id.view_health_bt);
        healthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHealthOnButtonClick();
            }
        });

        Button saveHealth = (Button) findViewById(R.id.save_health);
        saveHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View v) {

                Calendar startTime = Calendar.getInstance();
                startTime.set(Calendar.HOUR_OF_DAY, 22);
                startTime.set(Calendar.MINUTE, 23);
                startTime.set(Calendar.SECOND, 30);
                long alarmStartTime = startTime.getTimeInMillis();
                if (tag.getText().toString().isEmpty()) {
                    tag.setError("Please Enter Tag");
                } else if (condition.getText().toString().isEmpty()) {
                    condition.setError("Please Enter Condition");
                } else if (doctor.getText().toString().isEmpty()) {
                    doctor.setError("Please Enter the Vetenerian's name");
                } else if (contact1.getText().toString().isEmpty()) {
                    contact1.setError("Please Enter the Vetenerian's phone");
                } else if (contact1.getText().toString().length()<10) {
                    contact1.setError("Please Enter a valid number (10 digits)");
                } else if (contact2.getText().toString().isEmpty()) {
                    contact2.setError("Please Enter the Farmer's phone");
                } else if (contact2.getText().toString().length()<10) {
                    contact2.setError("Please Enter a valid number (10 digits)");
                }else {
                    myDb.setTag(tag.getText().toString().trim());
                    myDb.setCondition(condition.getText().toString().trim());
                    myDb.setDoctorName(doctor.getText().toString().trim());
                    myDb.setDoctorContact(contact1.getText().toString().trim());
                    myDb.setFarmerContact(contact2.getText().toString().trim());
                    // myDb.setDateDue(date.getText().toString().trim());
                    //myDb.setTime(time.getText().toString().trim());
                    // myDb.setCategory(repeat.getText().toString().trim());

                    ref.push().setValue(myDb);
                    Toast.makeText(HealthRecordsActivity.this, "Insertion Success", Toast.LENGTH_SHORT).show();
            //        nextOnButtonClick();
                }
            }
        });
    }

    private void viewHealthOnButtonClick() {
        Intent i=new Intent(this,ViewHealthActivity.class);
        startActivity(i);
    }

    private void nextOnButtonClick() {
        Intent i = new Intent(HealthRecordsActivity.this, ViewHealthActivity.class);
        startActivity(i);
    }

    private void remindOnButtonClick() {
        Intent i = new Intent(HealthRecordsActivity.this, AlarmRecordsActivity.class);
        startActivity(i);
    }

    //public void datePickOnButtonClick(View v) {
    //    edit = (EditText) findViewById(R.id.e_date_due);
    //   EditText datePick = (EditText) findViewById(R.id.e_date_due);
    //    final Calendar c = Calendar.getInstance();
    //    mYear = c.get(Calendar.YEAR);
    //    mMonth = c.get(Calendar.MONTH);
    //    mDay = c.get(Calendar.DAY_OF_MONTH);
     //   updateDisplay();

    //    datePick.setOnClickListener(new View.OnClickListener() {

    //        public void onClick(View v) {
    //            showDialog(DATE_DIALOG_ID);

    //        }
    //    });
   // }

  //  private void updateDisplay() {

   //     edit.setText(new StringBuilder().append(mDay).append("-")
   //             .append(mMonth + 1).append("-").append(mYear).append(" "));
  //  }

  //  private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
   //     public void onDateSet(DatePicker view, int year, int monthOfYear,
   //                           int dayOfMonth) {
   //         mYear = year;
   //         mMonth = monthOfYear;
  //          mDay = dayOfMonth;
  //          updateDisplay();
 //       }
  //  };

  //  @Override
   // protected Dialog onCreateDialog(int id) {
   //     switch (id) {
   //         case DATE_DIALOG_ID: // set date picker as current time
     //           return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
     //                   mDay);

     //       case TIME_DIALOG_ID:
      //          // set time picker as current time
     //           return new TimePickerDialog(this, timePickerListener, hour, minute,
                        //false);
   //     }

   //     return null;
 //   }

 //   public void timeSetOnClick(View v) {
  //      tvDisplayTime = (EditText) findViewById(R.id.e_time_due);
  //      setCurrentTimeOnView();

  //      tvDisplayTime.setOnClickListener(new View.OnClickListener() {
   //         public void onClick(View v) {
  //              showDialog(TIME_DIALOG_ID);
  //          }
  //      });
  //  }

 //   public void setCurrentTimeOnView() {

  //      final Calendar c = Calendar.getInstance();
  //      hour = c.get(Calendar.HOUR_OF_DAY);
   //     minute = c.get(Calendar.MINUTE);

   //     tvDisplayTime.setText(new StringBuilder().append(pad(hour)).append(":")
   //             .append(pad(minute)));
 //   }

 //   private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
  //      public void onTimeSet(TimePicker view, int selectedHour,
                 //             int selectedMinute) {
   //         hour = selectedHour;
   //         minute = selectedMinute;
//
    //        tvDisplayTime.setText(new StringBuilder().append(pad(hour))
      //              .append(":").append(pad(minute)));
    //    }
  //  };

   // private static String pad(int c) {
  //      if (c >= 10)
   //         return String.valueOf(c);
  //      else
   //         return "0" + String.valueOf(c);
 //   }

}