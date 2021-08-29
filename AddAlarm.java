package com.example.dfma_app_656995;

import java.util.Date;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;

import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.view.View;
import android.text.TextWatcher;
import android.text.Editable;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ListView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.content.Intent;
import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.RequiresApi;

public class AddAlarm extends Activity
{
    private EditText Title;
    private CheckBox Enabled;
    private Spinner Event;
    private Button DateButton;
    private Button TimeButton;

    private AlarmHelper AlarmHelper;
    private DateTime DateTime;

    private GregorianCalendar Calendar;
    private int Year;
    private int Month;
    private int Day;
    private int Hour;
    private int Minute;

    static final int DATE_DIALOG_ID = 0;
    static final int TIME_DIALOG_ID = 1;
    static final int DAYS_DIALOG_ID = 2;

    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.add_alarm);

        //mTag = (EditText)findViewById(R.id.e_tag);
        Title = (EditText)findViewById(R.id.e_title);
        //mDescription = (EditText) findViewById(R.id.e_description);
        Enabled = (CheckBox)findViewById(R.id.alarm_checkbox);
        Event = (Spinner)findViewById(R.id.occurence_spinner);
        DateButton = (Button)findViewById(R.id.date_button);
        TimeButton = (Button)findViewById(R.id.time_button);

        AlarmHelper = new AlarmHelper(this);
        AlarmHelper.fromIntent(getIntent());

        DateTime = new DateTime(this);

        Title.setText(AlarmHelper.getTitle());
        Title.addTextChangedListener(TitleChangedListener);

        //Tag.setText(mAlarm.getTag());
        //Title.addTextChangedListener(TitleChangedListener);

        //Description.setText(mAlarm.getDescription());
        //Title.addTextChangedListener(TitleChangedListener);

        Event.setSelection(AlarmHelper.getEvent());
        Event.setOnItemSelectedListener(EventSelectedListener);

        Enabled.setChecked(AlarmHelper.getEnabled());
        Enabled.setOnCheckedChangeListener(AlarmEnabledChangeListener);

        Calendar = new GregorianCalendar();
        Calendar.setTimeInMillis(AlarmHelper.getDate());
        Year = Calendar.get(Calendar.YEAR);
        Month = Calendar.get(Calendar.MONTH);
        Day = Calendar.get(Calendar.DAY_OF_MONTH);
        Hour = Calendar.get(Calendar.HOUR_OF_DAY);
        Minute = Calendar.get(Calendar.MINUTE);

        updateButtons();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected Dialog onCreateDialog(int id)
    {
        if (DATE_DIALOG_ID == id)
            return new DatePickerDialog(this, DateSetListener, Year, Month, Day);
        else if (TIME_DIALOG_ID == id)
            return new TimePickerDialog(this, TimeSetListener, Hour, Minute, DateTime.is24hClock());
        else if (DAYS_DIALOG_ID == id)
            return DaysPickerDialog();
        else
            return null;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog)
    {
        if (DATE_DIALOG_ID == id)
            ((DatePickerDialog)dialog).updateDate(Year, Month, Day);
        else if (TIME_DIALOG_ID == id)
            ((TimePickerDialog)dialog).updateTime(Hour, Minute);
    }

    public void onDateClick(View view)
    {
        if (AlarmHelper.ONCE == AlarmHelper.getEvent())
            showDialog(DATE_DIALOG_ID);
        else if (AlarmHelper.WEEKLY == AlarmHelper.getEvent())
            showDialog(DAYS_DIALOG_ID);
    }

    public void onTimeClick(View view)
    {
        showDialog(TIME_DIALOG_ID);
    }

    public void onDoneClick(View view)
    {
        Intent intent = new Intent();

        if (Title.getText().toString().isEmpty()) {
            Title.setError("Please Enter Reminder Title or Description");
        }else{
            AlarmHelper.toIntent(intent);
            setResult(RESULT_OK, intent);
            finish();}
    }

    private DatePickerDialog.OnDateSetListener DateSetListener = new DatePickerDialog.OnDateSetListener()
    {
        @RequiresApi(api = Build.VERSION_CODES.N)
        public void onDateSet(DatePicker view, int year, final int monthOfYear, int dayOfMonth)
        {
            Year = year;
            Month = monthOfYear;
            Day = dayOfMonth;

            Calendar = new GregorianCalendar(Year, Month, Day, Hour, Minute);
            AlarmHelper.setDate(Calendar.getTimeInMillis());

            updateButtons();
        }
    };

    private TimePickerDialog.OnTimeSetListener TimeSetListener = new TimePickerDialog.OnTimeSetListener()
    {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute)
        {
            Hour = hourOfDay;
            Minute = minute;

            Calendar = new GregorianCalendar(Year, Month, Day, Hour, Minute);
            AlarmHelper.setDate(Calendar.getTimeInMillis());

            updateButtons();
        }
    };

    private TextWatcher TitleChangedListener = new TextWatcher()
    {
        public void afterTextChanged(Editable s)
        {
            if (Title.getText().toString().isEmpty()) {
                Title.setError("Please Enter Reminder Title or Description");
            }else {
                AlarmHelper.setTitle(Title.getText().toString());}
            //mAlarm.setTag(mTag.getText().toString());
            //mAlarm.setDescription(mDescription.getText().toString());
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
        }
    };

    private AdapterView.OnItemSelectedListener EventSelectedListener = new AdapterView.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
        {
            AlarmHelper.setEvent(position);
            updateButtons();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {
        }
    };

    private CompoundButton.OnCheckedChangeListener AlarmEnabledChangeListener = new CompoundButton.OnCheckedChangeListener()
    {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        {
            AlarmHelper.setEnabled(isChecked);
        }
    };

    private void updateButtons()
    {
        if (AlarmHelper.ONCE == AlarmHelper.getEvent())
            DateButton.setText(DateTime.formatDate(AlarmHelper));
        else if (AlarmHelper.WEEKLY == AlarmHelper.getEvent())
            DateButton.setText(DateTime.formatDays(AlarmHelper));
        TimeButton.setText(DateTime.formatTime(AlarmHelper));
    }

    private Dialog DaysPickerDialog()
    {
        AlertDialog.Builder builder;
        final boolean[] days = DateTime.getDays(AlarmHelper);
        final String[] names = DateTime.getFullDayNames();

        builder = new AlertDialog.Builder(this);

        builder.setTitle("Week days");

        builder.setMultiChoiceItems(names, days, new DialogInterface.OnMultiChoiceClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton, boolean isChecked)
            {
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                DateTime.setDays(AlarmHelper, days);
                updateButtons();
            }
        });

        builder.setNegativeButton("Cancel", null);

        return builder.create();
    }
}
