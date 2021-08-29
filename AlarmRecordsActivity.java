package com.example.dfma_app_656995;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;

public class AlarmRecordsActivity extends Activity {
        private final String TAG = "AlarmMe";

        private ListView AlarmList;
        private AlarmListAdapter AlarmListAdapter;
        private AlarmHelper CurrentAlarm;

        private final int NEW_ALARM_ACTIVITY = 0;
        private final int EDIT_ALARM_ACTIVITY = 1;
        private final int PREFERENCES_ACTIVITY = 2;
        private final int ABOUT_ACTIVITY = 3;

        private final int CONTEXT_MENU_EDIT = 0;
        private final int CONTEXT_MENU_DELETE = 1;
        private final int CONTEXT_MENU_DUPLICATE = 2;

        Button add, home;

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onCreate(Bundle bundle)
        {
            super.onCreate(bundle);
            setContentView(R.layout.main);

            ViewPager mViewPager = findViewById(R.id.viewReminderPage);
            ReminderImageAdapter reminderAdapterView = new ReminderImageAdapter(this);
            mViewPager.setAdapter(reminderAdapterView);

            home = findViewById(R.id.home);
            home.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            homeOnClickListener();
                                        }
                                    }
            );

            TextView textView = findViewById(R.id.textView1);
            String message = getIntent().getStringExtra("message");
            textView.setText(message);

            Log.i(TAG, "AlarmMe.onCreate()");

            AlarmList = (ListView)findViewById(R.id.alarm_list);

            AlarmListAdapter = new AlarmListAdapter(this);
            AlarmList.setAdapter(AlarmListAdapter);
            AlarmList.setOnItemClickListener(mListOnItemClickListener);
            registerForContextMenu(AlarmList);

            CurrentAlarm = null;
        }

        private void homeOnClickListener() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

        private void addOnClickListener() {
        Intent i = new Intent(this, AddAlarm.class);
        startActivity(i);
    }

        @Override
        public void onDestroy()
        {
            super.onDestroy();
            Log.i(TAG, "AlarmMe.onDestroy()");
//    mAlarmListAdapter.save();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onResume()
        {
            super.onResume();
            Log.i(TAG, "AlarmMe.onResume()");
            AlarmListAdapter.updateAlarms();
        }

        public void onAddAlarmClick(View view)
        {
            Intent intent = new Intent(getBaseContext(), AddAlarm.class);

            CurrentAlarm = new AlarmHelper (this);
            CurrentAlarm.toIntent(intent);

            this.startActivityForResult(intent, NEW_ALARM_ACTIVITY);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data)
        {
            if (requestCode == NEW_ALARM_ACTIVITY)
            {
                if (resultCode == RESULT_OK)
                {
                    CurrentAlarm.fromIntent(data);
                    AlarmListAdapter.add(CurrentAlarm);
                }
                CurrentAlarm = null;
            }
            else if (requestCode == EDIT_ALARM_ACTIVITY)
            {
                if (resultCode == RESULT_OK)
                {
                    CurrentAlarm.fromIntent(data);
                    AlarmListAdapter.update(CurrentAlarm);
                }
                CurrentAlarm = null;
            }
            else if (requestCode == PREFERENCES_ACTIVITY)
            {
                AlarmListAdapter.onSettingsUpdated();
            }
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu)
        {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.menu, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item)
        {
            if (R.id.menu_settings == item.getItemId())
            {
                Intent intent = new Intent(getBaseContext(), Preferences.class);
                startActivityForResult(intent, PREFERENCES_ACTIVITY);
                return true;
            }
            else if (R.id.menu_about == item.getItemId())
            {
                Intent intent = new Intent(getBaseContext(), About.class);
                startActivity(intent);
                return true;
            }
            else
            {
                return super.onOptionsItemSelected(item);
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
        {
            if (v.getId() == R.id.alarm_list)
            {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;

                menu.setHeaderTitle(AlarmListAdapter.getItem(info.position).getTitle());
                menu.add(Menu.NONE, CONTEXT_MENU_EDIT, Menu.NONE, "Edit");
                menu.add(Menu.NONE, CONTEXT_MENU_DELETE, Menu.NONE, "Delete");
                menu.add(Menu.NONE, CONTEXT_MENU_DUPLICATE, Menu.NONE, "Duplicate");
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public boolean onContextItemSelected(MenuItem item)
        {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
            int index = item.getItemId();

            if (index == CONTEXT_MENU_EDIT)
            {
                Intent intent = new Intent(getBaseContext(), AddAlarm.class);

                CurrentAlarm = AlarmListAdapter.getItem(info.position);
                CurrentAlarm.toIntent(intent);
                startActivityForResult(intent, EDIT_ALARM_ACTIVITY);
            }
            else if (index == CONTEXT_MENU_DELETE)
            {
                AlarmListAdapter.delete(info.position);
            }
            else if (index == CONTEXT_MENU_DUPLICATE)
            {
                AlarmHelper alarm = AlarmListAdapter.getItem(info.position);
                AlarmHelper newAlarm = new AlarmHelper(this);
                Intent intent = new Intent();

                alarm.toIntent(intent);
                newAlarm.fromIntent(intent);
                newAlarm.setTitle(alarm.getTitle() + " (copy)");
                AlarmListAdapter.add(newAlarm);
            }

            return true;
        }

        private AdapterView.OnItemClickListener mListOnItemClickListener = new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(getBaseContext(), AddAlarm.class);

                CurrentAlarm = AlarmListAdapter.getItem(position);
                CurrentAlarm.toIntent(intent);
                AlarmRecordsActivity.this.startActivityForResult(intent, EDIT_ALARM_ACTIVITY);
            }
        };

    }

